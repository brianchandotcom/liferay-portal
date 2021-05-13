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

package com.liferay.dynamic.data.mapping.form.evaluator.internal.validation;

import com.liferay.dynamic.data.mapping.form.validation.DDMValidation;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcela Cunha
 */
@Component(
	immediate = true, property = "ddm.validation.name=is-less-than",
	service = DDMValidation.class
)
public class IsLessThanDDMValidation implements DDMValidation {

	@Override
	public String getDataType() {
		return "numeric";
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "is-less-than");
	}

	@Override
	public String getName() {
		return "lt";
	}

	@Override
	public String getParameterMessage(Locale locale) {
		return LanguageUtil.get(locale, "number-placeholder");
	}

	@Override
	public String getRegex() {
		return "/^(.+)>(\\d+\\.?\\d*)?$/";
	}

	@Override
	public String getTemplate() {
		return "{name} > {parameter}";
	}

}