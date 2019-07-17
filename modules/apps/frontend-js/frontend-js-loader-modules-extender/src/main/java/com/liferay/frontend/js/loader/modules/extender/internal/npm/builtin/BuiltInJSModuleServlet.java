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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.MimeTypes;

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
		"osgi.http.whiteboard.servlet.pattern=/js/module/*",
		"service.ranking:Integer=" + (Integer.MAX_VALUE - 1000)
	},
	service = {BuiltInJSModuleServlet.class, Servlet.class}
)
public class BuiltInJSModuleServlet extends BaseBuiltInJSModuleServlet {

	@Override
	protected MimeTypes getMimeTypes() {
		return _mimeTypes;
	}

	@Override
	protected ResourceDescriptor getResourceDescriptor(String pathInfo) {
		String identifier = pathInfo.substring(1);

		int i = identifier.indexOf(StringPool.SLASH);

		if (i == -1) {
			return null;
		}

		String bundleId = identifier.substring(0, i);

		identifier = identifier.substring(i + 1);

		String packageName = ModuleNameUtil.getPackageName(identifier);

		JSPackage jsPackage = _npmRegistry.getJSPackage(
			bundleId + StringPool.SLASH + packageName);

		if (jsPackage == null) {
			return null;
		}

		return new ResourceDescriptor(
			jsPackage, ModuleNameUtil.getPackagePath(identifier));
	}

	private static final long serialVersionUID = -8753225208295935344L;

	@Reference
	private MimeTypes _mimeTypes;

	@Reference
	private NPMRegistry _npmRegistry;

}