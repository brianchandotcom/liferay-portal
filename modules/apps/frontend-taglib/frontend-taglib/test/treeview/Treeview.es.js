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
import React from 'react';

import Treeview from '../../src/main/resources/META-INF/resources/treeview/Treeview';

const nodes = [
	{
		children: [
			{
				id: '1.1',
				name: 'Pablictor'
			},
			{
				children: [
					{
						id: '1.2.1',
						name: 'Eudaldo'
					}
				],
				id: '1.2',
				name: 'Pabla'
			}
		],
		id: '1',
		name: 'Sandro'
	},
	{
		id: '2',
		name: 'Victor'
	}
];

describe('Treeview', () => {
	beforeEach(cleanup);

	it('renders', () => {
		const treeView = render(<Treeview nodes={[]} />);
		expect(treeView);
	});

	it('renders a list of nodes', () => {
		const {getByText} = render(<Treeview nodes={nodes} />);

		expect(getByText('Sandro'));
		expect(getByText('Victor'));
	});

	it('calls the onSelectedNode callback if nodes are selected', () => {
		const onSelectedNodesChange = jest.fn();
		const {getByText} = render(
			<Treeview
				nodes={nodes}
				onSelectedNodesChange={onSelectedNodesChange}
			/>
		);

		fireEvent.click(getByText('Sandro'));
		fireEvent.click(getByText('Victor'));

		expect(onSelectedNodesChange).toBeCalledWith(nodes);
	});

	it('marks the initialSelectedNodeIds as selected', () => {
		const {getByLabelText} = render(
			<Treeview initialSelectedNodeIds={['1']} nodes={nodes} />
		);

		expect(getByLabelText('Sandro').checked).toBe(true);
	});

	it('expands nodes if initialSelectedNodeIds are children', () => {
		const {getByLabelText} = render(
			<Treeview initialSelectedNodeIds={['1.2.1']} nodes={nodes} />
		);

		expect(getByLabelText('Eudaldo').checked).toBe(true);
	});

	it('only expands necessary initial nodes', () => {
		const {getByLabelText, queryByLabelText} = render(
			<Treeview initialSelectedNodeIds={['1.1']} nodes={nodes} />
		);

		expect(getByLabelText('Pablictor').checked).toBe(true);
		expect(queryByLabelText('Eudaldo')).toBe(null);
	});

	it('allow selecting several nodes by default', async () => {
		const {getByLabelText} = render(<Treeview nodes={nodes} />);

		fireEvent.click(getByLabelText('Sandro'));
		fireEvent.click(getByLabelText('Victor'));

		expect(getByLabelText('Sandro').checked).toBe(true);
		expect(getByLabelText('Victor').checked).toBe(true);
	});

	it('allow selecting only one node if multiSelection is disabled', async () => {
		const {getByLabelText} = render(
			<Treeview multiSelection={false} nodes={nodes} />
		);

		fireEvent.click(getByLabelText('Sandro'));
		fireEvent.click(getByLabelText('Victor'));

		expect(getByLabelText('Sandro').checked).toBe(false);
		expect(getByLabelText('Victor').checked).toBe(true);
	});

	it('allows expanding nodes on click', () => {
		const {getByLabelText, queryByLabelText} = render(
			<Treeview nodes={nodes} />
		);

		fireEvent.click(getByLabelText('Expand Sandro'));

		expect(queryByLabelText('Pablictor'));
		expect(queryByLabelText('Pabla'));
	});

	it('allows collapsing nodes on click', () => {
		const {getByLabelText, queryByLabelText} = render(
			<Treeview initialSelectedNodeIds={['1.1']} nodes={nodes} />
		);

		fireEvent.click(getByLabelText('Collapse Sandro'));

		expect(queryByLabelText('Pablictor')).toBe(null);
		expect(queryByLabelText('Pabla')).toBe(null);
	});

	it('allows specifying a custom NodeComponent', () => {
		const node = {
			icon: 'cog',
			id: '1',
			name: 'Sandro',
			size: 'sm'
		};

		const {getByText} = render(
			<Treeview
				NodeComponent={({node, onNodeSelected, selectedNodeIds}) => (
					<button
						data-icon={node.icon}
						data-selected={selectedNodeIds.includes(node.id)}
						data-size={node.size}
						onClick={() => onNodeSelected(node.id)}
						type="button"
					>
						Super {node.name}
					</button>
				)}
				nodes={[node]}
			/>
		);

		const button = getByText('Super Sandro');

		fireEvent.click(button);

		expect(button.dataset.selected).toBe('true');
		expect(button.dataset.icon).toBe('cog');
		expect(button.dataset.size).toBe('sm');
	});
});
