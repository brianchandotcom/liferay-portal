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

package com.liferay.nested.portlets.web.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.configuration.admin.ConfigurationAdmin;

/**
 * @author Raymond Augé
 */
@ConfigurationAdmin(category = "web-experience-management")
@Meta.OCD(
	id = "com.liferay.nested.portlets.web.configuration.NestedPortletsConfiguration",
	localization = "content/Language"
)
public interface NestedPortletsConfiguration {

	public static final String TEMPLATE_CONTENT = "TEMPLATE_CONTENT";

	public static final String TEMPLATE_ID = "TEMPLATE_ID";

	@Meta.AD(
		deflt = "2_columns_i", id = "layout.template.default", required = false
	)
	public String layoutTemplateDefault();

	@Meta.AD(
		deflt = "freeform,1_column", id = "layout.template.unsupported",
		required = false
	)
	public String[] layoutTemplatesUnsupported();

}