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
 * The extended model interface for the FriendlyURL service. Represents a row in the &quot;FriendlyURL&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLModel
 * @see com.liferay.friendly.url.model.impl.FriendlyURLImpl
 * @see com.liferay.friendly.url.model.impl.FriendlyURLModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.friendly.url.model.impl.FriendlyURLImpl")
@ProviderType
public interface FriendlyURL extends FriendlyURLModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.friendly.url.model.impl.FriendlyURLImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<FriendlyURL, Long> FRIENDLY_URL_ID_ACCESSOR = new Accessor<FriendlyURL, Long>() {
			@Override
			public Long get(FriendlyURL friendlyURL) {
				return friendlyURL.getFriendlyURLId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<FriendlyURL> getTypeClass() {
				return FriendlyURL.class;
			}
		};
}