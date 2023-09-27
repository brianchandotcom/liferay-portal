/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the Subscription service. Represents a row in the &quot;Subscription&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see SubscriptionModel
 * @deprecated
 * @generated
 */
@Deprecated
@ImplementationClassName("com.liferay.portal.model.impl.SubscriptionImpl")
@ProviderType
public interface Subscription extends PersistedModel, SubscriptionModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.model.impl.SubscriptionImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<Subscription, Long> SUBSCRIPTION_ID_ACCESSOR =
		new Accessor<Subscription, Long>() {

			@Override
			public Long get(Subscription subscription) {
				return subscription.getSubscriptionId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<Subscription> getTypeClass() {
				return Subscription.class;
			}

		};

}