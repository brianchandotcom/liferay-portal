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

package com.liferay.fragment.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public abstract class FragmentManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public FragmentManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContainer<Object> searchContainer,
		FragmentDisplayContext fragmentDisplayContext) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			searchContainer);

		this.fragmentDisplayContext = fragmentDisplayContext;
	}

	@Override
	public String getClearResultsURL() {
		return PortletURLBuilder.create(
			getPortletURL()
		).setKeywords(
			StringPool.BLANK
		).buildString();
	}

	public abstract Map<String, Object> getComponentContext() throws Exception;

	@Override
	public String getComponentId() {
		return "fragmentEntriesManagementToolbar" +
			fragmentDisplayContext.getFragmentCollectionId();
	}

	@Override
	public String getSearchActionURL() {
		return PortletURLBuilder.create(
			getPortletURL()
		).setParameter(
			"fragmentCollectionId",
			fragmentDisplayContext.getFragmentCollectionId()
		).buildString();
	}

	@Override
	protected String[] getOrderByKeys() {
		return new String[] {"name", "create-date"};
	}

	protected final FragmentDisplayContext fragmentDisplayContext;

}