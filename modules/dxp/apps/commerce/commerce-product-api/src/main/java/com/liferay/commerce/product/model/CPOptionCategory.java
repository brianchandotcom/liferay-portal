/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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