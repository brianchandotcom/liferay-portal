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

package com.liferay.portlet.social.service;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.comparator.UserScreenNameComparator;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.social.kernel.model.SocialRelationConstants;
import com.liferay.social.kernel.service.SocialRelationLocalServiceUtil;

import java.util.List;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Brian Wing Shun Chan
 */
public class SocialRelationLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {

		// Users

		for (int i = 0; i < 9; i++) {
			_dlcUsers[i] = UserTestUtil.addUser("dlc" + (i + 1), null);
		}

		for (int i = 0; i < 9; i++) {
			_fraUsers[i] = UserTestUtil.addUser("fra" + (i + 1), null);
		}

		// Friend

		SocialRelationLocalServiceUtil.addRelation(
			_dlcUsers[0].getUserId(), _dlcUsers[1].getUserId(),
			SocialRelationConstants.TYPE_BI_FRIEND);

		SocialRelationLocalServiceUtil.addRelation(
			_dlcUsers[0].getUserId(), _dlcUsers[2].getUserId(),
			SocialRelationConstants.TYPE_BI_FRIEND);

		SocialRelationLocalServiceUtil.addRelation(
			_dlcUsers[0].getUserId(), _dlcUsers[3].getUserId(),
			SocialRelationConstants.TYPE_BI_FRIEND);

		SocialRelationLocalServiceUtil.addRelation(
			_dlcUsers[0].getUserId(), _dlcUsers[4].getUserId(),
			SocialRelationConstants.TYPE_BI_FRIEND);

		SocialRelationLocalServiceUtil.addRelation(
			_dlcUsers[0].getUserId(), _dlcUsers[5].getUserId(),
			SocialRelationConstants.TYPE_BI_FRIEND);

		SocialRelationLocalServiceUtil.addRelation(
			_dlcUsers[0].getUserId(), _dlcUsers[6].getUserId(),
			SocialRelationConstants.TYPE_BI_FRIEND);

		SocialRelationLocalServiceUtil.addRelation(
			_dlcUsers[0].getUserId(), _dlcUsers[7].getUserId(),
			SocialRelationConstants.TYPE_BI_FRIEND);

		// Friend

		SocialRelationLocalServiceUtil.addRelation(
			_dlcUsers[1].getUserId(), _dlcUsers[2].getUserId(),
			SocialRelationConstants.TYPE_BI_FRIEND);

		SocialRelationLocalServiceUtil.addRelation(
			_dlcUsers[1].getUserId(), _dlcUsers[3].getUserId(),
			SocialRelationConstants.TYPE_BI_FRIEND);

		SocialRelationLocalServiceUtil.addRelation(
			_dlcUsers[1].getUserId(), _dlcUsers[4].getUserId(),
			SocialRelationConstants.TYPE_BI_FRIEND);

		// Coworker

		SocialRelationLocalServiceUtil.addRelation(
			_dlcUsers[0].getUserId(), _dlcUsers[8].getUserId(),
			SocialRelationConstants.TYPE_BI_COWORKER);

		SocialRelationLocalServiceUtil.addRelation(
			_dlcUsers[1].getUserId(), _dlcUsers[8].getUserId(),
			SocialRelationConstants.TYPE_BI_COWORKER);

		// Romantic partner

		SocialRelationLocalServiceUtil.addRelation(
			_dlcUsers[0].getUserId(), _dlcUsers[1].getUserId(),
			SocialRelationConstants.TYPE_BI_ROMANTIC_PARTNER);

		// Parent

		SocialRelationLocalServiceUtil.addRelation(
			_fraUsers[0].getUserId(), _fraUsers[1].getUserId(),
			SocialRelationConstants.TYPE_UNI_PARENT);

		SocialRelationLocalServiceUtil.addRelation(
			_fraUsers[0].getUserId(), _fraUsers[2].getUserId(),
			SocialRelationConstants.TYPE_UNI_PARENT);

		SocialRelationLocalServiceUtil.addRelation(
			_fraUsers[0].getUserId(), _fraUsers[3].getUserId(),
			SocialRelationConstants.TYPE_UNI_PARENT);

		SocialRelationLocalServiceUtil.addRelation(
			_fraUsers[0].getUserId(), _fraUsers[4].getUserId(),
			SocialRelationConstants.TYPE_UNI_PARENT);

