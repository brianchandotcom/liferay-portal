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

package com.liferay.journal.info.item.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemPermissionProvider;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class JournalArticleInfoItemPermissionProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addGroupUser(_group, RoleConstants.SITE_MEMBER);
	}

	@Test
	public void testCannotViewArticleWithoutViewPermissions() throws Exception {
		JournalArticle article = JournalTestUtil.addArticle(
			_user.getUserId(), _group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		RoleTestUtil.removeResourcePermission(
			RoleConstants.GUEST, JournalArticle.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(article.getResourcePrimKey()), ActionKeys.VIEW);

		InfoItemPermissionProvider<JournalArticle> infoItemPermissionProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemPermissionProvider.class,
				JournalArticle.class.getName());

		Assert.assertFalse(
			infoItemPermissionProvider.hasPermission(
				PermissionCheckerFactoryUtil.create(
					UserLocalServiceUtil.getDefaultUser(_group.getCompanyId())),
				article, ActionKeys.VIEW));
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private InfoItemServiceTracker _infoItemServiceTracker;

	@DeleteAfterTestRun
	private User _user;

}