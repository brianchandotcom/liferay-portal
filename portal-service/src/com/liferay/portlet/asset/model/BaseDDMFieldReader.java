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

package com.liferay.portlet.asset.model;

import java.util.Map;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseDDMFieldReader implements DDMFieldReader {

	@Override
	public DDMFormValues getDDMFormValues(String ddmType) 
		throws PortalException {
		
		DDMFormValues filteredDDMFormValues = new DDMFormValues(new DDMForm());
		DDMFormValues currentDDMFormValues = getDDMFormValues();
		Map<String, DDMFormField> currentDDMFormFieldsMap =
				currentDDMFormValues.getDDMForm().getDDMFormFieldsMap(true);
		
		for (DDMFormFieldValue ddmFormFieldValue : currentDDMFormValues.getDDMFormFieldValues()) {

			DDMFormField ddmFormField = currentDDMFormFieldsMap.get(
					ddmFormFieldValue.getName());
			
			if (ddmType.equals(ddmFormField.getDataType())) {
				filteredDDMFormValues.addDDMFormFieldValue(ddmFormFieldValue);
				filteredDDMFormValues.getDDMForm().addDDMFormField(
					ddmFormField);
			}
		}

		return filteredDDMFormValues;
	}

}