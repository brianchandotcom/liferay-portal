/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.grouped.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CPDefinitionGroupedEntry service. Represents a row in the &quot;CPDefinitionGroupedEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Andrea Di Giorgi
 * @see CPDefinitionGroupedEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.product.type.grouped.model.impl.CPDefinitionGroupedEntryImpl"
)
@ProviderType
public interface CPDefinitionGroupedEntry
	extends CPDefinitionGroupedEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.product.type.grouped.model.impl.CPDefinitionGroupedEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CPDefinitionGroupedEntry, Long>
		CP_DEFINITION_GROUPED_ENTRY_ID_ACCESSOR =
			new Accessor<CPDefinitionGroupedEntry, Long>() {

				@Override
				public Long get(
					CPDefinitionGroupedEntry cpDefinitionGroupedEntry) {

					return cpDefinitionGroupedEntry.
						getCPDefinitionGroupedEntryId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CPDefinitionGroupedEntry> getTypeClass() {
					return CPDefinitionGroupedEntry.class;
				}

			};

	public com.liferay.commerce.product.model.CPDefinition getCPDefinition()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.commerce.product.model.CPDefinition
			getEntryCPDefinition()
		throws com.liferay.portal.kernel.exception.PortalException;

	public long getEntryCPDefinitionId()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.commerce.product.model.CProduct getEntryCProduct()
		throws com.liferay.portal.kernel.exception.PortalException;

}