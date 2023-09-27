/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.exportimport.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactory;
import com.liferay.exportimport.kernel.configuration.constants.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService;
import com.liferay.exportimport.kernel.service.ExportImportLocalService;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.layout.friendly.url.LayoutFriendlyURLEntryHelper;
import com.liferay.layout.page.template.constants.LayoutPageTemplateConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.test.constants.LayoutPortletKeys;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutPrototypeLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.sites.kernel.util.Sites;

import java.io.File;
import java.io.Serializable;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
public class ExportImportLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testExportImportLayoutsCreatedFromLayoutPrototypeWithSameName()
		throws Exception {

		Group group1 = GroupTestUtil.addGroup();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group1, TestPropsValues.getUserId());

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionLocalService.
				addLayoutPageTemplateCollection(
					TestPropsValues.getUserId(), group1.getGroupId(),
					LayoutPageTemplateConstants.
						PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
					RandomTestUtil.randomString(), StringPool.BLANK,
					LayoutPageTemplateEntryTypeConstants.TYPE_BASIC,
					serviceContext);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId(),
				RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.TYPE_WIDGET_PAGE, 0,
				WorkflowConstants.STATUS_APPROVED, serviceContext);

		Layout templateLayout = LayoutLocalServiceUtil.getLayout(
			layoutPageTemplateEntry.getPlid());

		LayoutTestUtil.addPortletToLayout(
			templateLayout, LayoutPortletKeys.LAYOUT_TEST_PORTLET);

		LayoutPrototype layoutPrototype =
			LayoutPrototypeLocalServiceUtil.getLayoutPrototype(
				layoutPageTemplateEntry.getLayoutPrototypeId());

		serviceContext.setAttribute(
			"layoutPrototypeUuid", layoutPrototype.getUuid());

		String layoutName = RandomTestUtil.randomString();

		_addLayoutFromLayoutPrototypeAndChangeFriendlyURL(
			group1, layoutName, serviceContext);

		_addLayoutFromLayoutPrototypeAndChangeFriendlyURL(
			group1, layoutName, serviceContext);

		long[] layoutIds = ListUtil.toLongArray(
			_layoutLocalService.getLayouts(group1.getGroupId(), false),
			Layout::getLayoutId);

		Map<String, Serializable> exportLayoutSettingsMap =
			_exportImportConfigurationSettingsMapFactory.
				buildExportLayoutSettingsMap(
					TestPropsValues.getUser(), group1.getGroupId(), false,
					layoutIds, _getExportParameterMap());

		_exportImportConfiguration =
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					TestPropsValues.getUserId(), RandomTestUtil.randomString(),
					ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
					exportLayoutSettingsMap);

		File file = _exportImportLocalService.exportLayoutsAsFile(
			_exportImportConfiguration);

		GroupTestUtil.deleteGroup(group1);

		Group group2 = GroupTestUtil.addGroup();

		try {
			Map<String, Serializable> importLayoutSettingsMap =
				_exportImportConfigurationSettingsMapFactory.
					buildImportLayoutSettingsMap(
						TestPropsValues.getUser(), group2.getGroupId(), false,
						layoutIds, _getImportParameterMap());

			_exportImportConfiguration =
				_exportImportConfigurationLocalService.
					addDraftExportImportConfiguration(
						TestPropsValues.getUserId(),
						RandomTestUtil.randomString(),
						ExportImportConfigurationConstants.TYPE_IMPORT_LAYOUT,
						importLayoutSettingsMap);

			_exportImportLocalService.importLayouts(
				_exportImportConfiguration, file);

			for (long layoutId : layoutIds) {
				Assert.assertNotNull(
					_layoutLocalService.getLayout(
						group2.getGroupId(), false, layoutId));
			}
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	private Layout _addLayoutFromLayoutPrototypeAndChangeFriendlyURL(
			Group group, String name, ServiceContext serviceContext)
		throws Exception {

		Layout layout = _layoutLocalService.addLayout(
			TestPropsValues.getUserId(), group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			HashMapBuilder.put(
				_portal.getSiteDefaultLocale(group.getGroupId()), name
			).build(),
			Collections.emptyMap(), Collections.emptyMap(),
			Collections.emptyMap(), Collections.emptyMap(),
			LayoutConstants.TYPE_PORTLET, StringPool.BLANK, false,
			Collections.emptyMap(), serviceContext);

		_sites.mergeLayoutPrototypeLayout(layout.getGroup(), layout);

		String newFriendlyURL = FriendlyURLNormalizerUtil.normalize(
			RandomTestUtil.randomString());

		layout = _layoutLocalService.updateFriendlyURL(
			layout.getUserId(), layout.getPlid(),
			StringPool.SLASH + newFriendlyURL, layout.getDefaultLanguageId());

		long classNameId = _layoutFriendlyURLEntryHelper.getClassNameId(false);

		String oldFriendlyURL = FriendlyURLNormalizerUtil.normalize(name);

		FriendlyURLEntry oldFriendlyURLEntry =
			_friendlyURLEntryLocalService.fetchFriendlyURLEntry(
				layout.getGroupId(), classNameId,
				StringPool.SLASH + oldFriendlyURL);

		Assert.assertNotNull(oldFriendlyURLEntry);

		_friendlyURLEntryLocalService.deleteFriendlyURLLocalizationEntry(
			oldFriendlyURLEntry.getFriendlyURLEntryId(),
			layout.getDefaultLanguageId());
		_friendlyURLEntryLocalService.deleteFriendlyURLEntry(
			oldFriendlyURLEntry);

		List<FriendlyURLEntry> friendlyURLEntries =
			_friendlyURLEntryLocalService.getFriendlyURLEntries(
				layout.getGroupId(), classNameId, layout.getPlid());

		Assert.assertEquals(
			friendlyURLEntries.toString(), 1, friendlyURLEntries.size());

		return layout;
	}

	private Map<String, String[]> _getExportParameterMap() throws Exception {
		return HashMapBuilder.put(
			Constants.CMD, new String[] {Constants.EXPORT}
		).put(
			PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_SETTINGS,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.LAYOUT_SET_SETTINGS,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_SETUP_ALL,
			new String[] {Boolean.TRUE.toString()}
		).put(
			"_page-templates_page-template-sets",
			new String[] {Boolean.TRUE.toString()}
		).put(
			"_page-templates_page-template-setsDisplay",
			new String[] {Boolean.TRUE.toString()}
		).put(
			"_page-templates_page-templates",
			new String[] {Boolean.TRUE.toString()}
		).build();
	}

	private Map<String, String[]> _getImportParameterMap() throws Exception {
		return HashMapBuilder.put(
			Constants.CMD, new String[] {Constants.IMPORT}
		).put(
			PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_SETTINGS,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.LAYOUT_SET_SETTINGS,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_SETUP_ALL,
			new String[] {Boolean.TRUE.toString()}
		).put(
			"_page-templates_page-template-sets",
			new String[] {Boolean.FALSE.toString()}
		).put(
			"_page-templates_page-templates",
			new String[] {Boolean.TRUE.toString()}
		).build();
	}

	@Inject
	private static FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	private ExportImportConfiguration _exportImportConfiguration;

	@Inject
	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;

	@Inject
	private ExportImportConfigurationSettingsMapFactory
		_exportImportConfigurationSettingsMapFactory;

	@Inject
	private ExportImportLocalService _exportImportLocalService;

	@Inject
	private LayoutFriendlyURLEntryHelper _layoutFriendlyURLEntryHelper;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateCollectionLocalService
		_layoutPageTemplateCollectionLocalService;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private Portal _portal;

	@Inject
	private Sites _sites;

}