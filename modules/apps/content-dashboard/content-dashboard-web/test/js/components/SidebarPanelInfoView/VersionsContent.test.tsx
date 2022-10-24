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

import {cleanup, fireEvent, render, waitFor} from '@testing-library/react';
import {fetch} from 'frontend-js-web';
import React from 'react';

import '@testing-library/jest-dom/extend-expect';

import VersionsContent from '../../../../src/main/resources/META-INF/resources/js/components/SidebarPanelInfoView/VersionsContent';

jest.mock('frontend-js-web', () => ({
	fetch: jest.fn().mockReturnValue({
		json: jest.fn().mockReturnValue({
			versions: [
				{
					actions: [
						{
							icon: 'time',
							label: 'Expire',
							name: 'expire',
							url: 'http://localhost:8080/expire-url',
						},
					],
					createDate: 'Thu Sep 29 11:22:34 GMT 2022',
					statusLabel: 'Approved',
					statusStyle: 'success',
					userName: 'Test Test',
					version: '2.1',
				},
				{
					actions: [
						{
							icon: 'time',
							label: 'Expire',
							name: 'expire',
							url: 'http://localhost:8080/expire-url',
						},
					],
					changeLog: 'Log text',
					createDate: 'Thu Sep 30 11:22:34 GMT 2022',
					statusLabel: 'Approved',
					statusStyle: 'success',
					userName: 'Test Test',
					version: '2.2',
				},
				{
					actions: [],
					changeLog: 'Another log text',
					createDate: 'Thu Sep 30 12:22:34 GMT 2022',
					statusLabel: 'Expired',
					statusStyle: 'danger',
					userName: 'Test Test',
					version: '2.3',
				},
				{
					actions: [
						{
							icon: 'view',
							label: 'View',
							name: 'view',
							url: 'http://localhost:8080/view-url',
						},
					],
					changeLog: 'Another log text',
					createDate: 'Thu Sep 30 14:22:34 GMT 2022',
					statusLabel: 'Approved',
					statusStyle: 'success',
					userName: 'Test Test',
					version: '2.4',
				},
			],
			viewVersionsURL: 'http://localhost:8080/view-more-versions',
		}),
		ok: true,
	}),
	sub: jest.fn(),
}));

window.submitForm = jest.fn();

const fetchItemVersionsURL =
	'http://localhost:8080/fetch-manage-collaborators-button-url';

const _getComponent = () => {
	return (
		<VersionsContent
			getItemVersionsURL={fetchItemVersionsURL}
			languageTag="en"
			onError={() => {}}
		/>
	);
};

Liferay.FeatureFlags['LPS-162924'] = true;

describe('Versions Content component', () => {
	afterEach(() => {
		jest.restoreAllMocks();
		cleanup();
	});

	it('does not call the endpoint on first render and calls it in the following render', async () => {
		const {rerender} = render(_getComponent());

		await waitFor(() => {
			expect(fetch).not.toHaveBeenCalledWith(fetchItemVersionsURL);
		});

		rerender(_getComponent());

		await waitFor(() => {
			expect(fetch).toHaveBeenCalledWith(fetchItemVersionsURL);
		});
	});

	it('displays the versions list', async () => {
		const {getByText, rerender} = render(_getComponent());
		rerender(_getComponent());

		await waitFor(() => {
			expect(getByText('2.1', {exact: false})).toBeInTheDocument();
			expect(getByText('no-change-log')).toBeInTheDocument();
			expect(getByText('2.2', {exact: false})).toBeInTheDocument();
			expect(getByText('Log text')).toBeInTheDocument();
		});
	});

	it('displays View more link if viewVersionsURL is provided', async () => {
		const {container, rerender} = render(_getComponent());

		rerender(_getComponent());

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

		const {container, rerender} = render(_getComponent());

		rerender(_getComponent());

		await waitFor(() => {
			const link = container.querySelectorAll('a.btn-secondary');
			expect(link.length).toBe(0);
		});
	});
});

describe('Versions actions', () => {
	afterEach(() => {
		jest.restoreAllMocks();
		cleanup();
	});

	it('are rendered within each version', async () => {
		const {getAllByText, getAllByTitle, rerender} = render(_getComponent());
		rerender(_getComponent());

		await waitFor(() => {
			const expireActionButtons: HTMLElement[] = getAllByText('Expire');

			expect(getAllByTitle('actions').length).toBe(3);
			expect(expireActionButtons.length).toBe(2);
			expect(getAllByText('View').length).toBe(1);

			const firstExpireButton: HTMLButtonElement = expireActionButtons[0].closest(
				'button'
			)!;

			fireEvent(
				firstExpireButton,
				new MouseEvent('click', {
					bubbles: true,
					cancelable: true,
				})
			);

			expect(window.submitForm).toHaveBeenCalledWith(
				undefined,
				'http://localhost:8080/expire-url'
			);
		});
	});
});
