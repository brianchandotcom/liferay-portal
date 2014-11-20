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
import com.liferay.portlet.dynamicdatamapping.DDMFormValidationException;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;

/**
 * @author Marcellus Tavares
 */
public class DDMFormValidatorUtil {

	public static DDMFormValidator getDDMFormValidator() {
		PortalRuntimePermission.checkGetBeanProperty(
			DDMFormValidatorUtil.class);

		return _ddmFormValidator;
	}

	public static void validate(DDMForm ddmForm)
		throws DDMFormValidationException {

		getDDMFormValidator().validate(ddmForm);
	}

	public void setDDMFormValidator(DDMFormValidator ddmFormValidator) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_ddmFormValidator = ddmFormValidator;
	}

	private static DDMFormValidator _ddmFormValidator;

}