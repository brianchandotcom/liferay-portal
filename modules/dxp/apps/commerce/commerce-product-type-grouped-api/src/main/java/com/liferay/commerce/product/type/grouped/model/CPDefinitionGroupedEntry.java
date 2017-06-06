/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
}