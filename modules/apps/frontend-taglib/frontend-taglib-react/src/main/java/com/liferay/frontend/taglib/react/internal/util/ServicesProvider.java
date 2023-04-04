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

package com.liferay.frontend.taglib.react.internal.util;

import com.liferay.frontend.js.module.launcher.JSModuleResolver;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.template.react.renderer.ReactRenderer;

/**
 * @author Chema Balsas
 */
public class ServicesProvider {

	public static JSModuleResolver getJSModuleResolver() {
		return _jsModuleResolverSnapshot.get();
	}

	public static ReactRenderer getReactRenderer() {
		return _reactRendererSnapshot.get();
	}

	private static final Snapshot<JSModuleResolver> _jsModuleResolverSnapshot =
		new Snapshot<>(ServicesProvider.class, JSModuleResolver.class);
	private static final Snapshot<ReactRenderer> _reactRendererSnapshot =
		new Snapshot<>(ServicesProvider.class, ReactRenderer.class);

}