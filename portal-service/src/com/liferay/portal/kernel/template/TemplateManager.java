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

package com.liferay.portal.kernel.template;

/**
 * @author Tina Tian
 */
public interface TemplateManager {

	public static final String FREE_MARKER = "FREE_MARKER";

	public static final String VELOCITY = "VELOCITY";

	public void clearCache();

	public void clearCache(String templateId);

	public void destroy()throws TemplateException;

	public String getManagerName();

	public Template getTemplate(
		String templateId, String templateContent, String errorTemplateId,
		String errorTemplateContent, TemplateContextType templateContextType);

	public Template getTemplate(
		String templateId, String templateContent, String errorTemplateId,
		TemplateContextType templateContextType);

	public Template getTemplate(
		String templateId, String templateContent,
		TemplateContextType templateContextType);

	public Template getTemplate(
		String templateId, TemplateContextType templateContextType);

	public boolean hasTemplate(String templateId);

	public void init() throws TemplateException;

}