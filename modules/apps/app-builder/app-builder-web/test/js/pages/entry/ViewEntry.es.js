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
import {waitForElementToBeRemoved} from '@testing-library/dom';
import {render} from '@testing-library/react';
import React from 'react';

import ViewEntry from '../../../../src/main/resources/META-INF/resources/js/pages/entry/ViewEntry.es';
import AppContextProviderWrapper from '../../AppContextProviderWrapper.es';
import PermissionsContextProviderWrapper from '../../PermissionsContextProviderWrapper.es';
import {ENTRY} from '../../constants.es';

const context = {
	appId: 1,
	dataDefinitionId: 1,
	dataLayoutId: 1,
	showFormView: true,
};

const mockToast = jest.fn();

jest.mock('app-builder-web/js/utils/toast.es', () => ({
	__esModule: true,
	errorToast: () => mockToast(),
}));

describe('ViewEntry', () => {
	it('renders', async () => {
		fetch.mockResponseOnce(JSON.stringify(ENTRY.DATA_DEFINITION));
		fetch.mockResponseOnce(JSON.stringify(ENTRY.DATA_LAYOUT));
		fetch.mockResponseOnce(JSON.stringify(ENTRY.DATA_RECORDS(1)));

		const {container} = render(
			<AppContextProviderWrapper appContext={context}>
				<ViewEntry match={{params: {entryIndex: 1}}} />
			</AppContextProviderWrapper>,
			{wrapper: PermissionsContextProviderWrapper}
		);

		await waitForElementToBeRemoved(() =>
			document.querySelector('span.loading-animation')
		);

		const infoItems = container.querySelectorAll('.info-item');
		const title = container.querySelector('.data-record-field-preview');

		expect(infoItems.length).toBe(1);
		expect(infoItems[0]).toHaveTextContent('status: approved');
		expect(title).toHaveTextContent('Name Test 0');
	});

	it('shows error toast when an error happens while trying to get Data Layout and Data Records', async () => {
		fetch.mockResponseOnce(JSON.stringify(ENTRY.DATA_DEFINITION));
		fetch.mockRejectedValue({});

		render(
			<AppContextProviderWrapper appContext={context}>
				<ViewEntry match={{params: {entryIndex: 1}}} />
			</AppContextProviderWrapper>,
			{wrapper: PermissionsContextProviderWrapper}
		);

		await waitForElementToBeRemoved(() =>
			document.querySelector('span.loading-animation')
		);

		expect(mockToast).toHaveBeenCalledTimes(2);
	});
});
