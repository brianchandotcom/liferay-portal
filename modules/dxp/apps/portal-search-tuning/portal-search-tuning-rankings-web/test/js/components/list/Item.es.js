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
import Item from '../../../../src/main/resources/META-INF/resources/js/components/list/Item.es';
import {fireEvent, render, within} from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';

/* eslint-disable no-unused-vars */
jest.mock('react-dnd', () => ({
	DragSource: el => el => el,
	DropTarget: el => el => el
}));
/* eslint-enable no-unused-vars */

const HIDE_BUTTON_LABEL = 'hide-result';

const UNPIN_BUTTON_LABEL = 'unpin-result';

const onBlurFn = jest.fn();
const onClickHideFn = jest.fn();
const onClickPinFn = jest.fn();
const onFocusFn = jest.fn();

function renderTestItem() {
	return render(
		<Item
			addedResult={false}
			author={'Test Test'}
			clicks={289}
			date={'Apr 18 2018, 11:04 AM'}
			description={
				'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod'
			}
			extension={''}
			hidden={false}
			id={101}
			index={1}
			key={101}
			onBlur={onBlurFn}
			onClickHide={onClickHideFn}
			onClickPin={onClickPinFn}
			onDragHover={jest.fn()}
			onFocus={onFocusFn}
			onMove={jest.fn()}
			onRemoveSelect={jest.fn()}
			onSelect={jest.fn()}
			pinned={true}
			reorder={true}
			selected={true}
			title={'This is a Web Content Example'}
			type={'Web Content'}
		/>
	);
}

describe('Item', () => {
	afterEach(() => {
		jest.clearAllMocks();
	});

	it('shows the dropdown when clicked on', () => {
		const {container, getByTitle} = renderTestItem();

		fireEvent.click(getByTitle('toggle-dropdown'));

		expect(container.querySelector('.dropdown-menu')).toHaveClass('show');
	});

	it('shows the appropriate subtext', () => {
		const {container} = renderTestItem();

		const subtitles = container.querySelectorAll('.list-group-subtext');

		expect(subtitles[0]).toHaveTextContent(
			'Test TestApr 18 2018, 11:04 AM'
		);
		expect(subtitles[1]).toHaveTextContent('[Web Content]');
	});

	it('shows the appropriate title', () => {
		const {container} = renderTestItem();

		expect(
			container.querySelector('.text-truncate-inline')
		).toHaveTextContent('This is a Web Content Example');
	});

	it('shows the appropriate description', () => {
		const {container} = renderTestItem();

		expect(
			container.querySelector('.list-item-description')
		).toHaveTextContent(
			'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod ips...'
		);
	});

	it('shows the appropriate view count', () => {
		const {getByText} = renderTestItem();

		expect(getByText('289', {exact: false})).toBeInTheDocument();
	});

	it('calls the onClickHide function when its button gets clicked on', () => {
		const {container} = renderTestItem();

		fireEvent.click(within(container).getByTitle(HIDE_BUTTON_LABEL));

		expect(onClickHideFn.mock.calls.length).toBe(1);
	});

	it('calls the onClickPin function when its button gets clicked on', () => {
		const {container} = renderTestItem();

		fireEvent.click(within(container).getByTitle(UNPIN_BUTTON_LABEL));

		expect(onClickPinFn.mock.calls.length).toBe(1);
	});

	it('calls the onFocus event when focused', () => {
		const {getByTestId} = renderTestItem();

		fireEvent.focus(getByTestId('101'));

		expect(onFocusFn.mock.calls.length).toBe(1);
	});

	it('calls the onBlur event when un-focused', () => {
		const {getByTestId} = renderTestItem();

		fireEvent.blur(getByTestId('101'));

		expect(onBlurFn.mock.calls.length).toBe(1);
	});

	it('does not call onFocus when a button within is focused', () => {
		const {getByTitle} = renderTestItem();

		fireEvent.focus(getByTitle('unpin-result'));

		expect(onFocusFn.mock.calls.length).toBe(0);
	});
});
