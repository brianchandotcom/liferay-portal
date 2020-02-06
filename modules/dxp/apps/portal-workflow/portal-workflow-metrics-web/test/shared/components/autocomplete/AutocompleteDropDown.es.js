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

import {Autocomplete} from '../../../../src/main/resources/META-INF/resources/js/shared/components/autocomplete/Autocomplete.es';

describe('The AssigneeInput component should', () => {
	const onChange = jest.fn();

	afterEach(cleanup);
	const items = [
		{
			additionalName: '',
			contentType: 'UserAccount',
			familyName: 'test0',
			givenName: '0test',
			id: 39431,
			name: '0test test0',
			profileURL: ''
		}
	];
	test('Render with the message no-results-found', () => {
		const {getByTestId} = render(
			<Autocomplete items={[]} onChange={onChange} />
		);
		const dropDownList = getByTestId('dropDownList');

		expect(dropDownList.children[0].innerHTML).toContain(
			'no-results-found'
		);
	});

	test('Fire blur event when user blurs the dropDown', () => {
		const {getByTestId} = render(
			<Autocomplete items={items} onChange={onChange} />
		);
		const dropDownList = getByTestId('dropDownList');

		expect(dropDownList.children[0].innerHTML).not.toContain(
			'no-results-found'
		);
	});
});
