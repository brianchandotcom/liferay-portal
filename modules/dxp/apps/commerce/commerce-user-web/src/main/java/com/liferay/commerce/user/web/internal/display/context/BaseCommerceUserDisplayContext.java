/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.user.web.internal.display.context;

import com.liferay.commerce.user.service.CommerceUserService;
import com.liferay.commerce.user.web.internal.display.context.util.CommerceUserRequestHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public abstract class BaseCommerceUserDisplayContext {

	public BaseCommerceUserDisplayContext(
		CommerceUserService commerceUserService,
		HttpServletRequest httpServletRequest, Portal portal) {

		this.commerceUserService = commerceUserService;
		this.portal = portal;

		commerceUserRequestHelper = new CommerceUserRequestHelper(
			httpServletRequest);
	}

	public User getSelectedUser() throws PortalException {
		long userId = ParamUtil.getLong(
			commerceUserRequestHelper.getRequest(), "userId");

		if (userId > 0) {
			return commerceUserService.getUser(userId);
		}

		return portal.getUser(commerceUserRequestHelper.getRequest());
	}

	protected final CommerceUserRequestHelper commerceUserRequestHelper;
	protected final CommerceUserService commerceUserService;
	protected final Portal portal;

}