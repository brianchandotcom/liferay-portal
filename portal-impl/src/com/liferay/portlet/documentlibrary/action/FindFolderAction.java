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

package com.liferay.portlet.documentlibrary.action;

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;

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
public class FindFolderAction extends Action {

	@Override
	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		try {
			long plid = ParamUtil.getLong(request, "p_l_id");
			long groupId = ParamUtil.getLong(request,"groupId");
			long folderId = ParamUtil.getLong(request, "folderId");

			plid = getPlid(plid, groupId, folderId);

			PortletURL portletURL = new PortletURLImpl(
				request, PortletKeys.DOCUMENT_LIBRARY_DISPLAY, plid,
				PortletRequest.RENDER_PHASE);

			portletURL.setWindowState(WindowState.NORMAL);
			portletURL.setPortletMode(PortletMode.VIEW);

			portletURL.setParameter("struts_action", "/document_library/view");
			portletURL.setParameter("folderId", String.valueOf(folderId));

			response.sendRedirect(portletURL.toString());

			return null;
		}
		catch (Exception e) {
			PortalUtil.sendError(e, request, response);

			return null;
		}
	}

	protected long getPlid(long plid, long groupId, long folderId)
		throws Exception {

		if (plid != LayoutConstants.DEFAULT_PLID) {
			try {
				Layout layout = LayoutLocalServiceUtil.getLayout(plid);

				LayoutTypePortlet layoutTypePortlet =
					(LayoutTypePortlet)layout.getLayoutType();

				if (layoutTypePortlet.hasPortletId(
						PortletKeys.DOCUMENT_LIBRARY_DISPLAY) ||
					layoutTypePortlet.hasPortletId(
						PortletKeys.IMAGE_GALLERY_DISPLAY)) {

					return plid;
				}
			}
			catch (NoSuchLayoutException nsle) {
			}
		}

		if (groupId <= 0) {
			Folder folder = DLAppLocalServiceUtil.getFolder(folderId);

			groupId = folder.getRepositoryId();
		}

		plid = PortalUtil.getPlidFromPortletId(
			groupId, PortletKeys.DOCUMENT_LIBRARY);

		if (plid != LayoutConstants.DEFAULT_PLID) {
			return plid;
		}

		plid = PortalUtil.getPlidFromPortletId(
			groupId, PortletKeys.DOCUMENT_LIBRARY_DISPLAY);

		if (plid != LayoutConstants.DEFAULT_PLID) {
			return plid;
		}

		plid = PortalUtil.getPlidFromPortletId(
			groupId, PortletKeys.IMAGE_GALLERY_DISPLAY);

		if (plid != LayoutConstants.DEFAULT_PLID) {
			return plid;
		}

		throw new NoSuchLayoutException(
			"No page was found with the Document Library portlet");
	}

}