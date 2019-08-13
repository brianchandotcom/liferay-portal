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

/* eslint-disable no-unused-vars */
jest.mock('react-dnd', () => ({
	DragSource: el => el => el,
	DropTarget: el => el => el
}));
/* eslint-enable no-unused-vars */

const DROPDOWN_TOGGLE_ID = 'dropdown-toggle';

const HIDE_BUTTON_LABEL = 'Hide Result';

const UNPIN_BUTTON_LABEL = 'Unpin Result';

describe('Item', () => {
	it('shows the dropdown when clicked on', () => {
		const {container, getByTestId} = render(
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
				onClickHide={jest.fn()}
				onClickPin={jest.fn()}
				onDragHover={jest.fn()}
				onMove={jest.fn()}
				onRemoveSelect={jest.fn()}
				onSelect={jest.fn()}
				pinned={true}
				selected={true}
				title={'This is a Web Content Example'}
				type={'Web Content'}
			/>
		);

		fireEvent.click(getByTestId(DROPDOWN_TOGGLE_ID));

		expect(container.querySelector('.dropdown-menu')).toHaveClass('show');
	});

	it('shows the appropriate subtext', () => {
		const {container} = render(
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
				onClickHide={jest.fn()}
				onClickPin={jest.fn()}
				onDragHover={jest.fn()}
				onMove={jest.fn()}
				onRemoveSelect={jest.fn()}
				onSelect={jest.fn()}
				pinned={true}
				selected={true}
				title={'This is a Web Content Example'}
				type={'Web Content'}
			/>
		);

		const subtitles = container.querySelectorAll('.list-group-subtext');

		expect(subtitles[0]).toHaveTextContent(
			'Test TestApr 18 2018, 11:04 AM'
		);
		expect(subtitles[1]).toHaveTextContent('[Web Content]');
	});

	it('shows the appropriate title', () => {
		const {container} = render(
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
				onClickHide={jest.fn()}
				onClickPin={jest.fn()}
				onDragHover={jest.fn()}
				onMove={jest.fn()}
				onRemoveSelect={jest.fn()}
				onSelect={jest.fn()}
				pinned={true}
				selected={true}
				title={'This is a Web Content Example'}
				type={'Web Content'}
			/>
		);

		expect(
			container.querySelector('.text-truncate-inline')
		).toHaveTextContent('This is a Web Content Example');
	});

	it('shows the appropriate description', () => {
		const {container} = render(
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
				onClickHide={jest.fn()}
				onClickPin={jest.fn()}
				onDragHover={jest.fn()}
				onMove={jest.fn()}
				onRemoveSelect={jest.fn()}
				onSelect={jest.fn()}
				pinned={true}
				selected={true}
				title={'This is a Web Content Example'}
				type={'Web Content'}
			/>
		);

		expect(
			container.querySelector('.list-item-description')
		).toHaveTextContent(
			'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod ips...'
		);
	});

	it('shows the appropriate view count', () => {
		const {container} = render(
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
				onClickHide={jest.fn()}
				onClickPin={jest.fn()}
				onDragHover={jest.fn()}
				onMove={jest.fn()}
				onRemoveSelect={jest.fn()}
				onSelect={jest.fn()}
				pinned={true}
				selected={true}
				title={'This is a Web Content Example'}
				type={'Web Content'}
			/>
		);

		expect(container.querySelector('.click-count')).toHaveTextContent(
			'289'
		);
	});

	it('calls the onClickHide function when its button gets clicked on', () => {
		const onClickHide = jest.fn();

		const {container} = render(
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
				onClickHide={onClickHide}
				onClickPin={jest.fn()}
				onDragHover={jest.fn()}
				onMove={jest.fn()}
				onRemoveSelect={jest.fn()}
				onSelect={jest.fn()}
				pinned={true}
				selected={true}
				title={'This is a Web Content Example'}
				type={'Web Content'}
			/>
		);

		fireEvent.click(within(container).getByTitle(HIDE_BUTTON_LABEL));

		expect(onClickHide.mock.calls.length).toBe(1);
	});

	it('calls the onClickPin function when its button gets clicked on', () => {
		const onClickPin = jest.fn();

		const {container} = render(
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
				onClickHide={jest.fn()}
				onClickPin={onClickPin}
				onDragHover={jest.fn()}
				onMove={jest.fn()}
				onRemoveSelect={jest.fn()}
				onSelect={jest.fn()}
				pinned={true}
				selected={true}
				title={'This is a Web Content Example'}
				type={'Web Content'}
			/>
		);

		fireEvent.click(within(container).getByTitle(UNPIN_BUTTON_LABEL));

		expect(onClickPin.mock.calls.length).toBe(1);
	});

	it('calls the onFocus event when focused', () => {
		const onFocus = jest.fn();

		const {getByTestId} = render(
			<Item
				addedResult={false}
				author={'Test Test'}
				clicks={289}
				date={'Apr 18 2018, 11:04 AM'}
				description={
					'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod'
				}
				extension={''}
				focus={false}
				hidden={false}
				id={101}
				index={1}
				key={101}
				onBlur={jest.fn()}
				onClickHide={jest.fn()}
				onClickPin={jest.fn()}
				onDragHover={jest.fn()}
				onFocus={onFocus}
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

		fireEvent.focus(getByTestId('101'));

		expect(onFocus.mock.calls.length).toBe(1);
	});

	it('calls the onBlur event when un-focused', () => {
		const onBlur = jest.fn();

		const {getByTestId} = render(
			<Item
				addedResult={false}
				author={'Test Test'}
				clicks={289}
				date={'Apr 18 2018, 11:04 AM'}
				description={
					'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod'
				}
				extension={''}
				focus={false}
				hidden={false}
				id={101}
				index={1}
				key={101}
				onBlur={onBlur}
				onClickHide={jest.fn()}
				onClickPin={jest.fn()}
				onDragHover={jest.fn()}
				onFocus={jest.fn()}
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

		fireEvent.blur(getByTestId('101'));

		expect(onBlur.mock.calls.length).toBe(1);
	});

	it('calls the onFocus event when a button within is focused', () => {
		const onFocus = jest.fn();

		const {getByTitle} = render(
			<Item
				addedResult={false}
				author={'Test Test'}
				clicks={289}
				date={'Apr 18 2018, 11:04 AM'}
				description={
					'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod'
				}
				extension={''}
				focus={false}
				hidden={false}
				id={101}
				index={1}
				key={101}
				onBlur={jest.fn()}
				onClickHide={jest.fn()}
				onClickPin={jest.fn()}
				onDragHover={jest.fn()}
				onFocus={onFocus}
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

		fireEvent.focus(getByTitle('Unpin Result'));

		expect(onFocus.mock.calls.length).toBe(0);
	});
});
