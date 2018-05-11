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

package com.liferay.staging.processes.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.List;

import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Péter Alius
 */
public class StagingProcessesWebDisplayContext {

	public StagingProcessesWebDisplayContext(
		RenderResponse renderResponse, HttpServletRequest request) {

		_renderResponse = renderResponse;
		_request = request;
	}

	public List<NavigationItem> getNavigationItems() {
		return new NavigationItemList() {
			{
				add(
					navigationItem -> {
						String activeTab = ParamUtil.getString(
							_request, "tabs1", "processes");

						navigationItem.setActive(activeTab.equals("processes"));

						navigationItem.setHref(
							_renderResponse.createRenderURL(), "tabs1",
							"processes");
						navigationItem.setLabel(
							LanguageUtil.get(_request, "processes"));
					});

				add(
					navigationItem -> {
						String activeTab = ParamUtil.getString(
							_request, "tabs1", "processes");

						navigationItem.setActive(activeTab.equals("scheduled"));

						navigationItem.setHref(
							_renderResponse.createRenderURL(), "tabs1",
							"scheduled");
						navigationItem.setLabel(
							LanguageUtil.get(_request, "scheduled"));
					});
			}
		};
	}

	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;

}