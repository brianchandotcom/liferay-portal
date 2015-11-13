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

package com.liferay.portal.template.freemarker.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.configuration.admin.ConfigurationAdmin;

/**
 * @author Peter Fellwock
 */
@ConfigurationAdmin(category = "platform")
@Meta.OCD(
	id = "com.liferay.portal.template.freemarker.configuration.FreeMarkerEngineConfiguration"
)
public interface FreeMarkerEngineConfiguration {

	@Meta.AD(required = false)
	public String[] allowedClasses();

	@Meta.AD(deflt = "false", required = false)
	public boolean localizedLookup();

	@Meta.AD(deflt = "FTL_liferay.ftl as liferay", required = false)
	public String[] macroLibrary();

	@Meta.AD(deflt = "60", required = false)
	public int resourceModificationCheck();

	@Meta.AD(
		deflt = "java.lang.Class|java.lang.ClassLoader|java.lang.Thread",
		required = false
	)
	public String[] restrictedClasses();

	@Meta.AD(deflt = "serviceLocator", required = false)
	public String[] restrictedVariables();

	@Meta.AD(deflt = "rethrow", required = false)
	public String templateExceptionHandler();

	@Meta.AD(
		deflt = "com.liferay.portal.template.freemarker.FreeMarkerBundleResourceParser|com.liferay.portal.template.freemarker.FreeMarkerServletResourceParser|com.liferay.portal.template.ThemeResourceParser|com.liferay.portal.template.DDMTemplateResourceParser|com.liferay.portal.template.ClassLoaderResourceParser",
		required = false
	)
	public String[] templateParsers();

}