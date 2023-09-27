/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		catch (PortalException portalException) {
			_log.error("Unable to check view permission", portalException);

			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutTypeURLImpl.class);

}