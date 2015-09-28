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

package com.liferay.portal.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the LayoutFriendlyURL service. Represents a row in the &quot;LayoutFriendlyURL&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutFriendlyURLModel
 * @see com.liferay.portal.model.impl.LayoutFriendlyURLImpl
 * @see com.liferay.portal.model.impl.LayoutFriendlyURLModelImpl
 * @generated
 */
@ProviderType
public interface LayoutFriendlyURL extends LayoutFriendlyURLModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portal.model.impl.LayoutFriendlyURLImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<LayoutFriendlyURL, Long> LAYOUT_FRIENDLY_U_R_L_ID_ACCESSOR =
		new Accessor<LayoutFriendlyURL, Long>() {
			@Override
			public Long get(LayoutFriendlyURL layoutFriendlyURL) {
				return layoutFriendlyURL.getLayoutFriendlyURLId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<LayoutFriendlyURL> getTypeClass() {
				return LayoutFriendlyURL.class;
			}
		};
}