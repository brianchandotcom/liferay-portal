/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.publisher.portlet.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.exception.NoSuchArticleException;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.webdav.methods.Method;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * Tests whether the friendly URL resolves for existent, nonexistent or expired
 * web content articles in the Asset Publisher.
 *
 * @author Eduardo García
 * @author Roberto Díaz
 */
@RunWith(Arquillian.class)
public class DisplayPageFriendlyURLResolverTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		UserTestUtil.setUser(TestPropsValues.getUser());

		_group = GroupTestUtil.addGroup();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		LayoutLocalServiceUtil.addLayout(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, "Home", StringPool.BLANK,
			StringPool.BLANK, LayoutConstants.TYPE_PORTLET, false,
			StringPool.BLANK, serviceContext);

		Layout layout = LayoutLocalServiceUtil.addLayout(
			TestPropsValues.getUserId(), _group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			"Test " + RandomTestUtil.nextInt(), StringPool.BLANK,
			StringPool.BLANK, LayoutConstants.TYPE_PORTLET, false,
			StringPool.BLANK, serviceContext);

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		String portletId = layoutTypePortlet.addPortletId(
			TestPropsValues.getUserId(),
			AssetPublisherPortletKeys.ASSET_PUBLISHER, "column-1", 0);

		layoutTypePortlet.setTypeSettingsProperty(
			LayoutTypePortletConstants.DEFAULT_ASSET_PUBLISHER_PORTLET_ID,
			portletId);

		layout = LayoutLocalServiceUtil.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());

		Map<Locale, String> titleMap = HashMapBuilder.put(
			LocaleUtil.US, "Test Journal Article"
		).build();

		_article = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASS_NAME_ID_DEFAULT, titleMap, titleMap,
			HashMapBuilder.put(
				LocaleUtil.US, "This test content is in English."
			).build(),
			layout.getUuid(), LocaleUtil.US, null, false, false,
			serviceContext);
	}

	@Test
	public void testJournalArticleFriendlyURL() throws Exception {
		Assert.assertNotNull(
			PortalUtil.getActualURL(
				_group.getGroupId(), false, Portal.PATH_MAIN,
				"/-/test-journal-article", new HashMap<>(),
				_getRequestContext()));
	}

	@Test
	public void testJournalArticleFriendlyURLWithEndingSlash()
		throws Exception {

		Assert.assertNotNull(
			PortalUtil.getActualURL(
				_group.getGroupId(), false, Portal.PATH_MAIN,
				"/-/test-journal-article/", new HashMap<>(),
				_getRequestContext()));
	}

	@Test
	public void testJournalArticleFriendlyURLWithExpiredArticle()
		throws Exception {

		JournalArticleLocalServiceUtil.expireArticle(
			_article.getUserId(), _article.getGroupId(),
			_article.getArticleId(), _article.getUrlTitle(),
			ServiceContextTestUtil.getServiceContext());

		String urlTitle = "/-/test-journal-article";

		try {
			PortalUtil.getActualURL(
				_group.getGroupId(), false, Portal.PATH_MAIN, urlTitle,
				new HashMap<>(), _getRequestContext());

			Assert.fail();
		}
		catch (NoSuchLayoutException noSuchLayoutException) {
			_assertCause(noSuchLayoutException, urlTitle);
		}
	}

	@Test
	public void testJournalArticleFriendlyURLWithNonexistentArticle()
		throws Exception {

		String urlTitle = "/-/nonexistent-test-journal-article";

		try {
			PortalUtil.getActualURL(
				_group.getGroupId(), false, Portal.PATH_MAIN, urlTitle,
				new HashMap<>(), _getRequestContext());

			Assert.fail();
		}
		catch (NoSuchLayoutException noSuchLayoutException) {
			_assertCause(noSuchLayoutException, urlTitle);
		}
	}

	@Test
	public void testJournalArticleFriendlyURLWithTrashedArticle()
		throws Exception {

		JournalArticleLocalServiceUtil.moveArticleToTrash(
			_article.getUserId(), _article);

		String urlTitle = "/-/test-journal-article";

		try {
			PortalUtil.getActualURL(
				_group.getGroupId(), false, Portal.PATH_MAIN, urlTitle,
				new HashMap<>(), _getRequestContext());

			Assert.fail();
		}
		catch (NoSuchLayoutException noSuchLayoutException) {
			_assertCause(noSuchLayoutException, urlTitle);
		}
	}

	private void _assertCause(
		NoSuchLayoutException noSuchLayoutException, String urlTitle) {

		Throwable throwable = noSuchLayoutException.getCause();

		Assert.assertTrue(
			String.valueOf(throwable),
			throwable instanceof NoSuchArticleException);

		urlTitle = urlTitle.substring(
			JournalArticleConstants.CANONICAL_URL_SEPARATOR.length());

		Assert.assertEquals(
			StringBundler.concat(
				"No JournalArticle exists with the key {groupId=",
				_group.getGroupId(), ", urlTitle=", urlTitle, ", status=",
				WorkflowConstants.STATUS_PENDING, "}"),
			throwable.getMessage());
	}

	private Map<String, Object> _getRequestContext() {
		return Collections.singletonMap(
			"request", new MockHttpServletRequest(Method.GET, "/"));
	}

	private JournalArticle _article;

	@DeleteAfterTestRun
	private Group _group;

}