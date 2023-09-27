/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CommerceShipment service. Represents a row in the &quot;CommerceShipment&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShipmentModel
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.model.impl.CommerceShipmentImpl")
@ProviderType
public interface CommerceShipment
	extends CommerceShipmentModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.model.impl.CommerceShipmentImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceShipment, Long>
		COMMERCE_SHIPMENT_ID_ACCESSOR = new Accessor<CommerceShipment, Long>() {

			@Override
			public Long get(CommerceShipment commerceShipment) {
				return commerceShipment.getCommerceShipmentId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CommerceShipment> getTypeClass() {
				return CommerceShipment.class;
			}

		};

	public CommerceAddress fetchCommerceAddress();

	public CommerceShippingMethod fetchCommerceShippingMethod();

	public com.liferay.account.model.AccountEntry getAccountEntry()
		throws com.liferay.portal.kernel.exception.PortalException;

	public String getAccountEntryName()
		throws com.liferay.portal.kernel.exception.PortalException;

	public CommerceShippingMethod getCommerceShippingMethod()
		throws com.liferay.portal.kernel.exception.PortalException;

}