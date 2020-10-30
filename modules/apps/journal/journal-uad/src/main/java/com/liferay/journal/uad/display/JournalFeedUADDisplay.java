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

package com.liferay.journal.uad.display;

import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalFeed;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.user.associated.data.display.UADDisplay;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Balázs Sáfrány-Kovalik
 */
@Component(immediate = true, service = UADDisplay.class)
public class JournalFeedUADDisplay extends BaseJournalFeedUADDisplay {

	@Override
	public String getEditURL(
			JournalFeed journalFeed,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			liferayPortletRequest, JournalPortletKeys.JOURNAL,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcPath", "/edit_feed.jsp");
		portletURL.setParameter(
			"redirect", _portal.getCurrentURL(liferayPortletRequest));
		portletURL.setParameter(
			"groupId", String.valueOf(journalFeed.getGroupId()));
		portletURL.setParameter(
			"feedId", String.valueOf(journalFeed.getFeedId()));

		return portletURL.toString();
	}

	@Reference
	private Portal _portal;

}