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

package com.liferay.portal.kernel.lock;

import java.util.Date;

/**
 * @author Tina Tian
 */
public interface Lock {

	public String getClassName();

	public long getCompanyId();

	public Date getCreateDate();

	public Date getExpirationDate();

	public long getExpirationTime();

	public boolean getInheritable();

	public String getKey();

	public long getLockId();

	public String getOwner();

	public long getUserId();

	public String getUserName();

	public String getUserUuid();

	public String getUuid();

	public boolean isCachedModel();

	public boolean isEscapedModel();

	public boolean isExpired();

	public boolean isInheritable();

	public boolean isNeverExpires();

	public boolean isNew();

}