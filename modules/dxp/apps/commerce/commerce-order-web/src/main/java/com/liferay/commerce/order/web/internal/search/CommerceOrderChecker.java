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

package com.liferay.commerce.order.web.internal.search;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.web.security.permission.resource.CommerceOrderPermission;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;

import javax.portlet.PortletResponse;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceOrderChecker extends EmptyOnClickRowChecker {

	public CommerceOrderChecker(PortletResponse portletResponse) {
		super(portletResponse);
	}

	@Override
	public boolean isDisabled(Object object) {
		CommerceOrder commerceOrder = (CommerceOrder)object;

		try {
			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			return !CommerceOrderPermission.contains(
				permissionChecker, commerceOrder, ActionKeys.DELETE);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return super.isDisabled(object);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderChecker.class);

}