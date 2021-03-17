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

package com.liferay.wiki.uad.display;

import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.user.associated.data.display.UADDisplay;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.model.WikiPage;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = UADDisplay.class)
public class WikiPageUADDisplay extends BaseWikiPageUADDisplay {

	@Override
	public String getEditURL(
			WikiPage wikiPage, LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		return PortletURLBuilder.createLiferayPortletURL(
			liferayPortletResponse,
			portal.getControlPanelPlid(liferayPortletRequest),
			WikiPortletKeys.WIKI, PortletRequest.RENDER_PHASE
		).setMVCRenderCommandName(
			"/wiki/edit_page"
		).setRedirect(
			portal.getCurrentURL(liferayPortletRequest)
		).setParameter(
			"nodeId", wikiPage.getNodeId()
		).setParameter(
			"title", wikiPage.getTitle()
		).buildString();
	}

	@Reference
	protected Portal portal;

}