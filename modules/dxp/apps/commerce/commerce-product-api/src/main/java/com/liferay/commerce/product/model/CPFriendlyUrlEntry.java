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
 * The extended model interface for the CPFriendlyUrlEntry service. Represents a row in the &quot;CPFriendlyUrlEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CPFriendlyUrlEntryModel
 * @see com.liferay.commerce.product.model.impl.CPFriendlyUrlEntryImpl
 * @see com.liferay.commerce.product.model.impl.CPFriendlyUrlEntryModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.product.model.impl.CPFriendlyUrlEntryImpl")
@ProviderType
public interface CPFriendlyUrlEntry extends CPFriendlyUrlEntryModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.product.model.impl.CPFriendlyUrlEntryImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CPFriendlyUrlEntry, Long> CP_FRIENDLY_URL_ENTRY_ID_ACCESSOR =
		new Accessor<CPFriendlyUrlEntry, Long>() {
			@Override
			public Long get(CPFriendlyUrlEntry cpFriendlyUrlEntry) {
				return cpFriendlyUrlEntry.getCPFriendlyUrlEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CPFriendlyUrlEntry> getTypeClass() {
				return CPFriendlyUrlEntry.class;
			}
		};
}