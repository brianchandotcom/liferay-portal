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

package com.liferay.frontend.js.a11y.web.internal.servlet.taglib.helper;

import com.liferay.frontend.js.a11y.web.internal.configuration.FlagA11yConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Matuzalem Teles
 */
@Component(
	configurationPid = "com.liferay.frontend.js.a11y.web.internal.configuration.FlagA11yConfiguration",
	immediate = true, service = FlagA11yHelper.class
)
public class FlagA11yHelper {

	public boolean getEnable() {
		return _flagA11yConfiguration.enable();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_flagA11yConfiguration = ConfigurableUtil.createConfigurable(
			FlagA11yConfiguration.class, properties);
	}

	private volatile FlagA11yConfiguration _flagA11yConfiguration;

}