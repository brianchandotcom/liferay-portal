/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.lock.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the Lock service. Represents a row in the &quot;Lock_&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see LockModel
 * @generated
 */
@ImplementationClassName("com.liferay.portal.lock.model.impl.LockImpl")
@ProviderType
public interface Lock extends LockModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.lock.model.impl.LockImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<Lock, Long> LOCK_ID_ACCESSOR =
		new Accessor<Lock, Long>() {

			@Override
			public Long get(Lock lock) {
				return lock.getLockId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<Lock> getTypeClass() {
				return Lock.class;
			}

		};

	public long getExpirationTime();

	public boolean isExpired();

	public boolean isNeverExpires();

}