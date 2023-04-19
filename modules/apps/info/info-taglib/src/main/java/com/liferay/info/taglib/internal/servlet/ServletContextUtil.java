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

package com.liferay.info.taglib.internal.servlet;

import com.liferay.info.item.renderer.InfoItemRendererRegistry;
import com.liferay.osgi.util.service.Snapshot;

import javax.servlet.ServletContext;

/**
 * @author Pavel Savinov
 */
public class ServletContextUtil {

	public static InfoItemRendererRegistry getInfoItemRendererRegistry() {
		return _infoItemRendererRegistrySnapshot.get();
	}

	public static ServletContext getServletContext() {
		return _servletContextSnapshot.get();
	}

	private static final Snapshot<InfoItemRendererRegistry>
		_infoItemRendererRegistrySnapshot = new Snapshot<>(
			ServletContextUtil.class, InfoItemRendererRegistry.class);
	private static final Snapshot<ServletContext> _servletContextSnapshot =
		new Snapshot<>(
			ServletContextUtil.class, ServletContext.class,
			"(osgi.web.symbolicname=com.liferay.info.taglib)");

}