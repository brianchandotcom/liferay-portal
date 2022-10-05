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

import {cleanup, render, waitFor} from '@testing-library/react';
import {fetch} from 'frontend-js-web';
import React from 'react';

import '@testing-library/jest-dom/extend-expect';

import VersionsContent from '../../../../src/main/resources/META-INF/resources/js/components/SidebarPanelInfoView/VersionsContent';

jest.mock('frontend-js-web', () => ({
	fetch: jest.fn().mockReturnValue({
		json: jest.fn().mockReturnValue({
			versions: [
				{
					createDate: 'Thu Sep 29 11:22:34 GMT 2022',
					statusLabel: 'Approved',
					statusStyle: 'success',
					userName: 'Test Test',
					version: '2.1',
				},
				{
					changeLog: 'Log text',
					createDate: 'Thu Sep 30 11:22:34 GMT 2022',
					statusLabel: 'Approved',
					statusStyle: 'success',
					userName: 'Test Test',
					version: '2.2',
				},
			],
			viewVersionsURL: 'http://localhost:8080/view-more-versions',
		}),
		ok: true,
	}),
	sub: jest.fn(),
}));

const fetchItemVersionsURL =
	'http://localhost:8080/fetch-manage-collaborators-button-url';

describe('Versions Content component', () => {
	afterEach(() => {
		jest.restoreAllMocks();
		cleanup();
	});

	it('does not call the endpoint on first render and calls it in the following render', async () => {
		const {rerender} = render(
			<VersionsContent
				getItemVersionsURL={fetchItemVersionsURL}
				languageTag="en"
				onError={() => {}}
			/>
		);

		await waitFor(() => {
			expect(fetch).not.toHaveBeenCalledWith(fetchItemVersionsURL);
		});

		rerender(
			<VersionsContent
				getItemVersionsURL={fetchItemVersionsURL}
				languageTag="en"
				onError={() => {}}
			/>
		);

		await waitFor(() => {
			expect(fetch).toHaveBeenCalledWith(fetchItemVersionsURL);
		});
	});

	it('displays the versions list', async () => {
		const {getByText, rerender} = render(
			<VersionsContent
				getItemVersionsURL={fetchItemVersionsURL}
				languageTag="en"
				onError={() => {}}
			/>
		);
		rerender(
			<VersionsContent
				getItemVersionsURL={fetchItemVersionsURL}
				languageTag="en"
				onError={() => {}}
			/>
		);

		await waitFor(() => {
			expect(getByText('2.1', {exact: false})).toBeInTheDocument();
			expect(getByText('no-change-log')).toBeInTheDocument();
			expect(getByText('2.2', {exact: false})).toBeInTheDocument();
			expect(getByText('Log text')).toBeInTheDocument();
		});
	});

	it('displays View more link if viewVersionsURL is provided', async () => {
		const {container, rerender} = render(
			<VersionsContent
				getItemVersionsURL={fetchItemVersionsURL}
				languageTag="en"
				onError={() => {}}
			/>
		);

		rerender(
			<VersionsContent
				getItemVersionsURL={fetchItemVersionsURL}
				languageTag="en"
				onError={() => {}}
			/>
		);

		await waitFor(() => {
			const link = container.querySelector(
				'a.btn-secondary'
			) as HTMLAnchorElement | null;
			expect(link?.textContent).toBe('view-more');
			expect(link?.href).toBe('http://localhost:8080/view-more-versions');
		});
	});

	it('does not display View more link if no viewVersionsURL is provided', async () => {
		jest.mock('frontend-js-web', () => ({
			fetch: jest.fn().mockReturnValue({
				json: jest.fn().mockReturnValue({versions: []}),
				ok: true,
			}),
			sub: jest.fn(),
		}));

		const {container, rerender} = render(
			<VersionsContent
				getItemVersionsURL={fetchItemVersionsURL}
				languageTag="en"
				onError={() => {}}
			/>
		);

		rerender(
			<VersionsContent
				getItemVersionsURL={fetchItemVersionsURL}
				languageTag="en"
				onError={() => {}}
			/>
		);

		await waitFor(() => {
			const link = container.querySelectorAll('a.btn-secondary');
			expect(link.length).toBe(0);
		});
	});
});
