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

package com.liferay.layout.content.page.editor.web.internal.portlet;

import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.content.page.editor.web.internal.constants.ContentPageEditorWebKeys;
import com.liferay.layout.content.page.editor.web.internal.display.context.ContentPageEditorDisplayContext;
import com.liferay.layout.content.page.editor.web.internal.display.context.ContentPageEditorDisplayContextProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.system=true",
		"com.liferay.portlet.use-default-template=false",
		"javax.portlet.display-name=Content Page Editor",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"javax.portlet.resource-bundle=content.Language"
	},
	service = Portlet.class
)
public class ContentPageEditorPortlet extends MVCPortlet {

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			renderRequest);

		ContentPageEditorDisplayContext contentPageEditorDisplayContext =
			(ContentPageEditorDisplayContext)httpServletRequest.getAttribute(
				ContentPageEditorWebKeys.
					LIFERAY_SHARED_CONTENT_PAGE_EDITOR_DISPLAY_CONTEXT);

		if (contentPageEditorDisplayContext == null) {
			contentPageEditorDisplayContext =
				_contentPageEditorDisplayContextProvider.
					getContentPageEditorDisplayContext(
						httpServletRequest, renderResponse);

			httpServletRequest.setAttribute(
				ContentPageEditorWebKeys.
					LIFERAY_SHARED_CONTENT_PAGE_EDITOR_DISPLAY_CONTEXT,
				contentPageEditorDisplayContext);
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		Layout draftLayout = _layoutLocalService.fetchLayout(
			_portal.getClassNameId(Layout.class), layout.getPlid());

		if (draftLayout != null) {
			HttpServletResponse httpServletResponse =
				_portal.getHttpServletResponse(renderResponse);

			try {
				String layoutFullURL = _portal.getLayoutFullURL(
					draftLayout, themeDisplay);

				httpServletResponse.sendRedirect(
					_http.addParameter(
						layoutFullURL, "p_l_mode", Constants.EDIT));
			}
			catch (PortalException pe) {
				throw new PortletException(pe);
			}
		}
		else {
			super.doDispatch(renderRequest, renderResponse);
		}
	}

	@Reference
	private ContentPageEditorDisplayContextProvider
		_contentPageEditorDisplayContextProvider;

	@Reference
	private Http _http;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}