/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.action;

import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.DiffResult;
import com.liferay.portal.kernel.util.DiffUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.wiki.NoSuchPageException;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageServiceUtil;
import com.liferay.portlet.wiki.util.WikiUtil;

import java.util.List;

import javax.portlet.PortletConfig;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Bruno Farache
 * @author Julio Camarero
 */
public class CompareVersionsAction extends PortletAction {

	@Override
	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getNode(renderRequest);
			ActionUtil.getPage(renderRequest);

			compareVersions(renderRequest, renderResponse);
		}
		catch (Exception e) {
			if (e instanceof NoSuchPageException) {
				SessionErrors.add(renderRequest, e.getClass());

				return mapping.findForward("portlet.wiki.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward("portlet.wiki.compare_versions");
	}

	protected void compareVersions(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long nodeId = ParamUtil.getLong(renderRequest, "nodeId");

		String title = ParamUtil.getString(renderRequest, "title");

		double sourceVersion = ParamUtil.getDouble(
			renderRequest, "sourceVersion");
		double targetVersion = ParamUtil.getDouble(
			renderRequest, "targetVersion");
		String type = ParamUtil.getString(renderRequest, "type", "escape");

		WikiPage sourcePage = WikiPageServiceUtil.getPage(
			nodeId, title, sourceVersion);
		WikiPage targetPage = WikiPageServiceUtil.getPage(
			nodeId, title, targetVersion);

		if (type.equals("html")) {
			WikiNode sourceNode = sourcePage.getNode();

			PortletURL viewPageURL = renderResponse.createRenderURL();

			viewPageURL.setParameter("struts_action", "/wiki/view");
			viewPageURL.setParameter("nodeName", sourceNode.getName());

			PortletURL editPageURL = renderResponse.createRenderURL();

			editPageURL.setParameter("struts_action", "/wiki/edit_page");
			editPageURL.setParameter("nodeId", String.valueOf(nodeId));
			editPageURL.setParameter("title", title);

			String attachmentURLPrefix = WikiUtil.getAttachmentURLPrefix(
				themeDisplay.getPathMain(), themeDisplay.getPlid(), nodeId,
				title);

			String htmlDiffResult = WikiUtil.diffHtml(
				sourcePage, targetPage, viewPageURL, editPageURL,
				attachmentURLPrefix);

			renderRequest.setAttribute(
				WebKeys.DIFF_HTML_RESULTS, htmlDiffResult);
		}
		else {
			String sourceContent = sourcePage.getContent();
			String targetContent = targetPage.getContent();

			sourceContent = WikiUtil.processContent(sourceContent);
			targetContent = WikiUtil.processContent(targetContent);

			if (type.equals("strip")) {
				sourceContent = HtmlUtil.extractText(sourceContent);
				targetContent = HtmlUtil.extractText(targetContent);
			}
			else {
				sourceContent = HtmlUtil.escape(sourceContent);
				targetContent = HtmlUtil.escape(targetContent);
			}

			List<DiffResult>[] diffResults = DiffUtil.diff(
				new UnsyncStringReader(sourceContent),
				new UnsyncStringReader(targetContent));

			renderRequest.setAttribute(WebKeys.DIFF_RESULTS, diffResults);
		}

		renderRequest.setAttribute(WebKeys.WIKI_NODE_ID, nodeId);
		renderRequest.setAttribute(WebKeys.TITLE, title);
		renderRequest.setAttribute(WebKeys.SOURCE_VERSION, sourceVersion);
		renderRequest.setAttribute(WebKeys.TARGET_VERSION, targetVersion);
	}

}