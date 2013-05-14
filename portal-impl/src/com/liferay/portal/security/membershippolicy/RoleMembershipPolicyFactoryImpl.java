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
 * @author Roberto Díaz
 */
public class RoleMembershipPolicyFactoryImpl
	implements RoleMembershipPolicyFactory {

	public void afterPropertiesSet() throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Instantiate " + PropsValues.MEMBERSHIP_POLICY_ROLES);
		}

		ClassLoader classLoader = ClassLoaderUtil.getPortalClassLoader();

		_originalRoleMembershipPolicy =
			(RoleMembershipPolicy)InstanceFactory.newInstance(
				classLoader, PropsValues.MEMBERSHIP_POLICY_ROLES);

		_roleMembershipPolicy = _originalRoleMembershipPolicy;
	}

	@Override
	public RoleMembershipPolicy getRoleMembershipPolicy() {
		return _roleMembershipPolicy;
	}

	public void setRoleMembershipPolicy(
		RoleMembershipPolicy roleMembershipPolicy) {

		if (_log.isDebugEnabled()) {
			_log.debug("Set " + ClassUtil.getClassName(roleMembershipPolicy));
		}

		if (roleMembershipPolicy == null) {
			_roleMembershipPolicy = _originalRoleMembershipPolicy;
		}
		else {
			_roleMembershipPolicy = roleMembershipPolicy;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		RoleMembershipPolicyFactory.class);

	private static RoleMembershipPolicy _originalRoleMembershipPolicy;
	private static volatile RoleMembershipPolicy _roleMembershipPolicy;

}