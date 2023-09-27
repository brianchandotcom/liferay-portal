/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.subscription.test.util;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;

import org.junit.Before;

/**
 * @author Sergio González
 * @author Roberto Díaz
 */
public abstract class BaseSubscriptionTestCase {

	@Before
	public void setUp() throws Exception {
		group = GroupTestUtil.addGroup();

		user = addUser();

		creatorUser = UserTestUtil.addGroupUser(
			group, RoleConstants.SITE_MEMBER);
	}

	protected long addBaseModel(long userId, long containerModelId)
		throws Exception {

		return 0;
	}

	protected long addContainerModel(long userId, long containerModelId)
		throws Exception {

		return 0;
	}

	protected User addUser() throws Exception {
		return UserTestUtil.addGroupUser(group, RoleConstants.SITE_MEMBER);
	}

	protected void updateBaseModel(long userId, long baseModelId)
		throws Exception {
	}

	protected static final long PARENT_CONTAINER_MODEL_ID_DEFAULT = 0;

	@DeleteAfterTestRun
	protected User creatorUser;

	@DeleteAfterTestRun
	protected Group group;

	@DeleteAfterTestRun
	protected User user;

}