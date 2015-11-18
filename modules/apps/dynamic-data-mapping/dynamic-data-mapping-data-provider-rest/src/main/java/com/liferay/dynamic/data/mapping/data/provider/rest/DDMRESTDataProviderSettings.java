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

package com.liferay.dynamic.data.mapping.data.provider.rest;

import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.annotations.DDMFormField;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderSettings;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, property = "ddm.data.provider.name=rest")
public class DDMRESTDataProviderSettings implements DDMDataProviderSettings {

	@Override
	public Class<?> getSettings() {
		return RESTSettings.class;
	}

	@DDMForm
	public interface RESTSettings {

		@DDMFormField
		public String key();

		@DDMFormField
		public String password();

		@DDMFormField
		public String url();

		@DDMFormField
		public String username();

		@DDMFormField
		public String value();

	}

}