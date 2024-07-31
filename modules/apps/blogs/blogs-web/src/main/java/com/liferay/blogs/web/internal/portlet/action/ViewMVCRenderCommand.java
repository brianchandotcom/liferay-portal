/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.web.internal.portlet.action;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.blogs.constants.BlogsPortletKeys;
import com.liferay.blogs.web.internal.display.context.BlogsViewEntriesDisplayContext;
import com.liferay.blogs.web.internal.display.context.BlogsViewEntryContentDisplayContext;
import com.liferay.blogs.web.internal.display.context.BlogsViewImagesDisplayContext;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlParser;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.trash.TrashHelper;

import java.util.Objects;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	property = {
		"javax.portlet.name=" + BlogsPortletKeys.BLOGS,
		"javax.portlet.name=" + BlogsPortletKeys.BLOGS_ADMIN,
		"mvc.command.name=/", "mvc.command.name=/blogs/search",
		"mvc.command.name=/blogs/view"
	},
	service = MVCRenderCommand.class
)
public class ViewMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		if (Objects.equals(
				_getPortletId(renderRequest), BlogsPortletKeys.BLOGS)) {

			renderRequest.setAttribute(
				BlogsViewEntryContentDisplayContext.class.getName(),
				new BlogsViewEntryContentDisplayContext(
					_assetDisplayPageFriendlyURLProvider,
					_portal.getLiferayPortletRequest(renderRequest),
					_portal.getLiferayPortletResponse(renderResponse)));

			return "/blogs/view.jsp";
		}

		renderRequest.setAttribute(
			BlogsViewEntriesDisplayContext.class.getName(),
			new BlogsViewEntriesDisplayContext(
				_htmlParser, _portal, renderRequest, renderResponse,
				_trashHelper));
		renderRequest.setAttribute(
			BlogsViewImagesDisplayContext.class.getName(),
			new BlogsViewImagesDisplayContext(
				_portal.getHttpServletRequest(renderRequest)));

		return "/blogs_admin/view.jsp";
	}

	private String _getPortletId(RenderRequest renderRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return portletDisplay.getPortletName();
	}

	@Reference
	private AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;

	@Reference
	private HtmlParser _htmlParser;

	@Reference
	private Portal _portal;

	@Reference
	private TrashHelper _trashHelper;

}