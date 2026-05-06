/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	getDefaultLanguageLabel,
	setDefaultLanguageLabels,
} from '../../../../src/main/resources/META-INF/resources/js/common/utils/defaultLanguageLabels';

describe('getDefaultLanguageLabel', () => {
	afterEach(() => {
		setDefaultLanguageLabels({labels: {}, locale: 'en_US'});
	});

	it('returns the default-language label when the singleton was initialized with the key', () => {
		setDefaultLanguageLabels({
			labels: {
				'option': 'Option',
				'select-related-content': 'Select related content',
			},
			locale: 'en_US',
		});

		expect(getDefaultLanguageLabel('option')).toBe('Option');
		expect(getDefaultLanguageLabel('select-related-content')).toBe(
			'Select related content'
		);
	});

	it('falls back to Liferay.Language.get when the key is missing in the singleton', () => {
		setDefaultLanguageLabels({labels: {}, locale: 'en_US'});

		expect(getDefaultLanguageLabel('untracked-key')).toBe('untracked-key');
	});

	it('falls back to Liferay.Language.get when the singleton was never initialized', () => {
		jest.isolateModules(() => {
			const {
				getDefaultLanguageLabel: freshGet,
			} = require('../../../../src/main/resources/META-INF/resources/js/common/utils/defaultLanguageLabels');

			expect(freshGet('option')).toBe('option');
		});
	});
});
