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

import com.liferay.blogs.kernel.exception.NoSuchEntryException;
import com.liferay.blogs.kernel.model.BlogsEntry;
import com.liferay.blogs.web.constants.BlogsPortletKeys;
import com.liferay.friendly.url.model.FriendlyURL;
import com.liferay.friendly.url.service.FriendlyURLLocalService;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
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
			BlogsEntry entry = ActionUtil.getEntry(renderRequest);

			FriendlyURL mainFriendlyURL =
				_friendlyURLLocalService.getMainFriendlyURL(
					entry.getCompanyId(), entry.getGroupId(), BlogsEntry.class,
					entry.getEntryId());

			String urlTitle = ParamUtil.getString(renderRequest, "urlTitle");

			if (!urlTitle.equals(mainFriendlyURL.getUrlTitle())) {
				HttpServletRequest request = PortalUtil.getHttpServletRequest(
					renderRequest);

				ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
					WebKeys.THEME_DISPLAY);

				LiferayPortletURL liferayPortletURL =
					PortletURLFactoryUtil.create(
						renderRequest, PortalUtil.getPortletId(renderRequest),
						themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

				liferayPortletURL.setParameter(
					"urlTitle", mainFriendlyURL.getUrlTitle());

				HttpServletResponse response =
					PortalUtil.getHttpServletResponse(renderResponse);

				response.sendRedirect(liferayPortletURL.toString());

				return MVCRenderConstants.MVC_PATH_VALUE_SKIP_DISPATCH;
			}

			HttpServletRequest request = PortalUtil.getHttpServletRequest(
				renderRequest);

			request.setAttribute(WebKeys.BLOGS_ENTRY, entry);

			if (PropsValues.BLOGS_PINGBACK_ENABLED) {
				if ((entry != null) && entry.isAllowPingbacks()) {
					HttpServletResponse response =
						PortalUtil.getHttpServletResponse(renderResponse);

					response.addHeader(
						"X-Pingback",
						PortalUtil.getPortalURL(renderRequest) +
							"/xmlrpc/pingback");
				}
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchEntryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return "/blogs/error.jsp";
			}
			else {
				throw new PortletException(e);
			}
		}

		return "/blogs/view_entry.jsp";
	}

	@Reference(unbind = "-")
	protected void setFriendlyURLLocalService(
		FriendlyURLLocalService friendlyURLLocalService) {

		_friendlyURLLocalService = friendlyURLLocalService;
	}

	private FriendlyURLLocalService _friendlyURLLocalService;

}