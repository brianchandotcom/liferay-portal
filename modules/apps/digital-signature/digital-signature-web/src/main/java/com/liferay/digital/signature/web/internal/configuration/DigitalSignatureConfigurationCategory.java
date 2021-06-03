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

package com.liferay.digital.signature.web.internal.configuration;

import com.liferay.configuration.admin.category.ConfigurationCategory;

import org.osgi.service.component.annotations.Component;

/**
 * @author José Abelenda
 */
@Component(service = ConfigurationCategory.class)
public class DigitalSignatureConfigurationCategory
	implements ConfigurationCategory {

	@Override
	public String getCategoryIcon() {
		return "signature";
	}

	@Override
	public String getCategoryKey() {
		return "digital-signature";
	}

	@Override
	public String getCategorySection() {
		return "content-and-data";
	}

}