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

package com.liferay.commerce.product.type.grouped.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the CPDefinitionGroupedEntry service. Represents a row in the &quot;CPDefinitionGroupedEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Andrea Di Giorgi
 * @see CPDefinitionGroupedEntryModel
 * @see com.liferay.commerce.product.type.grouped.model.impl.CPDefinitionGroupedEntryImpl
 * @see com.liferay.commerce.product.type.grouped.model.impl.CPDefinitionGroupedEntryModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.product.type.grouped.model.impl.CPDefinitionGroupedEntryImpl")
@ProviderType
public interface CPDefinitionGroupedEntry extends CPDefinitionGroupedEntryModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.product.type.grouped.model.impl.CPDefinitionGroupedEntryImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CPDefinitionGroupedEntry, Long> CP_DEFINITION_GROUPED_ENTRY_ID_ACCESSOR =
		new Accessor<CPDefinitionGroupedEntry, Long>() {
			@Override
			public Long get(CPDefinitionGroupedEntry cpDefinitionGroupedEntry) {
				return cpDefinitionGroupedEntry.getCPDefinitionGroupedEntryId();
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

	public com.liferay.commerce.product.model.CPDefinition getEntryCPDefinition()
		throws com.liferay.portal.kernel.exception.PortalException;
}