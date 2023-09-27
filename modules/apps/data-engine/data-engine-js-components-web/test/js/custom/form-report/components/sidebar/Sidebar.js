/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {waitForElementToBeRemoved} from '@testing-library/dom';
import {cleanup, render} from '@testing-library/react';
import React from 'react';

import Sidebar from '../../../../../../src/main/resources/META-INF/resources/js/custom/form-report/components/sidebar/Sidebar';
import SidebarContextProviderWrapper from '../../__utils__/SidebarContextProviderWrapper';

describe('Sidebar', () => {
	afterEach(cleanup);

	it('renders the list of itens', async () => {
		fetch.mockResponseOnce(
			JSON.stringify([
				'name1',
				'name2',
				'name3',
				'name4',
				'name5',
				'name6',
			])
		);
		const {container} = render(
			<SidebarContextProviderWrapper>
				<Sidebar />
			</SidebarContextProviderWrapper>
		);

		expect(container.querySelector('span.loading-animation')).toBeTruthy();

		await waitForElementToBeRemoved(() =>
			document.querySelector('span.loading-animation')
		);

		expect(container.querySelectorAll('.entries-list > li').length).toBe(6);
	});

	it('renders the header with the title and the number of itens', async () => {
		fetch.mockResponseOnce(
			JSON.stringify([
				'name1',
				'name2',
				'name3',
				'name4',
				'name5',
				'name6',
			])
		);
		const {queryByText} = render(
			<SidebarContextProviderWrapper>
				<Sidebar />
			</SidebarContextProviderWrapper>
		);

		const description = queryByText('6 entries');
		const title = queryByText('Text');

		expect(description).toBeTruthy();
		expect(title).toBeTruthy();
	});
});
