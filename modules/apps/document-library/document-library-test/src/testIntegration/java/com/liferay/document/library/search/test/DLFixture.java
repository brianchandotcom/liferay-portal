/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.search.test;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;

import java.util.List;
import java.util.Locale;

/**
 * @author Eric Yan
 */
public class DLFixture {

	public DLFixture(List<Group> groups, List<User> users) {
		_groups = groups;
		_users = users;
	}

	public Group addGroup() throws Exception {
		Group group = GroupTestUtil.addGroup();

		_groups.add(group);

		return group;
	}

	public User addUser() throws Exception {
		User user = UserTestUtil.addUser();

		_users.add(user);

		return user;
	}

	public long getUserId() {
		if (_user != null) {
			return _user.getUserId();
		}

		try {
			return TestPropsValues.getUserId();
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public void setGroup(Group group) {
		_group = group;
	}

	public void setUp() throws Exception {
		UserTestUtil.setUser(TestPropsValues.getUser());

		CompanyThreadLocal.setCompanyId(TestPropsValues.getCompanyId());
	}

	public void setUser(User user) {
		_user = user;
	}

	public void updateDisplaySettings(Locale locale) throws Exception {
		Group group = GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(), null, locale);

		_group.setModelAttributes(group.getModelAttributes());
	}

	protected long getGroupId() {
		return _group.getGroupId();
	}

	private Group _group;
	private final List<Group> _groups;
	private User _user;
	private final List<User> _users;

}