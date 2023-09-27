/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CPDefinitionLocalization service. Represents a row in the &quot;CPDefinitionLocalization&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CPDefinitionLocalizationModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.product.model.impl.CPDefinitionLocalizationImpl"
)
@ProviderType
public interface CPDefinitionLocalization
	extends CPDefinitionLocalizationModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.product.model.impl.CPDefinitionLocalizationImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CPDefinitionLocalization, Long>
		CP_DEFINITION_LOCALIZATION_ID_ACCESSOR =
			new Accessor<CPDefinitionLocalization, Long>() {

				@Override
				public Long get(
					CPDefinitionLocalization cpDefinitionLocalization) {

					return cpDefinitionLocalization.
						getCpDefinitionLocalizationId();
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