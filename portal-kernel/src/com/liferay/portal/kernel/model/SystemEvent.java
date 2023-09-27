/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the SystemEvent service. Represents a row in the &quot;SystemEvent&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see SystemEventModel
 * @generated
 */
@ImplementationClassName("com.liferay.portal.model.impl.SystemEventImpl")
@ProviderType
public interface SystemEvent extends PersistedModel, SystemEventModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.model.impl.SystemEventImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<SystemEvent, Long> SYSTEM_EVENT_ID_ACCESSOR =
		new Accessor<SystemEvent, Long>() {

			@Override
			public Long get(SystemEvent systemEvent) {
				return systemEvent.getSystemEventId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<SystemEvent> getTypeClass() {
				return SystemEvent.class;
			}

		};

	public String getReferrerClassName();

	public void setReferrerClassName(String referrerClassName);

}