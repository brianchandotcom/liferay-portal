/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.price.list.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CommercePriceListAccountRel service. Represents a row in the &quot;CommercePriceListAccountRel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListAccountRelModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.price.list.model.impl.CommercePriceListAccountRelImpl"
)
@ProviderType
public interface CommercePriceListAccountRel
	extends CommercePriceListAccountRelModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.price.list.model.impl.CommercePriceListAccountRelImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommercePriceListAccountRel, Long>
		COMMERCE_PRICE_LIST_ACCOUNT_REL_ID_ACCESSOR =
			new Accessor<CommercePriceListAccountRel, Long>() {

				@Override
				public Long get(
					CommercePriceListAccountRel commercePriceListAccountRel) {

					return commercePriceListAccountRel.
						getCommercePriceListAccountRelId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommercePriceListAccountRel> getTypeClass() {
					return CommercePriceListAccountRel.class;
				}

			};

	public com.liferay.account.model.AccountEntry getAccountEntry()
		throws com.liferay.portal.kernel.exception.PortalException;

	public CommercePriceList getCommercePriceList()
		throws com.liferay.portal.kernel.exception.PortalException;

}