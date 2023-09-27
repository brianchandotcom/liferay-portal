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
 * The extended model interface for the MBThreadFlag service. Represents a row in the &quot;MBThreadFlag&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see MBThreadFlagModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.message.boards.model.impl.MBThreadFlagImpl"
)
@ProviderType
public interface MBThreadFlag extends MBThreadFlagModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.message.boards.model.impl.MBThreadFlagImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<MBThreadFlag, Long> THREAD_FLAG_ID_ACCESSOR =
		new Accessor<MBThreadFlag, Long>() {

			@Override
			public Long get(MBThreadFlag mbThreadFlag) {
				return mbThreadFlag.getThreadFlagId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<MBThreadFlag> getTypeClass() {
				return MBThreadFlag.class;
			}

		};

}