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

package com.liferay.portal.events;

import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.MembershipPolicy;
import com.liferay.portal.security.auth.MembershipPolicyFactory;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Sergio González
 */
public class MembershipPolicyAction extends Action {

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response)
		throws ActionException {

		try {
			User user = PortalUtil.getUser(request);

			checkMembershipPolicy(user);
		}
		catch (Exception e) {
			throw new ActionException();
		}
	}

	protected void checkMembershipPolicy(User user) throws SystemException {
		MembershipPolicy membershipPolicy =
			MembershipPolicyFactory.getInstance();

		// Pre-filtering to prevent unnecessary service layer entering.

		if (membershipPolicy.isApplicableUser(user)) {
			GroupLocalServiceUtil.checkMembershipPolicy(user);
			UserGroupRoleLocalServiceUtil.checkMembershipPolicy(user);
		}
	}

}