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

package com.liferay.portlet.ratings.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.model.PersistedModel;

/**
 * The extended model interface for the RatingsEntry service. Represents a row in the &quot;RatingsEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see RatingsEntryModel
 * @see com.liferay.portlet.ratings.model.impl.RatingsEntryImpl
 * @see com.liferay.portlet.ratings.model.impl.RatingsEntryModelImpl
 * @generated
 */
@ProviderType
public interface RatingsEntry extends RatingsEntryModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portlet.ratings.model.impl.RatingsEntryImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<RatingsEntry, Long> ENTRY_ID_ACCESSOR = new Accessor<RatingsEntry, Long>() {
			@Override
			public Long get(RatingsEntry ratingsEntry) {
				return ratingsEntry.getEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<RatingsEntry> getTypeClass() {
				return RatingsEntry.class;
			}
		};
}