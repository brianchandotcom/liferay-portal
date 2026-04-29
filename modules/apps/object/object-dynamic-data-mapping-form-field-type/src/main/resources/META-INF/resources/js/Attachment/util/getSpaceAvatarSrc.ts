/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Utility function for generating a data-URI space avatar matching Clay's
 * `sticker-outline-N` palette
 */
export function getSpaceAvatarSrc(letter: string, displayType: string) {
	const probe = document.createElement('span');

	probe.className = `sticker sticker-${displayType}`;

	document.body.appendChild(probe);

	const {backgroundColor, color} = getComputedStyle(probe);

	probe.remove();

	const svg = `<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 300 200" preserveAspectRatio="xMidYMid slice"><rect width="300" height="200" fill="#ffffff"/><rect x="125" y="75" rx="6" ry="6" width="50" height="50" fill="${backgroundColor}" stroke="${color}" stroke-width="0.5"/><text x="150" y="100" dy=".35em" text-anchor="middle" font-family="-apple-system,BlinkMacSystemFont,'Segoe UI',sans-serif" font-size="26" font-weight="700" fill="${color}">${letter}</text></svg>`;

	return `data:image/svg+xml;charset=utf-8,${encodeURIComponent(svg)}`;
}
