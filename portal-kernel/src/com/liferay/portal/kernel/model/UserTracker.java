/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the UserTracker service. Represents a row in the &quot;UserTracker&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see UserTrackerModel
 * @generated
 */
@ImplementationClassName("com.liferay.portal.model.impl.UserTrackerImpl")
@ProviderType
public interface UserTracker extends PersistedModel, UserTrackerModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.model.impl.UserTrackerImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<UserTracker, Long> USER_TRACKER_ID_ACCESSOR =
		new Accessor<UserTracker, Long>() {

			@Override
			public Long get(UserTracker userTracker) {
				return userTracker.getUserTrackerId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<UserTracker> getTypeClass() {
				return UserTracker.class;
			}

		};

	public void addPath(UserTrackerPath path);

	public int compareTo(UserTracker userTracker);

	public String getEmailAddress();

	public String getFullName();

	public int getHits();

	public java.util.List<UserTrackerPath> getPaths();

}