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
 * The extended model interface for the CommerceAccountOrganizationRel service. Represents a row in the &quot;CommerceAccountOrganizationRel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CommerceAccountOrganizationRelModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.account.model.impl.CommerceAccountOrganizationRelImpl"
)
@ProviderType
public interface CommerceAccountOrganizationRel
	extends CommerceAccountOrganizationRelModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.account.model.impl.CommerceAccountOrganizationRelImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceAccountOrganizationRel, Long>
		COMMERCE_ACCOUNT_ID_ACCESSOR =
			new Accessor<CommerceAccountOrganizationRel, Long>() {

				@Override
				public Long get(
					CommerceAccountOrganizationRel
						commerceAccountOrganizationRel) {

					return commerceAccountOrganizationRel.
						getCommerceAccountId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommerceAccountOrganizationRel> getTypeClass() {
					return CommerceAccountOrganizationRel.class;
				}

			};
	public static final Accessor<CommerceAccountOrganizationRel, Long>
		ORGANIZATION_ID_ACCESSOR =
			new Accessor<CommerceAccountOrganizationRel, Long>() {

				@Override
				public Long get(
					CommerceAccountOrganizationRel
						commerceAccountOrganizationRel) {

					return commerceAccountOrganizationRel.getOrganizationId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommerceAccountOrganizationRel> getTypeClass() {
					return CommerceAccountOrganizationRel.class;
				}

			};

	public com.liferay.portal.kernel.model.Organization getOrganization()
		throws com.liferay.portal.kernel.exception.PortalException;

}