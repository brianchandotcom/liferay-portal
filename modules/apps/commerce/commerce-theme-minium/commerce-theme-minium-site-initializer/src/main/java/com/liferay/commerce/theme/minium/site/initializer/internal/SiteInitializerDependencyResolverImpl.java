/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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