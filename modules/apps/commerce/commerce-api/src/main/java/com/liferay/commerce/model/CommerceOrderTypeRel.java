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
 * The extended model interface for the CommerceOrderTypeRel service. Represents a row in the &quot;CommerceOrderTypeRel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderTypeRelModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.model.impl.CommerceOrderTypeRelImpl"
)
@ProviderType
public interface CommerceOrderTypeRel
	extends CommerceOrderTypeRelModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.model.impl.CommerceOrderTypeRelImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceOrderTypeRel, Long>
		COMMERCE_ORDER_TYPE_REL_ID_ACCESSOR =
			new Accessor<CommerceOrderTypeRel, Long>() {

				@Override
				public Long get(CommerceOrderTypeRel commerceOrderTypeRel) {
					return commerceOrderTypeRel.getCommerceOrderTypeRelId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommerceOrderTypeRel> getTypeClass() {
					return CommerceOrderTypeRel.class;
				}

			};

}