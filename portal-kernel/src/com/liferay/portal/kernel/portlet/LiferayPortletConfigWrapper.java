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

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.model.Portlet;

import javax.portlet.filter.PortletConfigWrapper;

/**
 * @author Neil Griffin
 */
public class LiferayPortletConfigWrapper
	extends PortletConfigWrapper implements LiferayPortletConfig {

	public LiferayPortletConfigWrapper(
		LiferayPortletConfig liferayPortletConfig) {

		super(liferayPortletConfig);

		_liferayPortletConfig = liferayPortletConfig;
	}

	@Override
	public Portlet getPortlet() {
		return _liferayPortletConfig.getPortlet();
	}

	@Override
	public String getPortletId() {
		return _liferayPortletConfig.getPortletId();
	}

	@Override
	public boolean isCopyRequestParameters() {
		return _liferayPortletConfig.isCopyRequestParameters();
	}

	@Override
	public boolean isWARFile() {
		return _liferayPortletConfig.isWARFile();
	}

	private final LiferayPortletConfig _liferayPortletConfig;

}