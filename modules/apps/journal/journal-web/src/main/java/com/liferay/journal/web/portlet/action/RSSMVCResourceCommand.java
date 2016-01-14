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

package com.liferay.journal.web.portlet.action;

import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.web.util.JournalRSSUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.util.PortalUtil;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Garcia
 */
@Component(
	property = {
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL,
		"mvc.command.name=rss"
	},
	service = MVCResourceCommand.class
)
public class RSSMVCResourceCommand implements MVCResourceCommand {

	@Override
	public boolean serveResource(
		ResourceRequest resourceRequest, ResourceResponse resourceResponse) {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			resourceRequest);

		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			resourceResponse);

		try {
			byte[] xml = _journalRSSUtil.getRSS(
				resourceRequest, resourceResponse);

			ServletResponseUtil.sendFile(
				request, response, null, xml, ContentTypes.TEXT_XML_UTF8);

			return false;
		}
		catch (Exception e) {
			_log.error(e);

			return true;
		}
	}

	@Reference
	protected void setJournalRSSUtil(JournalRSSUtil journalRSSUtil) {
		_journalRSSUtil = journalRSSUtil;
	}

	protected void unsetJournalRSSUtil(JournalRSSUtil journalRSSUtil) {
		_journalRSSUtil = null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RSSMVCResourceCommand.class);

	private JournalRSSUtil _journalRSSUtil;

}