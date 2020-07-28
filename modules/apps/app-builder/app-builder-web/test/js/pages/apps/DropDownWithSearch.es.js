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

import ClayButton from '@clayui/button';
import {cleanup, fireEvent, render} from '@testing-library/react';
import React, {useState} from 'react';

import EmptyState from '../../../../src/main/resources/META-INF/resources/js/components/table/EmptyState.es';
import DropDownWithSearch from '../../../../src/main/resources/META-INF/resources/js/pages/apps/DropDownWithSearch.es';

import '@testing-library/jest-dom/extend-expect';

const ITEMS = (size) => {
	const items = [];

	for (let i = 0; i < size; i++) {
		items.push({id: i, name: `object ${i}`});
	}

	return items;
};
const doFetch = jest.fn();
let onSelect = jest.fn();

const DropDownContainer = () => {
	const [buttonName, setButtonName] = useState('Button');

	onSelect = jest.fn((newName) => {
		setButtonName(newName.name);
	});

	return (
		<DropDownWithSearch
			stateProps={{
				emptyProps: {
					emptyState: () => <EmptyState />,
				},
				errorProps: {
					children: (
						<ClayButton displayType="link" onClick={doFetch}>
							{'retry'}
						</ClayButton>
					),
					label: 'unable-to-retrieve-the-objects',
				},
				loading: {
					label: 'retrieving-all-objects',
				},
			}}
			trigger={<ClayButton>{buttonName}</ClayButton>}
		>
			<DropDownWithSearch.Items
				emptyResultMessage="empty message"
				items={ITEMS(10)}
				onSelect={onSelect}
			/>
		</DropDownWithSearch>
	);
};

describe('DropDownWithSearch', () => {
	let asFragment, container, getByPlaceholderText, getByText;

	beforeAll(() => {
		const component = render(<DropDownContainer />);

		asFragment = component.asFragment;
		container = component.container;
		getByPlaceholderText = component.getByPlaceholderText;
		getByText = component.getByText;
	});

	it('renders', () => {
		expect(asFragment()).toMatchSnapshot();
	});

	it('renders with 10 items and a trigger button', () => {
		const button = getByText('Button');

		fireEvent.click(button);

		const dropDownMenu = document.querySelector('.select-dropdown-menu');

		expect(dropDownMenu.children[1].children.length).toEqual(10);
	});

	it('selects an option and triggers onSelect after clicking in it', () => {
		const search = getByPlaceholderText('search');
		const dropDownMenu = document.querySelector('.select-dropdown-menu');

		expect(container.children[0].children[0]).toHaveTextContent('Button');

		fireEvent.change(search, {target: {value: 'object 9'}});

		fireEvent.click(dropDownMenu.children[1].children[0].children[0]);

		expect(container.children[0].children[0]).toHaveTextContent('object 9');
	});

	it('shows loading state while fetching data', () => {
		cleanup();

		render(
			<DropDownWithSearch
				isLoading={true}
				stateProps={{
					emptyProps: {
						emptyState: () => <EmptyState />,
					},
					errorProps: {
						children: (
							<ClayButton displayType="link" onClick={doFetch}>
								{'retry'}
							</ClayButton>
						),
						label: 'unable-to-retrieve-the-objects',
					},
					loading: {
						label: 'retrieving-all-objects',
					},
				}}
				trigger={<ClayButton>Button</ClayButton>}
			>
				<DropDownWithSearch.Items
					emptyResultMessage="empty message"
					onSelect={onSelect}
				/>
			</DropDownWithSearch>
		);

		expect(
			document.querySelector('.loading-state-dropdown-menu')
		).toBeTruthy();
	});

	it('shows error state when fails the fetch', () => {
		cleanup();

		render(
			<DropDownWithSearch
				error={true}
				stateProps={{
					emptyProps: {
						emptyState: () => <EmptyState />,
					},
					errorProps: {
						children: (
							<ClayButton displayType="link" onClick={doFetch}>
								{'retry'}
							</ClayButton>
						),
						label: 'unable-to-retrieve-the-objects',
					},
					loading: {
						label: 'retrieving-all-objects',
					},
				}}
				trigger={<ClayButton>Button</ClayButton>}
			>
				<DropDownWithSearch.Items
					emptyResultMessage="empty message"
					onSelect={onSelect}
				/>
			</DropDownWithSearch>
		);

		expect(
			document.querySelector('.error-state-dropdown-menu')
		).toBeTruthy();
	});

	it('shows empty state when has no items', () => {
		cleanup();

		render(
			<DropDownWithSearch
				isEmpty={true}
				stateProps={{
					emptyProps: {
						emptyState: () => <EmptyState />,
					},
					errorProps: {
						children: (
							<ClayButton displayType="link" onClick={doFetch}>
								{'retry'}
							</ClayButton>
						),
						label: 'unable-to-retrieve-the-objects',
					},
					loading: {
						label: 'retrieving-all-objects',
					},
				}}
				trigger={<ClayButton>Button</ClayButton>}
			>
				<DropDownWithSearch.Items
					emptyResultMessage="empty message"
					onSelect={onSelect}
				/>
			</DropDownWithSearch>
		);

		expect(
			document.querySelector('.empty-state-dropdown-menu')
		).toBeTruthy();

		const search = getByPlaceholderText('search');

		expect(search).toBeDisabled();
	});
});
