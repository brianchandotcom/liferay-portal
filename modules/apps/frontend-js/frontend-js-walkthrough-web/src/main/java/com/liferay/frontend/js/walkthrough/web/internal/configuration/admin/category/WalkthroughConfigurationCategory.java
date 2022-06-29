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

package com.liferay.frontend.js.walkthrough.web.internal.configuration.admin.category;

import com.liferay.configuration.admin.category.ConfigurationCategory;

import org.osgi.service.component.annotations.Component;

/**
 * @author Matuzalem Teles
 */
@Component(service = ConfigurationCategory.class)
public class WalkthroughConfigurationCategory implements ConfigurationCategory {

	@Override
	public String getBundleSymbolicName() {
		return "com.liferay.frontend.js.walkthrough.web";
	}

	@Override
	public String getCategoryIcon() {
		return "cog";
	}

	@Override
	public String getCategoryKey() {
		return "frontend-walkthrough";
	}

	@Override
	public String getCategorySection() {
		return "content-and-data";
	}

}