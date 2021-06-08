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
 * The extended model interface for the CommerceAccountUserRel service. Represents a row in the &quot;CommerceAccountUserRel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CommerceAccountUserRelModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.account.model.impl.CommerceAccountUserRelImpl"
)
@ProviderType
public interface CommerceAccountUserRel extends CommerceAccountUserRelModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.account.model.impl.CommerceAccountUserRelImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceAccountUserRel, Long>
		COMMERCE_ACCOUNT_ID_ACCESSOR =
			new Accessor<CommerceAccountUserRel, Long>() {

				@Override
				public Long get(CommerceAccountUserRel commerceAccountUserRel) {
					return commerceAccountUserRel.getCommerceAccountId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommerceAccountUserRel> getTypeClass() {
					return CommerceAccountUserRel.class;
				}

			};
	public static final Accessor<CommerceAccountUserRel, Long>
		COMMERCE_ACCOUNT_USER_ID_ACCESSOR =
			new Accessor<CommerceAccountUserRel, Long>() {

				@Override
				public Long get(CommerceAccountUserRel commerceAccountUserRel) {
					return commerceAccountUserRel.getCommerceAccountUserId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommerceAccountUserRel> getTypeClass() {
					return CommerceAccountUserRel.class;
				}

			};

	public com.liferay.portal.kernel.model.User getUser()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<com.liferay.portal.kernel.model.UserGroupRole>
			getUserGroupRoles()
		throws com.liferay.portal.kernel.exception.PortalException;

}