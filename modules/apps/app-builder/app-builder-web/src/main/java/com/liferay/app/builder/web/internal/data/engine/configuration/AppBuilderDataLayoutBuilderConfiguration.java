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

package com.liferay.app.builder.web.internal.data.engine.configuration;

import com.liferay.data.engine.configuration.DataLayoutBuilderConfiguration;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true, property = "content.type=app-builder",
	service = DataLayoutBuilderConfiguration.class
)
public class AppBuilderDataLayoutBuilderConfiguration
	implements DataLayoutBuilderConfiguration {

	@Override
	public Map<String, Object> getSuccessPageSettings() {
		return HashMapBuilder.<String, Object>put(
			"enabled", false
		).build();
	}

}