/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.pacl.PACLClassLoaderUtil;
import com.liferay.portal.util.PropsValues;

/**
 * @author Sergio González
 * @author Shuyang Zhou
 */
public class MembershipPolicyFactory {

	public static MembershipPolicy getInstance() {
		return _membershipPolicy;
	}

	public static void setInstance(MembershipPolicy membershipPolicy) {
		if (membershipPolicy == null) {

			// On reset, fallback to portal default.

			MembershipPolicyFactory membershipPolicyFactory =
				new MembershipPolicyFactory();

			membershipPolicyFactory.afterPropertiesSet();
		}
		else {
			_membershipPolicy = membershipPolicy;
		}
	}

	public void afterPropertiesSet() {
		if (Validator.isNotNull(PropsValues.USERS_MEMBERSHIP_POLICY)) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Instantiate " + PropsValues.USERS_MEMBERSHIP_POLICY);
			}

			ClassLoader classLoader =
				PACLClassLoaderUtil.getPortalClassLoader();

			try {
				_membershipPolicy =
					(MembershipPolicy)InstanceFactory.newInstance(
						classLoader, PropsValues.USERS_MEMBERSHIP_POLICY);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		MembershipPolicyFactory.class);

	private static volatile MembershipPolicy _membershipPolicy;

}