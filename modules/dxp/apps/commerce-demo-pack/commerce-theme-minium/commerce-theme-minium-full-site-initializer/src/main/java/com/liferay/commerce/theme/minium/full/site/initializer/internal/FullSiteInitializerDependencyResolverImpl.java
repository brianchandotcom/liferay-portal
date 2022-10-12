/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
	immediate = true,
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