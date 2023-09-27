/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.locked.layouts.web.internal.events;

import com.liferay.layout.manager.LayoutLockManager;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.Portal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(property = "key=logout.events.post", service = LifecycleAction.class)
public class UnlockLayoutsLogoutPostAction extends Action {

	@Override
	public void run(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws ActionException {

		if (!FeatureFlagManagerUtil.isEnabled("LPS-180328")) {
			return;
		}

		try {
			User user = _portal.getUser(httpServletRequest);

			_layoutLockManager.unlockLayoutsByUserId(
				user.getCompanyId(), user.getUserId());
		}
		catch (Exception exception) {
			throw new ActionException(exception);
		}
	}

	@Reference
	private LayoutLockManager _layoutLockManager;

	@Reference
	private Portal _portal;

}