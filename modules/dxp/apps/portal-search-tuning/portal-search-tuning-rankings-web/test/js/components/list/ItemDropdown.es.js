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

describe('ItemDropdown', () => {
	it('has option to unpin a visible and pinned item', () => {
		const {getByText} = render(
			<ItemDropdown
				hidden={false}
				onClickHide={jest.fn()}
				onClickPin={jest.fn()}
				pinned={true}
			/>
		);

		expect(getByText('unpin-result')).not.toBeNull();
	});

	it('has option to unpin multiple visible', () => {
		const {getByText} = render(
			<ItemDropdown
				hidden={false}
				itemCount={2}
				onClickHide={jest.fn()}
				onClickPin={jest.fn()}
				pinned={true}
			/>
		);

		expect(getByText('unpin-results')).not.toBeNull();
	});

	it('has option to pin visible', () => {
		const {getByText} = render(
			<ItemDropdown
				hidden={false}
				onClickHide={jest.fn()}
				onClickPin={jest.fn()}
				pinned={false}
			/>
		);

		expect(getByText('pin-result')).not.toBeNull();
	});

	it('has option to unpin multiple visible', () => {
		const {getByText} = render(
			<ItemDropdown
				hidden={false}
				itemCount={2}
				onClickHide={jest.fn()}
				onClickPin={jest.fn()}
				pinned={false}
			/>
		);

		expect(getByText('pin-results')).not.toBeNull();
	});

	it('has option to hide visible', () => {
		const {getByText} = render(
			<ItemDropdown
				hidden={false}
				onClickHide={jest.fn()}
				onClickPin={jest.fn()}
				pinned={false}
			/>
		);

		expect(getByText('hide-result')).not.toBeNull();
	});

	it('has option to hide multiple visible', () => {
		const {getByText} = render(
			<ItemDropdown
				hidden={false}
				itemCount={2}
				onClickHide={jest.fn()}
				onClickPin={jest.fn()}
				pinned={false}
			/>
		);

		expect(getByText('hide-results')).not.toBeNull();
	});

	it('has option to show hidden', () => {
		const {getByText} = render(
			<ItemDropdown
				hidden={true}
				onClickHide={jest.fn()}
				onClickPin={jest.fn()}
				pinned={false}
			/>
		);

		expect(getByText('show-result')).not.toBeNull();
	});

	it('has option to show multiple hidden', () => {
		const {getByText} = render(
			<ItemDropdown
				hidden={true}
				itemCount={2}
				onClickHide={jest.fn()}
				onClickPin={jest.fn()}
				pinned={false}
			/>
		);

		expect(getByText('show-results')).not.toBeNull();
	});

	it('has option to pin hidden', () => {
		const {getByText} = render(
			<ItemDropdown
				hidden={true}
				onClickHide={jest.fn()}
				onClickPin={jest.fn()}
				pinned={false}
			/>
		);

		expect(getByText('pin-result')).not.toBeNull();
	});

	it('has option to pin multiple hidden', () => {
		const {getByText} = render(
			<ItemDropdown
				hidden={true}
				itemCount={2}
				onClickHide={jest.fn()}
				onClickPin={jest.fn()}
				pinned={false}
			/>
		);

		expect(getByText('pin-results')).not.toBeNull();
	});

	it('does not have option to show/hide when onClickHide is missing', () => {
		const {queryByText} = render(
			<ItemDropdown hidden={false} onClickPin={jest.fn()} pinned={true} />
		);
		expect(queryByText('show-result')).toBeNull();
		expect(queryByText('hide-result')).toBeNull();
	});

	it('shows the dropdown buttons when clicked on', () => {
		const {getByText, getByTitle} = render(
			<ItemDropdown
				hidden={false}
				onClickHide={jest.fn()}
				onClickPin={jest.fn()}
				pinned={false}
			/>
		);

		fireEvent.click(getByTitle('toggle-dropdown'));

		expect(getByText('pin-result')).not.toBeNull();
		expect(getByText('hide-result')).not.toBeNull();
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

		fireEvent.click(getByText('hide-result'));

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

		fireEvent.click(getByText('pin-result'));

		expect(onClickPin.mock.calls.length).toBe(1);
	});
});
