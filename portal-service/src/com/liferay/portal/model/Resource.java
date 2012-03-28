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

package com.liferay.portal.model;

/**
 * The extended model interface for the Resource service. Represents a row in the &quot;Resource_&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.model.impl.ResourceImpl
 * @generated
 */
public interface Resource {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portal.model.impl.ResourceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public long getCompanyId()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.lang.String getName()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.lang.String getPrimKey()
		throws com.liferay.portal.kernel.exception.PortalException,
		com.liferay.portal.kernel.exception.SystemException;

	public int getScope()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void setCompanyId(long companyId);

	public void setName(java.lang.String name);

	public void setPrimKey(String primKey);

	public void setScope(int scope);
}