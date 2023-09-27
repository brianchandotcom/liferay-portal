/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.starter;

import com.liferay.commerce.starter.CommerceRegionsStarter;
import com.liferay.portal.kernel.language.Language;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(
	property = "commerce.region.starter.key=" + NetherlandsCommerceRegionsStarter.NETHERLANDS_NUMERIC_ISO_CODE,
	service = CommerceRegionsStarter.class
)
public class NetherlandsCommerceRegionsStarter
	extends BaseCommerceRegionsStarter {

	public static final int NETHERLANDS_NUMERIC_ISO_CODE = 528;

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "country.netherlands");
	}

	@Override
	protected int getCountryIsoCode() {
		return NETHERLANDS_NUMERIC_ISO_CODE;
	}

	@Override
	protected String getFilePath() {
		return _FILEPATH;
	}

	private static final String _FILEPATH =
		"com/liferay/commerce/internal/netherlands.json";

	@Reference
	private Language _language;

}