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
 * The extended model interface for the CPDefinitionSpecificationOptionValue service. Represents a row in the &quot;CPDSpecificationOptionValue&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CPDefinitionSpecificationOptionValueModel
 * @see com.liferay.commerce.product.model.impl.CPDefinitionSpecificationOptionValueImpl
 * @see com.liferay.commerce.product.model.impl.CPDefinitionSpecificationOptionValueModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.product.model.impl.CPDefinitionSpecificationOptionValueImpl")
@ProviderType
public interface CPDefinitionSpecificationOptionValue
	extends CPDefinitionSpecificationOptionValueModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.product.model.impl.CPDefinitionSpecificationOptionValueImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CPDefinitionSpecificationOptionValue, Long> CP_DEFINITION_SPECIFICATION_OPTION_VALUE_ID_ACCESSOR =
		new Accessor<CPDefinitionSpecificationOptionValue, Long>() {
			@Override
			public Long get(
				CPDefinitionSpecificationOptionValue cpDefinitionSpecificationOptionValue) {
				return cpDefinitionSpecificationOptionValue.getCPDefinitionSpecificationOptionValueId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CPDefinitionSpecificationOptionValue> getTypeClass() {
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