/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function getElementVariationScript({
	js,
	targetElement,
}: {
	js: string;
	targetElement: string;
}): string {
	return `
(function () {
	const element = document.querySelector(${JSON.stringify(targetElement)});

	if (!element) {
		return;
	}

	${js}
})();
`;
}
