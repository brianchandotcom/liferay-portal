/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {fireEvent, render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React, {useState} from 'react';

import {RowBuilder} from '../../../src/main/resources/META-INF/resources/js/components/row_builder/RowBuilder';
import {ScreenReaderAnnouncerContext} from '../../../src/main/resources/META-INF/resources/js/contexts/ScreenReaderContext';

globalThis.Liferay = {
	Language: {
		get: jest.fn((key) => key),
	},
};

function TestRowBuilder({
	canDelete,
	createItem = jest.fn(),
	initialItems = [],
	labels = {
		add: 'Add item',
		addedAnnouncement: 'Item added',
		delete: 'Delete item',
		deletedAnnouncement: 'Item deleted',
		list: 'Test items',
	},
	onSendMessage = jest.fn(),
	renderItem = ({item, onChange}) => (
		<input
			aria-label={`Input ${item.id}`}
			onChange={(event) => onChange({...item, name: event.target.value})}
			value={item.name}
		/>
	),
}) {
	const [items, setItems] = useState(initialItems);
	const deleteAriaLabel =
		labels.deleteAriaLabel ||
		((_item, index) => `Delete item ${index + 1}`);
	const itemAriaLabel = labels.itemAriaLabel || ((item) => `Row ${item.id}`);

	return (
		<ScreenReaderAnnouncerContext.Provider
			value={{sendMessage: onSendMessage}}
		>
			<RowBuilder
				canDelete={canDelete}
				createItem={createItem}
				items={items}
				labels={{
					...labels,
					deleteAriaLabel,
					itemAriaLabel,
				}}
				renderItem={renderItem}
				setItems={setItems}
			/>
		</ScreenReaderAnnouncerContext.Provider>
	);
}

describe('RowBuilder', () => {
	it('adds a new item and announces it', async () => {
		const createItem = jest.fn(() => ({id: 'item-2', name: ''}));
		const onSendMessage = jest.fn();

		render(
			<TestRowBuilder
				createItem={createItem}
				initialItems={[{id: 'item-1', name: 'First'}]}
				onSendMessage={onSendMessage}
			/>
		);

		await userEvent.click(screen.getByRole('button', {name: 'Add item'}));

		expect(createItem).toHaveBeenCalledTimes(1);
		expect(document.activeElement).toBe(
			screen.getByRole('menuitem', {name: 'Row item-2'})
		);
		expect(onSendMessage).toHaveBeenCalledWith('Item added');
	});

	it('deletes an item and announces it', async () => {
		const onSendMessage = jest.fn();

		render(
			<TestRowBuilder
				initialItems={[
					{id: 'item-1', name: 'First'},
					{id: 'item-2', name: 'Second'},
				]}
				onSendMessage={onSendMessage}
			/>
		);

		await userEvent.click(screen.getByLabelText('Delete item 1'));

		expect(screen.queryByRole('menuitem', {name: 'Row item-1'})).toBeNull();
		expect(document.activeElement).toBe(
			screen.getByRole('menuitem', {name: 'Row item-2'})
		);
		expect(onSendMessage).toHaveBeenCalledWith('Item deleted');
	});

	it('replaces the last item with a new empty one when deleting it', async () => {
		const createItem = jest.fn(() => ({id: 'item-2', name: ''}));

		render(
			<TestRowBuilder
				createItem={createItem}
				initialItems={[{id: 'item-1', name: 'Only item'}]}
			/>
		);

		await userEvent.click(screen.getByLabelText('Delete item 1'));

		expect(createItem).toHaveBeenCalledTimes(1);
		expect(screen.queryByRole('menuitem', {name: 'Row item-1'})).toBeNull();
		expect(
			screen.getByRole('menuitem', {name: 'Row item-2'})
		).toBeInTheDocument();
	});

	it('does not render delete button when canDelete returns false', () => {
		render(
			<TestRowBuilder
				canDelete={() => false}
				initialItems={[{id: 'item-1', name: 'First'}]}
			/>
		);

		expect(screen.queryByLabelText('Delete item 1')).toBeNull();
	});

	it('focuses the first interactive element in the row when pressing Enter', () => {
		render(
			<TestRowBuilder initialItems={[{id: 'item-1', name: 'First'}]} />
		);

		const row = screen.getByRole('menuitem', {name: 'Row item-1'});

		row.focus();

		fireEvent.keyDown(row, {key: 'Enter'});

		expect(document.activeElement).toBe(
			screen.getByLabelText('Input item-1')
		);
	});

	it('moves focus between rows with arrow keys', () => {
		render(
			<TestRowBuilder
				initialItems={[
					{id: 'item-1', name: 'First'},
					{id: 'item-2', name: 'Second'},
				]}
			/>
		);

		const firstRow = screen.getByRole('menuitem', {name: 'Row item-1'});
		const secondRow = screen.getByRole('menuitem', {name: 'Row item-2'});

		firstRow.focus();

		fireEvent.keyDown(firstRow, {key: 'ArrowDown'});

		expect(document.activeElement).toBe(secondRow);

		fireEvent.keyDown(secondRow, {key: 'ArrowUp'});

		expect(document.activeElement).toBe(firstRow);
	});
});
