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

import '@testing-library/jest-dom/extend-expect';
import {
	act,
	cleanup,
	fireEvent,
	render,
	wait,
	waitForElement,
} from '@testing-library/react';
import React from 'react';

import {StoreAPIContextProvider} from '../../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';
import updateItemConfig from '../../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/updateItemConfig';
import {CollectionGeneralPanel} from '../../../../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/browser/components/page-structure/components/item-configuration-panels/CollectionGeneralPanel';

jest.mock(
	'../../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config',
	() => ({
		config: {
			searchContainerPageMaxDelta: '50',
		},
	})
);

jest.mock(
	'../../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/services/CollectionService',
	() => ({
		getCollectionItemCount: jest.fn(() =>
			Promise.resolve({
				totalNumberOfItems: '32',
			})
		),
	})
);

jest.mock(
	'../../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/updateItemConfig',
	() => jest.fn()
);

const ITEM_CONFIG = {
	collection: {
		classNameId: '22101',
		classPK: '40724',
		title: 'collection1',
	},
	numberOfColumns: 1,
	numberOfItems: 5,
	numberOfItemsPerPage: 5,
	showAllItems: false,
};

const renderComponent = ({config = ITEM_CONFIG, dispatch = () => {}}) => {
	Liferay.Util.sub.mockImplementation((langKey, args) =>
		[langKey, args].join('-')
	);

	return render(
		<StoreAPIContextProvider
			dispatch={dispatch}
			getState={() => ({segmentsExperienceId: '0'})}
		>
			<CollectionGeneralPanel
				item={{
					children: [],
					config,
					itemId: '0',
					parentId: '',
					type: 'collection',
				}}
			/>
		</StoreAPIContextProvider>
	);
};

describe('CollectionGeneralPanel', () => {
	afterEach(() => {
		cleanup();
		updateItemConfig.mockClear();
	});

	it('allows changing the Pagination select', async () => {
		const {getByLabelText} = renderComponent({});
		const input = getByLabelText('pagination');

		await act(async () => {
			fireEvent.change(input, {
				target: {value: 'numeric'},
			});
		});

		expect(updateItemConfig).toHaveBeenCalledWith({
			itemConfig: {
				paginationType: 'numeric',
			},
			itemId: '0',
			segmentsExperienceId: '0',
		});
	});

	it('allows changing the Display All Collection Items checkbox', async () => {
		const {getByLabelText} = renderComponent({
			config: {...ITEM_CONFIG, paginationType: 'numeric'},
		});
		const input = getByLabelText('display-all-collection-items');

		await act(async () => {
			fireEvent.click(input);
		});

		expect(updateItemConfig).toHaveBeenCalledWith({
			itemConfig: expect.objectContaining({
				showAllItems: true,
			}),
			itemId: '0',
			segmentsExperienceId: '0',
		});
	});

	describe('Number of Items Input', () => {
		afterEach(() => {
			cleanup();
			updateItemConfig.mockClear();
		});

		it('allows changing input value', async () => {
			const {getByLabelText} = renderComponent({});
			const input = getByLabelText('maximum-number-of-items');

			await wait(() => {
				fireEvent.change(input, {
					target: {value: '3'},
				});

				fireEvent.blur(input);
			});

			expect(updateItemConfig).toHaveBeenCalledWith({
				itemConfig: {
					numberOfItems: 3,
				},
				itemId: '0',
				segmentsExperienceId: '0',
			});
		});

		it('shows a warning message when the value is 0', async () => {
			const {getByText} = renderComponent({
				config: {
					...ITEM_CONFIG,
					...{numberOfItems: '0', paginationType: 'numeric'},
				},
			});

			expect(
				await waitForElement(() =>
					getByText(
						'you-need-at-least-one-item-to-use-this-configuration'
					)
				)
			).toBeInTheDocument();
		});

		it('shows a warning message when the number of items is bigger than the total items of the collection', async () => {
			const {getByText} = renderComponent({
				config: {
					...ITEM_CONFIG,
					...{numberOfItems: '33', paginationType: 'numeric'},
				},
			});

			expect(
				await waitForElement(() =>
					getByText(
						'the-current-number-of-items-in-this-collection-is-x-32'
					)
				)
			).toBeInTheDocument();
		});
	});

	describe('Number of Items per Page Input', () => {
		afterEach(() => {
			cleanup();
			updateItemConfig.mockClear();
		});

		it('allows changing the input value', async () => {
			const {getByLabelText} = renderComponent({
				config: {...ITEM_CONFIG, paginationType: 'numeric'},
			});
			const input = getByLabelText('maximum-number-of-items-per-page');

			await wait(async () => {
				fireEvent.change(input, {
					target: {value: '2'},
				});

				fireEvent.blur(input);
			});

			expect(updateItemConfig).toHaveBeenCalledWith({
				itemConfig: {
					numberOfItemsPerPage: 2,
				},
				itemId: '0',
				segmentsExperienceId: '0',
			});
		});

		it('shows a warning message in the the value is 0', async () => {
			const {getByText} = renderComponent({
				config: {
					...ITEM_CONFIG,
					...{numberOfItemsPerPage: '0', paginationType: 'numeric'},
				},
			});

			expect(
				await waitForElement(() =>
					getByText(
						'you-need-at-least-one-item-to-use-this-configuration'
					)
				)
			).toBeInTheDocument();
		});

		it('shows a warning message when the number of items per page is bigger than searchContainerPageMaxDelta', async () => {
			const {getByText} = renderComponent({
				config: {
					...ITEM_CONFIG,
					...{numberOfItemsPerPage: '53', paginationType: 'numeric'},
				},
			});

			expect(
				await waitForElement(() =>
					getByText(
						'you-can-only-display-a-maximum-of-x-items-per-page-50'
					)
				)
			).toBeInTheDocument();
		});
	});
});
