/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CPSpecificationOptionListTypeDefinitionRel service. Represents a row in the &quot;CPSOListTypeDefinitionRel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CPSpecificationOptionListTypeDefinitionRelModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.product.model.impl.CPSpecificationOptionListTypeDefinitionRelImpl"
)
@ProviderType
public interface CPSpecificationOptionListTypeDefinitionRel
	extends CPSpecificationOptionListTypeDefinitionRelModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.product.model.impl.CPSpecificationOptionListTypeDefinitionRelImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor
		<CPSpecificationOptionListTypeDefinitionRel, Long>
			CP_SPECIFICATION_OPTION_LIST_TYPE_DEFINITION_REL_ID_ACCESSOR =
				new Accessor
					<CPSpecificationOptionListTypeDefinitionRel, Long>() {

					@Override
					public Long get(
						CPSpecificationOptionListTypeDefinitionRel
							cpSpecificationOptionListTypeDefinitionRel) {

						return cpSpecificationOptionListTypeDefinitionRel.
							getCPSpecificationOptionListTypeDefinitionRelId();
					}

					@Override
					public Class<Long> getAttributeClass() {
						return Long.class;
					}

					@Override
					public Class<CPSpecificationOptionListTypeDefinitionRel>
						getTypeClass() {

						return CPSpecificationOptionListTypeDefinitionRel.class;
					}

				};

}