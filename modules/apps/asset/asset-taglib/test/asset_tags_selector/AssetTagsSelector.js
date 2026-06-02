/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {useResource} from '@clayui/data-provider';
import {render} from '@testing-library/react';
import React from 'react';

import AssetTagsSelector from '../../src/main/resources/META-INF/resources/js/asset_tags_selector/AssetTagsSelector';

const DEFAULT_PROPS = {
	groupIds: [],
	inputName: 'tags',
	inputValue: '',
	portletURL: '',
	selectedItems: [],
};

let capturedSourceItems;

jest.mock('@clayui/multi-select', () => ({
	__esModule: true,
	default: (props) => {
		capturedSourceItems = props.sourceItems;

		return null;
	},
}));

jest.mock('@clayui/data-provider', () => {
	const originalModule = jest.requireActual('@clayui/data-provider');

	return {
		__esModule: true,
		...originalModule,
		useResource: jest.fn(),
	};
});

const setResource = (resource) =>
	useResource.mockImplementation(() => ({refetch: jest.fn(), resource}));

describe('AssetTagsSelector', () => {
	beforeEach(() => {
		capturedSourceItems = undefined;
	});

	it('Remove duplicates from the list', () => {
		setResource([
			{text: 'apple', value: 'apple'},
			{text: 'apple', value: 'apple'},
			{text: 'banana', value: 'banana'},
			{text: 'banana', value: 'banana'},
			{text: 'carrot', value: 'carrot'},
		]);

		render(<AssetTagsSelector {...DEFAULT_PROPS} />);

		expect(capturedSourceItems).toEqual([
			{label: 'apple', value: 'apple'},
			{label: 'banana', value: 'banana'},
			{label: 'carrot', value: 'carrot'},
		]);
	});

	it('Passes an empty array when the resource is null', () => {
		setResource(null);

		render(<AssetTagsSelector {...DEFAULT_PROPS} />);

		expect(capturedSourceItems).toEqual([]);
	});
});
