/* eslint-disable no-undef */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const parameterName = configuration.parameterName;

const parameters = new URLSearchParams(window.location.search);
const paramValue = parameters.get(parameterName);

if (paramValue) {
	const spanElement = fragmentElement.querySelector(
		'#label-query-string-container h3'
	);
	spanElement.innerText = paramValue;
}
