/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
