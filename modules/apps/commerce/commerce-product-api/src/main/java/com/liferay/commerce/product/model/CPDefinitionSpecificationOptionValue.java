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
 * The extended model interface for the CPDefinitionSpecificationOptionValue service. Represents a row in the &quot;CPDSpecificationOptionValue&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CPDefinitionSpecificationOptionValueModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.product.model.impl.CPDefinitionSpecificationOptionValueImpl"
)
@ProviderType
public interface CPDefinitionSpecificationOptionValue
	extends CPDefinitionSpecificationOptionValueModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.product.model.impl.CPDefinitionSpecificationOptionValueImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CPDefinitionSpecificationOptionValue, Long>
		CP_DEFINITION_SPECIFICATION_OPTION_VALUE_ID_ACCESSOR =
			new Accessor<CPDefinitionSpecificationOptionValue, Long>() {

				@Override
				public Long get(
					CPDefinitionSpecificationOptionValue
						cpDefinitionSpecificationOptionValue) {

					return cpDefinitionSpecificationOptionValue.
						getCPDefinitionSpecificationOptionValueId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CPDefinitionSpecificationOptionValue>
					getTypeClass() {

					return CPDefinitionSpecificationOptionValue.class;
				}

			};

	public CPDefinition getCPDefinition()
		throws com.liferay.portal.kernel.exception.PortalException;

	public CPOptionCategory getCPOptionCategory()
		throws com.liferay.portal.kernel.exception.PortalException;

	public CPSpecificationOption getCPSpecificationOption()
		throws com.liferay.portal.kernel.exception.PortalException;

}