/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.portlet;

import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.content.page.editor.web.internal.constants.ContentPageEditorWebKeys;
import com.liferay.layout.content.page.editor.web.internal.display.context.ContentPageEditorDisplayContext;
import com.liferay.layout.content.page.editor.web.internal.display.context.ContentPageEditorDisplayContextProvider;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
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
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.version=3.0"
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
						httpServletRequest, renderResponse, renderRequest);

			httpServletRequest.setAttribute(
				ContentPageEditorWebKeys.
					LIFERAY_SHARED_CONTENT_PAGE_EDITOR_DISPLAY_CONTEXT,
				contentPageEditorDisplayContext);
		}

		super.doDispatch(renderRequest, renderResponse);
	}

	@Reference
	private ContentPageEditorDisplayContextProvider
		_contentPageEditorDisplayContextProvider;

	@Reference
	private Portal _portal;

}