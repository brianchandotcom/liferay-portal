/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.validator;

import com.liferay.search.experiences.exception.SXPBlueprintTitleException;

import java.util.Locale;
import java.util.Map;

/**
 * @author Petteri Karttunen
 */
public interface SXPBlueprintValidator {

	public void validate(Map<Locale, String> titleMap)
		throws SXPBlueprintTitleException;

}