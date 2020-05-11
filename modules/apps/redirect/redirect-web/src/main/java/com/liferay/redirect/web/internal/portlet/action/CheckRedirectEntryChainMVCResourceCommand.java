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

package com.liferay.redirect.web.internal.portlet.action;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.redirect.model.RedirectEntry;
import com.liferay.redirect.service.RedirectEntryLocalService;
import com.liferay.redirect.web.internal.constants.RedirectPortletKeys;
import com.liferay.redirect.web.internal.util.RedirectUtil;

import java.util.List;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = {
		"javax.portlet.name=" + RedirectPortletKeys.REDIRECT,
		"mvc.command.name=/redirect/check_redirect_entry_chain"
	},
	service = MVCResourceCommand.class
)
public class CheckRedirectEntryChainMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse,
			JSONUtil.put(
				"redirectEntryChainCause",
				_getRedirectEntryChainCause(resourceRequest)));
	}

	private String _getRedirectEntryChainCause(
		ResourceRequest resourceRequest) {

		String sourceURL = ParamUtil.getString(resourceRequest, "sourceURL");

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		List<RedirectEntry> redirectEntries =
			_redirectEntryLocalService.getRedirectEntries(
				themeDisplay.getScopeGroupId(),
				RedirectUtil.getGroupBaseURL(themeDisplay) +
					StringPool.FORWARD_SLASH + sourceURL);

		if (!ListUtil.isEmpty(redirectEntries)) {
			return "sourceURL";
		}

		String destinationURL = ParamUtil.getString(
			resourceRequest, "destinationURL");

		RedirectEntry redirectEntry =
			_redirectEntryLocalService.fetchRedirectEntry(
				themeDisplay.getScopeGroupId(),
				StringUtil.removeSubstring(
					destinationURL,
					RedirectUtil.getGroupBaseURL(themeDisplay) +
						StringPool.SLASH));

		if (redirectEntry != null) {
			return "destinationURL";
		}

		return null;
	}

	@Reference
	private RedirectEntryLocalService _redirectEntryLocalService;

}