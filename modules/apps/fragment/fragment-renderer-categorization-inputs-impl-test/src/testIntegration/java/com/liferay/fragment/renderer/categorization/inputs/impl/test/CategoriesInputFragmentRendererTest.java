/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.renderer.categorization.inputs.impl.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.test.util.AssetTestUtil;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class CategoriesInputFragmentRendererTest
	extends BaseInputFragmentRendererTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Override
	public ObjectEntry addObjectEntry() throws Exception {
		return _objectEntryLocalService.addObjectEntry(
			TestPropsValues.getUserId(), group.getGroupId(),
			objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				"firstName", RandomTestUtil.randomString()
			).build(),
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId(),
				new long[] {_assetCategory.getCategoryId()}));
	}

	@Override
	public void assertRender(
		int expectedResult, HttpServletRequest httpServletRequest) {

		Map<String, Object> data =
			(Map<String, Object>)httpServletRequest.getAttribute(
				"liferay-asset:asset-categories-selector:data");

		List<Map<String, Object>> vocabularies =
			(List<Map<String, Object>>)data.get("vocabularies");

		Map<String, Object> vocabulary = vocabularies.get(0);

		List<Map<String, Object>> selectedItems =
			(List<Map<String, Object>>)vocabulary.get("selectedItems");

		if (expectedResult <= 0) {
			Assert.assertNull(selectedItems);

			return;
		}

		Assert.assertEquals(
			selectedItems.toString(), expectedResult, selectedItems.size());

		Map<String, Object> selectedItem = selectedItems.get(0);

		Assert.assertEquals(
			String.valueOf(_assetCategory.getCategoryId()),
			selectedItem.get("value"));
	}

	@Override
	public FragmentRenderer getFragmentRenderer() {
		return _categoriesInputFragmentRenderer;
	}

	@Override
	public String getRenderKey() {
		return "com.liferay.fragment.renderer.categorization.inputs.internal." +
			"CategoriesInputFragmentRenderer";
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		AssetVocabulary assetVocabulary = AssetTestUtil.addVocabulary(
			group.getGroupId());

		_assetCategory = AssetTestUtil.addCategory(
			group.getGroupId(), assetVocabulary.getVocabularyId());
	}

	private AssetCategory _assetCategory;

	@Inject(
		filter = "component.name=com.liferay.fragment.renderer.categorization.inputs.internal.CategoriesInputFragmentRenderer",
		type = FragmentRenderer.class
	)
	private FragmentRenderer _categoriesInputFragmentRenderer;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

}