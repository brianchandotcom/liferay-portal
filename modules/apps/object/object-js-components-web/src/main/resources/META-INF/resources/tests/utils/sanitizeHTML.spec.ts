/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {sanitizeHTML} from '../../utils/sanitizeHTML';

const ALERT_REGEX = /alert\((.*?)\)/;
const ASP_CODE_REGEX = /<%[\s\S]*?%>/g;
const ASP_NET_CODE_REGEX = /(<asp:[^]+>[\s|\S]*?<\/asp:[^]+>)|(<asp:[^]+\/>)/gi;
const INNER_HTML_REGEX = /innerHTML\s*=\s*.*?/;
const ON_ATTRIBUTE_REGEX = /(\s+\bon\w+=(?:'[^']*'|"[^"]*"|[^'"\s>]+))/gi;
const PHP_CODE_REGEX = /<\?[\s\S]*?\?>/g;

describe('sanitizeHTML function', () => {
	test('handles multiple instances of potentially harmful patterns', () => {
		const html = `
			<script>alert("Hello");</script>
			<div innerHTML="some content"></div>
			<?php echo "World"; ?>
			<asp:Label runat="server">Hello</asp:Label>
			<button onclick="alert('Hello')">Click</button>
      `;

		const sanitized = sanitizeHTML(html);

		expect(sanitized).not.toMatch(ALERT_REGEX);
		expect(sanitized).not.toMatch(ASP_CODE_REGEX);
		expect(sanitized).not.toMatch(ASP_NET_CODE_REGEX);
		expect(sanitized).not.toMatch(INNER_HTML_REGEX);
		expect(sanitized).not.toMatch(ON_ATTRIBUTE_REGEX);
		expect(sanitized).not.toMatch(PHP_CODE_REGEX);
	});

	test('removes alert() function calls', () => {
		const html = '<script>alert("Hello");</script>';

		const sanitized = sanitizeHTML(html);

		expect(sanitized).not.toMatch(ALERT_REGEX);
	});

	test('removes ASP code', () => {
		const html = '<% Response.Write("Hello"); %>';

		const sanitized = sanitizeHTML(html);

		expect(sanitized).not.toMatch(ASP_CODE_REGEX);
	});

	test('removes ASP.NET code', () => {
		const html = '<asp:Label runat="server">Hello</asp:Label>';

		const sanitized = sanitizeHTML(html);

		expect(sanitized).not.toMatch(ASP_NET_CODE_REGEX);
	});

	test('removes innerHTML attribute', () => {
		const html = '<div innerHTML="some content"></div>';

		const sanitized = sanitizeHTML(html);

		expect(sanitized).not.toMatch(INNER_HTML_REGEX);
	});

	test('removes on-event attributes', () => {
		const html =
			'<button onclick="alert(\'Hello\')" onmouseover="doSomething()">Click</button>';

		const sanitized = sanitizeHTML(html);

		expect(sanitized).not.toMatch(ON_ATTRIBUTE_REGEX);
	});

	test('removes PHP code', () => {
		const html = '<?php echo "Hello"; ?>';

		const sanitized = sanitizeHTML(html);

		expect(sanitized).not.toMatch(PHP_CODE_REGEX);
	});
});
