/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	property = "key=" + PropsKeys.LOGIN_EVENTS_POST,
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
		catch (Exception exception) {
			throw new ActionException(exception);
		}
	}

	@Reference
	private MemberRequestLocalService _memberRequestLocalService;

	@Reference
	private Portal _portal;

}