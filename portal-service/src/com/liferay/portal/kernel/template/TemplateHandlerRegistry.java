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

import java.util.List;

/**
 * @author Juan Fernández
 */
public interface TemplateHandlerRegistry {

	public long[] getClassNameIds();

	/**
	 * Returns the template handler associated with the class name ID.
	 *
	 * @param  classNameId the class name ID of the template
	 * @return the template handler associated with the class name ID
	 */
	public TemplateHandler getTemplateHandler(long classNameId);

	/**
	 * Returns the template handler associated with the class name.
	 *
	 * @param  className the class name of the template
	 * @return the template handler associated with the class name
	 */
	public TemplateHandler getTemplateHandler(String className);

	/**
	 * Returns all the template handlers.
	 *
	 * @return the template handlers
	 */
	public List<TemplateHandler> getTemplateHandlers();

	/**
	 * Registers the template handler.
	 *
	 * @param templateHandler the template handler to register
	 */
	public void register(TemplateHandler templateHandler);

	/**
	 * Unregisters the template handler.
	 *
	 * @param templateHandler the template handler to unregister
	 */
	public void unregister(TemplateHandler templateHandler);

}