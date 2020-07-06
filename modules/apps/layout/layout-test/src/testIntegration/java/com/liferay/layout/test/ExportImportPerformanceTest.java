/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.layout.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactory;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService;
import com.liferay.exportimport.kernel.service.ExportImportLocalService;
import com.liferay.exportimport.kernel.service.StagingLocalService;
import com.liferay.exportimport.kernel.staging.MergeLayoutPrototypesThreadLocal;
import com.liferay.exportimport.kernel.staging.Staging;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.util.LayoutCopyHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.sites.kernel.util.SitesUtil;

import java.io.File;
import java.io.Serializable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import org.apache.commons.lang.time.StopWatch;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pavel Savinov
 */
@RunWith(Arquillian.class)
public class ExportImportPerformanceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		Class<?> clazz = ExportImportPerformanceTest.class;

		Properties properties = PropertiesUtil.load(
			clazz.getResourceAsStream("export-import-performance.properties"),
			"utf-8");

		_fragmentsPerPage = GetterUtil.getInteger(
			properties.getProperty("fragments.per.page"));
		_pagesCount = GetterUtil.getInteger(
			properties.getProperty("pages.count"));
		_pageType = properties.getProperty("page.type");
		_portletsPerContentPage = GetterUtil.getInteger(
			properties.getProperty("portlets.per.content.page"));
		_portletsPerPortletPage = GetterUtil.getInteger(
			properties.getProperty("portlets.per.portlet.page"));
	}

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_importGroup = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);

		_createLayouts();

		_layoutIds = ListUtil.toLongArray(
			_layoutLocalService.getLayouts(_group.getGroupId(), false),
			Layout::getLayoutId);

		_layoutSetPrototype = LayoutTestUtil.addLayoutSetPrototype(
			RandomTestUtil.randomString());
	}

	@Test
	public void testExportGroupToLAR() throws Exception {
		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		Map<String, Serializable> exportLayoutSettingsMap =
			_exportImportConfigurationSettingsMapFactory.
				buildExportLayoutSettingsMap(
					TestPropsValues.getUser(), _group.getGroupId(), false,
					_layoutIds, new HashMap<>());

		_exportImportConfiguration =
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					TestPropsValues.getUserId(), "export-group",
					ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
					exportLayoutSettingsMap);

		_exportImportLocalService.exportLayoutsAsFile(
			_exportImportConfiguration);

		stopWatch.stop();
	}

	@Test
	public void testImportGroupFromLAR() throws Exception {
		Map<String, Serializable> exportLayoutSettingsMap =
			_exportImportConfigurationSettingsMapFactory.
				buildExportLayoutSettingsMap(
					TestPropsValues.getUser(), _group.getGroupId(), false,
					_layoutIds, new HashMap<>());

		_exportImportConfiguration =
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					TestPropsValues.getUserId(), "export-group",
					ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
					exportLayoutSettingsMap);

		File file = _exportImportLocalService.exportLayoutsAsFile(
			_exportImportConfiguration);

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		Map<String, Serializable> importLayoutSettingsMap =
			_exportImportConfigurationSettingsMapFactory.
				buildImportLayoutSettingsMap(
					TestPropsValues.getUser(), _importGroup.getGroupId(), false,
					_layoutIds, new HashMap<>());

		_exportImportConfiguration =
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					TestPropsValues.getUserId(), "import-group",
					ExportImportConfigurationConstants.TYPE_IMPORT_LAYOUT,
					importLayoutSettingsMap);

		_exportImportLocalService.importLayouts(
			_exportImportConfiguration, file);

		stopWatch.stop();
	}

	@Test
	public void testInitialStagingPublication() throws Exception {
		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		_stagingLocalService.enableLocalStaging(
			TestPropsValues.getUserId(), _group, false, false, _serviceContext);

		stopWatch.stop();
	}

	@Test
	public void testSiteTemplatePropagation() throws Exception {
		MergeLayoutPrototypesThreadLocal.clearMergeComplete();

		for (long layoutId : _layoutIds) {
			Layout layout = _layoutLocalService.getLayout(
				_group.getGroupId(), false, layoutId);

			LayoutPrototype layoutPrototype = LayoutTestUtil.addLayoutPrototype(
				RandomTestUtil.randomString());

			Layout prototypeLayout = layoutPrototype.getLayout();

			prototypeLayout.setType(layout.getType());

			_layoutLocalService.updateLayout(prototypeLayout);
			_layoutCopyHelper.copyLayout(layout, prototypeLayout);

			layout.setLayoutPrototypeLinkEnabled(true);
			layout.setLayoutPrototypeUuid(layoutPrototype.getUuid());

			_layoutLocalService.updateLayout(layout);
		}

		SitesUtil.updateLayoutSetPrototypesLinks(
			_group, _layoutSetPrototype.getLayoutSetPrototypeId(), 0, true,
			true);

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		MergeLayoutPrototypesThreadLocal.clearMergeComplete();

		SitesUtil.mergeLayoutSetPrototypeLayouts(
			_group, _group.getPublicLayoutSet());

		stopWatch.stop();
	}

	@Test
	public void testStagingPublication() throws Exception {
		_stagingLocalService.enableLocalStaging(
			TestPropsValues.getUserId(), _group, false, false, _serviceContext);

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		Group stagingGroup = _group.getStagingGroup();

		Map<String, Serializable> stagingSettingsMap =
			_exportImportConfigurationSettingsMapFactory.
				buildPublishLayoutLocalSettingsMap(
					TestPropsValues.getUser(), stagingGroup.getGroupId(),
					_group.getGroupId(), false, _layoutIds, new HashMap<>());

		_exportImportConfiguration =
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					TestPropsValues.getUserId(), "publish-group",
					ExportImportConfigurationConstants.
						TYPE_PUBLISH_LAYOUT_LOCAL,
					stagingSettingsMap);

		long backgroundTaskId = _staging.publishLayouts(
			TestPropsValues.getUserId(), _exportImportConfiguration);

		BackgroundTask backgroundTask =
			_backgroundTaskManager.getBackgroundTask(backgroundTaskId);

		while (backgroundTask.isInProgress()) {
			backgroundTask = _backgroundTaskManager.getBackgroundTask(
				backgroundTaskId);

			Thread.sleep(1000);
		}

		stopWatch.stop();
	}

	private void _createFragments(Layout layout) throws Exception {
		Layout draftLayout = layout.fetchDraftLayout();

		if (draftLayout == null) {
			return;
		}

		for (int i = 0; i < _fragmentsPerPage; i++) {
			JournalArticle journalArticle1 = JournalTestUtil.addArticle(
				_group.getGroupId(),
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

			JournalArticle journalArticle2 = JournalTestUtil.addArticle(
				_group.getGroupId(),
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

			JournalArticle journalArticle3 = JournalTestUtil.addArticle(
				_group.getGroupId(),
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

			FragmentEntry fragmentEntry =
				_fragmentCollectionContributorTracker.getFragmentEntry(
					_FRAGMENT_RENDERER_KEY);

			Map<String, String> values = HashMapBuilder.put(
				"classNameId",
				String.valueOf(_portal.getClassNameId(JournalArticle.class))
			).put(
				"classPK_1",
				String.valueOf(journalArticle1.getResourcePrimKey())
			).put(
				"classPK_2",
				String.valueOf(journalArticle2.getResourcePrimKey())
			).put(
				"classPK_3",
				String.valueOf(journalArticle3.getResourcePrimKey())
			).build();

			String editableValues = StringUtil.replace(
				_FRAGMENT_EDITABLE_VALUES_TMPL, "${", "}", values);

			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(), 0,
				fragmentEntry.getFragmentEntryId(), 0, draftLayout.getPlid(),
				fragmentEntry.getCss(), fragmentEntry.getHtml(),
				fragmentEntry.getJs(), fragmentEntry.getConfiguration(),
				editableValues, StringPool.BLANK, 0, null, _serviceContext);
		}

		for (int i = 0; i < _portletsPerContentPage; i++) {
			String instanceId = PortletIdCodec.generateInstanceId();

			Map<String, String> values = HashMapBuilder.put(
				"instanceId", instanceId
			).put(
				"portletName", JournalPortletKeys.JOURNAL
			).build();

			String editableValues = StringUtil.replace(
				_FRAGMENT_PORTLET_TMPL, "${", "}", values);

			_savePortletPreferencesWithArticle(
				draftLayout,
				PortletIdCodec.encode(JournalPortletKeys.JOURNAL, instanceId));

			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				TestPropsValues.getUserId(), _group.getGroupId(), 0, 0, 0,
				draftLayout.getPlid(), StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK, StringPool.BLANK, editableValues,
				StringPool.BLANK, 0, null, _serviceContext);
		}

		_layoutPageTemplateStructureLocalService.
			rebuildLayoutPageTemplateStructure(
				_group.getGroupId(), draftLayout.getPlid());

		_layoutCopyHelper.copyLayout(draftLayout, layout);
	}

	private void _createLayouts() throws Exception {
		for (int i = 0; i < _pagesCount; i++) {
			Map<Locale, String> nameMap = HashMapBuilder.put(
				LocaleUtil.US, "Layout_" + i
			).build();

			Layout layout = _layoutLocalService.addLayout(
				TestPropsValues.getUserId(), _group.getGroupId(), false, 0, 0,
				0, nameMap, new HashMap<>(), new HashMap<>(), new HashMap<>(),
				new HashMap<>(), _pageType, StringPool.BLANK, false, false,
				new HashMap<>(), 0, _serviceContext);

			if (Objects.equals(_pageType, LayoutConstants.TYPE_CONTENT)) {
				_createFragments(layout);
			}
			else {
				_createPortlets(layout);
			}
		}
	}

	private void _createPortlets(Layout layout) throws Exception {
		UnicodeProperties unicodeProperties =
			layout.getTypeSettingsProperties();

		String columnSettings = unicodeProperties.getProperty("column-1");

		for (int i = 0; i < _portletsPerPortletPage; i++) {
			String portletId = PortletIdCodec.encode(
				JournalPortletKeys.JOURNAL,
				PortletIdCodec.generateInstanceId());

			_savePortletPreferencesWithArticle(layout, portletId);

			if (Validator.isNotNull(columnSettings)) {
				columnSettings += StringPool.COMMA;
			}

			columnSettings += portletId;
		}

		unicodeProperties.setProperty("column-1", columnSettings);

		layout.setTypeSettingsProperties(unicodeProperties);

		_layoutLocalService.updateLayout(layout);
	}

	private void _savePortletPreferencesWithArticle(
			Layout layout, String portletId)
		throws Exception {

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		AssetEntry assetEntry = _assetEntryLocalService.getEntry(
			JournalArticle.class.getName(),
			journalArticle.getResourcePrimKey());

		Map<String, String> values = HashMapBuilder.put(
			"articleId", journalArticle.getArticleId()
		).put(
			"assetEntryId", String.valueOf(assetEntry.getEntryId())
		).put(
			"groupId", String.valueOf(assetEntry.getGroupId())
		).build();

		String portletPreferencesXML = StringUtil.replace(
			_PORTLET_PREFERENCES_TMPL, "${", "}", values);

		PortletPreferencesFactoryUtil.getLayoutPortletSetup(
			layout, portletId, portletPreferencesXML);
	}

	private static final String _FRAGMENT_EDITABLE_VALUES_TMPL =
		StringUtil.read(
			ExportImportPerformanceTest.class, "fragment-editable-values.tmpl");

	private static final String _FRAGMENT_PORTLET_TMPL = StringUtil.read(
		ExportImportPerformanceTest.class, "fragment-portlet.tmpl");

	private static final String _FRAGMENT_RENDERER_KEY =
		"FEATURED_CONTENT-highlights-circle";

	private static final String _PORTLET_PREFERENCES_TMPL = StringUtil.read(
		ExportImportPerformanceTest.class, "portlet-preferences.tmpl");

	private static int _fragmentsPerPage;
	private static int _pagesCount;
	private static String _pageType;
	private static int _portletsPerContentPage;
	private static int _portletsPerPortletPage;

	@Inject
	private AssetEntryLocalService _assetEntryLocalService;

	@Inject
	private BackgroundTaskManager _backgroundTaskManager;

	@DeleteAfterTestRun
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
	private FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private Group _importGroup;

	@Inject
	private LayoutCopyHelper _layoutCopyHelper;

	private long[] _layoutIds;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@DeleteAfterTestRun
	private LayoutSetPrototype _layoutSetPrototype;

	@Inject
	private Portal _portal;

	private ServiceContext _serviceContext;

	@Inject
	private Staging _staging;

	@Inject
	private StagingLocalService _stagingLocalService;

}