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

package com.liferay.change.tracking.web.internal.display.adapter;

import com.liferay.change.tracking.display.CTDisplay;
import com.liferay.change.tracking.display.CTPortletDisplay;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.PortletContainerUtil;
import com.liferay.portal.kernel.service.PortletLocalService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Samuel Trong Tran
 */
public class CTPortletDisplayAdapter<T extends BaseModel<T>>
	implements CTDisplay<T> {

	public CTPortletDisplayAdapter(
		CTPortletDisplay<T> ctPortletDisplay,
		PortletLocalService portletLocalService) {

		_ctPortletDisplay = ctPortletDisplay;

		_modelClass = ctPortletDisplay.getModelClass();
		_portletLocalService = portletLocalService;
	}

	@Override
	public Class<T> getModelClass() {
		return _modelClass;
	}

	@Override
	public void render(
			HttpServletRequest request, HttpServletResponse response,
			T baseModel)
		throws Exception {

		_ctPortletDisplay.setRequestAttributes(request, baseModel);

		Portlet portlet = _portletLocalService.getPortletById(
			_ctPortletDisplay.getPortletId());

		if (portlet == null) {
			return;
		}

		PortletContainerUtil.render(request, response, portlet);
	}

	private final CTPortletDisplay _ctPortletDisplay;
	private final Class<T> _modelClass;
	private final PortletLocalService _portletLocalService;

}