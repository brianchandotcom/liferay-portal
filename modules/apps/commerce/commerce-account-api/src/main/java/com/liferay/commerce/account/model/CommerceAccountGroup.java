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
 * The extended model interface for the CommerceAccountGroup service. Represents a row in the &quot;CommerceAccountGroup&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CommerceAccountGroupModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.account.model.impl.CommerceAccountGroupImpl"
)
@ProviderType
public interface CommerceAccountGroup extends CommerceAccountGroupModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.account.model.impl.CommerceAccountGroupImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceAccountGroup, Long>
		COMMERCE_ACCOUNT_GROUP_ID_ACCESSOR =
			new Accessor<CommerceAccountGroup, Long>() {

				@Override
				public Long get(CommerceAccountGroup commerceAccountGroup) {
					return commerceAccountGroup.getCommerceAccountGroupId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommerceAccountGroup> getTypeClass() {
					return CommerceAccountGroup.class;
				}

			};

	public com.liferay.expando.kernel.model.ExpandoBridge getExpandoBridge();

}