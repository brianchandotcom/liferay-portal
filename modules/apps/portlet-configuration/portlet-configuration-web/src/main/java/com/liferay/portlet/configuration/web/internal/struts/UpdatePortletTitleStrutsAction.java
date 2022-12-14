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

package com.liferay.portlet.configuration.web.internal.struts;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.InvokerPortletUtil;

import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ming-Gih Lam
 */
@Component(
	property = "path=/portal/update_portlet_title", service = StrutsAction.class
)
public class UpdatePortletTitleStrutsAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		String portletId = ParamUtil.getString(httpServletRequest, "portletId");

		if (!PortletPermissionUtil.contains(
				themeDisplay.getPermissionChecker(), layout, portletId,
				ActionKeys.CONFIGURATION)) {

			return null;
		}

		String languageId = _language.getLanguageId(httpServletRequest);
		String title = ParamUtil.getString(httpServletRequest, "title");

		PortletPreferences portletSetup =
			themeDisplay.getStrictLayoutPortletSetup(layout, portletId);

		portletSetup.setValue("portletSetupTitle_" + languageId, title);
		portletSetup.setValue("portletSetupUseCustomTitle", "true");

		portletSetup.store();

		if (layout.isTypeContent()) {
			Layout draftLayout = layout.fetchDraftLayout();

			if (draftLayout != null) {
				PortletPreferences draftLayoutPortletSetup =
					themeDisplay.getStrictLayoutPortletSetup(
						draftLayout, portletId);

				draftLayoutPortletSetup.setValue(
					"portletSetupTitle_" + languageId, title);
				draftLayoutPortletSetup.setValue(
					"portletSetupUseCustomTitle", "true");

				draftLayoutPortletSetup.store();
			}
		}

		InvokerPortletUtil.clearResponse(
			httpServletRequest.getSession(), layout.getPrimaryKey(), portletId,
			_language.getLanguageId(httpServletRequest));

		return null;
	}

	@Reference
	private Language _language;

}