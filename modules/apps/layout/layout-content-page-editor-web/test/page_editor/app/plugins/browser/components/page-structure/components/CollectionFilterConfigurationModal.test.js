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
	getByText,
	render,
} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import {VIEWPORT_SIZES} from '../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/viewportSizes';
import {StoreContextProvider} from '../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/StoreContext';
import CollectionFilterConfigurationModal from '../../../../../../../../src/main/resources/META-INF/resources/page_editor/plugins/browser/components/page-structure/components/CollectionFilterConfigurationModal';

jest.mock(
	'../../../../../../../../src/main/resources/META-INF/resources/page_editor/app/services/CollectionService',
	() => ({
		getCollectionItemCount: jest.fn(() =>
			Promise.resolve({totalNumberOfItems: 4})
		),
	})
);

const fakeObserver = {
	dispatch: () => {},
	mutation: [true, true],
};

const COLLECTION_CONFIGURATION = {
	fieldSets: [
		{
			fields: [
				{
					dataType: 'string',
					label: 'Tag',
					name: 'assetTagNames',
					type: 'select',
					typeOptions: {
						multiSelect: true,
						validValues: [
							{label: 'tag1', value: 'tag1'},
							{label: 'tag2', value: 'tag2'},
						],
					},
				},
				{
					dataType: 'string',
					label: 'Title',
					name: 'title',
					type: 'text',
				},
			],
		},
	],
};

const COLLECTION_KEY = 'COLLECTION_KEY';

const handleConfigurationChanged = jest.fn();

const renderComponent = () => {
	Liferay.Util.sub.mockImplementation((langKey, ...args) =>
		[langKey, ...args].join('-')
	);

	const result = render(
		<StoreContextProvider
			initialState={{
				languageId: 'en_US',
				pageContents: [
					{
						classPK: COLLECTION_KEY,
						subtype: 'Web Content Article - Basic Web Content',
					},
				],
				selectedViewportSize: VIEWPORT_SIZES.desktop,
			}}
		>
			<CollectionFilterConfigurationModal
				collectionConfiguration={COLLECTION_CONFIGURATION}
				handleConfigurationChanged={handleConfigurationChanged}
				itemConfig={{
					collection: {
						classNameId: '1',
						classPK: '1',
						key: COLLECTION_KEY,
					},
				}}
				observer={fakeObserver}
				onClose={() => {}}
			/>
		</StoreContextProvider>
	);

	return result;
};

describe('CollectionFilterConfigurationModal', () => {
	beforeEach(() => {
		cleanup();
		handleConfigurationChanged.mockClear();
	});

	it('renders', () => {
		const {getByText} = renderComponent();

		expect(getByText('Title')).toBeInTheDocument();
	});

	it('shows collection type and subtype', () => {
		const {getByText} = renderComponent();

		expect(getByText('Web Content Article')).toBeInTheDocument();
		expect(getByText('Basic Web Content')).toBeInTheDocument();
	});

	it('updates configuration when the user saves', async () => {
		const {getByLabelText} = renderComponent();

		const titleInput = getByLabelText('Title');

		userEvent.type(titleInput, 'This is a test');

		await act(async () => {
			fireEvent.blur(titleInput);
		});

		userEvent.click(getByLabelText('Tag'));

		// Hackily work around:
		//
		//      "TypeError: Cannot read property '_defaultView' of undefined"
		//
		// Caused by: https://github.com/jsdom/jsdom/issues/2499

		document.activeElement.blur = () => {};

		userEvent.click(getByLabelText('tag1'));

		act(() => {
			userEvent.click(getByText(document.body, 'apply'));
		});

		expect(handleConfigurationChanged).toBeCalledWith(
			expect.objectContaining({
				collection: expect.objectContaining({
					config: {assetTagNames: ['tag1'], title: 'This is a test'},
				}),
			})
		);
	});

	it('shows filter information in the toolbar', async () => {
		const {getByLabelText, getByText} = renderComponent();

		const titleInput = getByLabelText('Title');

		userEvent.type(titleInput, 'This is a test');

		await act(async () => {
			fireEvent.blur(titleInput);
		});

		expect(
			getByText('there-are-x-results-for-x-4-This is a test')
		).toBeInTheDocument();
	});

	it('clears the filter when the clear button is clicked', async () => {
		const {getByLabelText} = renderComponent();

		const titleInput = getByLabelText('Title');

		userEvent.type(titleInput, 'This is a test');

		await act(async () => {
			fireEvent.blur(titleInput);
		});

		userEvent.click(getByText(document.body, 'clear'));

		userEvent.click(getByText(document.body, 'apply'));

		expect(handleConfigurationChanged).toBeCalledWith(
			expect.objectContaining({
				collection: expect.objectContaining({
					config: {},
				}),
			})
		);
	});
});
