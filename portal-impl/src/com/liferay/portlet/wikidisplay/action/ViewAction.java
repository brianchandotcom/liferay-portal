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

package com.liferay.portlet.wikidisplay.action;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.wiki.NoSuchNodeException;
import com.liferay.portlet.wiki.NoSuchPageException;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.model.WikiPageConstants;
import com.liferay.portlet.wiki.service.WikiNodeServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageServiceUtil;

import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewAction extends PortletAction {

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		try {
			PortletPreferences portletPreferences =
				renderRequest.getPreferences();

			ThemeDisplay themeDisplay =
				(ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

			long nodeId = GetterUtil.getLong(
				portletPreferences.getValue("nodeId", StringPool.BLANK));
			String title = ParamUtil.getString(
				renderRequest, "title",
				portletPreferences.getValue(
					"title", WikiPageConstants.FRONT_PAGE));
			double version = ParamUtil.getDouble(renderRequest, "version");

			WikiNode node = WikiNodeServiceUtil.getNode(nodeId);

			if (node.getGroupId() != themeDisplay.getScopeGroupId()) {
				throw new NoSuchNodeException();
			}

			WikiPage page = null;

			try {
				page = WikiPageServiceUtil.getPage(nodeId, title, version);
			}
			catch (NoSuchPageException nspe) {
				page = WikiPageServiceUtil.getPage(
					nodeId, WikiPageConstants.FRONT_PAGE);
			}

			renderRequest.setAttribute(WebKeys.WIKI_NODE, node);
			renderRequest.setAttribute(WebKeys.WIKI_PAGE, page);

			return actionMapping.findForward("portlet.wiki_display.view");
		}
		catch (NoSuchNodeException nsne) {
			return actionMapping.findForward("/portal/portlet_not_setup");
		}
		catch (NoSuchPageException nspe) {
			return actionMapping.findForward("/portal/portlet_not_setup");
		}
		catch (PrincipalException pe) {
			SessionErrors.add(renderRequest, pe.getClass());

			return actionMapping.findForward("portlet.wiki_display.error");
		}
	}

}