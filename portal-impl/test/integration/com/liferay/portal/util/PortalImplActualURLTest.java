/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.LayoutTypePortletConstants;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.UserGroupConstants;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.webdav.methods.Method;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Vilmos Papp
 */
@ExecutionTestListeners(listeners = {EnvironmentExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class PortalImplActualURLTest {

	@Test
	public void testPortalImplWithArticleFriendlyURL() throws Exception {
		setupEnvironmentForArticleFriendlyURLTest();

		Map<String, Object> requestContext = new HashMap<String, Object>();
		Map<String, String[]> params = new HashMap<String, String[]>();

		String requestURI = "/";

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest(Method.GET, requestURI);

		requestContext.put("request", mockHttpServletRequest);

		String actualURL = PortalUtil.getActualURL(
			_testSite.getGroupId(), false, Portal.PATH_MAIN,
			WORKING_ARTICLE_TITLE_FRIENDLYURL, params, requestContext);

		Assert.assertFalse(actualURL == null);

		boolean nonExistingLayoutFound = false;

		try {
			PortalUtil.getActualURL(
				_testSite.getGroupId(), false, Portal.PATH_MAIN,
				NOT_WORKING_ARTICLE_TITLE_FRIENDLYURL, params, requestContext);
		}
		catch (NoSuchLayoutException e) {
			nonExistingLayoutFound = true;
		}

		Assert.assertTrue(nonExistingLayoutFound);

		JournalArticleLocalServiceUtil.deleteArticle(
			_testSite.getGroupId(), _article.getArticleId(),
			ServiceTestUtil.getServiceContext());

		LayoutLocalServiceUtil.deleteLayout(_homePage);
		LayoutLocalServiceUtil.deleteLayout(_testPage);
		GroupLocalServiceUtil.deleteGroup(_testSite);
	}

	@Test
	public void testPortalImplWithVirtualLayoutFriendlyURL() throws Exception {
		setupEnvironmentForVirtualLayoutFriendlyURLTest();

		Map<String, Object> requestContext = new HashMap<String, Object>();
		Map<String, String[]> params = new HashMap<String, String[]>();

		String requestURI = "/";

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest(Method.GET, requestURI);

		requestContext.put("request", mockHttpServletRequest);

		String actualURL = PortalUtil.getActualURL(
			_userGroup.getGroup().getGroupId(), true, Portal.PATH_MAIN,
			_existingVirtualLayoutFriendlyURL, params, requestContext);

		Assert.assertFalse(actualURL == null);

		boolean nonExistingLayoutFound = false;

		try {
			PortalUtil.getActualURL(
				_userGroup.getGroup().getGroupId(), true, Portal.PATH_MAIN,
				_nonExistingVirtualLayoutFriendlyURL, params, requestContext);
		}
		catch (NoSuchLayoutException e) {
			nonExistingLayoutFound = true;
		}

		Assert.assertTrue(nonExistingLayoutFound);

		LayoutLocalServiceUtil.deleteLayout(_virtualHomeSub);
		LayoutLocalServiceUtil.deleteLayout(_virtualHome);
	}

	protected void setupEnvironmentForArticleFriendlyURLTest()
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		String siteName = "Test" + ServiceTestUtil.nextInt();
		String siteFriendlyURL = StringPool.SLASH + siteName.toLowerCase();

		_testSite = GroupLocalServiceUtil.addGroup(
			serviceContext.getUserId(), GroupConstants.DEFAULT_PARENT_GROUP_ID,
			null, 0, siteName, StringPool.BLANK, GroupConstants.TYPE_SITE_OPEN,
			siteFriendlyURL, true, true, ServiceTestUtil.getServiceContext());

		_homePage = LayoutLocalServiceUtil.addLayout(
			serviceContext.getUserId(), _testSite.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, "home", "Home",
			StringPool.BLANK, LayoutConstants.TYPE_PORTLET, false, "/home",
			ServiceTestUtil.getServiceContext());

		_testPage = LayoutLocalServiceUtil.addLayout(
			serviceContext.getUserId(), _testSite.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, "testpage", "TestPage",
			StringPool.BLANK, LayoutConstants.TYPE_PORTLET, false, "/testpage",
			ServiceTestUtil.getServiceContext());

		String portletId = PortletKeys.ASSET_PUBLISHER;

		LayoutTypePortlet layoutTypePortlet = LayoutTypePortletFactoryUtil
			.create(_testPage);

		String columnId = "column-1";
		int columnPos = 0;

		portletId = layoutTypePortlet.addPortletId(
			serviceContext.getUserId(), portletId, columnId, columnPos);

		layoutTypePortlet.resetModes();
		layoutTypePortlet.resetStates();

		_testPage = LayoutServiceUtil.updateLayout(
			_testPage.getGroupId(), _testPage.isPrivateLayout(),
			_testPage.getLayoutId(), _testPage.getTypeSettings());

		Map<Locale, String> titleMap = new HashMap<Locale, String>();

		Locale englishLocale = new Locale("en", "US");

		titleMap.put(englishLocale, "ExistingArticle");

		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

		StringBundler sb = new StringBundler();

		sb.append("<?xml version=\"1.0\"?><root available-locales=");
		sb.append("\"en_US\" default-locale=\"en_US\">");
		sb.append("<static-content language-id=\"en_US\"><![CDATA[<p>");
		sb.append("This is the test content in English");
		sb.append("</p>]]>");
		sb.append("</static-content></root>");

		_article = JournalArticleLocalServiceUtil.addArticle(
			serviceContext.getUserId(), _testSite.getGroupId(), 0, 0, 0,
			StringPool.BLANK, true, 1, titleMap, descriptionMap, sb.toString(),
			"general", null, null, _testPage.getUuid(), 1, 1, 1965, 0, 0, 0, 0,
			0, 0, 0, true, 0, 0, 0, 0, 0, true, false, false, null, null, null,
			"/existingarticle", serviceContext);

		UnicodeProperties typeSettingsProperties =
			_testPage.getTypeSettingsProperties();

		typeSettingsProperties.setProperty(
			LayoutTypePortletConstants.DEFAULT_ASSET_PUBLISHER_PORTLET_ID,
			portletId);

		_testPage.setTypeSettingsProperties(typeSettingsProperties);

		LayoutLocalServiceUtil.updateLayout(_testPage);
	}

	protected void setupEnvironmentForVirtualLayoutFriendlyURLTest()
			throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		long userGroupId = ServiceTestUtil.nextLong();

		_userGroup = UserGroupLocalServiceUtil.createUserGroup(userGroupId);

		_userGroup.setAddedByLDAPImport(false);
		_userGroup.setCompanyId(serviceContext.getCompanyId());
		_userGroup.setDescription("Test User Group");
		_userGroup.setExpandoBridgeAttributes(
			ServiceTestUtil.getServiceContext());
		_userGroup.setName("TestGroup_" + userGroupId);
		_userGroup.setNew(true);
		_userGroup.setParentUserGroupId(
			UserGroupConstants.DEFAULT_PARENT_USER_GROUP_ID);

		UserGroupLocalServiceUtil.addUserGroup(_userGroup);

		GroupLocalServiceUtil.addGroup(
			serviceContext.getUserId(), GroupConstants.DEFAULT_PARENT_GROUP_ID,
			UserGroup.class.getName(), _userGroup.getUserGroupId(),
			String.valueOf(userGroupId), null, 0, null, false, true, null);

		UserLocalServiceUtil.addUserGroupUsers(
			userGroupId, new long[] {serviceContext.getUserId()});

		_virtualHome = LayoutLocalServiceUtil.addLayout(
			serviceContext.getUserId(), _userGroup.getGroup().getGroupId(),
			true, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, "home", "Home",
			StringPool.BLANK, LayoutConstants.TYPE_PORTLET, false, "/home",
			ServiceTestUtil.getServiceContext());

		_virtualHomeSub = LayoutLocalServiceUtil.addLayout(
			serviceContext.getUserId(), _userGroup.getGroup().getGroupId(),
			true, _virtualHome.getLayoutId(), "homesub", "HomeSub",
			StringPool.BLANK, LayoutConstants.TYPE_PORTLET, false, "/homesub",
			ServiceTestUtil.getServiceContext());

		_existingVirtualLayoutFriendlyURL = "/~/" + userGroupId + "/homesub";
		_nonExistingVirtualLayoutFriendlyURL =
			"/~/" + userGroupId + "/homesubbb";
	}

	private static final String NOT_WORKING_ARTICLE_TITLE_FRIENDLYURL =
		"/-/nonexistingarticle";

	private static final String WORKING_ARTICLE_TITLE_FRIENDLYURL =
		"/-/existingarticle";

	private JournalArticle _article;
	private String _existingVirtualLayoutFriendlyURL;
	private Layout _homePage;
	private String _nonExistingVirtualLayoutFriendlyURL;
	private Layout _testPage;
	private Group _testSite;
	private UserGroup _userGroup;
	private Layout _virtualHome;
	private Layout _virtualHomeSub;

}