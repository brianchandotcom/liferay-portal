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
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
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
 * @author Juan Fernández
 * @author Ryan Park
 */
public class FindFileEntryAction extends Action {

	@Override
	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		try {
			long plid = ParamUtil.getLong(request, "p_l_id");
			String redirect = ParamUtil.getString(request, "redirect");
			long fileEntryId = ParamUtil.getLong(request, "fileEntryId");

			plid = getPlid(plid, fileEntryId);

			PortletURL portletURL = new PortletURLImpl(
				request, getPortletId(plid), plid, PortletRequest.RENDER_PHASE);

			portletURL.setWindowState(WindowState.NORMAL);
			portletURL.setPortletMode(PortletMode.VIEW);

			portletURL.setParameter(
				"struts_action", "/document_library/view_file_entry");
			portletURL.setParameter("fileEntryId", String.valueOf(fileEntryId));

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

	protected long getPlid(long plid, long fileEntryId) throws Exception {
		if (plid != LayoutConstants.DEFAULT_PLID) {
			try {
				Layout layout = LayoutLocalServiceUtil.getLayout(plid);

				LayoutTypePortlet layoutTypePortlet =
					(LayoutTypePortlet)layout.getLayoutType();

				if (layoutTypePortlet.hasPortletId(
						PortletKeys.DOCUMENT_LIBRARY) ||
					layoutTypePortlet.hasPortletId(
						PortletKeys.DOCUMENT_LIBRARY_DISPLAY) ||
					layoutTypePortlet.hasPortletId(
						PortletKeys.IMAGE_GALLERY_DISPLAY)) {

					return plid;
				}
			}
			catch (NoSuchLayoutException nsle) {
			}
		}

		FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(fileEntryId);

		plid = PortalUtil.getPlidFromPortletId(
			fileEntry.getRepositoryId(), PortletKeys.DOCUMENT_LIBRARY);

		if (plid != LayoutConstants.DEFAULT_PLID) {
			return plid;
		}

		plid = PortalUtil.getPlidFromPortletId(
			fileEntry.getRepositoryId(), PortletKeys.DOCUMENT_LIBRARY_DISPLAY);

		if (plid != LayoutConstants.DEFAULT_PLID) {
			return plid;
		}

		plid = PortalUtil.getPlidFromPortletId(
			fileEntry.getRepositoryId(), PortletKeys.IMAGE_GALLERY_DISPLAY);

		if (plid != LayoutConstants.DEFAULT_PLID) {
			return plid;
		}

		throw new NoSuchLayoutException(
			"No page was found with the Document Library portlet");
	}

	protected String getPortletId(long plid) throws Exception {
		Layout layout = LayoutLocalServiceUtil.getLayout(plid);

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		for (String portletId : layoutTypePortlet.getPortletIds()) {
			if (portletId.startsWith(PortletKeys.DOCUMENT_LIBRARY) ||
				portletId.startsWith(PortletKeys.DOCUMENT_LIBRARY_DISPLAY)) {

				return portletId;
			}
		}

		return PortletKeys.DOCUMENT_LIBRARY;
	}

}