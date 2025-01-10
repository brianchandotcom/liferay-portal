/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.info.display.internal.url.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.info.display.url.provider.AssetInfoEditURLProvider;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Mikel Lorza
 */
@RunWith(Arquillian.class)
public class AssetInfoEditURLProviderTest {

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
	public void testGetURL() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		String backURL = "http://localhost:8080/test";
		String backURLTitle = "Go to test page";

		mockHttpServletRequest.setParameter("backURL", backURL);
		mockHttpServletRequest.setParameter("backURLTitle", backURLTitle);
		mockHttpServletRequest.setParameter(
			"redirect", "http://localhost:8080/redirect");

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		String url = _assetInfoEditURLProvider.getURL(
			JournalArticle.class.getName(), journalArticle.getResourcePrimKey(),
			mockHttpServletRequest);

		Assert.assertNotNull(url);

		Assert.assertTrue(
			url.contains("articleId=" + journalArticle.getArticleId()));
		Assert.assertTrue(
			url.contains(
				"portletResource=com_liferay_journal_web_portlet_" +
					"JournalPortlet"));
		Assert.assertTrue(
			url.contains("version=" + journalArticle.getVersion()));

		String redirect = HttpComponentsUtil.getParameter(
			url, "_com_liferay_journal_web_portlet_JournalPortlet_redirect",
			false);

		Assert.assertNotNull(redirect);

		redirect = URLCodec.decodeURL(redirect);

		Assert.assertTrue(
			redirect.contains("p_l_back_url=" + URLCodec.encodeURL(backURL)));
		Assert.assertTrue(
			redirect.contains(
				"p_l_back_url_title=" + URLCodec.encodeURL(backURLTitle)));
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(_group.getCompanyId()));
		themeDisplay.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser()));
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());

		return themeDisplay;
	}

	@Inject
	private AssetInfoEditURLProvider _assetInfoEditURLProvider;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

}