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
 * The extended model interface for the Company service. Represents a row in the &quot;Company&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see CompanyModel
 * @see com.liferay.portal.model.impl.CompanyImpl
 * @see com.liferay.portal.model.impl.CompanyModelImpl
 * @generated
 */
public interface Company extends CompanyModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portal.model.impl.CompanyImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public int compareTo(com.liferay.portal.model.Company company);

	public com.liferay.portal.model.Account getAccount()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.lang.String getAdminName();

	public java.lang.String getAuthType()
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.User getDefaultUser()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.lang.String getDefaultWebId();

	public java.lang.String getEmailAddress();

	public com.liferay.portal.model.Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.security.Key getKeyObj();

	public java.util.Locale getLocale()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@com.liferay.portal.kernel.bean.AutoEscape()
	public java.lang.String getName()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.lang.String getPortalURL(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.lang.String getShardName()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.lang.String getShortName()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.util.TimeZone getTimeZone()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.lang.String getVirtualHostname();

	public boolean hasCompanyMx(java.lang.String emailAddress)
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean isAutoLogin()
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean isSendPassword()
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean isSendPasswordResetLink()
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean isSiteLogo()
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean isStrangers()
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean isStrangersVerify()
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean isStrangersWithMx()
		throws com.liferay.portal.kernel.exception.SystemException;

	public void setKey(java.lang.String key);

	public void setKeyObj(java.security.Key keyObj);

	public void setVirtualHostname(java.lang.String virtualHostname);
}