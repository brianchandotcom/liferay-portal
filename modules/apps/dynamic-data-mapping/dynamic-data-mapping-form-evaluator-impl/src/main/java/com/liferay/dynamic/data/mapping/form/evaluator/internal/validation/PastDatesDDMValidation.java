/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.validation;

import com.liferay.dynamic.data.mapping.form.validation.DDMValidation;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Oliveira
 * @author Carolina Barbosa
 */
@Component(
	property = {
		"ddm.validation.data.type=date", "ddm.validation.ranking:Float=2"
	},
	service = DDMValidation.class
)
public class PastDatesDDMValidation implements DDMValidation {

	@Override
	public String getLabel(Locale locale) {
		return _language.get(
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

	@Reference
	private Language _language;

}