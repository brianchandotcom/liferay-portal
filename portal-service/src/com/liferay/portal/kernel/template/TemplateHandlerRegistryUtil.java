/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.util.List;

/**
 * @author Juan Fernández
 */
public class TemplateHandlerRegistryUtil {

	public static long[] getClassNameIds() {
		return getTemplateRegistry().getClassNameIds();
	}

	public static TemplateHandler getTemplateHandler(long classNameId) {
		return getTemplateRegistry().getTemplateHandler(classNameId);
	}

	public static TemplateHandler getTemplateHandler(String className) {
		return getTemplateRegistry().getTemplateHandler(className);
	}

	public static List<TemplateHandler> getTemplateHandlers() {
		return getTemplateRegistry().getTemplateHandlers();
	}

	public static TemplateHandlerRegistry getTemplateRegistry() {
		PortalRuntimePermission.checkGetBeanProperty(
			TemplateHandlerRegistryUtil.class);

		return _templateHandlerRegistry;
	}

	public static void register(TemplateHandler templateHandler) {
		getTemplateRegistry().register(templateHandler);
	}

	public static void unregister(TemplateHandler templateHandler) {
		getTemplateRegistry().unregister(templateHandler);
	}

	public void setTemplateHandlerRegistry(
		TemplateHandlerRegistry templateHandlerRegistry) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_templateHandlerRegistry = templateHandlerRegistry;
	}

	private static TemplateHandlerRegistry _templateHandlerRegistry;

}