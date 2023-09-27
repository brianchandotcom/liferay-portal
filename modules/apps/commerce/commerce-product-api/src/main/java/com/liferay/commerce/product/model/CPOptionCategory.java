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
 * The extended model interface for the CPOptionCategory service. Represents a row in the &quot;CPOptionCategory&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CPOptionCategoryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.product.model.impl.CPOptionCategoryImpl"
)
@ProviderType
public interface CPOptionCategory
	extends CPOptionCategoryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.product.model.impl.CPOptionCategoryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CPOptionCategory, Long>
		CP_OPTION_CATEGORY_ID_ACCESSOR =
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