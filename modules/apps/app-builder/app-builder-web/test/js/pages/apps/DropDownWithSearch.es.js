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
import React from 'react';

import EmptyState from '../../../../src/main/resources/META-INF/resources/js/components/table/EmptyState.es';
import DropDownWithSearch from '../../../../src/main/resources/META-INF/resources/js/pages/apps/DropDownWithSearch.es';

const ITEMS = (size) => {
	const items = [];

	for (let i = 0; i < size; i++) {
		items.push({id: i, name: {'en-US': `object ${i}`}});
	}

	return items;
};
const onSelect = jest.fn();
const doFetch = jest.fn();

describe('DropDownWithSearch', () => {
	let asFragment, getByPlaceholderText, getByText;

	beforeAll(() => {
		const component = render(
			<DropDownWithSearch
				items={ITEMS(10)}
				onSelect={onSelect}
				stateProps={{
					emptyProps: {
						emptyState: () => <EmptyState />,
					},
					errorProps: {
						children: (
							<ClayButton displayType="link" onClick={doFetch}>
								{Liferay.Language.get('retry')}
							</ClayButton>
						),
						label: Liferay.Language.get(
							'unable-to-retrieve-objects'
						),
					},
					loading: {
						label: Liferay.Language.get('retrieving-all-objects'),
					},
				}}
				trigger={<ClayButton>Button</ClayButton>}
			/>
		);

		asFragment = component.asFragment;
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

	it('selects a option and triggers onSelect after clicking in it', () => {
		const search = getByPlaceholderText('search');
		const dropDownMenu = document.querySelector('.select-dropdown-menu');

		fireEvent.change(search, {target: {value: 'object 9'}});

		fireEvent.click(dropDownMenu.children[1].children[0].children[0]);

		expect(onSelect).toHaveBeenCalled();
	});

	it('shows loading state while fetching data', () => {
		cleanup();

		render(
			<DropDownWithSearch
				error={false}
				isLoading={true}
				onSelect={onSelect}
				stateProps={{
					emptyProps: {
						emptyState: () => <EmptyState />,
					},
					errorProps: {
						children: (
							<ClayButton displayType="link" onClick={doFetch}>
								{Liferay.Language.get('retry')}
							</ClayButton>
						),
						label: Liferay.Language.get(
							'unable-to-retrieve-objects'
						),
					},
					loading: {
						label: Liferay.Language.get('retrieving-all-objects'),
					},
				}}
				trigger={<ClayButton>Button</ClayButton>}
			/>
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
				isLoading={false}
				onSelect={onSelect}
				stateProps={{
					emptyProps: {
						emptyState: () => <EmptyState />,
					},
					errorProps: {
						children: (
							<ClayButton displayType="link" onClick={doFetch}>
								{Liferay.Language.get('retry')}
							</ClayButton>
						),
						label: Liferay.Language.get(
							'unable-to-retrieve-objects'
						),
					},
					loading: {
						label: Liferay.Language.get('retrieving-all-objects'),
					},
				}}
				trigger={<ClayButton>Button</ClayButton>}
			/>
		);

		expect(
			document.querySelector('.error-state-dropdown-menu')
		).toBeTruthy();
	});
});
