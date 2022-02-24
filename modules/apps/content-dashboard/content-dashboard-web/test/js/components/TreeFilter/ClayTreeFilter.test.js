/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import '@testing-library/jest-dom/extend-expect';

import TreeFilter from '../../../../src/main/resources/META-INF/resources/js/components/TreeFilter/ClayTreeFilter/ClayTreeFilter';
import {
	mockEmptyTreeProps,
	mockExtensionsProps,
	mockExtensionsProps2,
	mockTypesProps,
} from '../../mocks/clayTreeProps';

describe('SelectFileExtension', () => {
	beforeEach(() => {
		cleanup();

		window.Liferay.Util.getOpener = jest.fn().mockReturnValue({
			Liferay: {
				fire: jest.fn(),
			},
		});
	});

	it('renders a Treeview with parent nodes and only first node expanded', () => {
		const {getByRole, getByText, queryByText} = render(
			<TreeFilter {...mockTypesProps} />
		);

		const {className} = getByRole('tree');
		expect(className).toContain('treeview treeview-light');

		expect(
			getByText('Web Content Article', {exact: false})
		).toBeInTheDocument();
		expect(getByText('Document', {exact: false})).toBeInTheDocument();
		expect(getByText('Basic Web Content')).toBeInTheDocument();
		expect(queryByText('External Video Shortcut')).not.toBeInTheDocument();
	});

	it('renders a Treeview with filtered nodes (parents and children) if query is set', () => {
		const {getByPlaceholderText, getByText, queryByText} = render(
			<TreeFilter {...mockTypesProps} />
		);

		const input = getByPlaceholderText('search');
		fireEvent.change(input, {target: {value: 'web content'}});

		expect(
			getByText('Web Content Article', {exact: false})
		).toBeInTheDocument();
		expect(
			getByText('Basic Web Content', {exact: false})
		).toBeInTheDocument();

		expect(queryByText('Basic Document')).not.toBeInTheDocument();
		expect(queryByText('Document')).not.toBeInTheDocument();
	});

	it('renders the parent node when the query matches any of its children', async () => {
		const {findByText, getByPlaceholderText, getByText} = render(
			<TreeFilter {...mockExtensionsProps} />
		);
		const input = getByPlaceholderText('search');
		fireEvent.change(input, {target: {value: 'jp'}});

		await findByText('Image (2 items)');
		expect(getByText('Image (2 items)')).toBeInTheDocument();

		expect(getByText('jpg')).toBeInTheDocument();
		expect(getByText('jpeg')).toBeInTheDocument();
	});

	it('renders a Treeview with a selected node if selected is true', async () => {
		const {container, getByPlaceholderText} = render(
			<TreeFilter {...mockExtensionsProps} />
		);

		await userEvent.type(getByPlaceholderText('search'), 'aud');

		const checkboxes = container.querySelectorAll('input[type=checkbox]');
		const checkedCheckboxesCount = Array.from(checkboxes).reduce(
			(acc, checkbox) => (checkbox.checked ? ++acc : acc),
			0
		);
		expect(checkedCheckboxesCount).toBe(1);
	});

	it('renders a Treeview with a selected parent node if all children are selected', async () => {
		const {container, getByPlaceholderText} = render(
			<TreeFilter {...mockExtensionsProps2} />
		);

		await userEvent.type(getByPlaceholderText('search'), 'aud');

		const checkboxes = container.querySelectorAll('input[type=checkbox]');
		const checkedCheckboxesCount = Array.from(checkboxes).reduce(
			(acc, checkbox) => (checkbox.checked ? ++acc : acc),
			0
		);
		expect(checkedCheckboxesCount).toBe(3);
	});

	it('shows empty state when there are no nodes in the tree', async () => {
		const {findByText, getByText} = render(
			<TreeFilter {...mockEmptyTreeProps} />
		);

		await findByText('no-results-were-found');

		expect(getByText('no-results-were-found')).toBeInTheDocument();
	});

	it('shows empty state when the text input does not match with any of the parents or children', async () => {
		const {findByText, getByPlaceholderText, getByText} = render(
			<TreeFilter {...mockExtensionsProps} />
		);

		const input = getByPlaceholderText('search');
		fireEvent.change(input, {target: {value: 'blabla'}});

		await findByText('no-results-were-found');
		expect(getByText('no-results-were-found')).toBeInTheDocument();
	});

	it('clears the search results by hitting the times icon in the search bar', async () => {
		const {container, findByText, getByPlaceholderText, getByText} = render(
			<TreeFilter {...mockExtensionsProps} />
		);

		// First we search for something

		const input = getByPlaceholderText('search');
		fireEvent.change(input, {target: {value: 'jp'}});

		await findByText('Image (2 items)');
		expect(getByText('Image (2 items)')).toBeInTheDocument();

		// Then we clear the search input by hitting the clear button

		const clearButton = container.querySelector('.tree-filter-clear');

		expect(clearButton).toBeTruthy();

		fireEvent.click(clearButton);

		expect(await getByText('Image (4 items)')).toBeInTheDocument();
	});

	it('shows the total number of elements selected in the list above the tree', async () => {
		const {container, getByText} = render(
			<TreeFilter {...mockExtensionsProps} />
		);

		expect(getByText('1 item-selected')).toBeInTheDocument();

		const checkbox = container.querySelector('input[type=checkbox]');
		fireEvent.click(checkbox);

		expect(getByText('2 items-selected')).toBeInTheDocument();
	});
});
