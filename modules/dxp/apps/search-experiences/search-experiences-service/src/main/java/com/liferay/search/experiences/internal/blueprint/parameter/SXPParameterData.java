/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.blueprint.parameter;

import com.liferay.search.experiences.blueprint.parameter.SXPParameter;

import java.util.Map;

/**
 * @author Petteri Karttunen
 */
public class SXPParameterData {

	public SXPParameterData(
		String keywords, Map<String, SXPParameter> sxpParameters) {

		_keywords = keywords;
		_sxpParameters = sxpParameters;
	}

	public String getKeywords() {
		return _keywords;
	}

	public SXPParameter getSXPParameterByName(String name) {
		return _sxpParameters.get(name);
	}

	public Map<String, SXPParameter> getSXPParameters() {
		return _sxpParameters;
	}

	private final String _keywords;
	private final Map<String, SXPParameter> _sxpParameters;

}