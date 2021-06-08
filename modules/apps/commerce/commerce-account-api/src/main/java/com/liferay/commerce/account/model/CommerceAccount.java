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

package com.liferay.commerce.account.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CommerceAccount service. Represents a row in the &quot;CommerceAccount&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CommerceAccountModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.account.model.impl.CommerceAccountImpl"
)
@ProviderType
public interface CommerceAccount extends CommerceAccountModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.account.model.impl.CommerceAccountImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceAccount, Long>
		COMMERCE_ACCOUNT_ID_ACCESSOR = new Accessor<CommerceAccount, Long>() {

			@Override
			public Long get(CommerceAccount commerceAccount) {
				return commerceAccount.getCommerceAccountId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CommerceAccount> getTypeClass() {
				return CommerceAccount.class;
			}

		};
	public static final Accessor<CommerceAccount, String> NAME_ACCESSOR =
		new Accessor<CommerceAccount, String>() {

			@Override
			public String get(CommerceAccount commerceAccount) {
				return commerceAccount.getName();
			}

			@Override
			public Class<String> getAttributeClass() {
				return String.class;
			}

			@Override
			public Class<CommerceAccount> getTypeClass() {
				return CommerceAccount.class;
			}

		};

	public com.liferay.portal.kernel.model.Group getCommerceAccountGroup()
		throws com.liferay.portal.kernel.exception.PortalException;

	public long getCommerceAccountGroupId()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<CommerceAccountOrganizationRel>
		getCommerceAccountOrganizationRels();

	public java.util.List<CommerceAccountUserRel> getCommerceAccountUserRels();

	public com.liferay.expando.kernel.model.ExpandoBridge getExpandoBridge();

	public CommerceAccount getParentCommerceAccount()
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean isBusinessAccount();

	public boolean isPersonalAccount();

	public boolean isRoot();

}