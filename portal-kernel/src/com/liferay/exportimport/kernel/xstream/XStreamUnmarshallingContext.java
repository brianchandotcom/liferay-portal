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

package com.liferay.exportimport.kernel.xstream;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Daniel Kocsis
 */
@ProviderType
public interface XStreamUnmarshallingContext {

	public void addCompletionCallback(Runnable runnable, int i);

	public Object convertAnother(Object object, Class<?> clazz);

	public Object convertAnother(
		Object object, Class<?> clazz, XStreamConverter xStreamConverter);

	public Object currentObject();

	public Object getRequiredType();

}