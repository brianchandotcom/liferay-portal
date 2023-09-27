/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.message.storage.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the AnalyticsMessage service. Represents a row in the &quot;AnalyticsMessage&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see AnalyticsMessageModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.analytics.message.storage.model.impl.AnalyticsMessageImpl"
)
@ProviderType
public interface AnalyticsMessage
	extends AnalyticsMessageModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.analytics.message.storage.model.impl.AnalyticsMessageImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<AnalyticsMessage, Long>
		ANALYTICS_MESSAGE_ID_ACCESSOR = new Accessor<AnalyticsMessage, Long>() {

			@Override
			public Long get(AnalyticsMessage analyticsMessage) {
				return analyticsMessage.getAnalyticsMessageId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<AnalyticsMessage> getTypeClass() {
				return AnalyticsMessage.class;
			}

		};

}