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

package com.liferay.wiki.web.internal.portlet.action;

import com.liferay.document.library.kernel.document.conversion.DocumentConversionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.engine.WikiEngineRenderer;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiPageService;
import com.liferay.wiki.web.internal.util.WikiUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Farache
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + WikiPortletKeys.WIKI,
		"javax.portlet.name=" + WikiPortletKeys.WIKI_ADMIN,
		"javax.portlet.name=" + WikiPortletKeys.WIKI_DISPLAY,
		"mvc.command.name=/wiki/export_page"
	},
	service = MVCActionCommand.class
)
public class ExportPageMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		hideDefaultSuccessMessage(actionRequest);

		PortletConfig portletConfig = getPortletConfig(actionRequest);

		try {
			long nodeId = ParamUtil.getLong(actionRequest, "nodeId");
			String nodeName = ParamUtil.getString(actionRequest, "nodeName");
			String title = ParamUtil.getString(actionRequest, "title");
			double version = ParamUtil.getDouble(actionRequest, "version");

			String targetExtension = ParamUtil.getString(
				actionRequest, "targetExtension");

			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			PortletURL viewPageURL = PortletURLFactoryUtil.create(
				actionRequest, portletConfig.getPortletName(),
				PortletRequest.RENDER_PHASE);

			viewPageURL.setParameter("mvcRenderCommandName", "/wiki/view");
			viewPageURL.setParameter("nodeName", nodeName);
			viewPageURL.setParameter("title", title);
			viewPageURL.setPortletMode(PortletMode.VIEW);
			viewPageURL.setWindowState(WindowState.MAXIMIZED);

			PortletURL editPageURL = PortletURLFactoryUtil.create(
				actionRequest, portletConfig.getPortletName(),
				PortletRequest.RENDER_PHASE);

			editPageURL.setParameter("mvcRenderCommandName", "/wiki/edit_page");
			editPageURL.setParameter("nodeId", String.valueOf(nodeId));
			editPageURL.setParameter("title", title);
			editPageURL.setPortletMode(PortletMode.VIEW);
			editPageURL.setWindowState(WindowState.MAXIMIZED);

			getFile(
				nodeId, title, version, targetExtension, viewPageURL,
				editPageURL, themeDisplay,
				_portal.getHttpServletRequest(actionRequest),
				_portal.getHttpServletResponse(actionResponse));

			actionResponse.setRenderParameter("mvcPath", "/null.jsp");
		}
		catch (Exception e) {
			_log.error(e, e);

			_portal.sendError(e, actionRequest, actionResponse);
		}
	}

	protected void getFile(
			long nodeId, String title, double version, String targetExtension,
			PortletURL viewPageURL, PortletURL editPageURL,
			ThemeDisplay themeDisplay, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		WikiPage page = _wikiPageService.getPage(nodeId, title, version);

		String content = page.getContent();

		String attachmentURLPrefix = WikiUtil.getAttachmentURLPrefix(
			themeDisplay.getPathMain(), themeDisplay.getPlid(), nodeId, title);

		try {
			content = _wikiEngineRenderer.convert(
				page, viewPageURL, editPageURL, attachmentURLPrefix);
		}
		catch (Exception e) {
			_log.error(
				StringBundler.concat(
					"Error formatting the wiki page ", page.getPageId(),
					" with the format ", page.getFormat()),
				e);
		}

		StringBundler sb = new StringBundler(17);

		sb.append("<!DOCTYPE html>");

		sb.append("<html>");

		sb.append("<head>");
		sb.append("<meta content=\"");
		sb.append(ContentTypes.TEXT_HTML_UTF8);
		sb.append("\" http-equiv=\"content-type\" />");
		sb.append("<base href=\"");
		sb.append(themeDisplay.getPortalURL());
		sb.append("\" />");
		sb.append("</head>");

		sb.append("<body>");

		sb.append("<h1>");
		sb.append(title);
		sb.append("</h1>");
		sb.append(content);

		sb.append("</body>");
		sb.append("</html>");

		String s = sb.toString();

		InputStream is = new UnsyncByteArrayInputStream(
			s.getBytes(StringPool.UTF8));

		String sourceExtension = "html";

		String fileName = title.concat(
			StringPool.PERIOD
		).concat(
			sourceExtension
		);

		if (Validator.isNotNull(targetExtension)) {
			String id =
				PrincipalThreadLocal.getUserId() + StringPool.UNDERLINE +
					page.getUuid();

			File convertedFile = DocumentConversionUtil.convert(
				id, is, sourceExtension, targetExtension);

			if (convertedFile != null) {
				fileName = title.concat(
					StringPool.PERIOD
				).concat(
					targetExtension
				);

				is = new FileInputStream(convertedFile);
			}
		}

		ServletResponseUtil.sendFile(
			httpServletRequest, httpServletResponse, fileName, is,
			MimeTypesUtil.getContentType(fileName));
	}

	@Reference(unbind = "-")
	protected void setWikiEngineRenderer(
		WikiEngineRenderer wikiEngineRenderer) {

		_wikiEngineRenderer = wikiEngineRenderer;
	}

	@Reference(unbind = "-")
	protected void setWikiPageService(WikiPageService wikiPageService) {
		_wikiPageService = wikiPageService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExportPageMVCActionCommand.class);

	@Reference
	private Portal _portal;

	private WikiEngineRenderer _wikiEngineRenderer;
	private WikiPageService _wikiPageService;

}