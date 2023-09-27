/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import QRCode from 'qrcode';

export default function generateQRCode(
	containerId,
	{account, algorithm, counter, digits, issuer, secret}
) {
	const url = new URL('otpauth://totp/' + encodeURIComponent(account));

	const params = {
		algorithm,
		counter,
		digits,
		issuer,
		secret,
	};

	url.search = Object.entries(params)
		.map(([key, value]) => `${key}=${encodeURIComponent(value)}`)
		.join('&');

	QRCode.toDataURL(url.toString())
		.then((dataUrl) => {
			const image = document.createElement('img');

			image.setAttribute('src', dataUrl);

			const container = document.getElementById(containerId);

			container.appendChild(image);
		})
		.catch((error) => {
			console.error(error);
		});
}
