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
import ReactModal from 'react-modal';
import {fireEvent, render} from '@testing-library/react';

const MODAL_ID = 'alias-modal';

describe('Alias', () => {
	beforeEach(() => {
		ReactModal.setAppElement('body');
	});

	it('has a list of tags available', () => {
		const {container} = render(
			<Alias
				keywords={['one', 'two', 'three']}
				onClickDelete={jest.fn()}
				onClickSubmit={jest.fn()}
				searchTerm={'example'}
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
				searchTerm={'example'}
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
				searchTerm={'example'}
			/>
		);

		fireEvent.click(getByText('Add an Alias'));

		expect(queryByTestId(MODAL_ID)).not.toBeNull();
	});

	it('closes the modal after the cancel button gets clicked', () => {
		const {getByText, queryByTestId} = render(
			<Alias
				keywords={['one', 'two', 'three']}
				onClickDelete={jest.fn()}
				onClickSubmit={jest.fn()}
				searchTerm={'example'}
			/>
		);

		fireEvent.click(getByText('Add an Alias'));

		fireEvent.click(getByText('Cancel'));

		expect(queryByTestId(MODAL_ID)).toBeNull();
	});

	it('prompts to input an alias', () => {
		const {getByText, queryByText} = render(
			<Alias
				keywords={['one', 'two', 'three']}
				onClickDelete={jest.fn()}
				onClickSubmit={jest.fn()}
				searchTerm={'example'}
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
				searchTerm={'example'}
			/>
		);

		fireEvent.click(getByText('Add an Alias'));

		const modal = queryByTestId(MODAL_ID);

		expect(
			modal.querySelector('.modal-footer .btn-primary')
		).toHaveAttribute('disabled');
	});

	it('renders blank keywords', () => {
		const {container} = render(
			<Alias
				keywords={['', ' ']}
				onClickDelete={jest.fn()}
				onClickSubmit={jest.fn()}
				searchTerm={'example'}
			/>
		);

		const tagsElement = container.querySelectorAll('.label-item');

		expect(tagsElement.length).toBe(0);
	});
});
