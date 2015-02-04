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

package com.liferay.portlet.journal.service.permission;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.permission.test.BasePermissionTestCase;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.util.test.JournalTestUtil;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Eric Chin
 * @author Shinn Lok
 */
public class JournalArticlePermissionTest extends BasePermissionTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Test
	public void testContains() throws Exception {
		Assert.assertTrue(
			JournalArticlePermission.contains(
				permissionChecker, _article, ActionKeys.VIEW));
		Assert.assertTrue(
			JournalArticlePermission.contains(
				permissionChecker, _subarticle, ActionKeys.VIEW));

		removePortletModelViewPermission();

		if (PropsValues.JOURNAL_ARTICLE_VIEW_PERMISSION_CHECK_ENABLED) {
			Assert.assertFalse(
				JournalArticlePermission.contains(
					permissionChecker, _article, ActionKeys.VIEW));
			Assert.assertFalse(
				JournalArticlePermission.contains(
					permissionChecker, _subarticle, ActionKeys.VIEW));
		}
		else {
			Assert.assertTrue(
				JournalArticlePermission.contains(
					permissionChecker, _article, ActionKeys.VIEW));
			Assert.assertTrue(
				JournalArticlePermission.contains(
					permissionChecker, _subarticle, ActionKeys.VIEW));
		}
	}

	@Override
	protected void doSetUp() throws Exception {
		_article = JournalTestUtil.addArticle(
			group.getGroupId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		JournalFolder folder = JournalTestUtil.addFolder(
			group.getGroupId(), RandomTestUtil.randomString());

		_subarticle = JournalTestUtil.addArticle(
			group.getGroupId(), folder.getFolderId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString());
	}

	@Override
	protected String getResourceName() {
		return JournalPermission.RESOURCE_NAME;
	}

	private JournalArticle _article;
	private JournalArticle _subarticle;

}