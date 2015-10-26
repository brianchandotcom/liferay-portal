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

package com.liferay.portal.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the Company service. Represents a row in the &quot;Company&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see CompanyModel
 * @see com.liferay.portal.model.impl.CompanyImpl
 * @see com.liferay.portal.model.impl.CompanyModelImpl
 * @generated
 */
@ProviderType
public interface Company extends CompanyModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portal.model.impl.CompanyImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<Company, Long> COMPANY_ID_ACCESSOR = new Accessor<Company, Long>() {
			@Override
			public Long get(Company company) {
				return company.getCompanyId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<Company> getTypeClass() {
				return Company.class;
			}
		};

	public com.liferay.portal.model.Account getAccount()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.lang.String getAdminName();

	public java.lang.String getAuthType();

	public com.liferay.portal.model.User getDefaultUser()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.lang.String getDefaultWebId();

	public java.lang.String getEmailAddress();

	public com.liferay.portal.model.Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException;

	public long getGroupId()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.security.Key getKeyObj();

	public java.util.Locale getLocale()
		throws com.liferay.portal.kernel.exception.PortalException;

	@com.liferay.portal.kernel.bean.AutoEscape()
	public java.lang.String getName()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.lang.String getPortalURL(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.lang.String getShortName()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.TimeZone getTimeZone()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.lang.String getVirtualHostname();

	public boolean hasCompanyMx(java.lang.String emailAddress);

	public boolean isAutoLogin();

	public boolean isSendPassword();

	public boolean isSendPasswordResetLink();

	public boolean isSiteLogo();

	public boolean isStrangers();

	public boolean isStrangersVerify();

	public boolean isStrangersWithMx();

	public void setKeyObj(java.security.Key keyObj);

	public void setVirtualHostname(java.lang.String virtualHostname);
}