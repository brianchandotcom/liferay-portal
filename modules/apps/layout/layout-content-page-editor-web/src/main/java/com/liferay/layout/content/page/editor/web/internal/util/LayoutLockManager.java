/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.util;

import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.portal.kernel.exception.LockedLayoutException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.lock.LockManagerUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.impl.LayoutModelImpl;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Lourdes Fernández Besada
 */
public class LayoutLockManager {

	public static void getLock(ActionRequest actionRequest)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		if (!FeatureFlagManagerUtil.isEnabled("LPS-180328") ||
			!layout.isDraftLayout()) {

			return;
		}

		Lock lock = LockManagerUtil.fetchLock(
			Layout.class.getName(), layout.getPlid());

		if (lock == null) {
			try {
				LockManagerUtil.lock(
					themeDisplay.getUserId(), Layout.class.getName(),
					layout.getPlid(), null, false,
					LayoutModelImpl.LOCK_EXPIRATION_TIME);
			}
			catch (PortalException portalException) {
				throw new LockedLayoutException(portalException);
			}
		}
		else if (lock.getUserId() != themeDisplay.getUserId()) {
			throw new LockedLayoutException();
		}
	}

	public static String getLockedLayoutURL(ActionRequest actionRequest) {
		return PortletURLBuilder.create(
			PortalUtil.getControlPanelPortletURL(
				actionRequest, LayoutAdminPortletKeys.GROUP_PAGES,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/layout_admin/locked_layout"
		).setBackURL(
			() -> {
				String backURL = ParamUtil.getString(actionRequest, "backURL");

				if (Validator.isNotNull(backURL)) {
					return backURL;
				}

				HttpServletRequest httpServletRequest =
					PortalUtil.getHttpServletRequest(actionRequest);

				backURL = ParamUtil.getString(
					httpServletRequest, "p_l_back_url");

				if (Validator.isNotNull(backURL)) {
					return backURL;
				}

				return ParamUtil.getString(httpServletRequest, "redirect");
			}
		).buildString();
	}

}