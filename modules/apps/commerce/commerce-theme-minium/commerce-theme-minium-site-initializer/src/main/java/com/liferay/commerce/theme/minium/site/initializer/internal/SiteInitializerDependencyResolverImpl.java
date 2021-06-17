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

package com.liferay.commerce.theme.minium.site.initializer.internal;

import com.liferay.commerce.theme.minium.SiteInitializerDependencyResolver;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 */
@Component(
	enabled = false, immediate = true,
	property = "site.initializer.key=" + MiniumSiteInitializer.KEY,
	service = SiteInitializerDependencyResolver.class
)
public class SiteInitializerDependencyResolverImpl
	implements SiteInitializerDependencyResolver {

	@Override
	public String getDependenciesPath() {
		return _DEPENDENCIES_PATH;
	}

	@Override
	public ClassLoader getDisplayTemplatesClassLoader() {
		return SiteInitializerDependencyResolverImpl.class.getClassLoader();
	}

	@Override
	public String getDisplayTemplatesDependencyPath() {
		return _DEPENDENCIES_PATH + "display_templates/";
	}

	@Override
	public ClassLoader getDocumentsClassLoader() {
		return SiteInitializerDependencyResolverImpl.class.getClassLoader();
	}

	@Override
	public String getDocumentsDependencyPath() {
		return _DEPENDENCIES_PATH + "documents/";
	}

	@Override
	public ClassLoader getImageClassLoader() {
		return SiteInitializerDependencyResolverImpl.class.getClassLoader();
	}

	@Override
	public String getImageDependencyPath() {
		return _DEPENDENCIES_PATH + "images/";
	}

	@Override
	public String getJSON(String name) throws IOException {
		return StringUtil.read(
			SiteInitializerDependencyResolverImpl.class.getClassLoader(),
			_DEPENDENCIES_PATH + name);
	}

	@Override
	public String getKey() {
		return MiniumSiteInitializer.KEY;
	}

	private static final String _DEPENDENCIES_PATH =
		"com/liferay/commerce/theme/minium/site/initializer/internal" +
			"/dependencies/";

}