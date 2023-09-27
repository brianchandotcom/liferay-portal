/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.webdav;

import com.liferay.portal.kernel.lock.Lock;

import java.io.InputStream;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public interface Resource {

	public String getClassName();

	public InputStream getContentAsStream() throws WebDAVException;

	public String getContentType();

	public String getCreateDateString();

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