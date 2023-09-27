/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.theme.minium.full.site.initializer.internal;

import com.liferay.commerce.theme.minium.SiteInitializerDependencyResolver;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "site.initializer.key=" + MiniumFullSiteInitializer.KEY,
	service = SiteInitializerDependencyResolver.class
)
public class FullSiteInitializerDependencyResolverImpl
	implements SiteInitializerDependencyResolver {

	@Override
	public String getDependenciesPath() {
		return _DEPENDENCIES_PATH;
	}

	@Override
	public ClassLoader getDisplayTemplatesClassLoader() {
		return FullSiteInitializerDependencyResolverImpl.class.getClassLoader();
	}

	@Override
	public String getDisplayTemplatesDependencyPath() {
		return _DEPENDENCIES_PATH + "display_templates/";
	}

	@Override
	public ClassLoader getDocumentsClassLoader() {
		return FullSiteInitializerDependencyResolverImpl.class.getClassLoader();
	}

	@Override
	public String getDocumentsDependencyPath() {
		return _DEPENDENCIES_PATH + "documents/";
	}

	@Override
	public ClassLoader getImageClassLoader() {
		return FullSiteInitializerDependencyResolverImpl.class.getClassLoader();
	}

	@Override
	public String getImageDependencyPath() {
		return _DEPENDENCIES_PATH + "images/";
	}

	@Override
	public String getJSON(String name) throws IOException {
		ClassLoader classLoader =
			FullSiteInitializerDependencyResolverImpl.class.getClassLoader();

		try (InputStream inputStream = classLoader.getResourceAsStream(
				_DEPENDENCIES_PATH + name)) {

			if (inputStream != null) {
				return StringUtil.read(inputStream);
			}
		}

		return _miniumSiteInitializerDependencyResolver.getJSON(name);
	}

	@Override
	public String getKey() {
		return MiniumFullSiteInitializer.KEY;
	}

	private static final String _DEPENDENCIES_PATH =
		"com/liferay/commerce/theme/minium/full/site/initializer/internal" +
			"/dependencies/";

	@Reference(target = "(site.initializer.key=minium-initializer)")
	private SiteInitializerDependencyResolver
		_miniumSiteInitializerDependencyResolver;

}