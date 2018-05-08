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

package com.liferay.portal.custom.jsp.bag;

import com.liferay.portal.deploy.hot.CustomJspBag;
import com.liferay.portal.kernel.url.URLContainer;

import java.net.URL;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;

/**
 * @author Jenny Chen
 * @author Brian Wing Shun Chan
 */
public abstract class BaseCustomJspBag implements CustomJspBag {

	@Override
	public String getCustomJspDir() {
		return "META-INF/resources/custom_jsps";
	}

	@Override
	public List<String> getCustomJsps() {
		return _customJsps;
	}

	@Override
	public URLContainer getURLContainer() {
		return _urlContainer;
	}

	@Override
	public boolean isCustomJspGlobal() {
		return true;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundle = bundleContext.getBundle();

		_customJsps = new ArrayList<>();

		Enumeration<URL> enumeration = _bundle.findEntries(
			getCustomJspDir(), "*.jsp", true);

		while (enumeration.hasMoreElements()) {
			URL url = enumeration.nextElement();

			_customJsps.add(url.getPath());
		}
	}

	private Bundle _bundle;
	private List<String> _customJsps;

	private final URLContainer _urlContainer = new URLContainer() {

		@Override
		public URL getResource(String name) {
			return _bundle.getEntry(name);
		}

		@Override
		public Set<String> getResources(String path) {
			Set<String> paths = new HashSet<>();

			for (String customJsp : _customJsps) {
				if (customJsp.startsWith(path)) {
					paths.add(customJsp);
				}
			}

			return paths;
		}

	};

}