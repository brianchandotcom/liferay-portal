/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const easeOutExpo = (x) => (x >= 1 ? 1 : 1 - Math.pow(2, -5 * x));

const pageSpeedTypicalCompletionTimeInSeconds = 30;

const getPageSpeedProgress = (timeInSeconds) =>
	99 * easeOutExpo(timeInSeconds / pageSpeedTypicalCompletionTimeInSeconds);

export default getPageSpeedProgress;
