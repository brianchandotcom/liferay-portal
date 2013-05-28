/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.membershippolicy;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.util.ClassLoaderUtil;
import com.liferay.portal.util.PropsValues;

/**
 * @author Sergio González
 * @author Shuyang Zhou
 */
public class UserGroupMembershipPolicyFactoryImpl
	implements UserGroupMembershipPolicyFactory {

	public void afterPropertiesSet() throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug(
				"Instantiate " + PropsValues.MEMBERSHIP_POLICY_USER_GROUPS);
		}

		ClassLoader classLoader = ClassLoaderUtil.getPortalClassLoader();

		_originalUserGroupMembershipPolicy =
			(UserGroupMembershipPolicy)InstanceFactory.newInstance(
				classLoader, PropsValues.MEMBERSHIP_POLICY_USER_GROUPS);

		_userGroupMembershipPolicy = _originalUserGroupMembershipPolicy;
	}

	@Override
	public UserGroupMembershipPolicy getUserGroupMembershipPolicy() {
		return _userGroupMembershipPolicy;
	}

	public void setUserGroupMembershipPolicy(
		UserGroupMembershipPolicy userGroupMembershipPolicy) {

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Set " + ClassUtil.getClassName(userGroupMembershipPolicy));
		}

		if (userGroupMembershipPolicy == null) {
			_userGroupMembershipPolicy = _originalUserGroupMembershipPolicy;
		}
		else {
			_userGroupMembershipPolicy = userGroupMembershipPolicy;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		UserGroupMembershipPolicyFactoryImpl.class);

	private static UserGroupMembershipPolicy _originalUserGroupMembershipPolicy;
	private static volatile UserGroupMembershipPolicy
		_userGroupMembershipPolicy;

}