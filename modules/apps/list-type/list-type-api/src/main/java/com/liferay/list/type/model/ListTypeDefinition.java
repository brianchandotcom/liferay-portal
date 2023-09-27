/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.list.type.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the ListTypeDefinition service. Represents a row in the &quot;ListTypeDefinition&quot; database table, with each column mapped to a property of this class.
 *
 * @author Gabriel Albuquerque
 * @see ListTypeDefinitionModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.list.type.model.impl.ListTypeDefinitionImpl"
)
@ProviderType
public interface ListTypeDefinition
	extends ListTypeDefinitionModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.list.type.model.impl.ListTypeDefinitionImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ListTypeDefinition, Long>
		LIST_TYPE_DEFINITION_ID_ACCESSOR =
			new Accessor<ListTypeDefinition, Long>() {

				@Override
				public Long get(ListTypeDefinition listTypeDefinition) {
					return listTypeDefinition.getListTypeDefinitionId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<ListTypeDefinition> getTypeClass() {
					return ListTypeDefinition.class;
				}

			};

}