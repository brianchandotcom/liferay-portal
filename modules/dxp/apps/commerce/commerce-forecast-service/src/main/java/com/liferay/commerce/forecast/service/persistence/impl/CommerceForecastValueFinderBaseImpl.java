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

package com.liferay.commerce.forecast.service.persistence.impl;

import com.liferay.commerce.forecast.model.CommerceForecastValue;
import com.liferay.commerce.forecast.service.persistence.CommerceForecastValuePersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;

import java.lang.reflect.Field;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Andrea Di Giorgi
 * @generated
 */
public class CommerceForecastValueFinderBaseImpl extends BasePersistenceImpl<CommerceForecastValue> {
	public CommerceForecastValueFinderBaseImpl() {
		setModelClass(CommerceForecastValue.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("time", "time_");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	@Override
	public Set<String> getBadColumnNames() {
		return getCommerceForecastValuePersistence().getBadColumnNames();
	}

	/**
	 * Returns the commerce forecast value persistence.
	 *
	 * @return the commerce forecast value persistence
	 */
	public CommerceForecastValuePersistence getCommerceForecastValuePersistence() {
		return commerceForecastValuePersistence;
	}

	/**
	 * Sets the commerce forecast value persistence.
	 *
	 * @param commerceForecastValuePersistence the commerce forecast value persistence
	 */
	public void setCommerceForecastValuePersistence(
		CommerceForecastValuePersistence commerceForecastValuePersistence) {
		this.commerceForecastValuePersistence = commerceForecastValuePersistence;
	}

	@BeanReference(type = CommerceForecastValuePersistence.class)
	protected CommerceForecastValuePersistence commerceForecastValuePersistence;
	private static final Log _log = LogFactoryUtil.getLog(CommerceForecastValueFinderBaseImpl.class);
}