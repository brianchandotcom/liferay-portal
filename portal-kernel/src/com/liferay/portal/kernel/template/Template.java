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

import java.io.Writer;

import java.util.Map;
import java.util.function.Supplier;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Tina Tian
 */
public interface Template extends Map<String, Object> {

	public void prepare(HttpServletRequest httpServletRequest);

	public void processTemplate(Writer writer) throws TemplateException;

	public void processTemplate(
			Writer writer,
			Supplier<TemplateResource> errorTemplateResourceSupplier)
		throws TemplateException;

}