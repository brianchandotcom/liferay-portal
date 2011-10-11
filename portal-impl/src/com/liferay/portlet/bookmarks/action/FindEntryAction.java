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

package com.liferay.portlet.bookmarks.action;

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
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.service.BookmarksEntryLocalServiceUtil;

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
 * @author Juan Fernández
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

			plid = getPlid(plid, entryId);

			PortletURL portletURL = new PortletURLImpl(
				request, PortletKeys.BOOKMARKS, plid,
				PortletRequest.RENDER_PHASE);

			portletURL.setWindowState(WindowState.NORMAL);
			portletURL.setPortletMode(PortletMode.VIEW);

			portletURL.setParameter("struts_action", "/bookmarks/view_entry");

			if (Validator.isNotNull(redirect)) {
				portletURL.setParameter("redirect", redirect);
			}

			portletURL.setParameter("entryId", String.valueOf(entryId));

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

	protected long getPlid(long plid, long entryId) throws Exception {
		if (plid != LayoutConstants.DEFAULT_PLID) {
			try {
				Layout layout = LayoutLocalServiceUtil.getLayout(plid);

				LayoutTypePortlet layoutTypePortlet =
					(LayoutTypePortlet)layout.getLayoutType();

				if (layoutTypePortlet.hasPortletId(PortletKeys.BOOKMARKS)) {
					return plid;
				}
			}
			catch (NoSuchLayoutException nsle) {
			}
		}

		BookmarksEntry entry = BookmarksEntryLocalServiceUtil.getEntry(entryId);

		plid = PortalUtil.getPlidFromPortletId(
			entry.getGroupId(), PortletKeys.BOOKMARKS);

		if (plid != LayoutConstants.DEFAULT_PLID) {
			return plid;
		}

		throw new NoSuchLayoutException(
			"No page was found with the Bookmarks portlet");
	}

}