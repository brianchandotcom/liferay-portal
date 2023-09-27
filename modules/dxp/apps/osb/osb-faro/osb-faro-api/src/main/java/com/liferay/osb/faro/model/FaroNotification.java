/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the FaroNotification service. Represents a row in the &quot;OSBFaro_FaroNotification&quot; database table, with each column mapped to a property of this class.
 *
 * @author Matthew Kong
 * @see FaroNotificationModel
 * @generated
 */
@ImplementationClassName("com.liferay.osb.faro.model.impl.FaroNotificationImpl")
@ProviderType
public interface FaroNotification
	extends FaroNotificationModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.osb.faro.model.impl.FaroNotificationImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<FaroNotification, Long>
		FARO_NOTIFICATION_ID_ACCESSOR = new Accessor<FaroNotification, Long>() {

			@Override
			public Long get(FaroNotification faroNotification) {
				return faroNotification.getFaroNotificationId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<FaroNotification> getTypeClass() {
				return FaroNotification.class;
			}

		};

}