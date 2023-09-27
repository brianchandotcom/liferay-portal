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
 * The extended model interface for the AnalyticsDeleteMessage service. Represents a row in the &quot;AnalyticsDeleteMessage&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see AnalyticsDeleteMessageModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.analytics.message.storage.model.impl.AnalyticsDeleteMessageImpl"
)
@ProviderType
public interface AnalyticsDeleteMessage
	extends AnalyticsDeleteMessageModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.analytics.message.storage.model.impl.AnalyticsDeleteMessageImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<AnalyticsDeleteMessage, Long>
		ANALYTICS_DELETE_MESSAGE_ID_ACCESSOR =
			new Accessor<AnalyticsDeleteMessage, Long>() {

				@Override
				public Long get(AnalyticsDeleteMessage analyticsDeleteMessage) {
					return analyticsDeleteMessage.getAnalyticsDeleteMessageId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<AnalyticsDeleteMessage> getTypeClass() {
					return AnalyticsDeleteMessage.class;
				}

			};

}