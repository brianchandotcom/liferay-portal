/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CPOptionValue service. Represents a row in the &quot;CPOptionValue&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CPOptionValueModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.product.model.impl.CPOptionValueImpl"
)
@ProviderType
public interface CPOptionValue extends CPOptionValueModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.product.model.impl.CPOptionValueImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CPOptionValue, Long>
		CP_OPTION_VALUE_ID_ACCESSOR = new Accessor<CPOptionValue, Long>() {

			@Override
			public Long get(CPOptionValue cpOptionValue) {
				return cpOptionValue.getCPOptionValueId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CPOptionValue> getTypeClass() {
				return CPOptionValue.class;
			}

		};

	public CPOption getCPOption()
		throws com.liferay.portal.kernel.exception.PortalException;

}