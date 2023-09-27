/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the PasswordTracker service. Represents a row in the &quot;PasswordTracker&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see PasswordTrackerModel
 * @generated
 */
@ImplementationClassName("com.liferay.portal.model.impl.PasswordTrackerImpl")
@ProviderType
public interface PasswordTracker extends PasswordTrackerModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.model.impl.PasswordTrackerImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<PasswordTracker, Long>
		PASSWORD_TRACKER_ID_ACCESSOR = new Accessor<PasswordTracker, Long>() {

			@Override
			public Long get(PasswordTracker passwordTracker) {
				return passwordTracker.getPasswordTrackerId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<PasswordTracker> getTypeClass() {
				return PasswordTracker.class;
			}

		};

}