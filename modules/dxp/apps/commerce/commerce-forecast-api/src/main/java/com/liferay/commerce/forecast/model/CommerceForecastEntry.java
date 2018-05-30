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

package com.liferay.commerce.forecast.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the CommerceForecastEntry service. Represents a row in the &quot;CommerceForecastEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Andrea Di Giorgi
 * @see CommerceForecastEntryModel
 * @see com.liferay.commerce.forecast.model.impl.CommerceForecastEntryImpl
 * @see com.liferay.commerce.forecast.model.impl.CommerceForecastEntryModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.forecast.model.impl.CommerceForecastEntryImpl")
@ProviderType
public interface CommerceForecastEntry extends CommerceForecastEntryModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.forecast.model.impl.CommerceForecastEntryImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceForecastEntry, Long> COMMERCE_FORECAST_ENTRY_ID_ACCESSOR =
		new Accessor<CommerceForecastEntry, Long>() {
			@Override
			public Long get(CommerceForecastEntry commerceForecastEntry) {
				return commerceForecastEntry.getCommerceForecastEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CommerceForecastEntry> getTypeClass() {
				return CommerceForecastEntry.class;
			}
		};
}