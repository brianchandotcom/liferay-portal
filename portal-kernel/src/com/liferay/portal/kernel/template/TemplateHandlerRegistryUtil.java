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

package com.liferay.portal.kernel.template;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.util.List;

/**
 * @author Juan Fernández
 */
public class TemplateHandlerRegistryUtil {

	public static long[] getClassNameIds() {
		return _templateHandlerRegistry.getClassNameIds();
	}

	public static TemplateHandler getTemplateHandler(long classNameId) {
		return _templateHandlerRegistry.getTemplateHandler(classNameId);
	}

	public static TemplateHandler getTemplateHandler(String className) {
		return _templateHandlerRegistry.getTemplateHandler(className);
	}

	public static List<TemplateHandler> getTemplateHandlers() {
		return _templateHandlerRegistry.getTemplateHandlers();
	}

	private static volatile TemplateHandlerRegistry _templateHandlerRegistry =
		ServiceProxyFactory.newServiceTrackedInstance(
			TemplateHandlerRegistry.class, TemplateHandlerRegistryUtil.class,
			"_templateHandlerRegistry", false);

}