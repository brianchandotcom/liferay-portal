/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.freemarker;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.io.Writer;

/**
 * @author Mika Koivisto
 */
public class FreeMarkerEngineUtil {

	public static void destroy(ClassLoader classLoader) {
		getFreeMarkerEngine().destroy(classLoader);
	}

	public static void flushTemplate(String freeMarkerTemplateId) {
		getFreeMarkerEngine().flushTemplate(freeMarkerTemplateId);
	}

	public static FreeMarkerEngine getFreeMarkerEngine() {
		PortalRuntimePermission.checkGetBeanProperty(
			FreeMarkerEngineUtil.class);

		return _freeMarkerEngine;
	}

	public static FreeMarkerContext getWrappedClassLoaderToolsContext() {
		return getFreeMarkerEngine().getWrappedClassLoaderToolsContext();
	}

	public static FreeMarkerContext getWrappedRestrictedToolsContext() {
		return getFreeMarkerEngine().getWrappedRestrictedToolsContext();
	}

	public static FreeMarkerContext getWrappedStandardToolsContext() {
		return getFreeMarkerEngine().getWrappedStandardToolsContext();
	}

	public static void init() throws Exception {
		getFreeMarkerEngine().init();
	}

	public static boolean mergeTemplate(
			String freeMarkerTemplateId, FreeMarkerContext freeMarkerContext,
			Writer writer)
		throws Exception {

		return getFreeMarkerEngine().mergeTemplate(
			freeMarkerTemplateId, freeMarkerContext, writer);
	}

	public static boolean mergeTemplate(
			String freeMarkerTemplateId, String freemarkerTemplateContent,
			FreeMarkerContext freeMarkerContext, Writer writer)
		throws Exception {

		return getFreeMarkerEngine().mergeTemplate(
			freeMarkerTemplateId, freemarkerTemplateContent, freeMarkerContext,
			writer);
	}

	public static boolean resourceExists(String resource) {
		return getFreeMarkerEngine().resourceExists(resource);
	}

	public void setFreeMarkerEngine(FreeMarkerEngine freeMarkerEngine) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_freeMarkerEngine = freeMarkerEngine;
	}

	private static FreeMarkerEngine _freeMarkerEngine;

}