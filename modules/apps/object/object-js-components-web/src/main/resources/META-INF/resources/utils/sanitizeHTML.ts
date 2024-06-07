/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const ALERT_REGEX = /alert\((.*?)\)/;
const ASP_CODE_REGEX = /<%[\s\S]*?%>/g;
const ASP_NET_CODE_REGEX = /(<asp:[^]+>[\s|\S]*?<\/asp:[^]+>)|(<asp:[^]+\/>)/gi;
const HTML_TAG_WITH_ON_ATTRIBUTE_REGEX =
	/<[^>]+?(\s+\bon\w+=(?:'[^']*'|"[^"]*"|[^'"\s>]+))*\s*\/?>/gi;
const INNER_HTML_REGEX = /innerHTML\s*=\s*.*?/;
const ON_ATTRIBUTE_REGEX = /(\s+\bon\w+=(?:'[^']*'|"[^"]*"|[^'"\s>]+))/gi;
const PHP_CODE_REGEX = /<\?[\s\S]*?\?>/g;

/**
 * Sanitize the HTML string by removing alerts, asp and php codes. Also removes 'on' and innerHTML attributes
 */

export function sanitizeHTML(html: string) {
	const sanitizedHtml = html
		.replace(HTML_TAG_WITH_ON_ATTRIBUTE_REGEX, (match) => {
			return match.replace(ON_ATTRIBUTE_REGEX, '');
		})
		.replace(ALERT_REGEX, '')
		.replace(INNER_HTML_REGEX, '')
		.replace(PHP_CODE_REGEX, '')
		.replace(ASP_CODE_REGEX, '')
		.replace(ASP_NET_CODE_REGEX, '');

	return sanitizedHtml;
}
