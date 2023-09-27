/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.validation;

import com.liferay.dynamic.data.mapping.form.validation.DDMValidation;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcela Cunha
 */
@Component(
	property = {
		"ddm.validation.data.type=string", "ddm.validation.ranking:Float=5"
	},
	service = DDMValidation.class
)
public class MatchesDDMValidation implements DDMValidation {

	@Override
	public String getLabel(Locale locale) {
		return _language.get(
			ResourceBundleUtil.getModuleAndPortalResourceBundle(
				locale, getClass()),
			"matches");
	}

	@Override
	public String getName() {
		return "regularExpression";
	}

	@Override
	public String getParameterMessage(Locale locale) {
		return _language.get(
			ResourceBundleUtil.getModuleAndPortalResourceBundle(
				locale, getClass()),
			"regular-expression");
	}

	@Override
	public String getTemplate() {
		return "match({name}, \"{parameter}\")";
	}

	@Reference
	private Language _language;

}