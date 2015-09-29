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
 * The extended model interface for the PortalPreferences service. Represents a row in the &quot;PortalPreferences&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see PortalPreferencesModel
 * @see com.liferay.portal.model.impl.PortalPreferencesImpl
 * @see com.liferay.portal.model.impl.PortalPreferencesModelImpl
 * @generated
 */
@ProviderType
public interface PortalPreferences extends PortalPreferencesModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portal.model.impl.PortalPreferencesImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<PortalPreferences, Long> PORTAL_PREFERENCES_ID_ACCESSOR =
		new Accessor<PortalPreferences, Long>() {
			@Override
			public Long get(PortalPreferences portalPreferences) {
				return portalPreferences.getPortalPreferencesId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<PortalPreferences> getTypeClass() {
				return PortalPreferences.class;
			}
		};
}