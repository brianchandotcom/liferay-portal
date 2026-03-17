/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the LaunchEntry service. Represents a row in the &quot;LaunchEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see LaunchEntryModel
 * @generated
 */
@ImplementationClassName("com.liferay.launch.model.impl.LaunchEntryImpl")
@ProviderType
public interface LaunchEntry extends LaunchEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.launch.model.impl.LaunchEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<LaunchEntry, Long> LAUNCH_ENTRY_ID_ACCESSOR =
		new Accessor<LaunchEntry, Long>() {

			@Override
			public Long get(LaunchEntry launchEntry) {
				return launchEntry.getLaunchEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<LaunchEntry> getTypeClass() {
				return LaunchEntry.class;
			}

		};

}
// SB-Hash:-343509717