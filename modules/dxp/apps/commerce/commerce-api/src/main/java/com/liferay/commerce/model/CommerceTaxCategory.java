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

package com.liferay.commerce.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the CommerceTaxCategory service. Represents a row in the &quot;CommerceTaxCategory&quot; database table, with each column mapped to a property of this class.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxCategoryModel
 * @see com.liferay.commerce.model.impl.CommerceTaxCategoryImpl
 * @see com.liferay.commerce.model.impl.CommerceTaxCategoryModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.model.impl.CommerceTaxCategoryImpl")
@ProviderType
public interface CommerceTaxCategory extends CommerceTaxCategoryModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.model.impl.CommerceTaxCategoryImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceTaxCategory, Long> COMMERCE_TAX_CATEGORY_ID_ACCESSOR =
		new Accessor<CommerceTaxCategory, Long>() {
			@Override
			public Long get(CommerceTaxCategory commerceTaxCategory) {
				return commerceTaxCategory.getCommerceTaxCategoryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CommerceTaxCategory> getTypeClass() {
				return CommerceTaxCategory.class;
			}
		};
}