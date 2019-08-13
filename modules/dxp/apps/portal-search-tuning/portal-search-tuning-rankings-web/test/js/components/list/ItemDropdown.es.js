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

import React from 'react';
import ItemDropdown from '../../../../src/main/resources/META-INF/resources/js/components/list/ItemDropdown.es';
import {fireEvent, render} from '@testing-library/react';

const DROPDOWN_TOGGLE_ID = 'dropdown-toggle';

describe('ItemDropdown', () => {
	it('has option to unpin visible', () => {
		const {queryByText} = render(
			<ItemDropdown
				hidden={false}
				onClickHide={jest.fn()}
				onClickPin={jest.fn()}
				pinned={true}
			/>
		);

		expect(queryByText('Unpin Result')).not.toBeNull();
		expect(queryByText('Unpin Results')).toBeNull();
	});

	it('has option to unpin multiple visible', () => {
		const {queryByText} = render(
			<ItemDropdown
				hidden={false}
				itemCount={2}
				onClickHide={jest.fn()}
				onClickPin={jest.fn()}
				pinned={true}
			/>
		);

		expect(queryByText('Unpin Results')).not.toBeNull();
	});

	it('has option to pin visible', () => {
		const {queryByText} = render(
			<ItemDropdown
				hidden={false}
				onClickHide={jest.fn()}
				onClickPin={jest.fn()}
				pinned={false}
			/>
		);

		expect(queryByText('Pin Result')).not.toBeNull();
		expect(queryByText('Pin Results')).toBeNull();
	});

	it('has option to unpin multiple visible', () => {
		const {queryByText} = render(
			<ItemDropdown
				hidden={false}
				itemCount={2}
				onClickHide={jest.fn()}
				onClickPin={jest.fn()}
				pinned={false}
			/>
		);

		expect(queryByText('Pin Results')).not.toBeNull();
	});

	it('has option to hide visible', () => {
		const {queryByText} = render(
			<ItemDropdown
				hidden={false}
				onClickHide={jest.fn()}
				onClickPin={jest.fn()}
				pinned={false}
			/>
		);

		expect(queryByText('Hide Result')).not.toBeNull();
		expect(queryByText('Hide Results')).toBeNull();
	});

	it('has option to hide multiple visible', () => {
		const {queryByText} = render(
			<ItemDropdown
				hidden={false}
				itemCount={2}
				onClickHide={jest.fn()}
				onClickPin={jest.fn()}
				pinned={false}
			/>
		);

		expect(queryByText('Hide Results')).not.toBeNull();
	});

	it('has option to show hidden', () => {
		const {queryByText} = render(
			<ItemDropdown
				hidden={true}
				onClickHide={jest.fn()}
				onClickPin={jest.fn()}
				pinned={false}
			/>
		);

		expect(queryByText('Show Result')).not.toBeNull();
		expect(queryByText('Show Results')).toBeNull();
	});

	it('has option to show multiple hidden', () => {
		const {queryByText} = render(
			<ItemDropdown
				hidden={true}
				itemCount={2}
				onClickHide={jest.fn()}
				onClickPin={jest.fn()}
				pinned={false}
			/>
		);

		expect(queryByText('Show Results')).not.toBeNull();
	});

	it('has option to pin hidden', () => {
		const {queryByText} = render(
			<ItemDropdown
				hidden={true}
				onClickHide={jest.fn()}
				onClickPin={jest.fn()}
				pinned={false}
			/>
		);

		expect(queryByText('Pin Result')).not.toBeNull();
		expect(queryByText('Pin Results')).toBeNull();
	});

	it('has option to pin multiple hidden', () => {
		const {queryByText} = render(
			<ItemDropdown
				hidden={true}
				itemCount={2}
				onClickHide={jest.fn()}
				onClickPin={jest.fn()}
				pinned={false}
			/>
		);

		expect(queryByText('Pin Results')).not.toBeNull();
	});

	it('does not have option to show/hide when onClickHide is missing', () => {
		const {queryByText} = render(
			<ItemDropdown hidden={false} onClickPin={jest.fn()} pinned={true} />
		);

		expect(queryByText('Show Result')).toBeNull();
		expect(queryByText('Hide Result')).toBeNull();
	});

	it('shows the dropdown when clicked on', () => {
		const {container, getByTestId} = render(
			<ItemDropdown
				hidden={false}
				onClickHide={jest.fn()}
				onClickPin={jest.fn()}
				pinned={false}
			/>
		);

		fireEvent.click(getByTestId(DROPDOWN_TOGGLE_ID));

		expect(container.querySelector('.dropdown-menu')).toHaveClass('show');
	});

	it('calls the onClickHide function when it gets clicked on', () => {
		const onClickHide = jest.fn();

		const {getByText} = render(
			<ItemDropdown
				hidden={false}
				onClickHide={onClickHide}
				onClickPin={jest.fn()}
				pinned={false}
			/>
		);

		fireEvent.click(getByText('Hide Result'));

		expect(onClickHide.mock.calls.length).toBe(1);
	});

	it('calls the onClickPin function when it gets clicked on', () => {
		const onClickPin = jest.fn();

		const {getByText} = render(
			<ItemDropdown
				hidden={false}
				onClickHide={jest.fn()}
				onClickPin={onClickPin}
				pinned={false}
			/>
		);

		fireEvent.click(getByText('Pin Result'));

		expect(onClickPin.mock.calls.length).toBe(1);
	});
});
