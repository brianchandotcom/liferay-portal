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

package com.liferay.portal.security.access.control;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.security.access.control.AccessControlPolicy;
import com.liferay.portal.kernel.security.access.control.AccessControlThreadLocal;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.util.PropsValues;

import java.lang.reflect.Method;

/**
 * @author Tomas Polesovsky
 * @author Igor Spasic
 * @author Michael C. Han
 * @author Raymond Augé
 */
public class AccessControlAdvisorImpl implements AccessControlAdvisor {

	@Override
	public void accept(
			Method method, Object[] arguments,
			AccessControlled accessControlled)
		throws SecurityException {

		if (AccessControlThreadLocal.isRemoteAccess()) {
			try {
				for (AccessControlPolicy accessControlPolicy :
						_accessControlPolicies) {

					accessControlPolicy.onServiceRemoteAccess(
						method, arguments, accessControlled);
				}
			}
			catch (SecurityException securityException) {
				if (_log.isDebugEnabled()) {
					_log.debug(securityException, securityException);
				}

				if (PropsValues.ACCESS_CONTROL_SANITIZE_SECURITY_EXCEPTION) {
					throw new SecurityException();
				}

				throw securityException;
			}
		}
		else {
			for (AccessControlPolicy accessControlPolicy :
					_accessControlPolicies) {

				accessControlPolicy.onServiceAccess(
					method, arguments, accessControlled);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AccessControlAdvisorImpl.class.getName());

	private static final ServiceTrackerList<AccessControlPolicy>
		_accessControlPolicies = ServiceTrackerListFactory.open(
			SystemBundleUtil.getBundleContext(), AccessControlPolicy.class);

}