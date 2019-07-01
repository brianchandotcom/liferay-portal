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

package com.liferay.blogs.web.internal.portlet.action;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.blogs.constants.BlogsPortletKeys;
import com.liferay.blogs.exception.NoSuchEntryException;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + BlogsPortletKeys.BLOGS,
		"javax.portlet.name=" + BlogsPortletKeys.BLOGS_ADMIN,
		"javax.portlet.name=" + BlogsPortletKeys.BLOGS_AGGREGATOR,
		"mvc.command.name=/blogs/view_entry"
	},
	service = MVCRenderCommand.class
)
public class ViewEntryMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		long assetCategoryId = ParamUtil.getLong(renderRequest, "categoryId");
		String assetCategoryName = ParamUtil.getString(renderRequest, "tag");

		if ((assetCategoryId > 0) || Validator.isNotNull(assetCategoryName)) {
			return "/blogs/view.jsp";
		}

		try {
			boolean redirectToLastFriendlyURL = ParamUtil.getBoolean(
				renderRequest, "redirectToLastFriendlyURL", true);

			BlogsEntry entry = ActionUtil.getEntry(renderRequest);

			ThemeDisplay themeDisplay =
				(ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

			String assetDisplayPageFriendlyURL =
				_assetDisplayPageFriendlyURLProvider.getFriendlyURL(
					BlogsEntry.class.getName(), entry.getEntryId(),
					themeDisplay);

			if (assetDisplayPageFriendlyURL != null) {
				HttpServletResponse httpServletResponse =
					_portal.getHttpServletResponse(renderResponse);

				httpServletResponse.sendRedirect(assetDisplayPageFriendlyURL);

				return MVCRenderConstants.MVC_PATH_VALUE_SKIP_DISPATCH;
			}

			FriendlyURLEntry mainFriendlyURLEntry =
				_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
					BlogsEntry.class, entry.getEntryId());

			String urlTitle = ParamUtil.getString(renderRequest, "urlTitle");

			if (redirectToLastFriendlyURL && Validator.isNotNull(urlTitle) &&
				!urlTitle.equals(mainFriendlyURLEntry.getUrlTitle())) {

				PortletURL portletURL = renderResponse.createRenderURL();

				portletURL.setParameter(
					"mvcRenderCommandName", "/blogs/view_entry");
				portletURL.setParameter(
					"urlTitle", mainFriendlyURLEntry.getUrlTitle());

				HttpServletResponse httpServletResponse =
					_portal.getHttpServletResponse(renderResponse);

				httpServletResponse.sendRedirect(portletURL.toString());

				return MVCRenderConstants.MVC_PATH_VALUE_SKIP_DISPATCH;
			}

			HttpServletRequest httpServletRequest =
				_portal.getHttpServletRequest(renderRequest);

			httpServletRequest.setAttribute(WebKeys.BLOGS_ENTRY, entry);

			if (PropsValues.BLOGS_PINGBACK_ENABLED && (entry != null) &&
				entry.isAllowPingbacks()) {

				HttpServletResponse httpServletResponse =
					_portal.getHttpServletResponse(renderResponse);

				httpServletResponse.addHeader(
					"X-Pingback",
					_portal.getPortalURL(renderRequest) + "/xmlrpc/pingback");
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchEntryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return "/blogs/error.jsp";
			}

			throw new PortletException(e);
		}

		return "/blogs/view_entry.jsp";
	}

	@Reference
	private AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Reference
	private Portal _portal;

}