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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bruno Oliveira
 * @author Carolina Barbosa
 */
@Component(
	immediate = true,
	property = {
		"ddm.validation.data.type=date", "ddm.validation.ranking:Float=2"
	},
	service = DDMValidation.class
)
public class PastDatesDDMValidation implements DDMValidation {

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(
			ResourceBundleUtil.getModuleAndPortalResourceBundle(
				locale, getClass()),
			"past-dates");
	}

	@Override
	public String getName() {
		return "pastDates";
	}

	@Override
	public String getParameterMessage(Locale locale) {
		return StringPool.BLANK;
	}

	@Override
	public String getTemplate() {
		return "pastDates({name}, \"{parameter}\")";
	}

}