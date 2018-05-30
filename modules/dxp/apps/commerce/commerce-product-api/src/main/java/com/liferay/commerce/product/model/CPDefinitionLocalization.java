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
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the CPDefinitionLocalization service. Represents a row in the &quot;CPDefinitionLocalization&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CPDefinitionLocalizationModel
 * @see com.liferay.commerce.product.model.impl.CPDefinitionLocalizationImpl
 * @see com.liferay.commerce.product.model.impl.CPDefinitionLocalizationModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.product.model.impl.CPDefinitionLocalizationImpl")
@ProviderType
public interface CPDefinitionLocalization extends CPDefinitionLocalizationModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.product.model.impl.CPDefinitionLocalizationImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CPDefinitionLocalization, Long> CP_DEFINITION_LOCALIZATION_ID_ACCESSOR =
		new Accessor<CPDefinitionLocalization, Long>() {
			@Override
			public Long get(CPDefinitionLocalization cpDefinitionLocalization) {
				return cpDefinitionLocalization.getCpDefinitionLocalizationId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CPDefinitionLocalization> getTypeClass() {
				return CPDefinitionLocalization.class;
			}
		};
}