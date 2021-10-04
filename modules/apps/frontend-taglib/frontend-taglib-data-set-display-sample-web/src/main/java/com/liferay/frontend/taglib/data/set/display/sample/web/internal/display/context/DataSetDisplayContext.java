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

package com.liferay.frontend.taglib.data.set.display.sample.web.internal.display.context;

import com.liferay.frontend.taglib.clay.data.set.servlet.taglib.util.ClayDataSetActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.data.set.display.sample.web.internal.display.context.util.DataSetDisplayRequestHelper;
import com.liferay.portal.kernel.portlet.PortletURLUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Javier Gamarra
 * @author Javier de Arcos
 */
public class DataSetDisplayContext {

	public DataSetDisplayContext(HttpServletRequest httpServletRequest) {
		_dataSetDisplayRequestHelper = new DataSetDisplayRequestHelper(
			httpServletRequest);
	}

	public String getAPIURL() {
		return "/o/c/datasetdisplaysamples";
	}

	public List<ClayDataSetActionDropdownItem>
			getClayDataSetActionDropdownItems()
		throws Exception {

		return new ArrayList<>();
	}

	public CreationMenu getCreationMenu() throws Exception {
		return new CreationMenu();
	}

	public PortletURL getPortletURL() throws PortletException {
		return PortletURLUtil.clone(
			PortletURLUtil.getCurrent(
				_dataSetDisplayRequestHelper.getLiferayPortletRequest(),
				_dataSetDisplayRequestHelper.getLiferayPortletResponse()),
			_dataSetDisplayRequestHelper.getLiferayPortletResponse());
	}

	private final DataSetDisplayRequestHelper _dataSetDisplayRequestHelper;

}