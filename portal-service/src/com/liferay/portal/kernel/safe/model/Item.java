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

package com.liferay.portal.kernel.safe.model;

/**
 * Represents super-interface for items that are kept inside portal safe.
 *
 * @author Tomas Polesovsky
 */
public interface Item {
	public long getCompanyId();

	public long getGroupId();

	public String getName();

	public void setCompanyId(long companyId);

	public void setGroupId(long groupId);

	public void setName(String name);

}