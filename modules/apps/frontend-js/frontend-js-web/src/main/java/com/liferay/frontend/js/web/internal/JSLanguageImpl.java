/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal;

import com.liferay.frontend.js.JSLanguage;

import org.osgi.service.component.annotations.Component;

/**
 * @author Iván Zaera Avellón
 */
@Component(service = JSLanguage.class)
public class JSLanguageImpl implements JSLanguage {

	@Override
	public String getHash() {
		LanguageState languageState = LanguageState.get();

		return languageState.getHash();
	}

}