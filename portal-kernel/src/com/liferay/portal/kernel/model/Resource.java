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

package com.liferay.portal.kernel.model;

/**
 * @author Brian Wing Shun Chan
 * @see    com.liferay.portal.model.impl.ResourceImpl
 */
public interface Resource {

	public long getCodeId();

	public long getCompanyId();

	public String getName();

	public String getPrimKey();

	public long getResourceId();

	public int getScope();

	public void setCodeId(long codeId);

	public void setCompanyId(long companyId);

	public void setName(String name);

	public void setPrimKey(String primKey);

	public void setResourceId(long resourceId);

	public void setScope(int scope);

}