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

package com.liferay.portal.loader.module.extender;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author Raymond Augé
 */
@Meta.OCD(
	id = "com.liferay.portal.loader.module.extender.LoaderModulesServlet",
	name = "%Loader Module Servlet Configuration"
)
public @interface Details {

	public static final String CONFIG_JSON = "META-INF/config.json";

	public static final String CONTENT_TYPE = "text/javascript; charset=UTF-8";

	public static final int MAX_VALUE_LESS_1K = (Integer.MAX_VALUE - 1000);

	public static final String OSGI_WEBRESOURCE = "osgi.webresource";

	@Meta.AD(deflt = "true")
	public boolean loader_modules_apply_versioning() default true;

	@Meta.AD(deflt = "Liferay")
	public String loader_modules_global_js_variable() default "Liferay";

}