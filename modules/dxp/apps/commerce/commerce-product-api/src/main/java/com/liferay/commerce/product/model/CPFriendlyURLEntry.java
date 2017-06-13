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

package com.liferay.commerce.product.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the CPFriendlyURLEntry service. Represents a row in the &quot;CPFriendlyURLEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CPFriendlyURLEntryModel
 * @see com.liferay.commerce.product.model.impl.CPFriendlyURLEntryImpl
 * @see com.liferay.commerce.product.model.impl.CPFriendlyURLEntryModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.product.model.impl.CPFriendlyURLEntryImpl")
@ProviderType
public interface CPFriendlyURLEntry extends CPFriendlyURLEntryModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.product.model.impl.CPFriendlyURLEntryImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CPFriendlyURLEntry, Long> CP_FRIENDLY_URL_ENTRY_ID_ACCESSOR =
		new Accessor<CPFriendlyURLEntry, Long>() {
			@Override
			public Long get(CPFriendlyURLEntry cpFriendlyURLEntry) {
				return cpFriendlyURLEntry.getCPFriendlyURLEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CPFriendlyURLEntry> getTypeClass() {
				return CPFriendlyURLEntry.class;
			}
		};
}