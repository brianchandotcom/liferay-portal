/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the DEDataDefinitionFieldLink service. Represents a row in the &quot;DEDataDefinitionFieldLink&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DEDataDefinitionFieldLinkModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.data.engine.model.impl.DEDataDefinitionFieldLinkImpl"
)
@ProviderType
public interface DEDataDefinitionFieldLink
	extends DEDataDefinitionFieldLinkModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.data.engine.model.impl.DEDataDefinitionFieldLinkImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DEDataDefinitionFieldLink, Long>
		DE_DATA_DEFINITION_FIELD_LINK_ID_ACCESSOR =
			new Accessor<DEDataDefinitionFieldLink, Long>() {

				@Override
				public Long get(
					DEDataDefinitionFieldLink deDataDefinitionFieldLink) {

					return deDataDefinitionFieldLink.
						getDeDataDefinitionFieldLinkId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<DEDataDefinitionFieldLink> getTypeClass() {
					return DEDataDefinitionFieldLink.class;
				}

			};

}