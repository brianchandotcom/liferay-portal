/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.tax.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CommerceTaxCategoryMapping service. Represents a row in the &quot;CommerceTaxCategoryMapping&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CommerceTaxCategoryMappingModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.tax.model.impl.CommerceTaxCategoryMappingImpl"
)
@ProviderType
public interface CommerceTaxCategoryMapping
	extends CommerceTaxCategoryMappingModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.tax.model.impl.CommerceTaxCategoryMappingImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceTaxCategoryMapping, Long>
		COMMERCE_TAX_CATEGORY_MAPPING_ID_ACCESSOR =
			new Accessor<CommerceTaxCategoryMapping, Long>() {

				@Override
				public Long get(
					CommerceTaxCategoryMapping commerceTaxCategoryMapping) {

					return commerceTaxCategoryMapping.
						getCommerceTaxCategoryMappingId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommerceTaxCategoryMapping> getTypeClass() {
					return CommerceTaxCategoryMapping.class;
				}

			};

}
// LIFERAY-SERVICE-BUILDER-HASH:493094140