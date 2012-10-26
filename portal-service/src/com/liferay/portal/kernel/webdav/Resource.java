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

package com.liferay.portal.kernel.webdav;

import com.liferay.portal.model.Lock;

import java.io.InputStream;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public interface Resource {

	public String getClassName();

	public InputStream getContentAsStream() throws WebDAVException;

	public String getContentType();

	public String getCreateDate();

	public String getDisplayName();

	public String getHREF();

	public Lock getLock();

	public Object getModel();

	public String getModifiedDate();

	public long getPrimaryKey();

	public long getSize();

	public boolean isCollection();

	public boolean isLocked();

	public void setClassName(String className);

	public void setModel(Object model);

	public void setPrimaryKey(long primaryKey);

}