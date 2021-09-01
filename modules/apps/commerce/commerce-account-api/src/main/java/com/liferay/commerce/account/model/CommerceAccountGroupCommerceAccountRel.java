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
 * The extended model interface for the CommerceAccountGroupCommerceAccountRel service. Represents a row in the &quot;CAccountGroupCAccountRel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CommerceAccountGroupCommerceAccountRelModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.account.model.impl.CommerceAccountGroupCommerceAccountRelImpl"
)
@ProviderType
public interface CommerceAccountGroupCommerceAccountRel
	extends CommerceAccountGroupCommerceAccountRelModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.account.model.impl.CommerceAccountGroupCommerceAccountRelImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceAccountGroupCommerceAccountRel, Long>
		COMMERCE_ACCOUNT_GROUP_COMMERCE_ACCOUNT_REL_ID_ACCESSOR =
			new Accessor<CommerceAccountGroupCommerceAccountRel, Long>() {

				@Override
				public Long get(
					CommerceAccountGroupCommerceAccountRel
						commerceAccountGroupCommerceAccountRel) {

					return commerceAccountGroupCommerceAccountRel.
						getCommerceAccountGroupCommerceAccountRelId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommerceAccountGroupCommerceAccountRel>
					getTypeClass() {

					return CommerceAccountGroupCommerceAccountRel.class;
				}

			};

	public CommerceAccount getCommerceAccount()
		throws com.liferay.portal.kernel.exception.PortalException;

}