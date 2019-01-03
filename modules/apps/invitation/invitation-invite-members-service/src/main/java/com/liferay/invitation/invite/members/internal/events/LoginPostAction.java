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

package com.liferay.invitation.invite.members.internal.events;

import com.liferay.invitation.invite.members.service.MemberRequestLocalService;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.events.LifecycleEvent;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	immediate = true, property = "key=" + PropsKeys.LOGIN_EVENTS_POST,
	service = LifecycleAction.class
)
public class LoginPostAction implements LifecycleAction {

	@Override
	public void processLifecycleEvent(LifecycleEvent lifecycleEvent)
		throws ActionException {

		try {
			String ppid = ParamUtil.getString(
				lifecycleEvent.getRequest(), "p_p_id");

			String portletNamespace = _portal.getPortletNamespace(ppid);

			String memberRequestKey = ParamUtil.getString(
				lifecycleEvent.getRequest(), portletNamespace.concat("key"));

			if (Validator.isNull(memberRequestKey)) {
				return;
			}

			User user = _portal.getUser(lifecycleEvent.getRequest());

			_memberRequestLocalService.updateMemberRequest(
				memberRequestKey, user.getUserId());
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

	@Reference(unbind = "-")
	protected void setMemberRequestLocalService(
		MemberRequestLocalService memberRequestLocalService) {

		_memberRequestLocalService = memberRequestLocalService;
	}

	@Reference(unbind = "-")
	protected void setPortal(Portal portal) {
		_portal = portal;
	}

	private MemberRequestLocalService _memberRequestLocalService;
	private Portal _portal;

}