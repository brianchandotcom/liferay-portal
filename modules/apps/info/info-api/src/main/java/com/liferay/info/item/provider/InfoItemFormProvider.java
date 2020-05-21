/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.info.item.provider;

import com.liferay.info.fields.InfoFieldValue;
import com.liferay.info.fields.InfoForm;
import com.liferay.info.fields.InfoFormValues;
import com.liferay.info.item.NoSuchClassTypeException;

/**
 * @author Jorge Ferrer
 */
public interface InfoItemFormProvider<T> {

	public default InfoFieldValue getInfoFieldValue(T t, String fieldName) {
		InfoFormValues infoFormValues = getInfoFormValues(t);

		return infoFormValues.getInfoFieldValue(fieldName);
	}

	public InfoForm getInfoForm();

	public default InfoForm getInfoForm(long itemClassTypeId)
		throws NoSuchClassTypeException {

		return getInfoForm();
	}

	public default InfoForm getInfoForm(T t) {
		return getInfoForm();
	}

	public InfoFormValues getInfoFormValues(T t);

}