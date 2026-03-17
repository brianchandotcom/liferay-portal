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
 * The extended model interface for the LaunchSet service. Represents a row in the &quot;LaunchSet&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see LaunchSetModel
 * @generated
 */
@ImplementationClassName("com.liferay.launch.model.impl.LaunchSetImpl")
@ProviderType
public interface LaunchSet extends LaunchSetModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.launch.model.impl.LaunchSetImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<LaunchSet, Long> LAUNCH_SET_ID_ACCESSOR =
		new Accessor<LaunchSet, Long>() {

			@Override
			public Long get(LaunchSet launchSet) {
				return launchSet.getLaunchSetId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<LaunchSet> getTypeClass() {
				return LaunchSet.class;
			}

		};

}
// SB-Hash:371930059