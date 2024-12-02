/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.publisher.internal.display.context.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.asset.publisher.constants.AssetPublisherWebKeys;
import com.liferay.asset.publisher.util.AssetEntryResult;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.portlet.PortletConfigFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.constants.MVCRenderConstants;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.test.MockLiferayPortletContext;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.portlet.Portlet;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class AssetPublisherDisplayContextTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@AfterClass
	public static void tearDownClass() throws Exception {
		ConfigurationTestUtil.deleteConfiguration(
			_assetPublisherWebConfiguration);
	}

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_company = _companyLocalService.getCompany(_group.getCompanyId());
		_layout = LayoutTestUtil.addTypePortletLayout(_group);

		_assetPublisherWebConfiguration = _configurationAdmin.getConfiguration(
			"com.liferay.asset.publisher.web.internal.configuration." +
				"AssetPublisherWebConfiguration",
			StringPool.QUESTION);

		ConfigurationTestUtil.saveConfiguration(
			_assetPublisherWebConfiguration,
			HashMapDictionaryBuilder.<String, Object>put(
				"searchWithIndex", false
			).build());
	}

	@Test
	public void testGetAssetEntryResultsFilterAssetsByTags() throws Exception {
		String tag = RandomTestUtil.randomString();

		JournalArticle journalArticle = _addArticle(new String[] {tag});

		AssetEntry expectedAssetEntry = _assetEntryLocalService.getEntry(
			JournalArticle.class.getName(),
			journalArticle.getResourcePrimKey());

		JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		PortletPreferences portletPreferences = new PortletPreferencesImpl();

		portletPreferences.setValue("selectionStyle", "dynamic");

		_assertAndGetAssetEntries(_getAssetEntryResults(portletPreferences), 2);

		portletPreferences.setValue("queryContains0", "true");
		portletPreferences.setValue("queryName0", "assetTags");
		portletPreferences.setValue("queryValues0", tag);

		List<AssetEntry> assetEntries = _assertAndGetAssetEntries(
			_getAssetEntryResults(portletPreferences), 1);

		Assert.assertEquals(expectedAssetEntry, assetEntries.get(0));
	}

	@Test
	public void testGetAssetEntryResultsOrderWebContents() throws Exception {
		Date date = new Date();

		AssetEntry assetEntry1 = _addAssetEntry(date, date, date, 0.9);

		date = new Date(date.getYear(), date.getMonth(), date.getDate() - 10);

		AssetEntry assetEntry2 = _addAssetEntry(date, date, date, 0.5);

		date = new Date(date.getYear(), date.getMonth(), date.getDate() + 5);

		AssetEntry assetEntry3 = _addAssetEntry(date, date, date, 0.3);

		_testGetAssetEntryResultsOrderWebContents(
			"createDate", Arrays.asList(assetEntry2, assetEntry3, assetEntry1));
		_testGetAssetEntryResultsOrderWebContents(
			"modifiedDate",
			Arrays.asList(assetEntry2, assetEntry3, assetEntry1));
		_testGetAssetEntryResultsOrderWebContents(
			"publishedDate",
			Arrays.asList(assetEntry2, assetEntry3, assetEntry1));
	}

	private JournalArticle _addArticle(String[] tagNames) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAssetTagNames(tagNames);

		return JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, serviceContext);
	}

	private AssetEntry _addAssetEntry(
			Date createDate, Date modifiedDate, Date publishedDate,
			double priority)
		throws Exception {

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		AssetEntry entry = _assetEntryLocalService.getEntry(
			JournalArticle.class.getName(),
			journalArticle.getResourcePrimKey());

		entry.setCreateDate(createDate);
		entry.setModifiedDate(modifiedDate);
		entry.setPublishDate(publishedDate);
		entry.setPriority(priority);

		return _assetEntryLocalService.updateAssetEntry(entry);
	}

	private List<AssetEntry> _assertAndGetAssetEntries(
		List<AssetEntryResult> assetEntryResults, int expectedAssetEntries) {

		Assert.assertEquals(
			assetEntryResults.toString(), 1, assetEntryResults.size());

		AssetEntryResult assetEntryResult = assetEntryResults.get(0);

		List<AssetEntry> assetEntries = assetEntryResult.getAssetEntries();

		Assert.assertEquals(
			assetEntries.toString(), expectedAssetEntries, assetEntries.size());

		return assetEntries;
	}

	private List<AssetEntryResult> _getAssetEntryResults(
			PortletPreferences portletPreferences)
		throws Exception {

		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new TestMockLiferayPortletRenderRequest(
				new MockHttpServletRequest());

		String path = "/view.jsp";

		com.liferay.portal.kernel.model.Portlet portlet =
			_portletLocalService.getPortletById(
				AssetPublisherPortletKeys.ASSET_PUBLISHER);

		LiferayPortletConfig liferayPortletConfig =
			(LiferayPortletConfig)PortletConfigFactoryUtil.create(
				portlet, null);

		mockLiferayPortletRenderRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG, liferayPortletConfig);

		mockLiferayPortletRenderRequest.setAttribute(
			MVCRenderConstants.
				PORTLET_CONTEXT_OVERRIDE_REQUEST_ATTIBUTE_NAME_PREFIX + path,
			new MockLiferayPortletContext(path));

		mockLiferayPortletRenderRequest.setAttribute(
			WebKeys.PORTLET_ID, AssetPublisherPortletKeys.ASSET_PUBLISHER);
		mockLiferayPortletRenderRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay(portletPreferences));

		mockLiferayPortletRenderRequest.setParameter("mvcPath", path);

		ReflectionTestUtil.invoke(
			_portlet, "doDispatch",
			new Class<?>[] {RenderRequest.class, RenderResponse.class},
			mockLiferayPortletRenderRequest,
			new TestMockLiferayPortletRenderResponse());

		return ReflectionTestUtil.invoke(
			mockLiferayPortletRenderRequest.getAttribute(
				AssetPublisherWebKeys.ASSET_PUBLISHER_DISPLAY_CONTEXT),
			"getAssetEntryResults", new Class<?>[0]);
	}

	private ThemeDisplay _getThemeDisplay(PortletPreferences portletPreferences)
		throws Exception {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);
		themeDisplay.setLayout(_layout);
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		portletDisplay.setPortletName(
			AssetPublisherPortletKeys.ASSET_PUBLISHER);

		portletDisplay.setPortletPreferences(portletPreferences);

		themeDisplay.setRealUser(TestPropsValues.getUser());
		themeDisplay.setScopeGroupId(_layout.getGroupId());
		themeDisplay.setSiteGroupId(_layout.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private void _testGetAssetEntryResultsOrderWebContents(
			String orderByColumn, List<AssetEntry> expectedAssetEntries)
		throws Exception {

		PortletPreferences portletPreferences = new PortletPreferencesImpl();

		portletPreferences.setValue(orderByColumn, "createDate");
		portletPreferences.setValue("orderByType1", "ASC");
		portletPreferences.setValue("selectionStyle", "dynamic");

		List<AssetEntry> assetEntries = _assertAndGetAssetEntries(
			_getAssetEntryResults(portletPreferences),
			expectedAssetEntries.size());

		for (int i = 0; i < assetEntries.size(); i++) {
			Assert.assertEquals(
				expectedAssetEntries.get(i), assetEntries.get(i));
		}

		portletPreferences.setValue("orderByType1", "DESC");

		assetEntries = _assertAndGetAssetEntries(
			_getAssetEntryResults(portletPreferences),
			expectedAssetEntries.size());

		for (int i = 0; i < assetEntries.size(); i++) {
			Assert.assertEquals(
				expectedAssetEntries.get(assetEntries.size() - 1 - i),
				assetEntries.get(i));
		}
	}

	private static Configuration _assetPublisherWebConfiguration;

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	@Inject
	private AssetEntryLocalService _assetEntryLocalService;

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject(
		filter = "component.name=com.liferay.asset.publisher.web.internal.portlet.AssetPublisherPortlet"
	)
	private Portlet _portlet;

	@Inject
	private PortletLocalService _portletLocalService;

	private static class TestMockLiferayPortletRenderRequest
		extends MockLiferayPortletRenderRequest {

		public TestMockLiferayPortletRenderRequest(
			HttpServletRequest httpServletRequest) {

			_httpServletRequest = httpServletRequest;
		}

		@Override
		public void setAttribute(String name, Object value) {
			super.setAttribute(name, value);

			_httpServletRequest.setAttribute(name, value);
		}

		private final HttpServletRequest _httpServletRequest;

	}

	private static class TestMockLiferayPortletRenderResponse
		extends MockLiferayPortletRenderResponse {

		@Override
		public String getNamespace() {
			return AssetPublisherPortletKeys.ASSET_PUBLISHER;
		}

	}

}