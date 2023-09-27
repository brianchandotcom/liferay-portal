/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	escapeHTML,
	unescapeHTML,
} from '../../../src/main/resources/META-INF/resources/liferay/util/html_util';

describe('Liferay.Util.escapeHTML and Liferay.Util.unescapeHTML', () => {
	describe('Liferay.Util.escapeHTML', () => {
		it('escapes HTML entities', () => {
			expect(escapeHTML('"')).toEqual('&#034;');
			expect(escapeHTML('&')).toEqual('&amp;');
			expect(escapeHTML("'")).toEqual('&#039;');
			expect(escapeHTML('/')).toEqual('&#047;');
			expect(escapeHTML('<')).toEqual('&lt;');
			expect(escapeHTML('>')).toEqual('&gt;');
			expect(escapeHTML('`')).toEqual('&#096;');

			expect(escapeHTML('[<p>one & two</p>]')).toEqual(
				'[&lt;p&gt;one &amp; two&lt;&#047;p&gt;]'
			);
			expect(escapeHTML('<&>')).toEqual('&lt;&amp;&gt;');
		});
	});

	describe('Liferay.Util.unescapeHTML', () => {
		it('unescapes HTML entities', () => {
			expect(unescapeHTML('&#034;')).toEqual('"');
			expect(unescapeHTML('&amp;')).toEqual('&');
			expect(unescapeHTML('&#039;')).toEqual("'");
			expect(unescapeHTML('&#047;')).toEqual('/');
			expect(unescapeHTML('&lt;')).toEqual('<');
			expect(unescapeHTML('&gt;')).toEqual('>');
			expect(unescapeHTML('&#096;')).toEqual('`');

			expect(unescapeHTML('&#040;')).toEqual('(');
			expect(unescapeHTML('&#041;')).toEqual(')');
			expect(unescapeHTML('&#043;')).toEqual('+');
			expect(unescapeHTML('&#061;')).toEqual('=');
			expect(unescapeHTML('&#064;')).toEqual('@');
			expect(unescapeHTML('&#091;')).toEqual('[');
			expect(unescapeHTML('&#093;')).toEqual(']');

			expect(unescapeHTML('&num;')).toEqual('#');
			expect(unescapeHTML('&dollar;')).toEqual('$');
			expect(unescapeHTML('&dollar;')).toEqual('$');

			expect(
				unescapeHTML('&dollar;&lpar;&quot;&period;stuff&quot;&rpar;')
			).toEqual('$(".stuff")');

			expect(unescapeHTML('ma&ntilde;ana means tomorrow')).toEqual(
				'mañana means tomorrow'
			);
		});
	});

	describe('round-tripping from unescaped to escaped and back again', () => {
		it('unescaping an escaped string', () => {
			const string = "'hello' & <p>goodbye</p>";

			const escaped = escapeHTML(string);

			expect(escaped).toBe(
				'&#039;hello&#039; &amp; &lt;p&gt;goodbye&lt;&#047;p&gt;'
			);

			const unescaped = unescapeHTML(escaped);

			expect(unescaped).toBe(string);
		});
	});

	describe("when using strings that look like entities but aren't", () => {
		it("doesn't throw errors", () => {
			expect(() =>
				unescapeHTML('my test !"§$%&/()=?`@€<>|,.-;:_+*#~')
			).not.toThrow();

			expect(() =>
				unescapeHTML('my test !"§$%&/()=?`@€<sth here>|,.-;:_+*#~')
			).not.toThrow();
		});
	});
});
