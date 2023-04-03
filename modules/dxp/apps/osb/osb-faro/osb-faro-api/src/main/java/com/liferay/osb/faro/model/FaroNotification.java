/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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