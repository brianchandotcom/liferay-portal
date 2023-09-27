/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as Utils from '../../../src/main/resources/META-INF/resources/js/utils/utils.es';

describe('utils', () => {
	describe('slugToText', () => {
		it('returns a text with spaces instead of hyphens', () => {
			expect(Utils.slugToText('Lorem-ipsum-dolor')).toEqual(
				'lorem ipsum dolor'
			);
		});
	});

	describe('stringToSlug', () => {
		it('return a text with hyphens instead of spaces', () => {
			expect(
				Utils.stringToSlug(
					'lorem ipsum dolor sit amet consectetur adipiscing elit'
				)
			).toEqual('lorem-ipsum-dolor-sit-amet-consectetur-adipiscing-elit');
		});
	});
});
