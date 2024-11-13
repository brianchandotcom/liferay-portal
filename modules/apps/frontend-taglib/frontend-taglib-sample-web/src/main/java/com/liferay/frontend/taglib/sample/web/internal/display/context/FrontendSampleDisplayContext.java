/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.sample.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Antonio Ortega
 */
public class FrontendSampleDisplayContext {

	public FrontendSampleDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public List<NavigationItem> getNavigationItems() {
		String navigation = ParamUtil.getString(
			PortalUtil.getHttpServletRequest(_renderRequest), "navigation",
			"search-iterator");

		return NavigationItemList.of(
			NavigationItemBuilder.setActive(
				navigation.equals("search-iterator")
			).setHref(
				_renderResponse.createRenderURL(), "navigation",
				"search-iterator"
			).setLabel(
				"Search Iterator"
			).build(),
			NavigationItemBuilder.setActive(
				navigation.equals("search-paginator")
			).setHref(
				_renderResponse.createRenderURL(), "navigation",
				"search-paginator"
			).setLabel(
				"Search Paginator"
			).build());
	}

	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}