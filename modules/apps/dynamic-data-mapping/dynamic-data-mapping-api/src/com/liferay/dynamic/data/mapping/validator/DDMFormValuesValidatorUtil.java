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

package com.liferay.portlet.dynamicdatamapping.validator;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portlet.dynamicdatamapping.StorageException;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;

/**
 * @author Marcellus Tavares
 */
public class DDMFormValuesValidatorUtil {

	public static DDMFormValuesValidator getDDMFormValuesValidator() {
		PortalRuntimePermission.checkGetBeanProperty(
			DDMFormValuesValidatorUtil.class);

		return _ddmFormValuesValidator;
	}

	public static void validate(DDMFormValues ddmFormValues)
		throws StorageException {

		DDMFormValuesValidator ddmFormValuesValidator =
			getDDMFormValuesValidator();

		ddmFormValuesValidator.validate(ddmFormValues);
	}

	public void setDDMFormValuesValidator(
		DDMFormValuesValidator ddmFormValuesValidator) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_ddmFormValuesValidator = ddmFormValuesValidator;
	}

	private static DDMFormValuesValidator _ddmFormValuesValidator;

}