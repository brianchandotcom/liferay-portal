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

package com.liferay.batch.engine.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Matija Petanjek
 */
public class BaseDisplayContext {

	public BaseDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		this.renderRequest = renderRequest;
		this.renderResponse = renderResponse;

		companyId = PortalUtil.getCompanyId(renderRequest);
		httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
	}

	public List<NavigationItem> getNavigationItems() {
		String tabs1 = ParamUtil.getString(
			httpServletRequest, "tabs1", "import");

		return NavigationItemList.of(
			() -> {
				NavigationItem navigationItem = new NavigationItem();

				navigationItem.setActive(tabs1.equals("import"));
				navigationItem.setHref(
					renderResponse.createRenderURL(), "tabs1", "import");
				navigationItem.setLabel(
					LanguageUtil.get(httpServletRequest, "import"));

				return navigationItem;
			},
			() -> {
				NavigationItem navigationItem = new NavigationItem();

				navigationItem.setActive(tabs1.equals("export"));
				navigationItem.setHref(
					renderResponse.createRenderURL(), "tabs1", "export",
					"mvcRenderCommandName",
					"/batch_engine/view_batch_engine_export_tasks");
				navigationItem.setLabel(
					LanguageUtil.get(httpServletRequest, "export"));

				return navigationItem;
			});
	}

	public String getOrderByCol() {
		return ParamUtil.getString(
			renderRequest, SearchContainer.DEFAULT_ORDER_BY_COL_PARAM,
			"modifiedDate");
	}

	public String getOrderByType() {
		return ParamUtil.getString(
			renderRequest, SearchContainer.DEFAULT_ORDER_BY_TYPE_PARAM, "desc");
	}

	public String getSimpleName(String className) {
		return className.substring(
			className.lastIndexOf(StringPool.PERIOD) + 1);
	}

	protected final long companyId;
	protected final HttpServletRequest httpServletRequest;
	protected final RenderRequest renderRequest;
	protected final RenderResponse renderResponse;

}