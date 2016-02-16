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

package com.liferay.invitation.invite.members.listeners;

import com.liferay.invitation.invite.members.service.MemberRequestLocalServiceUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Norbert Kocsis
 */
@Component(immediate = true, service = ModelListener.class)
public class UserModelListener extends BaseModelListener<User> {

	@Override
	public void onAfterCreate(User user) {
		try {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			if (serviceContext == null) {
				return;
			}

			Map<String, String> headers = serviceContext.getHeaders();

			if (headers == null) {
				return;
			}

			String refererURL = headers.get(WebKeys.REFERER);

			String portletId = HttpUtil.getParameter(
				refererURL, "p_p_id", false);

			String redirectURL = HttpUtil.getParameter(
				refererURL,
				PortalUtil.getPortletNamespace(portletId) + "redirectURL",
				false);

			String key = HttpUtil.getParameter(
				redirectURL, PortalUtil.getPortletNamespace(portletId) + "key",
				false);

			if (Validator.isNotNull(key)) {
				MemberRequestLocalServiceUtil.updateMemberRequest(
					key, user.getUserId());
			}
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

}