/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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
