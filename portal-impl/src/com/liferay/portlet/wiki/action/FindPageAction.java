/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPageResource;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.portlet.wiki.service.WikiPageResourceLocalServiceUtil;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Samuel Kong
 */
public class FindPageAction extends Action {

	@Override
	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		try {
			long plid = ParamUtil.getLong(request, "p_l_id");
			String redirect = ParamUtil.getString(request, "redirect");
			long pageResourcePrimKey = ParamUtil.getLong(
				request, "pageResourcePrimKey");

			plid = getPlid(plid, pageResourcePrimKey);

			WikiPageResource pageResource =
				WikiPageResourceLocalServiceUtil.getPageResource(
					pageResourcePrimKey);

			WikiNode node = WikiNodeLocalServiceUtil.getNode(
				pageResource.getNodeId());

			PortletURL portletURL = new PortletURLImpl(
				request, getPortletId(plid), plid, PortletRequest.RENDER_PHASE);

			portletURL.setWindowState(WindowState.NORMAL);
			portletURL.setPortletMode(PortletMode.VIEW);

			portletURL.setParameter("struts_action", "/wiki/view");
			portletURL.setParameter("nodeName", node.getName());
			portletURL.setParameter("title", pageResource.getTitle());

			if (Validator.isNotNull(redirect)) {
				portletURL.setParameter("redirect", redirect);
			}

			response.sendRedirect(portletURL.toString());

			return null;
		}
		catch (Exception e) {
			String noSuchEntryRedirect = ParamUtil.getString(
				request, "noSuchEntryRedirect");

			if (e.getClass().equals(NoSuchLayoutException.class) &&
				Validator.isNotNull(noSuchEntryRedirect)) {

				response.sendRedirect(noSuchEntryRedirect);
			}
			else {
				PortalUtil.sendError(e, request, response);
			}

			return null;
		}
	}

	protected long getPlid(long plid, long pageResourcePrimKey)
		throws Exception {

		if (plid != LayoutConstants.DEFAULT_PLID) {
			try {
				Layout layout = LayoutLocalServiceUtil.getLayout(plid);

				LayoutTypePortlet layoutTypePortlet =
					(LayoutTypePortlet)layout.getLayoutType();

				if (layoutTypePortlet.hasPortletId(PortletKeys.WIKI) ||
					layoutTypePortlet.hasPortletId(PortletKeys.WIKI_DISPLAY)) {

					return plid;
				}
			}
			catch (NoSuchLayoutException nsle) {
			}
		}

		WikiPageResource pageResource =
			WikiPageResourceLocalServiceUtil.getPageResource(
				pageResourcePrimKey);

		WikiNode node = WikiNodeLocalServiceUtil.getNode(
			pageResource.getNodeId());

		plid = PortalUtil.getPlidFromPortletId(
			node.getGroupId(), PortletKeys.WIKI);

		if (plid != LayoutConstants.DEFAULT_PLID) {
			return plid;
		}

		plid = PortalUtil.getPlidFromPortletId(
			node.getGroupId(), PortletKeys.WIKI_DISPLAY);

		if (plid != LayoutConstants.DEFAULT_PLID) {
			return plid;
		}

		throw new NoSuchLayoutException(
			"No page was found with the Wiki portlet");
	}

	protected String getPortletId(long plid) throws Exception {
		Layout layout = LayoutLocalServiceUtil.getLayout(plid);

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		for (String portletId : layoutTypePortlet.getPortletIds()) {
			if (portletId.startsWith(PortletKeys.WIKI) ||
				portletId.startsWith(PortletKeys.WIKI_DISPLAY)) {

				return portletId;
			}
		}

		return PortletKeys.WIKI;
	}

}