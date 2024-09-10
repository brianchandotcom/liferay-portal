/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.exportimport.portlet.preferences.processor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.asset.test.util.AssetTestUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.portlet.preferences.processor.ExportImportPortletPreferencesProcessor;
import com.liferay.exportimport.test.util.ExportImportTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.test.util.exportimport.BaseExportImportPortletPreferencesProcessorTestCase;
import com.liferay.portal.search.web.internal.category.facet.constants.CategoryFacetPortletKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.HashMap;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Gustavo Lima
 * @author Joshua Cords
 */
@RunWith(Arquillian.class)
public class CategoryFacetExportImportPortletPreferencesProcessorTest
	extends BaseExportImportPortletPreferencesProcessorTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		UserTestUtil.setUser(TestPropsValues.getUser());

		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addTypePortletLayout(_group.getGroupId());

		LayoutTestUtil.addPortletToLayout(
			TestPropsValues.getUserId(), _layout,
			CategoryFacetPortletKeys.CATEGORY_FACET, "column-1",
			new HashMap<String, String[]>());

		_portletDataContextExport =
			ExportImportTestUtil.getExportPortletDataContext(
				_group.getGroupId());

		_portletDataContextExport.setPlid(_layout.getPlid());
		_portletDataContextExport.setPortletId(
			CategoryFacetPortletKeys.CATEGORY_FACET);

		_portletDataContextImport =
			ExportImportTestUtil.getImportPortletDataContext(
				_group.getGroupId());

		_portletDataContextImport.setPlid(_layout.getPlid());
		_portletDataContextImport.setPortletId(
			CategoryFacetPortletKeys.CATEGORY_FACET);

		_portletPreferences =
			PortletPreferencesFactoryUtil.getStrictPortletSetup(
				_layout, CategoryFacetPortletKeys.CATEGORY_FACET);

		_portletPreferences.setValue("selectionStyle", "manual");
	}

	@Test
	public void testProcessAssetVocabularyId() throws Exception {
		AssetVocabulary exportedAssetVocabulary = AssetTestUtil.addVocabulary(
			_group.getGroupId());

		_setCategoryFacetPortletPreferences(exportedAssetVocabulary);

		PortletPreferences exportedPortletPreferences =
			_exportImportPortletPreferencesProcessor.
				processExportPortletPreferences(
					_portletDataContextExport, _portletPreferences);

		String exportedAssetVocabularyId = exportedPortletPreferences.getValue(
			"vocabularyIds", "");

		Assert.assertEquals(
			StringBundler.concat(
				exportedAssetVocabulary.getExternalReferenceCode(),
				StringPool.POUND, _group.getGroupId(), StringPool.POUND,
				_group.getExternalReferenceCode()),
			exportedAssetVocabularyId);

		AssetVocabulary importedAssetVocabulary = _addImportedAssetVocabulary(
			exportedAssetVocabulary, exportedAssetVocabularyId);

		PortletPreferences importedPortletPreferences =
			_exportImportPortletPreferencesProcessor.
				processImportPortletPreferences(
					_portletDataContextImport, exportedPortletPreferences);

		String importedVocabularyId = importedPortletPreferences.getValue(
			"vocabularyIds", "");

		Assert.assertEquals(
			importedAssetVocabulary.getVocabularyId(),
			GetterUtil.getLong(importedVocabularyId));
	}

	@Override
	protected ExportImportPortletPreferencesProcessor
		getExportImportPortletPreferencesProcessor() {

		return _exportImportPortletPreferencesProcessor;
	}

	private AssetVocabulary _addImportedAssetVocabulary(
			AssetVocabulary assetVocabulary, String exportedAssetVocabularyId)
		throws Exception {

		AssetVocabularyLocalServiceUtil.deleteVocabulary(
			assetVocabulary.getVocabularyId());

		assetVocabulary = AssetTestUtil.addVocabulary(_group.getGroupId());

		assetVocabulary.setExternalReferenceCode(
			exportedAssetVocabularyId.substring(
				0, exportedAssetVocabularyId.indexOf(CharPool.POUND)));

		return AssetVocabularyLocalServiceUtil.updateAssetVocabulary(
			assetVocabulary);
	}

	private void _setCategoryFacetPortletPreferences(
			AssetVocabulary assetVocabulary)
		throws Exception {

		_portletPreferences.setValue(
			"vocabularyIds", String.valueOf(assetVocabulary.getVocabularyId()));

		_portletPreferences.store();
	}

	@Inject(
		filter = "javax.portlet.name=" + CategoryFacetPortletKeys.CATEGORY_FACET
	)
	private ExportImportPortletPreferencesProcessor
		_exportImportPortletPreferencesProcessor;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;
	private PortletDataContext _portletDataContextExport;
	private PortletDataContext _portletDataContextImport;
	private PortletPreferences _portletPreferences;

}