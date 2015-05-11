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

package com.liferay.nested.portlets.web.configuration.internal;

import com.liferay.nested.portlets.web.configuration.NestedPortletsPortletInstanceConfiguration;
import com.liferay.nested.portlets.web.constants.NestedPortletsPortletKeys;
import com.liferay.portal.kernel.settings.definition.SettingsIdMapping;

import org.osgi.service.component.annotations.Component;

/**
 * @author Juergen Kappler
 */
@Component
public class NestedPortletsPortletInstanceSettingsIdMapping
	implements SettingsIdMapping {

	@Override
	public Class<?> getConfigurationBeanClass() {
		return NestedPortletsPortletInstanceConfiguration.class;
	}

	@Override
	public String getSettingsId() {
		return NestedPortletsPortletKeys.NESTED_PORTLETS;
	}

}