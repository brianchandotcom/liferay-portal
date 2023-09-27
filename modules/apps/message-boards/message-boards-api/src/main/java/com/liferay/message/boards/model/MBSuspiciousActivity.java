/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the MBSuspiciousActivity service. Represents a row in the &quot;MBSuspiciousActivity&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see MBSuspiciousActivityModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.message.boards.model.impl.MBSuspiciousActivityImpl"
)
@ProviderType
public interface MBSuspiciousActivity
	extends MBSuspiciousActivityModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.message.boards.model.impl.MBSuspiciousActivityImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<MBSuspiciousActivity, Long>
		SUSPICIOUS_ACTIVITY_ID_ACCESSOR =
			new Accessor<MBSuspiciousActivity, Long>() {

				@Override
				public Long get(MBSuspiciousActivity mbSuspiciousActivity) {
					return mbSuspiciousActivity.getSuspiciousActivityId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<MBSuspiciousActivity> getTypeClass() {
					return MBSuspiciousActivity.class;
				}

			};

}