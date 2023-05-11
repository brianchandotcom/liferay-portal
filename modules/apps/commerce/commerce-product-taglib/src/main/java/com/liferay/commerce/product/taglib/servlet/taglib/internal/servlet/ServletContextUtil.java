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

package com.liferay.commerce.product.taglib.servlet.taglib.internal.servlet;

import com.liferay.commerce.product.content.render.list.CPContentListRendererRegistry;
import com.liferay.commerce.product.content.render.list.entry.CPContentListEntryRendererRegistry;
import com.liferay.commerce.product.content.util.CPContentHelper;
import com.liferay.osgi.util.service.Snapshot;

import javax.servlet.ServletContext;

/**
 * @author Alessio Antonio Rendina
 */
public class ServletContextUtil {

	public static CPContentHelper getCPContentHelper() {
		return _cpContentHelperSnapshot.get();
	}

	public static CPContentListEntryRendererRegistry
		getCPContentListEntryRendererRegistry() {

		return _cpContentListEntryRendererRegistrySnapshot.get();
	}

	public static CPContentListRendererRegistry
		getCPContentListRendererRegistry() {

		return _cpContentListRendererRegistrySnapshot.get();
	}

	public static ServletContext getServletContext() {
		return _servletContextSnapshot.get();
	}

	private static final Snapshot<CPContentHelper> _cpContentHelperSnapshot =
		new Snapshot<>(ServletContextUtil.class, CPContentHelper.class);
	private static final Snapshot<CPContentListEntryRendererRegistry>
		_cpContentListEntryRendererRegistrySnapshot = new Snapshot<>(
			ServletContextUtil.class, CPContentListEntryRendererRegistry.class);
	private static final Snapshot<CPContentListRendererRegistry>
		_cpContentListRendererRegistrySnapshot = new Snapshot<>(
			ServletContextUtil.class, CPContentListRendererRegistry.class);
	private static final Snapshot<ServletContext> _servletContextSnapshot =
		new Snapshot<>(
			ServletContextUtil.class, ServletContext.class,
			"(osgi.web.symbolicname=com.liferay.commerce.product.taglib)");

}