		SocialRelationLocalServiceUtil.addRelation(
			_fraUsers[0].getUserId(), _fraUsers[5].getUserId(),
			SocialRelationConstants.TYPE_UNI_PARENT);

		SocialRelationLocalServiceUtil.addRelation(
			_fraUsers[0].getUserId(), _fraUsers[6].getUserId(),
			SocialRelationConstants.TYPE_UNI_PARENT);

		SocialRelationLocalServiceUtil.addRelation(
			_fraUsers[0].getUserId(), _fraUsers[7].getUserId(),
			SocialRelationConstants.TYPE_UNI_PARENT);

		SocialRelationLocalServiceUtil.addRelation(
			_fraUsers[0].getUserId(), _fraUsers[8].getUserId(),
			SocialRelationConstants.TYPE_UNI_PARENT);

		// Child

		SocialRelationLocalServiceUtil.addRelation(
			_fraUsers[2].getUserId(), _fraUsers[0].getUserId(),
			SocialRelationConstants.TYPE_UNI_CHILD);

		SocialRelationLocalServiceUtil.addRelation(
			_fraUsers[2].getUserId(), _fraUsers[1].getUserId(),
			SocialRelationConstants.TYPE_UNI_CHILD);

		SocialRelationLocalServiceUtil.addRelation(
			_fraUsers[3].getUserId(), _fraUsers[0].getUserId(),
			SocialRelationConstants.TYPE_UNI_CHILD);

		SocialRelationLocalServiceUtil.addRelation(
			_fraUsers[3].getUserId(), _fraUsers[1].getUserId(),
			SocialRelationConstants.TYPE_UNI_CHILD);

		SocialRelationLocalServiceUtil.addRelation(
			_fraUsers[4].getUserId(), _fraUsers[0].getUserId(),
			SocialRelationConstants.TYPE_UNI_CHILD);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		for (User user : _dlcUsers) {
			UserLocalServiceUtil.deleteUser(user);
		}

