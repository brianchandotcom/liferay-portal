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
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the FriendlyURLEntryMapping service. Represents a row in the &quot;FriendlyURLEntryMapping&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLEntryMappingModel
 * @see com.liferay.friendly.url.model.impl.FriendlyURLEntryMappingImpl
 * @see com.liferay.friendly.url.model.impl.FriendlyURLEntryMappingModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.friendly.url.model.impl.FriendlyURLEntryMappingImpl")
@ProviderType
public interface FriendlyURLEntryMapping extends FriendlyURLEntryMappingModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.friendly.url.model.impl.FriendlyURLEntryMappingImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<FriendlyURLEntryMapping, Long> FRIENDLY_URL_ENTRY_MAPPING_ID_ACCESSOR =
		new Accessor<FriendlyURLEntryMapping, Long>() {
			@Override
			public Long get(FriendlyURLEntryMapping friendlyURLEntryMapping) {
				return friendlyURLEntryMapping.getFriendlyURLEntryMappingId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<FriendlyURLEntryMapping> getTypeClass() {
				return FriendlyURLEntryMapping.class;
			}
		};
}