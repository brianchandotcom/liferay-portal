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

package com.liferay.batch.planner.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemListBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.List;
import java.util.Objects;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Matija Petanjek
 */
public abstract class BaseDisplayContext {

	public BaseDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		this.renderRequest = renderRequest;
		this.renderResponse = renderResponse;
		httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
	}

	public List<NavigationItem> getNavigationItems() {
		String tabs1 = ParamUtil.getString(
			renderRequest, "tabs1", "batch-planner-logs");

		return NavigationItemListBuilder.add(
			navigationItem -> {
				navigationItem.setActive(tabs1.equals("batch-planner-logs"));
				navigationItem.setHref(
					renderResponse.createRenderURL(), "tabs1",
					"batch-planner-logs");
				navigationItem.setLabel(
					LanguageUtil.get(
						PortalUtil.getHttpServletRequest(renderRequest),
						"import-and-export"));
			}
		).add(
			navigationItem -> {
				navigationItem.setActive(tabs1.equals("batch-planner-plans"));
				navigationItem.setHref(
					renderResponse.createRenderURL(), "tabs1",
					"batch-planner-plans", "mvcRenderCommandName",
					"/batch_planner/view_batch_planner_plans");
				navigationItem.setLabel(
					LanguageUtil.get(
						PortalUtil.getHttpServletRequest(renderRequest),
						"templates"));
			}
		).build();
	}

	public String getSimpleInternalClassName(String internalClassName) {
		return internalClassName.substring(
			internalClassName.lastIndexOf(StringPool.PERIOD) + 1);
	}

	protected boolean isExport(String navigation) {
		return Objects.equals(navigation, "export");
	}

	protected HttpServletRequest httpServletRequest;
	protected RenderRequest renderRequest;
	protected RenderResponse renderResponse;

}