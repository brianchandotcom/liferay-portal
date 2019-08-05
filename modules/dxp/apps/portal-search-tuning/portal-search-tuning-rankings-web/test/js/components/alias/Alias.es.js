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
import {act} from 'react-dom/test-utils';
import {fireEvent, render, wait, within} from '@testing-library/react';

const MODAL_ID = 'alias-modal';

describe('Alias', () => {
	/*
	Console error occurs alongside message stating that "React state
	should be wrapped in 'act'." This can be removed after react-dom@16.9.0.
	Link for reference:
	https://github.com/testing-library/react-testing-library/issues/281#issuecomment-480349256
	 */

	const originalError = console.error;

	beforeAll(() => {
		console.error = (...args) => {
			if (
				/Warning.*not wrapped in act/.test(args[0]) ||
				/Warning: Can't perform a React state update/.test(args[0])
			) {
				return;
			}
			originalError.call(console, ...args);
		};
	});

	afterAll(() => {
		console.error = originalError;
	});

	it('has a list of tags available', () => {
		const {container} = render(
			<Alias
				keywords={['one', 'two', 'three']}
				onClickDelete={jest.fn()}
				onClickSubmit={jest.fn()}
				searchQuery={'example'}
			/>
		);

		const tagsElement = container.querySelectorAll('.label-item-expand');

		expect(tagsElement[0]).toHaveTextContent('one');
		expect(tagsElement[1]).toHaveTextContent('two');
		expect(tagsElement[2]).toHaveTextContent('three');
	});

	it('shows a modal by default', () => {
		const {queryByTestId} = render(
			<Alias
				keywords={['one', 'two', 'three']}
				onClickDelete={jest.fn()}
				onClickSubmit={jest.fn()}
				searchQuery={'example'}
			/>
		);

		expect(queryByTestId(MODAL_ID)).toBeNull();
	});

	it('renders a modal when the add an alias button gets clicked', () => {
		const {getByText, queryByTestId} = render(
			<Alias
				keywords={['one', 'two', 'three']}
				onClickDelete={jest.fn()}
				onClickSubmit={jest.fn()}
				searchQuery={'example'}
			/>
		);

		fireEvent.click(getByText('Add an Alias'));

		expect(queryByTestId(MODAL_ID)).not.toBeNull();
	});

	it('closes the modal after the cancel button gets clicked', async () => {
		const {getByTestId, getByText, queryByTestId} = render(
			<Alias
				keywords={['one', 'two', 'three']}
				onClickDelete={jest.fn()}
				onClickSubmit={jest.fn()}
				searchQuery={'example'}
			/>
		);

		fireEvent.click(getByText('Add an Alias'));

		fireEvent.click(within(getByTestId(MODAL_ID)).getByText('Cancel'));

		await wait(() => {
			expect(queryByTestId(MODAL_ID)).not.toBeInTheDocument();
		});
	});

	it('prompts to input an alias', () => {
		const {getByText, queryByText} = render(
			<Alias
				keywords={['one', 'two', 'three']}
				onClickDelete={jest.fn()}
				onClickSubmit={jest.fn()}
				searchQuery={'example'}
			/>
		);

		fireEvent.click(getByText('Add an Alias'));

		expect(
			queryByText('Type a comma or press enter to input an alias')
		).not.toBeNull();
	});

	it('has the modal with a default disabled add button', () => {
		const {getByText, queryByTestId} = render(
			<Alias
				keywords={['one', 'two', 'three']}
				onClickDelete={jest.fn()}
				onClickSubmit={jest.fn()}
				searchQuery={'example'}
			/>
		);

		fireEvent.click(getByText('Add an Alias'));

		expect(
			within(queryByTestId(MODAL_ID)).getByText('Add')
		).toHaveAttribute('disabled');
	});

	it('renders blank keywords', () => {
		const {container} = render(
			<Alias
				keywords={['', ' ']}
				onClickDelete={jest.fn()}
				onClickSubmit={jest.fn()}
				searchQuery={'example'}
			/>
		);

		const tagsElement = container.querySelectorAll('.label-item');

		expect(tagsElement.length).toBe(0);
	});
});
