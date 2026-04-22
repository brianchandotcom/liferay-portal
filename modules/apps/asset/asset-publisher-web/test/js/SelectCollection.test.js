/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {fireEvent, render, screen} from '@testing-library/react';
import {openSelectionModal} from 'frontend-js-components-web';
import React from 'react';

import SelectCollection from '../../src/main/resources/META-INF/resources/js/SelectCollection';

jest.mock('frontend-js-components-web');

const DEFAULT_PROPS = {
	addAssetListEntryURL: 'addAssetListEntryURL',
	assetListEntryId: 0,
	clearButtonEnabled: false,
	defaultTitle: 'defaultTitle',
	infoListProviderKey: '',
	portletNamespace: 'portletNamespace',
	selectEventName: 'selectEventName',
	title: 'title',
	url: 'url',
};

const renderComponent = (props = {}) =>
	render(<SelectCollection {...DEFAULT_PROPS} {...props} />);

describe('SelectCollection', () => {
	afterEach(() => {
		openSelectionModal.mockReset();
	});

	it('renders the add collection button', () => {
		renderComponent();

		expect(
			screen.getByRole('button', {name: 'add-collection'})
		).toBeInTheDocument();
	});

	it('opens the selection modal with the addAssetListEntryURL when the add collection button is clicked', () => {
		renderComponent();

		fireEvent.click(screen.getByRole('button', {name: 'add-collection'}));

		expect(openSelectionModal).toHaveBeenCalledWith(
			expect.objectContaining({
				title: 'add-collection',
				url: DEFAULT_PROPS.addAssetListEntryURL,
			})
		);
	});
});
