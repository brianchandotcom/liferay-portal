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

package com.liferay.staging.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.lists.constants.DDLPortletKeys;
import com.liferay.dynamic.data.lists.constants.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.test.util.DDMFormInstanceTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationParameterMapFactoryUtil;
import com.liferay.exportimport.kernel.lar.ExportImportDateUtil;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.service.StagingLocalServiceUtil;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.journal.constants.JournalContentPortletKeys;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.staging.configuration.StagingConfiguration;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiNodeLocalService;
import com.liferay.wiki.service.WikiPageLocalService;
import com.liferay.wiki.test.util.WikiTestUtil;

import java.io.Serializable;

import java.util.Map;

import javax.portlet.PortletPreferences;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Tamas Molnar
 */
@RunWith(Arquillian.class)
@Sync(cleanTransaction = true)
public class StagingDataPortletPreferencesTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		ConfigurationTestUtil.saveConfiguration(
			StagingConfiguration.class.getName(),
			HashMapDictionaryBuilder.<String, Object>put(
				"publishDisplayedContent", false
			).build());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		ConfigurationTestUtil.deleteConfiguration(
			StagingConfiguration.class.getName());
	}

	@Before
	public void setUp() throws Exception {
		UserTestUtil.setUser(TestPropsValues.getUser());

		liveGroup = GroupTestUtil.addGroup();

		enableLocalStaging();

		stagingGroup = liveGroup.getStagingGroup();

		stagingLayout = LayoutTestUtil.addTypePortletLayout(stagingGroup);
	}

	@Test
	public void testDDLDisplayPortletPreferences() throws Exception {
		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			stagingGroup.getGroupId(), DDLRecordSet.class.getName());

		DDMTemplate displayDDMTemplate = DDMTemplateTestUtil.addTemplate(
			stagingGroup.getGroupId(), ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(DDLRecordSet.class));
		DDMTemplate formDDMTemplate = DDMTemplateTestUtil.addTemplate(
			stagingGroup.getGroupId(), ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(DDLRecordSet.class));

		DDLRecordSet ddlRecordSet = _ddlRecordSetLocalService.addRecordSet(
			TestPropsValues.getUserId(), stagingGroup.getGroupId(),
			ddmStructure.getStructureId(), null,
			HashMapBuilder.put(
				LocaleUtil.US, RandomTestUtil.randomString()
			).build(),
			null, DDLRecordSetConstants.MIN_DISPLAY_ROWS_DEFAULT,
			DDLRecordSetConstants.SCOPE_DYNAMIC_DATA_LISTS,
			ServiceContextTestUtil.getServiceContext(
				stagingGroup.getGroupId()));

		Map<String, String[]> preferenceMap = HashMapBuilder.put(
			"displayDDMTemplateId",
			new String[] {String.valueOf(displayDDMTemplate.getTemplateId())}
		).put(
			"formDDMTemplateId",
			new String[] {String.valueOf(formDDMTemplate.getTemplateId())}
		).put(
			"recordSetId",
			new String[] {String.valueOf(ddlRecordSet.getRecordSetId())}
		).build();

		String portletId = publishLayoutWithDisplayPortlet(
			DDLPortletKeys.DYNAMIC_DATA_LISTS_DISPLAY, preferenceMap, true);

		Assert.assertEquals(
			String.valueOf(displayDDMTemplate.getTemplateId()),
			livePortletPreferences.getValue(
				"displayDDMTemplateId", StringPool.BLANK));
		Assert.assertEquals(
			String.valueOf(formDDMTemplate.getTemplateId()),
			livePortletPreferences.getValue(
				"formDDMTemplateId", StringPool.BLANK));
		Assert.assertEquals(
			String.valueOf(ddlRecordSet.getRecordSetId()),
			livePortletPreferences.getValue("recordSetId", StringPool.BLANK));

		publishPortletData(DDLPortletKeys.DYNAMIC_DATA_LISTS);

		publishLayoutWithDisplayPortlet(portletId, preferenceMap, false);

		DDMTemplate liveDisplayDDMTemplate =
			_ddmTemplateLocalService.getDDMTemplateByUuidAndGroupId(
				displayDDMTemplate.getUuid(), liveGroup.getGroupId());
		DDMTemplate liveFormDDMTemplate =
			_ddmTemplateLocalService.getDDMTemplateByUuidAndGroupId(
				formDDMTemplate.getUuid(), liveGroup.getGroupId());

		DDLRecordSet liveDDLRecordSet =
			_ddlRecordSetLocalService.getDDLRecordSetByUuidAndGroupId(
				ddlRecordSet.getUuid(), liveGroup.getGroupId());

		Assert.assertEquals(
			String.valueOf(liveDisplayDDMTemplate.getTemplateId()),
			livePortletPreferences.getValue(
				"displayDDMTemplateId", StringPool.BLANK));
		Assert.assertEquals(
			String.valueOf(liveFormDDMTemplate.getTemplateId()),
			livePortletPreferences.getValue(
				"formDDMTemplateId", StringPool.BLANK));
		Assert.assertEquals(
			String.valueOf(liveDDLRecordSet.getRecordSetId()),
			livePortletPreferences.getValue("recordSetId", StringPool.BLANK));
	}

	@Test
	public void testDDMFormPortletPreferences() throws Exception {
		DDMFormInstance ddmFormInstance =
			DDMFormInstanceTestUtil.addDDMFormInstance(
				stagingGroup, TestPropsValues.getUserId());

		Map<String, String[]> preferenceMap = HashMapBuilder.put(
			"formInstanceId",
			new String[] {String.valueOf(ddmFormInstance.getFormInstanceId())}
		).put(
			"groupId", new String[] {String.valueOf(stagingGroup.getGroupId())}
		).build();

		String portletId = publishLayoutWithDisplayPortlet(
			DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM, preferenceMap, true);

		Assert.assertEquals(
			String.valueOf(ddmFormInstance.getFormInstanceId()),
			livePortletPreferences.getValue(
				"formInstanceId", StringPool.BLANK));

		publishPortletData(DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_ADMIN);

		publishLayoutWithDisplayPortlet(portletId, preferenceMap, false);

		DDMFormInstance liveDDMFormInstance =
			_ddmFormInstanceLocalService.getDDMFormInstanceByUuidAndGroupId(
				ddmFormInstance.getUuid(), liveGroup.getGroupId());

		Assert.assertEquals(
			String.valueOf(liveDDMFormInstance.getFormInstanceId()),
			livePortletPreferences.getValue(
				"formInstanceId", StringPool.BLANK));
	}

	@Test
	public void testJournalContentDataPortletPreferences() throws Exception {
		JournalArticle journalArticle = JournalTestUtil.addArticle(
			stagingGroup.getGroupId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		Map<String, String[]> preferenceMap = HashMapBuilder.put(
			"articleId",
			new String[] {String.valueOf(journalArticle.getArticleId())}
		).put(
			"groupId", new String[] {String.valueOf(stagingGroup.getGroupId())}
		).build();

		String portletId = publishLayoutWithDisplayPortlet(
			JournalContentPortletKeys.JOURNAL_CONTENT, preferenceMap, true);

		Assert.assertEquals(
			journalArticle.getArticleId(),
			livePortletPreferences.getValue("articleId", StringPool.BLANK));

		publishPortletData(JournalPortletKeys.JOURNAL);

		publishLayoutWithDisplayPortlet(portletId, preferenceMap, false);

		JournalArticle liveJournalArticle =
			_journalArticleLocalService.getJournalArticleByUuidAndGroupId(
				journalArticle.getUuid(), liveGroup.getGroupId());

		Assert.assertEquals(
			liveJournalArticle.getArticleId(),
			livePortletPreferences.getValue("articleId", StringPool.BLANK));
	}

	@Test
	public void testWikiDisplayDataPortletPreferences() throws Exception {
		WikiNode wikiNode = WikiTestUtil.addNode(stagingGroup.getGroupId());

		WikiPage wikiPage = WikiTestUtil.addPage(
			wikiNode.getGroupId(), wikiNode.getNodeId(), true);

		Map<String, String[]> preferenceMap = HashMapBuilder.put(
			"nodeId", new String[] {String.valueOf(wikiPage.getNodeId())}
		).put(
			"title", new String[] {String.valueOf(wikiPage.getTitle())}
		).build();

		String portletId = publishLayoutWithDisplayPortlet(
			WikiPortletKeys.WIKI_DISPLAY, preferenceMap, true);

		Assert.assertEquals(
			String.valueOf(wikiPage.getNodeId()),
			livePortletPreferences.getValue("nodeId", StringPool.BLANK));
		Assert.assertEquals(
			wikiPage.getTitle(),
			livePortletPreferences.getValue("title", StringPool.BLANK));

		publishPortletData(WikiPortletKeys.WIKI);

		publishLayoutWithDisplayPortlet(portletId, preferenceMap, false);

		WikiNode liveWikiNode =
			_wikiNodeLocalService.getWikiNodeByUuidAndGroupId(
				wikiNode.getUuid(), liveGroup.getGroupId());

		WikiPage liveWikiPage =
			_wikiPageLocalService.getWikiPageByUuidAndGroupId(
				wikiPage.getUuid(), liveGroup.getGroupId());

		Assert.assertEquals(
			String.valueOf(liveWikiNode.getNodeId()),
			livePortletPreferences.getValue("nodeId", StringPool.BLANK));
		Assert.assertEquals(
			liveWikiPage.getTitle(),
			livePortletPreferences.getValue("title", StringPool.BLANK));
	}

	protected void enableLocalStaging() throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(liveGroup.getGroupId());

		Map<String, Serializable> attributes = serviceContext.getAttributes();

		attributes.putAll(
			ExportImportConfigurationParameterMapFactoryUtil.
				buildParameterMap());

		StagingLocalServiceUtil.enableLocalStaging(
			TestPropsValues.getUserId(), liveGroup, false, false,
			serviceContext);
	}

	protected String publishLayoutWithDisplayPortlet(
			String portletId, Map<String, String[]> preferenceMap,
			boolean addPortlet)
		throws Exception {

		if (addPortlet) {
			portletId = LayoutTestUtil.addPortletToLayout(
				stagingLayout, portletId, preferenceMap);
		}

		Map<String, String[]> parameterMap =
			ExportImportConfigurationParameterMapFactoryUtil.
				buildParameterMap();

		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.FALSE.toString()});

		StagingUtil.publishLayouts(
			TestPropsValues.getUserId(), stagingGroup.getGroupId(),
			liveGroup.getGroupId(), false, parameterMap);

		if (liveLayout == null) {
			liveLayout = LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
				stagingLayout.getUuid(), liveGroup.getGroupId(),
				stagingLayout.isPrivateLayout());
		}

		livePortletPreferences = LayoutTestUtil.getPortletPreferences(
			liveLayout, portletId);

		return portletId;
	}

	protected void publishPortletData(String portletId) throws PortalException {
		Map<String, String[]> parameterMap =
			ExportImportConfigurationParameterMapFactoryUtil.
				buildParameterMap();

		parameterMap.put(
			ExportImportDateUtil.RANGE,
			new String[] {ExportImportDateUtil.RANGE_ALL});

		StagingUtil.publishPortlet(
			TestPropsValues.getUserId(), stagingGroup.getGroupId(),
			liveGroup.getGroupId(), stagingLayout.getPlid(),
			liveLayout.getPlid(), portletId, parameterMap);
	}

	@DeleteAfterTestRun
	protected Group liveGroup;

	protected Layout liveLayout;
	protected PortletPreferences livePortletPreferences;
	protected Group stagingGroup;
	protected Layout stagingLayout;

	@Inject
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	@Inject
	private DDMFormInstanceLocalService _ddmFormInstanceLocalService;

	@Inject
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

	@Inject
	private WikiNodeLocalService _wikiNodeLocalService;

	@Inject
	private WikiPageLocalService _wikiPageLocalService;

}