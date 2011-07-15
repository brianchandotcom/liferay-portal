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

package com.liferay.portlet.blogs.action;

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;

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
 * @author Brian Wing Shun Chan
 */
public class FindEntryAction extends Action {

	@Override
	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		try {
			long plid = ParamUtil.getLong(request, "p_l_id");
			String redirect = ParamUtil.getString(request, "redirect");
			long entryId = ParamUtil.getLong(request, "entryId");
			boolean showAllEntries = ParamUtil.getBoolean(
				request, "showAllEntries");

			String portletId = PortletKeys.BLOGS;

			try {
				plid = getPlid(plid, portletId, entryId);
			}
			catch (NoSuchLayoutException nsle) {
				portletId = PortletKeys.BLOGS_AGGREGATOR;

				plid = getPlid(plid, portletId, entryId);
			}

			PortletURL portletURL = new PortletURLImpl(
				request, portletId, plid, PortletRequest.RENDER_PHASE);

			portletURL.setWindowState(WindowState.NORMAL);
			portletURL.setPortletMode(PortletMode.VIEW);

			if (Validator.isNotNull(redirect)) {
				portletURL.setParameter("redirect", redirect);
			}

			String strutsAction = getStrutsAction(portletId, showAllEntries);

			portletURL.setParameter("struts_action", strutsAction);

			if (!showAllEntries) {
				portletURL.setParameter("entryId", String.valueOf(entryId));
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

	protected long getPlid(long plid, String portletId, long entryId)
		throws Exception {

		if (plid != LayoutConstants.DEFAULT_PLID) {
			try {
				Layout layout = LayoutLocalServiceUtil.getLayout(plid);

				LayoutTypePortlet layoutTypePortlet =
					(LayoutTypePortlet)layout.getLayoutType();

				if (layoutTypePortlet.hasPortletId(portletId)) {
					return plid;
				}
			}
			catch (NoSuchLayoutException nsle) {
			}
		}

		BlogsEntry entry = BlogsEntryLocalServiceUtil.getEntry(entryId);

		plid = PortalUtil.getPlidFromPortletId(entry.getGroupId(), portletId);

		if (plid != LayoutConstants.DEFAULT_PLID) {
			return plid;
		}

		throw new NoSuchLayoutException(
			"No page was found with the Blogs portlet");
	}

	protected String getStrutsAction(String portletId, boolean showAllEntries) {
		String strutsAction = StringPool.BLANK;

		if (portletId.equals(PortletKeys.BLOGS)) {
			strutsAction = "/blogs";
		}
		else {
			strutsAction = "/blogs_aggregator";
		}

		if (showAllEntries) {
			strutsAction += "/view";
		}
		else {
			strutsAction += "/view_entry";
		}

		return strutsAction;
	}

}