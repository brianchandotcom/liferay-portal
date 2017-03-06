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

package com.liferay.friendly.url.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the FriendlyURLEntry service. Represents a row in the &quot;FriendlyURLEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLEntryModel
 * @see com.liferay.friendly.url.model.impl.FriendlyURLEntryImpl
 * @see com.liferay.friendly.url.model.impl.FriendlyURLEntryModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.friendly.url.model.impl.FriendlyURLEntryImpl")
@ProviderType
public interface FriendlyURLEntry extends FriendlyURLEntryModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.friendly.url.model.impl.FriendlyURLEntryImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<FriendlyURLEntry, Long> FRIENDLY_URL_ENTRY_ID_ACCESSOR =
		new Accessor<FriendlyURLEntry, Long>() {
			@Override
			public Long get(FriendlyURLEntry friendlyURLEntry) {
				return friendlyURLEntry.getFriendlyURLEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<FriendlyURLEntry> getTypeClass() {
				return FriendlyURLEntry.class;
			}
		};

	public java.lang.String getUrlTitle(java.util.Locale locale);

	public boolean isLocalized();
}