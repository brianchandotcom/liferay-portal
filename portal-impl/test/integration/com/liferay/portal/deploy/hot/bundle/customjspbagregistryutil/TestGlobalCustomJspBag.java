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

package com.liferay.portal.deploy.hot.bundle.customjspbagregistryutil;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.deploy.hot.CustomJspBag;
import com.liferay.portal.kernel.url.URLContainer;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.osgi.service.component.annotations.Component;

/**
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	property = {
		"context.id=TestGlobalCustomJspBag",
		"context.name=Test Global Custom JSP Bag",
		"service.ranking:Integer=" + Integer.MAX_VALUE
	}
)
public class TestGlobalCustomJspBag implements CustomJspBag {

	@Override
	public String getCustomJspDir() {
		return StringPool.SLASH;
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

	private final List<String> _customJsps = new ArrayList<>();

	private final URLContainer _urlContainer = new URLContainer() {

		@Override
		public URL getResource(String name) {
			Class<?> clazz = getClass();

			return clazz.getResource("dependencies/bottom-ext.jsp");
		}

		@Override
		public Set<String> getResources(String path) {
			return Collections.singleton("/html/common/themes/bottom-ext.jsp");
		}

	};

}