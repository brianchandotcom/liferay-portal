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
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.util.PortalUtil;

import javax.portlet.MimeResponse;
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Garcia
 */
@Component(
	immediate = true,
	property = {
		"auth.token.ignore.mvc.action=true",
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL,
		"mvc.command.name=rss",
		"portlet.add.default.resource.check.whitelist.mvc.action=true"
	},
	service = MVCResourceCommand.class
)
public class RSSMVCResourceCommand implements MVCResourceCommand {

	@Override
	public boolean serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		if (!(resourceResponse instanceof MimeResponse)) {
			return false;
		}

		if (!PortalUtil.isRSSFeedsEnabled()) {
			try {
				PortalUtil.sendRSSFeedsDisabledError(
					resourceRequest, resourceResponse);
			}
			catch (Exception e) {
			}

			return false;
		}

		MimeResponse mimeResponse = (MimeResponse)resourceResponse;

		try {
			byte[] xml = _journalRSSUtil.getRSS(
				resourceRequest, resourceResponse);

			PortletResponseUtil.sendFile(
				resourceRequest, mimeResponse, null, xml,
				ContentTypes.TEXT_XML_UTF8);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}

		return true;
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