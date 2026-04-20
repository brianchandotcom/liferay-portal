/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.internal.exportimport.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactoryUtil;
import com.liferay.exportimport.kernel.configuration.constants.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService;
import com.liferay.exportimport.kernel.service.ExportImportLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.page.template.constants.LayoutPageTemplateCollectionTypeConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.File;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Daniel Raposo
 */
@RunWith(Arquillian.class)
public class LayoutPageTemplatePortletDataHandlerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_importedGroup = GroupTestUtil.addGroup();
	}

	@Test
	public void testExportImportLayoutPageTemplateCollectionsDeletions()
		throws Exception {

		LayoutPageTemplateCollection basicLayoutPageTemplateCollection1 =
			_addLayoutPageTemplateCollection(
				LayoutPageTemplateCollectionTypeConstants.BASIC);
		LayoutPageTemplateCollection basicLayoutPageTemplateCollection2 =
			_addLayoutPageTemplateCollection(
				LayoutPageTemplateCollectionTypeConstants.BASIC);
		LayoutPageTemplateCollection displayPageLayoutPageTemplateCollection1 =
			_addLayoutPageTemplateCollection(
				LayoutPageTemplateCollectionTypeConstants.DISPLAY_PAGE);
		LayoutPageTemplateCollection displayPageLayoutPageTemplateCollection2 =
			_addLayoutPageTemplateCollection(
				LayoutPageTemplateCollectionTypeConstants.DISPLAY_PAGE);

		_importLayouts(
			false, _importedGroup.getGroupId(),
			_exportLayouts(false, _group.getGroupId()));

		_assertLayoutPageTemplateCollectionNotNull(
			basicLayoutPageTemplateCollection1);
		_assertLayoutPageTemplateCollectionNotNull(
			basicLayoutPageTemplateCollection2);
		_assertLayoutPageTemplateCollectionNotNull(
			displayPageLayoutPageTemplateCollection1);
		_assertLayoutPageTemplateCollectionNotNull(
			displayPageLayoutPageTemplateCollection2);

		_layoutPageTemplateCollectionLocalService.
			deleteLayoutPageTemplateCollection(
				basicLayoutPageTemplateCollection2);
		_layoutPageTemplateCollectionLocalService.
			deleteLayoutPageTemplateCollection(
				displayPageLayoutPageTemplateCollection2);

		_importLayouts(
			true, _importedGroup.getGroupId(),
			_exportLayouts(true, _group.getGroupId()));

		_assertLayoutPageTemplateCollectionNotNull(
			basicLayoutPageTemplateCollection1);
		_assertLayoutPageTemplateCollectionNotNull(
			displayPageLayoutPageTemplateCollection1);

		_assertLayoutPageTemplateCollectionNull(
			basicLayoutPageTemplateCollection2);
		_assertLayoutPageTemplateCollectionNull(
			displayPageLayoutPageTemplateCollection2);
	}

	@Test
	public void testExportImportLayoutPageTemplateEntriesDeletions()
		throws Exception {

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_addLayoutPageTemplateCollection(
				LayoutPageTemplateCollectionTypeConstants.BASIC);

		LayoutPageTemplateEntry basicLayoutPageTemplateEntry1 =
			_addLayoutPageTemplateEntry(
				0,
				layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId(),
				LayoutPageTemplateEntryTypeConstants.BASIC);
		LayoutPageTemplateEntry basicLayoutPageTemplateEntry2 =
			_addLayoutPageTemplateEntry(
				0,
				layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId(),
				LayoutPageTemplateEntryTypeConstants.BASIC);

		long journalArticleClassNameId = _portal.getClassNameId(
			JournalArticle.class.getName());

		LayoutPageTemplateEntry displayPageLayoutPageTemplateEntry1 =
			_addLayoutPageTemplateEntry(
				journalArticleClassNameId,
				LayoutPageTemplateConstants.
					PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
				LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE);
		LayoutPageTemplateEntry displayPageLayoutPageTemplateEntry2 =
			_addLayoutPageTemplateEntry(
				journalArticleClassNameId,
				LayoutPageTemplateConstants.
					PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
				LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE);

		LayoutPageTemplateEntry masterLayoutPageTemplateEntry1 =
			_addLayoutPageTemplateEntry(
				0,
				LayoutPageTemplateConstants.
					PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
				LayoutPageTemplateEntryTypeConstants.MASTER_LAYOUT);
		LayoutPageTemplateEntry masterLayoutPageTemplateEntry2 =
			_addLayoutPageTemplateEntry(
				0,
				LayoutPageTemplateConstants.
					PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
				LayoutPageTemplateEntryTypeConstants.MASTER_LAYOUT);

		_importLayouts(
			false, _importedGroup.getGroupId(),
			_exportLayouts(false, _group.getGroupId()));

		_assertLayoutPageTemplateEntryNotNull(basicLayoutPageTemplateEntry1);
		_assertLayoutPageTemplateEntryNotNull(basicLayoutPageTemplateEntry2);
		_assertLayoutPageTemplateEntryNotNull(
			displayPageLayoutPageTemplateEntry1);
		_assertLayoutPageTemplateEntryNotNull(
			displayPageLayoutPageTemplateEntry2);
		_assertLayoutPageTemplateEntryNotNull(masterLayoutPageTemplateEntry1);
		_assertLayoutPageTemplateEntryNotNull(masterLayoutPageTemplateEntry2);

		_layoutPageTemplateEntryLocalService.deleteLayoutPageTemplateEntry(
			basicLayoutPageTemplateEntry2);
		_layoutPageTemplateEntryLocalService.deleteLayoutPageTemplateEntry(
			displayPageLayoutPageTemplateEntry2);
		_layoutPageTemplateEntryLocalService.deleteLayoutPageTemplateEntry(
			masterLayoutPageTemplateEntry2);

		_importLayouts(
			true, _importedGroup.getGroupId(),
			_exportLayouts(true, _group.getGroupId()));

		_assertLayoutPageTemplateEntryNotNull(basicLayoutPageTemplateEntry1);
		_assertLayoutPageTemplateEntryNotNull(
			displayPageLayoutPageTemplateEntry1);
		_assertLayoutPageTemplateEntryNotNull(masterLayoutPageTemplateEntry1);

		_assertLayoutPageTemplateEntryNull(basicLayoutPageTemplateEntry2);
		_assertLayoutPageTemplateEntryNull(displayPageLayoutPageTemplateEntry2);
		_assertLayoutPageTemplateEntryNull(masterLayoutPageTemplateEntry2);
	}

	private LayoutPageTemplateCollection _addLayoutPageTemplateCollection(
			int type)
		throws Exception {

		return _layoutPageTemplateCollectionLocalService.
			addLayoutPageTemplateCollection(
				null, TestPropsValues.getUserId(), _group.getGroupId(),
				LayoutPageTemplateConstants.
					PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
				null, RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), type,
				ServiceContextTestUtil.getServiceContext(
					_group.getGroupId(), TestPropsValues.getUserId()));
	}

	private LayoutPageTemplateEntry _addLayoutPageTemplateEntry(
			long classNameId, long layoutPageTemplateCollectionId, int type)
		throws Exception {

		return _layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			_group.getGroupId(), layoutPageTemplateCollectionId, null,
			classNameId, null, RandomTestUtil.randomString(), type, 0, false, 0,
			0, 0, WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));
	}

	private void _assertLayoutPageTemplateCollectionNotNull(
		LayoutPageTemplateCollection layoutPageTemplateCollection) {

		Assert.assertNotNull(
			_layoutPageTemplateCollectionLocalService.
				fetchLayoutPageTemplateCollectionByExternalReferenceCode(
					layoutPageTemplateCollection.getExternalReferenceCode(),
					_importedGroup.getGroupId()));
	}

	private void _assertLayoutPageTemplateCollectionNull(
		LayoutPageTemplateCollection layoutPageTemplateCollection) {

		Assert.assertNull(
			_layoutPageTemplateCollectionLocalService.
				fetchLayoutPageTemplateCollectionByExternalReferenceCode(
					layoutPageTemplateCollection.getExternalReferenceCode(),
					_importedGroup.getGroupId()));
	}

	private void _assertLayoutPageTemplateEntryNotNull(
		LayoutPageTemplateEntry layoutPageTemplateEntry) {

		Assert.assertNotNull(
			_layoutPageTemplateEntryLocalService.
				fetchLayoutPageTemplateEntryByExternalReferenceCode(
					layoutPageTemplateEntry.getExternalReferenceCode(),
					_importedGroup.getGroupId()));
	}

	private void _assertLayoutPageTemplateEntryNull(
		LayoutPageTemplateEntry layoutPageTemplateEntry) {

		Assert.assertNull(
			_layoutPageTemplateEntryLocalService.
				fetchLayoutPageTemplateEntryByExternalReferenceCode(
					layoutPageTemplateEntry.getExternalReferenceCode(),
					_importedGroup.getGroupId()));
	}

	private File _exportLayouts(boolean deletions, long groupId)
		throws Exception {

		return _exportImportLocalService.exportLayoutsAsFile(
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					TestPropsValues.getUserId(),
					ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
					ExportImportConfigurationSettingsMapFactoryUtil.
						buildExportLayoutSettingsMap(
							TestPropsValues.getUser(), groupId, false,
							new long[0], _getParameterMap(deletions))));
	}

	private Map<String, String[]> _getParameterMap(boolean deletions) {
		return HashMapBuilder.put(
			PortletDataHandlerKeys.DELETIONS,
			new String[] {Boolean.toString(deletions)}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA + "_" +
				LayoutAdminPortletKeys.GROUP_PAGES,
			new String[] {Boolean.TRUE.toString()}
		).build();
	}

	private void _importLayouts(boolean deletions, long groupId, File larFile)
		throws Exception {

		ExportImportConfiguration exportImportConfiguration =
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					TestPropsValues.getUserId(),
					ExportImportConfigurationConstants.TYPE_IMPORT_LAYOUT,
					ExportImportConfigurationSettingsMapFactoryUtil.
						buildImportLayoutSettingsMap(
							TestPropsValues.getUser(), groupId, false,
							new long[0], _getParameterMap(deletions)));

		if (deletions) {
			_exportImportLocalService.importLayoutsDataDeletions(
				exportImportConfiguration, larFile);
		}

		_exportImportLocalService.importLayouts(
			exportImportConfiguration, larFile);
	}

	@Inject
	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;

	@Inject
	private ExportImportLocalService _exportImportLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private Group _importedGroup;

	@Inject
	private LayoutPageTemplateCollectionLocalService
		_layoutPageTemplateCollectionLocalService;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private Portal _portal;

}