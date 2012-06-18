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

package com.liferay.portal.kernel.velocity;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.io.Writer;

/**
 * @author Raymond Augé
 */
public class VelocityEngineUtil {

	public static void destroy(ClassLoader classLoader) {
		getVelocityEngine().destroy(classLoader);
	}

	public static void flushTemplate(String velocityTemplateId) {
		getVelocityEngine().flushTemplate(velocityTemplateId);
	}

	public static VelocityContext getEmptyContext() {
		return getVelocityEngine().getEmptyContext();
	}

	public static VelocityContext getRestrictedToolsContext() {
		return getVelocityEngine().getRestrictedToolsContext();
	}

	public static VelocityContext getStandardToolsContext() {
		return getVelocityEngine().getStandardToolsContext();
	}

	public static VelocityEngine getVelocityEngine() {
		PortalRuntimePermission.checkGetBeanProperty(VelocityEngineUtil.class);

		return _velocityEngine;
	}

	public static VelocityContext getWrappedClassLoaderToolsContext() {
		return getVelocityEngine().getWrappedClassLoaderToolsContext();
	}

	public static VelocityContext getWrappedRestrictedToolsContext() {
		return getVelocityEngine().getWrappedRestrictedToolsContext();
	}

	public static VelocityContext getWrappedStandardToolsContext() {
		return getVelocityEngine().getWrappedStandardToolsContext();
	}

	public static void init() throws Exception {
		getVelocityEngine().init();
	}

	public static boolean mergeTemplate(
			String velocityTemplateId, String velocityTemplateContent,
			VelocityContext velocityContext, Writer writer)
		throws Exception {

		return getVelocityEngine().mergeTemplate(
			velocityTemplateId, velocityTemplateContent, velocityContext,
			writer);
	}

	public static boolean mergeTemplate(
			String velocityTemplateId, VelocityContext velocityContext,
			Writer writer)
		throws Exception {

		return getVelocityEngine().mergeTemplate(
			velocityTemplateId, velocityContext, writer);
	}

	public static boolean resourceExists(String resource) {
		return getVelocityEngine().resourceExists(resource);
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_velocityEngine = velocityEngine;
	}

	private static VelocityEngine _velocityEngine;

}