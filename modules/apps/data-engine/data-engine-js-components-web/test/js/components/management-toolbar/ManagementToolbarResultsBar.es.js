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

import ManagementToolbarResultsBar from '../../../../src/main/resources/META-INF/resources/js/components/management-toolbar/ManagementToolbarResultsBar.es';
import SearchContextProviderWrapper from '../../SearchContextProviderWrapper.es';
import {FILTERS} from '../../constants.es';

describe('ManagementToolbarResultsBar', () => {
	afterEach(cleanup);

	it('renders empty', () => {
		const {container} = render(<ManagementToolbarResultsBar />, {
			wrapper: SearchContextProviderWrapper,
		});

		expect(container.innerHTML).toBe('');
	});

	it('renders with selected filters', () => {
		const query = {
			filters: {
				active: 'true',
				deploymentTypes: ['productMenu', 'standalone', 'widget'],
			},
		};

		const {container, queryAllByText, queryByText} = render(
			<SearchContextProviderWrapper defaultQuery={query}>
				<ManagementToolbarResultsBar filters={FILTERS} />
			</SearchContextProviderWrapper>
		);

		const clearButton = queryByText('clear-all');
		let filterResults = queryAllByText(/deployment-type|status/i);
		const closeButtons = container.querySelectorAll('button.close');

		expect(clearButton).toBeTruthy();
		expect(filterResults.length).toBe(2);

		expect(filterResults[0].textContent).toBe('status: Deployed');
		expect(filterResults[1].textContent).toBe(
			'deployment-type: Product Menu, Standalone and Widget'
		);

		fireEvent.click(closeButtons[1]);

		filterResults = queryAllByText(/deployment-type|status/i);

		expect(filterResults.length).toBe(1);

		fireEvent.click(clearButton);

		expect(container.innerHTML).toBe('');
	});
});
