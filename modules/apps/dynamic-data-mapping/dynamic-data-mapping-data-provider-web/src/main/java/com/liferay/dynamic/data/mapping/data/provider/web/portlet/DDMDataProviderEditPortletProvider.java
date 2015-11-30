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

package com.liferay.dynamic.data.mapping.data.provider.web.portlet;

import com.liferay.dynamic.data.mapping.data.provider.web.constants.DDMDataProviderPortletKeys;
import com.liferay.portal.kernel.portlet.BasePortletProvider;
import com.liferay.portal.kernel.portlet.EditPortletProvider;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance"},
	service = EditPortletProvider.class
)
public class DDMDataProviderEditPortletProvider
	extends BasePortletProvider implements EditPortletProvider {

	@Override
	public String getPortletId() {
		return DDMDataProviderPortletKeys.DYNAMIC_DATA_MAPPING_DATA_PROVIDER;
	}

}