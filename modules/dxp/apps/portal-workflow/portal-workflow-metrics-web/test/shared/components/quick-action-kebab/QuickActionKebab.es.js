/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import QuickActionKebab from '../../../../src/main/resources/META-INF/resources/js/shared/components/quick-action-kebab/QuickActionKebab.es';

describe('The QuickActionKebab component should', () => {
	afterEach(cleanup);

	test('Render quick action options when has no items prop', () => {
		const kebabIconItems = [
			{
				action: jest.fn(),
				icon: 'change',
				title: Liferay.Language.get('reassign-task'),
			},
		];
		const {container} = render(
			<QuickActionKebab iconItems={kebabIconItems} />
		);

		const iconItemButton = container.querySelector('.quick-action-item');

		expect(iconItemButton).not.toBeUndefined();
	});
});
