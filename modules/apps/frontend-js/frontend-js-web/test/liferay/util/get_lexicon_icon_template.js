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

import getLexiconIconTpl from '../../../src/main/resources/META-INF/resources/liferay/util/get_lexicon_icon_template';

describe('Liferay.Util.getLexiconIconTpl', () => {
	it('to return a string representing an svg icon', () => {
		themeDisplay = {
			getPathThemeImages: jest.fn(() => 'foo'),
		};

		const icon = getLexiconIconTpl('close', 'test-class');

		expect(icon).toMatchInlineSnapshot(
			`"<svg aria-hidden=\\"true\\" class=\\"lexicon-icon lexicon-icon-close test-class\\" focusable=\\"false\\" role=\\"presentation\\"><use href=\\"foo/clay/icons.svg#close\\" /></svg>"`
		);
	});

	it('adds an empty string if the second argument is missing', () => {
		const icon = getLexiconIconTpl('close');

		expect(icon).toMatchInlineSnapshot(
			`"<svg aria-hidden=\\"true\\" class=\\"lexicon-icon lexicon-icon-close \\" focusable=\\"false\\" role=\\"presentation\\"><use href=\\"foo/clay/icons.svg#close\\" /></svg>"`
		);
	});
});
