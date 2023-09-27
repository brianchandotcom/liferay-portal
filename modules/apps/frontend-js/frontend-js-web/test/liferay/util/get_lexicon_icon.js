/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getLexiconIcon from '../../../src/main/resources/META-INF/resources/liferay/util/get_lexicon_icon';

describe('Liferay.Util.getLexiconIcon', () => {
	it('returns an svg element', () => {
		const icon = getLexiconIcon('close');

		expect(icon.nodeName).toBe('svg');
	});

	it('returns an element with the class determining the icon', () => {
		const icon = getLexiconIcon('close');

		expect(icon.classList.contains('lexicon-icon-close')).toBe(true);
	});

	it('returns an element pointing to the icon within the svg spritemap', () => {
		const icon = getLexiconIcon('close');

		const svgHref = icon.children[0].getAttribute('href');

		expect(svgHref).toContain('close');
	});

	it('returns an element containing the provided css class', () => {
		const icon = getLexiconIcon('close', 'test-class');

		expect(icon.classList.contains('test-class')).toBe(true);
	});

	it("throws an error if the icon string isn't provided", () => {
		expect(() => getLexiconIcon()).toThrow();
	});
});
