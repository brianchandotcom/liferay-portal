/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getLexiconIconTpl from '../../../src/main/resources/META-INF/resources/liferay/util/get_lexicon_icon_template';

describe('Liferay.Util.getLexiconIconTpl', () => {
	it('to return a string representing an svg icon', () => {
		themeDisplay = {
			getPathThemeImages: jest.fn(() => 'foo'),
		};

		const icon = getLexiconIconTpl('close', 'test-class');

		expect(icon).toMatchInlineSnapshot(
			`"<svg aria-hidden=\\"true\\" class=\\"lexicon-icon lexicon-icon-close test-class\\" focusable=\\"false\\" role=\\"presentation\\"><use href=\\"/o/icons/pack/clay.svg#close\\" /></svg>"`
		);
	});

	it('adds an empty string if the second argument is missing', () => {
		const icon = getLexiconIconTpl('close');

		expect(icon).toMatchInlineSnapshot(
			`"<svg aria-hidden=\\"true\\" class=\\"lexicon-icon lexicon-icon-close \\" focusable=\\"false\\" role=\\"presentation\\"><use href=\\"/o/icons/pack/clay.svg#close\\" /></svg>"`
		);
	});
});
