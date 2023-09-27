/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import ManagementToolbarResultsBar from '../../../../src/main/resources/META-INF/resources/js/components/management-toolbar/ManagementToolbarResultsBar';
import SearchContextProviderWrapper from '../../SearchContextProviderWrapper';
import {FILTERS} from '../../constants';

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
