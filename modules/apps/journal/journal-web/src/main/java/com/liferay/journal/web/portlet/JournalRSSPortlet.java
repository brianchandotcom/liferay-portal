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

package com.liferay.journal.web.portlet;

import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.web.util.JournalRSSUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.util.PortalUtil;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL_RSS,
		"javax.portlet.security-role-ref=administrator"
	},
	service = Portlet.class
)
public class JournalRSSPortlet extends MVCPortlet {

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			resourceRequest);

		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			resourceResponse);

		try {
			byte[] xml = _journalRSSUtil.getRSS(
				resourceRequest, resourceResponse);

			ServletResponseUtil.sendFile(
				request, response, null, xml, ContentTypes.TEXT_XML_UTF8);
		}
		catch (Exception e) {
			try {
				PortalUtil.sendError(e, request, response);
			}
			catch (ServletException se) {
			}
		}
	}

	@Reference
	protected void setJournalRSSUtil(JournalRSSUtil journalRSSUtil) {
		_journalRSSUtil = journalRSSUtil;
	}

	protected void unsetJournalRSSUtil(JournalRSSUtil journalRSSUtil) {
		_journalRSSUtil = null;
	}

	private JournalRSSUtil _journalRSSUtil;

}