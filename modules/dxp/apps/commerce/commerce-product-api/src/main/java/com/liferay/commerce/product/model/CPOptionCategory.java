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

package com.liferay.commerce.product.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the CPOptionCategory service. Represents a row in the &quot;CPOptionCategory&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CPOptionCategoryModel
 * @see com.liferay.commerce.product.model.impl.CPOptionCategoryImpl
 * @see com.liferay.commerce.product.model.impl.CPOptionCategoryModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.product.model.impl.CPOptionCategoryImpl")
@ProviderType
public interface CPOptionCategory extends CPOptionCategoryModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.product.model.impl.CPOptionCategoryImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CPOptionCategory, Long> CP_OPTION_CATEGORY_ID_ACCESSOR =
		new Accessor<CPOptionCategory, Long>() {
			@Override
			public Long get(CPOptionCategory cpOptionCategory) {
				return cpOptionCategory.getCPOptionCategoryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CPOptionCategory> getTypeClass() {
				return CPOptionCategory.class;
			}
		};
}