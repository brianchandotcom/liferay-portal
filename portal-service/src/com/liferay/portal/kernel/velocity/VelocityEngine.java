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

import java.io.Writer;

/**
 * @author Raymond Augé
 */
public interface VelocityEngine {

	public void destroy(ClassLoader classLoader);

	public void flushTemplate(String velocityTemplateId);

	public VelocityContext getEmptyContext();

	public VelocityContext getRestrictedToolsContext();

	public VelocityContext getStandardToolsContext();

	public VelocityContext getWrappedClassLoaderToolsContext();

	public VelocityContext getWrappedRestrictedToolsContext();

	public VelocityContext getWrappedStandardToolsContext();

	public void init() throws Exception;

	public boolean mergeTemplate(
			String velocityTemplateId, String velocityTemplateContent,
			VelocityContext velocityContext, Writer writer)
		throws Exception;

	public boolean mergeTemplate(
			String velocityTemplateId, VelocityContext velocityContext,
			Writer writer)
		throws Exception;

	public boolean resourceExists(String resource);

}