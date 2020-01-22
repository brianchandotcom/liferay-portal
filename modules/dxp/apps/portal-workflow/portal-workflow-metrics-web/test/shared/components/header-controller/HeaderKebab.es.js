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

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import HeaderKebab from '../../../../src/main/resources/META-INF/resources/js/shared/components/header/HeaderKebab.es';
import {MockRouter} from '../../../mock/MockRouter.es';

import '@testing-library/jest-dom/extend-expect';

describe('The HeaderKebab component should', () => {
	afterEach(cleanup);

	beforeAll(() => {
		const body = document.createElement('div');

		body.className = 'user-control-group';

		body.innerHTML = `<div class="control-menu-icon"></div>`;

		document.body.appendChild(body);
	});

	test('Not render when has no kebabItems prop', () => {
		const {container} = render(<HeaderKebab />);

		expect(container.innerHTML).toBe('');
	});

	test('Render with kebabItems prop', () => {
		const mockAction = jest.fn();
		const kebabItems = [
			{
				action: mockAction,
				label: 'test'
			},
			{
				label: 'test1',
				link: '/'
			}
		];

		const {getAllByTestId, getByTestId} = render(
			<MockRouter>
				<HeaderKebab kebabItems={kebabItems} />
			</MockRouter>
		);

		const button = getByTestId('headerKebabButton');

		fireEvent.click(button);

		const dropDownItems = getAllByTestId('headerKebabItem');

		expect(dropDownItems[0]).toHaveTextContent('test');

		expect(dropDownItems[1]).toHaveTextContent('test1');

		fireEvent.click(dropDownItems[0]);

		expect(mockAction).toHaveBeenCalled();
	});
});
