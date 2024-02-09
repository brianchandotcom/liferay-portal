/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.staging.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.resource.v2_0.DataDefinitionResource;
import com.liferay.data.engine.rest.test.util.DataDefinitionTestUtil;
import com.liferay.exportimport.kernel.exception.MissingReferenceException;
import com.liferay.exportimport.kernel.lar.ExportImportDateUtil;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.service.StagingLocalServiceUtil;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.persistence.GroupUtil;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.PropsValuesTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portletmvc4spring.test.mock.web.portlet.MockPortletRequest;
import com.liferay.staging.configuration.StagingConfiguration;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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
public class RemoteStagingPublishParentPagesByDefaultTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		UserTestUtil.setUser(TestPropsValues.getUser());

		_addRemoteStagingGroups();
	}

	@After
	public void tearDown() throws Exception {
		try {
			GroupLocalServiceUtil.deleteGroup(_remoteLiveGroup.getGroupId());
		}
		catch (NoSuchGroupException noSuchGroupException) {
			if (_log.isDebugEnabled()) {
				_log.debug(noSuchGroupException);
			}
		}

		try {
			GroupLocalServiceUtil.deleteGroup(_remoteStagingGroup.getGroupId());
		}
		catch (NoSuchGroupException noSuchGroupException) {
			if (_log.isDebugEnabled()) {
				_log.debug(noSuchGroupException);
			}
		}
	}

	// LPD-6808 - AC8

	@Test
	public void testRemoteStagingPublishJournalContentWithLayoutHrefLayoutDoesNotExistOnImportSide()
		throws Exception {

		_configurationProvider.saveCompanyConfiguration(
			StagingConfiguration.class, CompanyThreadLocal.getCompanyId(),
			HashMapDictionaryBuilder.<String, Object>put(
				"publishParentLayoutsByDefault", false
			).build());

		Layout parentLayout = LayoutTestUtil.addTypePortletLayout(
			_remoteStagingGroup);

		Layout childLayout = LayoutTestUtil.addTypePortletLayout(
			_remoteStagingGroup, parentLayout.getPlid());

		DataDefinition dataDefinition =
			DataDefinitionTestUtil.addDataDefinition(
				"journal", _dataDefinitionResourceFactory,
				_remoteStagingGroup.getGroupId(), _read("data_definition.json"),
				TestPropsValues.getUser());

		String content = StringUtil.replace(
			_read("journal_content.xml"), new String[] {"$GROUP_NAME"},
			new String[] {
				_remoteStagingGroup.getName(
					"en_US"
				).toLowerCase()
			}
		).replace(
			"$LAYOUT_FRIENDLY_URL",
			childLayout.getFriendlyURL(
			).toLowerCase()
		);

		JournalArticle article = JournalTestUtil.addArticleWithXMLContent(
			_remoteStagingGroup.getGroupId(), content,
			dataDefinition.getDataDefinitionKey(), null);

		_mockPortletRequest = new MockPortletRequest();

		_mockPortletRequest.setParameter(
			"groupId", String.valueOf(_remoteStagingGroup.getGroupId()));
		_mockPortletRequest.setParameter(
			"range", ExportImportDateUtil.RANGE_FROM_LAST_PUBLISH_DATE);
		_mockPortletRequest.setParameter("tabs3", "new-publish-process");
		_mockPortletRequest.setParameter(
			"exportImportConfigurationId", String.valueOf(0));
		_mockPortletRequest.setParameter("plid", String.valueOf(1));
		_mockPortletRequest.setParameter(
			"portletResource", JournalPortletKeys.JOURNAL);
		_mockPortletRequest.setParameter(
			"_journal_web-content", Boolean.TRUE.toString());
		_mockPortletRequest.setParameter(
			PortletDataHandlerKeys.PORTLET_SETUP, Boolean.TRUE.toString());
		_mockPortletRequest.setParameter(
			PortletDataHandlerKeys.PORTLET_DATA, Boolean.TRUE.toString());
		_mockPortletRequest.setParameter(
			PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE +
				JournalPortletKeys.JOURNAL,
			Boolean.TRUE.toString());
		_mockPortletRequest.setAttribute(
			WebKeys.THEME_DISPLAY,
			_getThemeDisplay(childLayout, _remoteStagingGroup));

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			_getThemeDisplay(
				childLayout, _remoteStagingGroup
			).getCompanyId(),
			JournalPortletKeys.JOURNAL);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(WebKeys.LAYOUT, childLayout);
		mockHttpServletRequest.setParameter(
			"doAsGroupId", String.valueOf(_remoteStagingGroup.getGroupId()));

		_mockPortletRequest.setAttribute(
			PortletServlet.PORTLET_SERVLET_REQUEST, mockHttpServletRequest);

		StagingUtil.addModelToChangesetCollection(article);

		try {
			StagingUtil.publishToLive(_mockPortletRequest, portlet);
		}
		catch (MissingReferenceException missingReferenceException) {
			Assert.assertEquals(
				1,
				missingReferenceException.getMissingReferences(
				).getDependencyMissingReferences(
				).size());
		}

		JournalArticleLocalServiceUtil.deleteArticle(article);
	}

	private void _addRemoteStagingGroups() throws Exception {
		_remoteLiveGroup = GroupTestUtil.addGroup();
		_remoteStagingGroup = GroupTestUtil.addGroup();

		try (SafeCloseable safeCloseable1 =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"TUNNELING_SERVLET_SHARED_SECRET",
					"F0E1D2C3B4A5968778695A4B3C2D1E0F");
			SafeCloseable safeCloseable2 =
				PropsValuesTestUtil.swapWithSafeCloseable(
					"TUNNELING_SERVLET_SHARED_SECRET_HEX", true)) {

			int serverPort = PortalUtil.getPortalServerPort(false);

			Assert.assertFalse(
				"Invalid server port: " + serverPort,
				(serverPort < 1) || (serverPort > 65535));

			String pathContext = PortalUtil.getPathContext();

			UserTestUtil.setUser(TestPropsValues.getUser());

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAttribute(
				"staged--staged-portlet_" + JournalPortletKeys.JOURNAL + "--",
				"true");

			StagingLocalServiceUtil.enableRemoteStaging(
				TestPropsValues.getUserId(), _remoteStagingGroup, false, false,
				"localhost", serverPort, pathContext, false,
				_remoteLiveGroup.getGroupId(), serviceContext);

			GroupUtil.clearCache();

			_remoteLiveGroup = GroupLocalServiceUtil.getGroup(
				_remoteLiveGroup.getGroupId());
		}
	}

	private ThemeDisplay _getThemeDisplay(Layout layout, Group group)
		throws Exception {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		layout = LayoutTestUtil.addTypePortletLayout(group);

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

	private static final Log _log = LogFactoryUtil.getLog(
		RemoteStagingPublishParentPagesByDefaultTest.class);

	@Inject
	private static ConfigurationProvider _configurationProvider;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private DataDefinitionResource.Factory _dataDefinitionResourceFactory;

	private MockPortletRequest _mockPortletRequest;
	private Group _remoteLiveGroup;
	private Group _remoteStagingGroup;

}