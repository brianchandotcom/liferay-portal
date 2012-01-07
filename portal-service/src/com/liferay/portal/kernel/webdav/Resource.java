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

 	public String getHREF();

	public String getDisplayName();

	public Lock getLock();

 	public String getCreateDate();

 	public String getModifiedDate();

 	public long getSize();

	public Object getModel();

	public void setModel(Object model);

 	public String getClassName();

 	public void setClassName(String className);

 	public long getPrimaryKey();

 	public void setPrimaryKey(long primaryKey);

	public String getContentType();

	public InputStream getContentAsStream() throws WebDAVException ;

	public boolean isCollection();

	public boolean isLocked();

}