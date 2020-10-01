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
