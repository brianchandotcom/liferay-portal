/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CommercePaymentMethodGroupRel service. Represents a row in the &quot;CommercePaymentMethodGroupRel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Luca Pellizzon
 * @see CommercePaymentMethodGroupRelModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.payment.model.impl.CommercePaymentMethodGroupRelImpl"
)
@ProviderType
public interface CommercePaymentMethodGroupRel
	extends CommercePaymentMethodGroupRelModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.payment.model.impl.CommercePaymentMethodGroupRelImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommercePaymentMethodGroupRel, Long>
		COMMERCE_PAYMENT_METHOD_GROUP_REL_ID_ACCESSOR =
			new Accessor<CommercePaymentMethodGroupRel, Long>() {

				@Override
				public Long get(
					CommercePaymentMethodGroupRel
						commercePaymentMethodGroupRel) {

					return commercePaymentMethodGroupRel.
						getCommercePaymentMethodGroupRelId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommercePaymentMethodGroupRel> getTypeClass() {
					return CommercePaymentMethodGroupRel.class;
				}

			};

	public String getImageURL(
		com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay);

	public com.liferay.portal.kernel.util.UnicodeProperties
		getTypeSettingsUnicodeProperties();

}