/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.social.activity.service.test;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portlet.social.util.SocialActivityHierarchyEntryThreadLocal;
import com.liferay.social.activity.service.test.util.SocialActivityTestUtil;
import com.liferay.social.kernel.util.SocialConfigurationUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * @author Zsolt Berentey
 */
public abstract class BaseSocialActivityTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		userClassNameId = PortalUtil.getClassNameId(User.class.getName());

		Class<?> clazz = SocialActivitySettingLocalServiceTest.class;

		String xml = new String(
			FileUtil.getBytes(clazz, "dependencies/liferay-social.xml"));

		SocialConfigurationUtil.read(
			clazz.getClassLoader(), new String[] {xml});
	}

	@Before
	public void setUp() throws Exception {
		group = GroupTestUtil.addGroup();

		actorUser = UserTestUtil.addUser("actor", group.getGroupId());

		creatorUser = UserTestUtil.addUser("creator", group.getGroupId());

		assetEntry = SocialActivityTestUtil.addAssetEntry(
			creatorUser, group, null);

		SocialActivityHierarchyEntryThreadLocal.clear();
	}

	@After
	public void tearDown() throws Exception {
		SocialActivityHierarchyEntryThreadLocal.clear();
	}

	protected static final String TEST_MODEL = "test-model";

	@DeleteAfterTestRun
	protected static User actorUser;

	@DeleteAfterTestRun
	protected static AssetEntry assetEntry;

	@DeleteAfterTestRun
	protected static User creatorUser;

	@DeleteAfterTestRun
	protected static Group group;

	protected static long userClassNameId;

}