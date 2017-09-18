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

package com.liferay.modern.site.building.fragment.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the MSBFragmentEntry service. Represents a row in the &quot;MSBFragmentEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see MSBFragmentEntryModel
 * @see com.liferay.modern.site.building.fragment.model.impl.MSBFragmentEntryImpl
 * @see com.liferay.modern.site.building.fragment.model.impl.MSBFragmentEntryModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.modern.site.building.fragment.model.impl.MSBFragmentEntryImpl")
@ProviderType
public interface MSBFragmentEntry extends MSBFragmentEntryModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.modern.site.building.fragment.model.impl.MSBFragmentEntryImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<MSBFragmentEntry, Long> MSB_FRAGMENT_ENTRY_ID_ACCESSOR =
		new Accessor<MSBFragmentEntry, Long>() {
			@Override
			public Long get(MSBFragmentEntry msbFragmentEntry) {
				return msbFragmentEntry.getMsbFragmentEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<MSBFragmentEntry> getTypeClass() {
				return MSBFragmentEntry.class;
			}
		};
}