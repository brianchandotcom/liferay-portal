/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const grantMoney = Number(
	document.querySelector('.grant-money div h2').innerText
);

document.querySelector('.grant-money div h2').innerHTML = Liferay.Util.escape(
	new Intl.NumberFormat('en-GB', {
		compactDisplay: 'short',
		notation: 'compact',
	}).format(grantMoney)
);
