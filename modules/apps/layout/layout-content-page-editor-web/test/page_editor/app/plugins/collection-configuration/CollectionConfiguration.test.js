/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {act, fireEvent, render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import React from 'react';

import CollectionConfiguration from '../../../../../src/main/resources/META-INF/resources/page_editor/plugins/collection-configuration/CollectionConfiguration';

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/page_editor/app/config/index',
	() => ({
		initializeConfig: jest.fn(() => {}),
	})
);

jest.mock(
	'../../../../../src/main/resources/META-INF/resources/page_editor/app/services/CollectionService',
	() => ({
		getCollectionItemCount: jest.fn(() =>
			Promise.resolve({totalNumberOfItems: 4})
		),
	})
);

jest.mock('frontend-js-web', () => ({
	...jest.requireActual('frontend-js-web'),
	fetch: () => Promise.resolve({json: () => ({totalNumberOfItems: 4})}),
}));

const CONFIGURATION_DEFINITION = {
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

const COLLECTION = {
	itemType: 'itemType',
	key: 'collectionKey',
	title: 'Example Collection',
};

const renderComponent = () => {
	return render(
		<CollectionConfiguration
			collection={COLLECTION}
			collectionItemTypeLabel="Item Type"
			collectionLabel="Example Collection"
			configurationDefinition={CONFIGURATION_DEFINITION}
			languageId="en_US"
		/>
	);
};

describe('CollectionConfiguration', () => {
	it('renders', () => {
		renderComponent();

		expect(screen.getByText('Title')).toBeInTheDocument();
	});

	it('shows collection type and title', () => {
		renderComponent();

		expect(screen.getByText('Item Type')).toBeInTheDocument();
		expect(screen.getByText('Example Collection')).toBeInTheDocument();
	});

	it('shows filter information in the toolbar', async () => {
		renderComponent();

		const titleInput = screen.getByLabelText('Title');

		userEvent.type(titleInput, 'This is a test');

		await act(async () => {
			fireEvent.blur(titleInput);
		});

		expect(
			screen.getByText('there-are-x-results-for-x')
		).toBeInTheDocument();
	});

	it('clears the filter when the clear button is clicked', async () => {
		renderComponent();

		const titleInput = screen.getByLabelText('Title');

		userEvent.type(titleInput, 'This is a test');

		await act(async () => {
			fireEvent.blur(titleInput);
		});

		userEvent.click(screen.getByText('clear'));

		expect(
			screen.queryByText('there-are-x-results-for-x')
		).not.toBeInTheDocument();
	});
});
