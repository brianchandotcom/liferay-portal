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

import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;

/**
 * @author Tina Tian
 */
public interface TemplateResource extends Serializable {

	public static final String DEFAUT_ENCODING = StringPool.UTF8;

	public long getLastModified();

	public Reader getReader() throws IOException;

	public String getTemplateId();

}