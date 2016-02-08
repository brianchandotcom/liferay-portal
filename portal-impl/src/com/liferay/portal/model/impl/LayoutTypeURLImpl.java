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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypeAccessPolicy;
import com.liferay.portal.kernel.model.LayoutTypeController;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;

import java.util.Map;

/**
 * @author László Csontos
 */
public class LayoutTypeURLImpl extends LayoutTypePortletImpl {

	public LayoutTypeURLImpl(
		Layout layout, LayoutTypeController layoutTypeController,
		LayoutTypeAccessPolicy layoutTypeAccessPolicy) {

		super(layout, layoutTypeController, layoutTypeAccessPolicy);
	}

	@Override
	public String getURL(Map<String, String> variables) {
		if (hasViewPermission()) {
			return super.getURL(variables);
		}

		return replaceVariables(getDefaultURL(), variables);
	}

	protected boolean hasViewPermission() {
		try {
			LayoutTypeAccessPolicy layoutTypeAccessPolicy =
				getLayoutTypeAccessPolicy();

			return layoutTypeAccessPolicy.isViewLayoutAllowed(
				PermissionThreadLocal.getPermissionChecker(), getLayout());
		}
		catch (PortalException pe) {
			_log.error(pe);

			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutTypeURLImpl.class);

}