		for (User user : _fraUsers) {
			UserLocalServiceUtil.deleteUser(user);
		}
	}

	@Test
	public void testGetMultipleGroups() throws Exception {
		User dlc3User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.getCompanyId(), "dlc3");

		List<User> groupUsers = UserLocalServiceUtil.getGroupUsers(
			TestPropsValues.getGroupId());

		GroupLocalServiceUtil.addUserGroup(
			dlc3User.getUserId(), TestPropsValues.getGroupId());

		User dlc4User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.getCompanyId(), "dlc4");

		GroupLocalServiceUtil.addUserGroup(
			dlc4User.getUserId(), TestPropsValues.getGroupId());

		Set<Long> groupIds = SetUtil.intersect(
			dlc3User.getGroupIds(), dlc4User.getGroupIds());

		List<User> users = UserLocalServiceUtil.searchSocial(
			TestPropsValues.getCompanyId(),
			ArrayUtil.toArray(groupIds.toArray(new Long[groupIds.size()])),
			"dlc", QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		users = ListUtil.remove(users, groupUsers);

		Assert.assertEquals(users.toString(), 2, users.size());

		GroupLocalServiceUtil.deleteUserGroup(
			dlc3User.getUserId(), TestPropsValues.getGroupId());
		GroupLocalServiceUtil.deleteUserGroup(
			dlc4User.getUserId(), TestPropsValues.getGroupId());
	}

	@Test
	public void testGetMultipleRelations() throws Exception {
		User dlc2User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.getCompanyId(), "dlc2");

		User dlc3User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.getCompanyId(), "dlc3");

		GroupLocalServiceUtil.addUserGroup(
			dlc3User.getUserId(), TestPropsValues.getGroupId());

		User dlc4User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.getCompanyId(), "dlc4");

		GroupLocalServiceUtil.addUserGroup(
			dlc4User.getUserId(), TestPropsValues.getGroupId());

		int[] socialRelationTypes = {
			SocialRelationConstants.TYPE_BI_FRIEND,
			SocialRelationConstants.TYPE_BI_COWORKER
		};

		// Does dlc2 should have 1 coworker and 4 friends?

		List<User> users = UserLocalServiceUtil.searchSocial(
			dlc2User.getUserId(), socialRelationTypes, "dlc", QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(users.toString(), 5, users.size());

		GroupLocalServiceUtil.deleteUserGroup(
			dlc3User.getUserId(), TestPropsValues.getGroupId());
		GroupLocalServiceUtil.deleteUserGroup(
			dlc4User.getUserId(), TestPropsValues.getGroupId());
	}

	@Test
	public void testGetMutualRelations() throws Exception {
		User dlc1User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.getCompanyId(), "dlc1");

		User dlc2User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.getCompanyId(), "dlc2");

		// Do dlc1 and dlc2 have 4 mutual relations?

		List<User> users = UserLocalServiceUtil.getSocialUsers(
			dlc1User.getUserId(), dlc2User.getUserId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, new UserScreenNameComparator(true));

		Assert.assertEquals(users.toString(), 4, users.size());
	}

	@Test
	public void testGetMutualRelationsByBiType() throws Exception {
		User dlc1User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.getCompanyId(), "dlc1");

		User dlc2User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.getCompanyId(), "dlc2");

		// Do dlc1 and dlc2 have 3 mutual friends?

		List<User> users = UserLocalServiceUtil.getSocialUsers(
			dlc1User.getUserId(), dlc2User.getUserId(),
			SocialRelationConstants.TYPE_BI_FRIEND, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, new UserScreenNameComparator(true));

		Assert.assertEquals(users.toString(), 3, users.size());

		Assert.assertEquals("dlc3", users.get(0).getScreenName());
		Assert.assertEquals("dlc4", users.get(1).getScreenName());
		Assert.assertEquals("dlc5", users.get(2).getScreenName());
	}

	@Test
	public void testGetMutualRelationsByUniType() throws Exception {
		User fra3User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.getCompanyId(), "fra3");

		User fra4User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.getCompanyId(), "fra4");

		User fra5User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.getCompanyId(), "fra5");

		// Are fra3 and fra4 both children of fra1 and fra2?

		List<User> users = UserLocalServiceUtil.getSocialUsers(
			fra3User.getUserId(), fra4User.getUserId(),
			SocialRelationConstants.TYPE_UNI_CHILD, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, new UserScreenNameComparator(true));

		Assert.assertEquals(users.toString(), 2, users.size());

		Assert.assertEquals("fra1", users.get(0).getScreenName());
		Assert.assertEquals("fra2", users.get(1).getScreenName());

		// Are fra3 and fra5 both children of fra1?

		users = UserLocalServiceUtil.getSocialUsers(
			fra3User.getUserId(), fra5User.getUserId(),
			SocialRelationConstants.TYPE_UNI_CHILD, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, new UserScreenNameComparator(true));

		Assert.assertEquals(users.toString(), 1, users.size());

		Assert.assertEquals("fra1", users.get(0).getScreenName());
	}

	@Test
	public void testGetRelations() throws Exception {
		User dlc1User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.getCompanyId(), "dlc1");

		// Does dlc1 have 8 relations?

		List<User> users = UserLocalServiceUtil.getSocialUsers(
			dlc1User.getUserId(), SocialRelationConstants.TYPE_UNI_ENEMY,
			StringPool.NOT_EQUAL, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new UserScreenNameComparator(true));

		Assert.assertEquals(users.toString(), 8, users.size());

		Assert.assertEquals("dlc2", users.get(0).getScreenName());
		Assert.assertEquals("dlc3", users.get(1).getScreenName());
		Assert.assertEquals("dlc4", users.get(2).getScreenName());
		Assert.assertEquals("dlc5", users.get(3).getScreenName());
		Assert.assertEquals("dlc6", users.get(4).getScreenName());
		Assert.assertEquals("dlc7", users.get(5).getScreenName());
		Assert.assertEquals("dlc8", users.get(6).getScreenName());
		Assert.assertEquals("dlc9", users.get(7).getScreenName());
	}

	@Test
	public void testGetRelationsByBiType() throws Exception {
		User dlc1User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.getCompanyId(), "dlc1");

		User dlc2User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.getCompanyId(), "dlc2");

		User dlc3User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.getCompanyId(), "dlc3");

		// Does dlc1 have 7 friends?

		List<User> users = UserLocalServiceUtil.getSocialUsers(
			dlc1User.getUserId(), SocialRelationConstants.TYPE_BI_FRIEND,
			StringPool.EQUAL, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new UserScreenNameComparator(true));

		Assert.assertEquals(users.toString(), 7, users.size());

		Assert.assertEquals("dlc2", users.get(0).getScreenName());
		Assert.assertEquals("dlc3", users.get(1).getScreenName());
		Assert.assertEquals("dlc4", users.get(2).getScreenName());
		Assert.assertEquals("dlc5", users.get(3).getScreenName());
		Assert.assertEquals("dlc6", users.get(4).getScreenName());
		Assert.assertEquals("dlc7", users.get(5).getScreenName());
		Assert.assertEquals("dlc8", users.get(6).getScreenName());

		// Is dlc1 a coworker of dlc9?

		users = UserLocalServiceUtil.getSocialUsers(
			dlc1User.getUserId(), SocialRelationConstants.TYPE_BI_COWORKER,
			StringPool.EQUAL, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new UserScreenNameComparator(true));

		Assert.assertEquals(users.toString(), 1, users.size());

		Assert.assertEquals("dlc9", users.get(0).getScreenName());

		// Is dlc1 romantically involved with dlc2?

		users = UserLocalServiceUtil.getSocialUsers(
			dlc1User.getUserId(),
			SocialRelationConstants.TYPE_BI_ROMANTIC_PARTNER, StringPool.EQUAL,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new UserScreenNameComparator(true));

		Assert.assertEquals(users.toString(), 1, users.size());

		Assert.assertEquals("dlc2", users.get(0).getScreenName());

		// Is dlc2 romantically involved with dlc1?

		users = UserLocalServiceUtil.getSocialUsers(
			dlc2User.getUserId(),
			SocialRelationConstants.TYPE_BI_ROMANTIC_PARTNER, StringPool.EQUAL,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new UserScreenNameComparator(true));

		Assert.assertEquals(users.toString(), 1, users.size());

		Assert.assertEquals("dlc1", users.get(0).getScreenName());

		// Is dlc3 romantically involved with anyone?

		users = UserLocalServiceUtil.getSocialUsers(
			dlc3User.getUserId(),
			SocialRelationConstants.TYPE_BI_ROMANTIC_PARTNER, StringPool.EQUAL,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new UserScreenNameComparator(true));

		Assert.assertEquals(users.toString(), 0, users.size());
	}

	@Test
	public void testGetRelationsByUniType() throws Exception {
		User fra1User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.getCompanyId(), "fra1");

		User fra2User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.getCompanyId(), "fra2");

		User fra3User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.getCompanyId(), "fra3");

		User fra6User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.getCompanyId(), "fra6");

		// Is fra1 a parent to 8 children?

		List<User> users = UserLocalServiceUtil.getSocialUsers(
			fra1User.getUserId(), SocialRelationConstants.TYPE_UNI_PARENT,
			StringPool.EQUAL, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new UserScreenNameComparator(true));

		Assert.assertEquals(users.toString(), 8, users.size());

		Assert.assertEquals("fra2", users.get(0).getScreenName());
		Assert.assertEquals("fra3", users.get(1).getScreenName());
		Assert.assertEquals("fra4", users.get(2).getScreenName());
		Assert.assertEquals("fra5", users.get(3).getScreenName());
		Assert.assertEquals("fra6", users.get(4).getScreenName());
		Assert.assertEquals("fra7", users.get(5).getScreenName());
		Assert.assertEquals("fra8", users.get(6).getScreenName());
		Assert.assertEquals("fra9", users.get(7).getScreenName());

		// Is fra2 a parent of anyone?

		users = UserLocalServiceUtil.getSocialUsers(
			fra2User.getUserId(), SocialRelationConstants.TYPE_UNI_PARENT,
			StringPool.EQUAL, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new UserScreenNameComparator(true));

		Assert.assertEquals(users.toString(), 0, users.size());

		// Is fra3 a child of anyone?

		users = UserLocalServiceUtil.getSocialUsers(
			fra3User.getUserId(), SocialRelationConstants.TYPE_UNI_CHILD,
			StringPool.EQUAL, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new UserScreenNameComparator(true));

		Assert.assertEquals(users.toString(), 2, users.size());

		Assert.assertEquals("fra1", users.get(0).getScreenName());
		Assert.assertEquals("fra2", users.get(1).getScreenName());

		// Is fra6 a child of fra1?

		users = UserLocalServiceUtil.getSocialUsers(
			fra6User.getUserId(), SocialRelationConstants.TYPE_UNI_CHILD,
			StringPool.EQUAL, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new UserScreenNameComparator(true));

		Assert.assertEquals(users.toString(), 0, users.size());
	}

	private static final User[] _dlcUsers = new User[9];
	private static final User[] _fraUsers = new User[9];

}