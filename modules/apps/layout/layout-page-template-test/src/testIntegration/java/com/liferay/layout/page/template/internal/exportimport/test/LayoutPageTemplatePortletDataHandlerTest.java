/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.internal.exportimport.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactoryUtil;
import com.liferay.exportimport.kernel.configuration.constants.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService;
import com.liferay.exportimport.kernel.service.ExportImportLocalService;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.page.template.constants.LayoutPageTemplateCollectionTypeConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.File;

import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

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
	}

	@Test
	public void testExportLayoutPageTemplateCollectionsDeletions()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		LayoutPageTemplateCollection basicLayoutPageTemplateCollection =
			_layoutPageTemplateCollectionLocalService.
				addLayoutPageTemplateCollection(
					null, TestPropsValues.getUserId(), _group.getGroupId(),
					LayoutPageTemplateConstants.
						PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
					null, RandomTestUtil.randomString(),
					RandomTestUtil.randomString(),
					LayoutPageTemplateCollectionTypeConstants.BASIC,
					serviceContext);
		LayoutPageTemplateCollection displayPageLayoutPageTemplateCollection =
			_layoutPageTemplateCollectionLocalService.
				addLayoutPageTemplateCollection(
					null, TestPropsValues.getUserId(), _group.getGroupId(),
					LayoutPageTemplateConstants.
						PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
					null, RandomTestUtil.randomString(),
					RandomTestUtil.randomString(),
					LayoutPageTemplateCollectionTypeConstants.DISPLAY_PAGE,
					serviceContext);

		_layoutPageTemplateCollectionLocalService.
			deleteLayoutPageTemplateCollection(
				basicLayoutPageTemplateCollection);
		_layoutPageTemplateCollectionLocalService.
			deleteLayoutPageTemplateCollection(
				displayPageLayoutPageTemplateCollection);

		File larFile = _exportImportLocalService.exportLayoutsAsFile(
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					TestPropsValues.getUserId(),
					ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
					ExportImportConfigurationSettingsMapFactoryUtil.
						buildExportLayoutSettingsMap(
							TestPropsValues.getUser(), _group.getGroupId(),
							false, new long[0],
							HashMapBuilder.put(
								PortletDataHandlerKeys.DELETIONS,
								new String[] {Boolean.TRUE.toString()}
							).put(
								PortletDataHandlerKeys.PORTLET_DATA,
								new String[] {Boolean.TRUE.toString()}
							).put(
								PortletDataHandlerKeys.PORTLET_DATA + "_" +
									LayoutAdminPortletKeys.GROUP_PAGES,
								new String[] {Boolean.TRUE.toString()}
							).build())));

		Assert.assertEquals(
			JSONFactoryUtil.createJSONArray(
			).put(
				JSONUtil.put(
					"externalReferenceCode",
					basicLayoutPageTemplateCollection.
						getExternalReferenceCode())
			).toString(),
			_getExportedJSONArray(
				StringBundler.concat(
					LayoutPageTemplateCollection.class.getName(), "#",
					LayoutPageTemplateCollectionTypeConstants.BASIC,
					"_deletions"),
				_group.getGroupId(), larFile
			).toString());
		Assert.assertEquals(
			JSONFactoryUtil.createJSONArray(
			).put(
				JSONUtil.put(
					"externalReferenceCode",
					displayPageLayoutPageTemplateCollection.
						getExternalReferenceCode())
			).toString(),
			_getExportedJSONArray(
				StringBundler.concat(
					LayoutPageTemplateCollection.class.getName(), "#",
					LayoutPageTemplateCollectionTypeConstants.DISPLAY_PAGE,
					"_deletions"),
				_group.getGroupId(), larFile
			).toString());
	}

	@Test
	public void testExportLayoutPageTemplateEntriesDeletions()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			LayoutPageTemplateCollectionLocalServiceUtil.
				addLayoutPageTemplateCollection(
					null, TestPropsValues.getUserId(),
					serviceContext.getScopeGroupId(),
					LayoutPageTemplateConstants.
						PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
					null, RandomTestUtil.randomString(),
					RandomTestUtil.randomString(),
					LayoutPageTemplateCollectionTypeConstants.BASIC,
					serviceContext);

		LayoutPageTemplateEntry basicLayoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_group.getGroupId(),
				layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId(),
				null, RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.BASIC, 0,
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		LayoutPageTemplateEntry displayPageLayoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_group.getGroupId(),
				LayoutPageTemplateConstants.
					PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
				null, RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE, 0,
				WorkflowConstants.STATUS_APPROVED, serviceContext);
		LayoutPageTemplateEntry masterLayoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				RandomTestUtil.randomString(), TestPropsValues.getUserId(),
				_group.getGroupId(),
				LayoutPageTemplateConstants.
					PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
				null, RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.MASTER_LAYOUT, 0,
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		_layoutPageTemplateEntryLocalService.deleteLayoutPageTemplateEntry(
			basicLayoutPageTemplateEntry);
		_layoutPageTemplateEntryLocalService.deleteLayoutPageTemplateEntry(
			displayPageLayoutPageTemplateEntry);
		_layoutPageTemplateEntryLocalService.deleteLayoutPageTemplateEntry(
			masterLayoutPageTemplateEntry);

		File larFile = _exportImportLocalService.exportLayoutsAsFile(
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					TestPropsValues.getUserId(),
					ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
					ExportImportConfigurationSettingsMapFactoryUtil.
						buildExportLayoutSettingsMap(
							TestPropsValues.getUser(), _group.getGroupId(),
							false, new long[0],
							HashMapBuilder.put(
								PortletDataHandlerKeys.DELETIONS,
								new String[] {Boolean.TRUE.toString()}
							).put(
								PortletDataHandlerKeys.PORTLET_DATA,
								new String[] {Boolean.TRUE.toString()}
							).put(
								PortletDataHandlerKeys.PORTLET_DATA + "_" +
									LayoutAdminPortletKeys.GROUP_PAGES,
								new String[] {Boolean.TRUE.toString()}
							).build())));

		Assert.assertEquals(
			JSONFactoryUtil.createJSONArray(
			).put(
				JSONUtil.put(
					"externalReferenceCode",
					basicLayoutPageTemplateEntry.getExternalReferenceCode())
			).toString(),
			_getExportedJSONArray(
				StringBundler.concat(
					LayoutPageTemplateEntry.class.getName(), "#",
					LayoutPageTemplateEntryTypeConstants.BASIC, "_deletions"),
				_group.getGroupId(), larFile
			).toString());

		Assert.assertEquals(
			JSONFactoryUtil.createJSONArray(
			).put(
				JSONUtil.put(
					"externalReferenceCode",
					displayPageLayoutPageTemplateEntry.
						getExternalReferenceCode())
			).toString(),
			_getExportedJSONArray(
				StringBundler.concat(
					LayoutPageTemplateEntry.class.getName(), "#",
					LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE,
					"_deletions"),
				_group.getGroupId(), larFile
			).toString());
		Assert.assertEquals(
			JSONFactoryUtil.createJSONArray(
			).put(
				JSONUtil.put(
					"externalReferenceCode",
					masterLayoutPageTemplateEntry.getExternalReferenceCode())
			).toString(),
			_getExportedJSONArray(
				StringBundler.concat(
					LayoutPageTemplateEntry.class.getName(), "#",
					LayoutPageTemplateEntryTypeConstants.MASTER_LAYOUT,
					"_deletions"),
				_group.getGroupId(), larFile
			).toString());
	}

	private JSONArray _getExportedJSONArray(
			String fileNamePrefix, long groupId, File larFile)
		throws Exception {

		try (ZipFile zipFile = new ZipFile(larFile)) {
			ZipEntry zipEntry = zipFile.getEntry(
				StringBundler.concat(
					"group/", groupId, StringPool.FORWARD_SLASH, fileNamePrefix,
					".json"));

			return JSONFactoryUtil.createJSONArray(
				StringUtil.read(zipFile.getInputStream(zipEntry)));
		}
	}

	@Inject
	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;

	@Inject
	private ExportImportLocalService _exportImportLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutPageTemplateCollectionLocalService
		_layoutPageTemplateCollectionLocalService;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

}