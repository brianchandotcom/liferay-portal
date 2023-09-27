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
 * The extended model interface for the CPTaxCategory service. Represents a row in the &quot;CPTaxCategory&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CPTaxCategoryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.product.model.impl.CPTaxCategoryImpl"
)
@ProviderType
public interface CPTaxCategory extends CPTaxCategoryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.product.model.impl.CPTaxCategoryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CPTaxCategory, Long>
		CP_TAX_CATEGORY_ID_ACCESSOR = new Accessor<CPTaxCategory, Long>() {

			@Override
			public Long get(CPTaxCategory cpTaxCategory) {
				return cpTaxCategory.getCPTaxCategoryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CPTaxCategory> getTypeClass() {
				return CPTaxCategory.class;
			}

		};

}