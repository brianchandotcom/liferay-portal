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

package com.liferay.change.tracking.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the CTEntryBag service. Represents a row in the &quot;CTEntryBag&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see CTEntryBagModel
 * @generated
 */
@ImplementationClassName("com.liferay.change.tracking.model.impl.CTEntryBagImpl")
@ProviderType
public interface CTEntryBag extends CTEntryBagModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.change.tracking.model.impl.CTEntryBagImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CTEntryBag, Long> CT_ENTRY_BAG_ID_ACCESSOR = new Accessor<CTEntryBag, Long>() {
			@Override
			public Long get(CTEntryBag ctEntryBag) {
				return ctEntryBag.getCtEntryBagId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CTEntryBag> getTypeClass() {
				return CTEntryBag.class;
			}
		};

	public java.util.List<CTEntry> getRelatedCTEntries();
}