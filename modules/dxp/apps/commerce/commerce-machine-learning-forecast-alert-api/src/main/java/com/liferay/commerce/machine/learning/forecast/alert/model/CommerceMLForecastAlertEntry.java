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

package com.liferay.commerce.machine.learning.forecast.alert.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CommerceMLForecastAlertEntry service. Represents a row in the &quot;CommerceMLForecastAlertEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Riccardo Ferrari
 * @see CommerceMLForecastAlertEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.machine.learning.forecast.alert.model.impl.CommerceMLForecastAlertEntryImpl"
)
@ProviderType
public interface CommerceMLForecastAlertEntry
	extends CommerceMLForecastAlertEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.machine.learning.forecast.alert.model.impl.CommerceMLForecastAlertEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceMLForecastAlertEntry, Long>
		COMMERCE_ML_FORECAST_ALERT_ENTRY_ID_ACCESSOR =
			new Accessor<CommerceMLForecastAlertEntry, Long>() {

				@Override
				public Long get(
					CommerceMLForecastAlertEntry commerceMLForecastAlertEntry) {

					return commerceMLForecastAlertEntry.
						getCommerceMLForecastAlertEntryId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CommerceMLForecastAlertEntry> getTypeClass() {
					return CommerceMLForecastAlertEntry.class;
				}

			};

}