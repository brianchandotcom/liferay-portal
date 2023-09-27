/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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