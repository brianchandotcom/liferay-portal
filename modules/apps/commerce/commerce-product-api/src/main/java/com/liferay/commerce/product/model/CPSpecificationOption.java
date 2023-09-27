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
 * The extended model interface for the CPSpecificationOption service. Represents a row in the &quot;CPSpecificationOption&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CPSpecificationOptionModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.product.model.impl.CPSpecificationOptionImpl"
)
@ProviderType
public interface CPSpecificationOption
	extends CPSpecificationOptionModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.product.model.impl.CPSpecificationOptionImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CPSpecificationOption, Long>
		CP_SPECIFICATION_OPTION_ID_ACCESSOR =
			new Accessor<CPSpecificationOption, Long>() {

				@Override
				public Long get(CPSpecificationOption cpSpecificationOption) {
					return cpSpecificationOption.getCPSpecificationOptionId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CPSpecificationOption> getTypeClass() {
					return CPSpecificationOption.class;
				}

			};

	public CPOptionCategory getCPOptionCategory()
		throws com.liferay.portal.kernel.exception.PortalException;

}