/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.workflow.resource.v1_0.test.util;

import com.liferay.headless.admin.workflow.client.dto.v1_0.Assignee;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

/**
 * @author Rafael Praxedes
 */
public class AssigneeTestUtil {

	public static Assignee addAssignee(Group group) throws Exception {
		return addAssignee(group, RoleConstants.SITE_CONTENT_REVIEWER);
	}

	public static Assignee addAssignee(Group group, String roleName)
		throws Exception {

		User user = UserTestUtil.addUser(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			RandomTestUtil.randomString(),
			RandomTestUtil.randomString() + "@liferay.com",
			RandomTestUtil.randomString(), LocaleUtil.getDefault(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			ServiceContextTestUtil.getServiceContext());

		UserTestUtil.addUserGroupRole(
			user.getUserId(), group.getGroupId(), roleName);

		return toAssignee(user);
	}

	public static Assignee toAssignee(User user) {
		return new Assignee() {
			{
				id = user.getUserId();
				name = user.getFullName();
			}
		};
	}

}