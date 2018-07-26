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

package com.liferay.frontend.js.loader.modules.extender.internal.npm.builtin;

import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.frontend.js.loader.modules.extender.npm.ModuleNameUtil;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistry;
import com.liferay.portal.kernel.util.MimeTypes;

import java.net.URL;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Servlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 */
@Component(
	immediate = true,
	property = {
		"osgi.http.whiteboard.servlet.name=Serve Package Servlet",
		"osgi.http.whiteboard.servlet.pattern=/js/resolved-module/*",
		"service.ranking:Integer=" + (Integer.MAX_VALUE - 1000)
	},
	service = {BuiltInJSResolvedModuleServlet.class, Servlet.class}
)
public class BuiltInJSResolvedModuleServlet extends BaseBuiltInJSModuleServlet {

	@Override
	protected MimeTypes getMimeTypes() {
		return _mimeTypes;
	}

	@Override
	protected URL getURL(String pathInfo) {
		String identifier = pathInfo.substring(1);

		String resolvedJSPackageId = ModuleNameUtil.getPackageName(identifier);

		JSPackage jsPackage = _getResolvedJSPackage(resolvedJSPackageId);

		if (jsPackage == null) {
			return null;
		}

		String packagePath = ModuleNameUtil.getPackagePath(identifier);

		return jsPackage.getResourceURL(packagePath);
	}

	private JSPackage _getResolvedJSPackage(String resolvedJSPackageId) {
		String packageId = _resolvedPackageIdentifiersCache.get(
			resolvedJSPackageId);

		if (packageId != null) {
			JSPackage jsPackage = _npmRegistry.getJSPackage(packageId);

			if (jsPackage != null) {
				return jsPackage;
			}

			_resolvedPackageIdentifiersCache.remove(resolvedJSPackageId);
		}

		Collection<JSPackage> resolvedJSPackages =
			_npmRegistry.getResolvedJSPackages();

		for (JSPackage resolvedJSPackage : resolvedJSPackages) {
			if (resolvedJSPackageId.equals(resolvedJSPackage.getResolvedId())) {
				_resolvedPackageIdentifiersCache.put(
					resolvedJSPackageId, resolvedJSPackage.getId());

				return resolvedJSPackage;
			}
		}

		return null;
	}

	private static final long serialVersionUID = 2647715401054034600L;

	@Reference
	private MimeTypes _mimeTypes;

	@Reference
	private NPMRegistry _npmRegistry;

	private LinkedHashMap<String, String> _resolvedPackageIdentifiersCache =
		new LinkedHashMap<String, String>() {

			@Override
			protected boolean removeEldestEntry(Map.Entry eldest) {
				Collection<JSPackage> resolvedJSPackages =
					_npmRegistry.getResolvedJSPackages();

				if (size() > resolvedJSPackages.size()) {
					return true;
				}

				return false;
			}

		};

}