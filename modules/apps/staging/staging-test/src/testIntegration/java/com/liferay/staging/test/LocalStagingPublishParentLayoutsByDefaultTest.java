/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.staging.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.resource.v2_0.DataDefinitionResource;
import com.liferay.data.engine.rest.test.util.DataDefinitionTestUtil;
import com.liferay.exportimport.kernel.lar.ExportImportDateUtil;
import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.journal.constants.JournalContentPortletKeys;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portletmvc4spring.test.mock.web.portlet.MockPortletRequest;
import com.liferay.staging.configuration.StagingConfiguration;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Daniel Szimko
 */
@FeatureFlags("LPS-199086")
@RunWith(Arquillian.class)
@Sync(cleanTransaction = true)
public class LocalStagingPublishParentLayoutsByDefaultTest
	extends BaseLocalStagingTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@AfterClass
	public static void tearDownClass() throws Exception {
		ConfigurationTestUtil.deleteConfiguration(
			StagingConfiguration.class.getName());
	}

	/**
	 * LPD-6808: AC7
	 */
	@Test
	public void testLocalStagingPublishJournalContentWithLayoutURLLayoutDoesNotExistOnImportSide()
		throws Exception {

		_configurationProvider.saveCompanyConfiguration(
			StagingConfiguration.class, CompanyThreadLocal.getCompanyId(),
			HashMapDictionaryBuilder.<String, Object>put(
				"publishParentLayoutsByDefault", false
			).build());

		Layout parentLayout = LayoutTestUtil.addTypePortletLayout(stagingGroup);

		Layout childLayout = LayoutTestUtil.addTypePortletLayout(
			stagingGroup, parentLayout.getPlid());

		String content = StringUtil.replace(
			_read("journal_content.xml"),
			new String[] {"[$GROUP_NAME$]", "[$LAYOUT_FRIENDLY_URL$]"},
			new String[] {
				StringUtil.toLowerCase(stagingGroup.getName("en_US")),
				StringUtil.toLowerCase(childLayout.getFriendlyURL())
			});

		DataDefinition dataDefinition =
			DataDefinitionTestUtil.addDataDefinition(
				"journal", _dataDefinitionResourceFactory,
				stagingGroup.getGroupId(), _read("data_definition.json"),
				TestPropsValues.getUser());

		JournalArticle journalArticle = JournalTestUtil.addArticleWithXMLContent(
			stagingGroup.getGroupId(), content,
			dataDefinition.getDataDefinitionKey(), null);

		StagingUtil.addModelToChangesetCollection(journalArticle);

		_mockPortletRequest = new MockPortletRequest();

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(WebKeys.LAYOUT, childLayout);
		mockHttpServletRequest.setParameter(
			"doAsGroupId", String.valueOf(stagingGroup.getGroupId()));

		_mockPortletRequest.setAttribute(
			PortletServlet.PORTLET_SERVLET_REQUEST, mockHttpServletRequest);

		ThemeDisplay themeDisplay = _getThemeDisplay(stagingGroup);

		_mockPortletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		_mockPortletRequest.setParameter(
			PortletDataHandlerKeys.PORTLET_DATA, Boolean.TRUE.toString());
		_mockPortletRequest.setParameter(
			PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE +
				JournalPortletKeys.JOURNAL,
			Boolean.TRUE.toString());
		_mockPortletRequest.setParameter(
			PortletDataHandlerKeys.PORTLET_SETUP, Boolean.TRUE.toString());
		_mockPortletRequest.setParameter(
			"_journal_web-content", Boolean.TRUE.toString());
		_mockPortletRequest.setParameter(
			"exportImportConfigurationId", String.valueOf(0));
		_mockPortletRequest.setParameter(
			"groupId", String.valueOf(stagingGroup.getGroupId()));
		_mockPortletRequest.setParameter("plid", String.valueOf(1));
		_mockPortletRequest.setParameter(
			"portletResource", JournalPortletKeys.JOURNAL);
		_mockPortletRequest.setParameter(
			"range", ExportImportDateUtil.RANGE_FROM_LAST_PUBLISH_DATE);
		_mockPortletRequest.setParameter("tabs3", "new-publish-process");

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			themeDisplay.getCompanyId(), JournalPortletKeys.JOURNAL);

		StagingUtil.publishToLive(_mockPortletRequest, portlet);

		Assert.assertEquals(
			1,
			JournalArticleLocalServiceUtil.getArticlesCount(
				liveGroup.getGroupId()));

		JournalArticle groupJournalArticle =
			JournalArticleLocalServiceUtil.fetchJournalArticleByUuidAndGroupId(
				journalArticle.getUuid(), liveGroup.getGroupId());

		Assert.assertNotNull(groupJournalArticle);
		Assert.assertEquals(content, groupJournalArticle.getContent());
	}

	/**
	 * LPD-6808: AC3
	 */
	@Test
	public void testStagingWithCheckedConfigurationAndModifiedContentAndExistingParentAndChildLayoutsOnLive()
		throws Exception {

		_configurationProvider.saveCompanyConfiguration(
			StagingConfiguration.class, CompanyThreadLocal.getCompanyId(),
			HashMapDictionaryBuilder.<String, Object>put(
				"publishParentLayoutsByDefault", true
			).build());

		Layout parentLayout = LayoutTestUtil.addTypePortletLayout(stagingGroup);

		Layout childLayout = LayoutTestUtil.addTypePortletLayout(
			stagingGroup, parentLayout.getPlid());

		JSONArray selectedLayoutsJSONArray = JSONFactoryUtil.createJSONArray(
			ExportImportHelperUtil.getSelectedLayoutsJSON(
				stagingGroup.getGroupId(), false,
				StringUtil.merge(
					new long[] {
						childLayout.getLayoutId(), parentLayout.getLayoutId()
					})));

		ThemeDisplay themeDisplay = _getThemeDisplay(stagingGroup);

		_mockPortletRequest = new MockPortletRequest();

		_mockPortletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);
		_mockPortletRequest.setAttribute(
			"layoutIdMap", selectedLayoutsJSONArray.toString());

		_mockPortletRequest.setParameter(
			"exportImportConfigurationId", String.valueOf(0));
		_mockPortletRequest.setParameter(
			"groupId", String.valueOf(stagingGroup.getGroupId()));
		_mockPortletRequest.setParameter("PERMISSIONS", "false");
		_mockPortletRequest.setParameter("tabs1", "public-pages");

		StagingUtil.publishToLive(_mockPortletRequest);

		Layout liveParentLayout = LayoutLocalServiceUtil.fetchLayout(
			parentLayout.getUuid(), liveGroup.getGroupId(),
			parentLayout.isPrivateLayout());

		Layout liveChildLayout = LayoutLocalServiceUtil.fetchLayout(
			childLayout.getUuid(), liveGroup.getGroupId(),
			childLayout.isPrivateLayout());

		Assert.assertNotNull(liveParentLayout);

		Assert.assertNotNull(liveChildLayout);

		LayoutTestUtil.addPortletToLayout(
			parentLayout, JournalContentPortletKeys.JOURNAL_CONTENT);

		LayoutTestUtil.addPortletToLayout(
			childLayout, JournalContentPortletKeys.JOURNAL_CONTENT);

		selectedLayoutsJSONArray = JSONFactoryUtil.createJSONArray(
			ExportImportHelperUtil.getSelectedLayoutsJSON(
				stagingGroup.getGroupId(), false,
				StringUtil.merge(new long[] {childLayout.getLayoutId()})));

		themeDisplay = _getThemeDisplay(stagingGroup);

		_mockPortletRequest = new MockPortletRequest();

		_mockPortletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);
		_mockPortletRequest.setAttribute(
			"layoutIdMap", selectedLayoutsJSONArray.toString());

		_mockPortletRequest.setParameter(
			"exportImportConfigurationId", String.valueOf(0));
		_mockPortletRequest.setParameter(
			"groupId", String.valueOf(stagingGroup.getGroupId()));
		_mockPortletRequest.setParameter("PERMISSIONS", "false");
		_mockPortletRequest.setParameter("tabs1", "public-pages");

		StagingUtil.publishToLive(_mockPortletRequest);

		liveParentLayout = LayoutLocalServiceUtil.fetchLayout(
			parentLayout.getUuid(), liveGroup.getGroupId(),
			parentLayout.isPrivateLayout());

		liveChildLayout = LayoutLocalServiceUtil.fetchLayout(
			childLayout.getUuid(), liveGroup.getGroupId(),
			childLayout.isPrivateLayout());

		Assert.assertTrue(
			liveChildLayout.getTypeSettingsProperties(
			).getProperty(
				"column-1"
			).contains(
				JournalContentPortletKeys.JOURNAL_CONTENT
			));

		Assert.assertTrue(
			liveParentLayout.getTypeSettingsProperties(
			).getProperty(
				"column-1"
			).contains(
				JournalContentPortletKeys.JOURNAL_CONTENT
			));
	}

	/**
	 * LPD-6808: AC4
	 */
	@Test
	public void testStagingWithCheckedConfigurationAndModifiedContentAndNonexistingParentAndChildLayoutsOnLive()
		throws Exception {

		_configurationProvider.saveCompanyConfiguration(
			StagingConfiguration.class, CompanyThreadLocal.getCompanyId(),
			HashMapDictionaryBuilder.<String, Object>put(
				"publishParentLayoutsByDefault", true
			).build());

		Layout parentLayout = LayoutTestUtil.addTypePortletLayout(stagingGroup);

		Layout childLayout = LayoutTestUtil.addTypePortletLayout(
			stagingGroup, parentLayout.getPlid());

		JSONArray selectedLayoutsJSONArray = JSONFactoryUtil.createJSONArray(
			ExportImportHelperUtil.getSelectedLayoutsJSON(
				stagingGroup.getGroupId(), false,
				StringUtil.merge(new long[] {childLayout.getLayoutId()})));

		ThemeDisplay themeDisplay = _getThemeDisplay(stagingGroup);

		_mockPortletRequest = new MockPortletRequest();

		_mockPortletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);
		_mockPortletRequest.setAttribute(
			"layoutIdMap", selectedLayoutsJSONArray.toString());

		_mockPortletRequest.setParameter(
			"exportImportConfigurationId", String.valueOf(0));
		_mockPortletRequest.setParameter(
			"groupId", String.valueOf(stagingGroup.getGroupId()));
		_mockPortletRequest.setParameter("PERMISSIONS", "false");
		_mockPortletRequest.setParameter("tabs1", "public-pages");

		StagingUtil.publishToLive(_mockPortletRequest);

		Layout importedParentLayout = LayoutLocalServiceUtil.fetchLayout(
			parentLayout.getUuid(), liveGroup.getGroupId(),
			parentLayout.isPrivateLayout());

		Layout importedChildLayout = LayoutLocalServiceUtil.fetchLayout(
			childLayout.getUuid(), liveGroup.getGroupId(),
			childLayout.isPrivateLayout());

		Assert.assertNotNull(importedParentLayout);

		Assert.assertNotNull(importedChildLayout);

		Assert.assertEquals(
			importedChildLayout.getParentLayoutId(),
			importedParentLayout.getLayoutId());
	}

	/**
	 * LPD-6808: AC5
	 */
	@Test
	public void testStagingWithUncheckedConfigurationAndModifiedContentAndExistingParentAndChildLayoutsOnLive()
		throws Exception {

		_configurationProvider.saveCompanyConfiguration(
			StagingConfiguration.class, CompanyThreadLocal.getCompanyId(),
			HashMapDictionaryBuilder.<String, Object>put(
				"publishParentLayoutsByDefault", false
			).build());

		Layout parentLayout = LayoutTestUtil.addTypePortletLayout(stagingGroup);

		Layout childLayout = LayoutTestUtil.addTypePortletLayout(
			stagingGroup, parentLayout.getPlid());

		JSONArray selectedLayoutsJSONArray = JSONFactoryUtil.createJSONArray(
			ExportImportHelperUtil.getSelectedLayoutsJSON(
				stagingGroup.getGroupId(), false,
				StringUtil.merge(
					new long[] {
						childLayout.getLayoutId(), parentLayout.getLayoutId()
					})));

		ThemeDisplay themeDisplay = _getThemeDisplay(stagingGroup);

		_mockPortletRequest = new MockPortletRequest();

		_mockPortletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		_mockPortletRequest.setParameter(
			"exportImportConfigurationId", String.valueOf(0));
		_mockPortletRequest.setParameter(
			"groupId", String.valueOf(stagingGroup.getGroupId()));
		_mockPortletRequest.setAttribute(
			"layoutIdMap", selectedLayoutsJSONArray.toString());
		_mockPortletRequest.setParameter("PERMISSIONS", "false");
		_mockPortletRequest.setParameter("tabs1", "public-pages");

		StagingUtil.publishToLive(_mockPortletRequest);

		Layout liveParentLayout = LayoutLocalServiceUtil.fetchLayout(
			parentLayout.getUuid(), liveGroup.getGroupId(),
			parentLayout.isPrivateLayout());

		Layout liveChildLayout = LayoutLocalServiceUtil.fetchLayout(
			childLayout.getUuid(), liveGroup.getGroupId(),
			childLayout.isPrivateLayout());

		Assert.assertNotNull(liveParentLayout);

		Assert.assertNotNull(liveChildLayout);

		LayoutTestUtil.addPortletToLayout(
			parentLayout, JournalContentPortletKeys.JOURNAL_CONTENT);

		LayoutTestUtil.addPortletToLayout(
			childLayout, JournalContentPortletKeys.JOURNAL_CONTENT);

		selectedLayoutsJSONArray = JSONFactoryUtil.createJSONArray(
			ExportImportHelperUtil.getSelectedLayoutsJSON(
				stagingGroup.getGroupId(), false,
				StringUtil.merge(new long[] {childLayout.getLayoutId()})));

		themeDisplay = _getThemeDisplay(stagingGroup);

		_mockPortletRequest = new MockPortletRequest();

		_mockPortletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);
		_mockPortletRequest.setAttribute(
			"layoutIdMap", selectedLayoutsJSONArray.toString());

		_mockPortletRequest.setParameter(
			"exportImportConfigurationId", String.valueOf(0));
		_mockPortletRequest.setParameter(
			"groupId", String.valueOf(stagingGroup.getGroupId()));
		_mockPortletRequest.setParameter("PERMISSIONS", "false");
		_mockPortletRequest.setParameter("tabs1", "public-pages");

		StagingUtil.publishToLive(_mockPortletRequest);

		liveParentLayout = LayoutLocalServiceUtil.fetchLayout(
			parentLayout.getUuid(), liveGroup.getGroupId(),
			parentLayout.isPrivateLayout());

		liveChildLayout = LayoutLocalServiceUtil.fetchLayout(
			childLayout.getUuid(), liveGroup.getGroupId(),
			childLayout.isPrivateLayout());

		Assert.assertTrue(
			liveChildLayout.getTypeSettingsProperties(
			).getProperty(
				"column-1"
			).contains(
				JournalContentPortletKeys.JOURNAL_CONTENT
			));

		Assert.assertNull(
			liveParentLayout.getTypeSettingsProperties(
			).getProperty(
				"column-1"
			));
	}

	/**
	 * LPD-6808: AC6
	 */
	@Test
	public void testStagingWithUncheckedConfigurationAndModifiedContentAndNonexistingParentAndChildLayoutsOnLive()
		throws Exception {

		_configurationProvider.saveCompanyConfiguration(
			StagingConfiguration.class, CompanyThreadLocal.getCompanyId(),
			HashMapDictionaryBuilder.<String, Object>put(
				"publishParentLayoutsByDefault", false
			).build());

		try (LogCapture logCapture1 = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.background.task.internal.messaging." +
					"BackgroundTaskMessageListener",
				LoggerTestUtil.ERROR)) {

			Layout parentLayout = LayoutTestUtil.addTypePortletLayout(
				stagingGroup);

			Layout childLayout = LayoutTestUtil.addTypePortletLayout(
				stagingGroup, parentLayout.getPlid());

			JSONArray selectedLayoutsJSONArray =
				JSONFactoryUtil.createJSONArray(
					ExportImportHelperUtil.getSelectedLayoutsJSON(
						stagingGroup.getGroupId(), false,
						StringUtil.merge(
							new long[] {childLayout.getLayoutId()})));

			ThemeDisplay themeDisplay = _getThemeDisplay(stagingGroup);

			_mockPortletRequest = new MockPortletRequest();

			_mockPortletRequest.setAttribute(
				WebKeys.THEME_DISPLAY, themeDisplay);
			_mockPortletRequest.setAttribute(
				"layoutIdMap", selectedLayoutsJSONArray.toString());

			_mockPortletRequest.setParameter(
				"exportImportConfigurationId", String.valueOf(0));
			_mockPortletRequest.setParameter(
				"groupId", String.valueOf(stagingGroup.getGroupId()));
			_mockPortletRequest.setParameter("PERMISSIONS", "false");
			_mockPortletRequest.setParameter("tabs1", "public-pages");

			StagingUtil.publishToLive(_mockPortletRequest);

			List<LogEntry> logEntries = logCapture1.getLogEntries();

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				"Unable to execute background task", logEntry.getMessage());

			Throwable throwable = logEntry.getThrowable();

			Assert.assertSame(PortletDataException.class, throwable.getClass());
		}
	}

	private ThemeDisplay _getThemeDisplay(Group group) throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		Layout layout = LayoutTestUtil.addTypePortletLayout(group);

		themeDisplay.setCompany(
			_companyLocalService.getCompany(group.getCompanyId()));
		themeDisplay.setLayout(layout);
		themeDisplay.setLocale(
			LocaleUtil.fromLanguageId(group.getDefaultLanguageId()));
		themeDisplay.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser()));
		themeDisplay.setScopeGroupId(group.getGroupId());
		themeDisplay.setSiteGroupId(group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private String _read(String fileName) throws Exception {
		return new String(
			FileUtil.getBytes(getClass(), "dependencies/" + fileName));
	}

	@Inject
	private static ConfigurationProvider _configurationProvider;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private DataDefinitionResource.Factory _dataDefinitionResourceFactory;

	private MockPortletRequest _mockPortletRequest;

}