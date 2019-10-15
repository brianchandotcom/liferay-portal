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

import Alias from '../../../../src/main/resources/META-INF/resources/js/components/alias/Alias.es';
import React from 'react';
import {fireEvent, render} from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';

describe('Alias', () => {
	it('has a list of aliases available', () => {
		const {container} = render(
			<Alias keywords={['one', 'two', 'three']} onChange={jest.fn()} />
		);

		const tagsElement = container.querySelectorAll('.label-item-expand');

		expect(tagsElement[0]).toHaveTextContent('one');
		expect(tagsElement[1]).toHaveTextContent('two');
		expect(tagsElement[2]).toHaveTextContent('three');
	});

	it('prompts to input an alias', () => {
		const {getByText} = render(
			<Alias keywords={['one', 'two', 'three']} onChange={jest.fn()} />
		);

		expect(getByText('add-an-alias-instruction')).toBeInTheDocument();
	});

	it('updates the input value', () => {
		const {getByLabelText} = render(
			<Alias keywords={['one']} onChange={jest.fn()} />
		);

		const input = getByLabelText('aliases');

		fireEvent.change(input, {target: {value: 'test'}});

		expect(input.getAttribute('value')).toEqual('test');
	});
});
