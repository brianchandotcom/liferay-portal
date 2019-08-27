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

import {setLocalizedValue} from '../../../src/main/resources/META-INF/resources/js/util/i18n.es';

describe('Internationlization', () => {
	it('creates a localized property for a specific object string value', () => {
		const obj = {
			title: ''
		};
		const value = 'the title will be localized';

		setLocalizedValue(obj, 'en_US', 'title', value);

		expect(obj).toMatchObject({
			localizedTitle: {
				en_US: value
			},
			title: value
		});
	});

	it('replaces a localized value for a specific object string', () => {
		const obj = {
			localizedTitle: {
				en_US: ''
			},
			title: ''
		};
		const value = 'the title will be localized';

		setLocalizedValue(obj, 'en_US', 'title', value);

		expect(obj).toMatchObject({
			localizedTitle: {
				en_US: value
			},
			title: value
		});
	});
});
