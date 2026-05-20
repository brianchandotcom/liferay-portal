/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {cleanup, render, screen} from '@testing-library/react';
import React from 'react';

const mockGetFDSInternalRenderer = jest.fn();

jest.mock('@liferay/frontend-data-set-web', () => ({
	getFDSInternalRenderer: (...args: any[]) =>
		mockGetFDSInternalRenderer(...args),
}));

function loadFDSItemTitle() {
	let component: any;

	jest.isolateModules(() => {
		component =
			require('../../../src/main/resources/META-INF/resources/js/components/FDSItemTitle').default;
	});

	return component;
}

describe('FDSItemTitle', () => {
	beforeEach(() => {
		mockGetFDSInternalRenderer.mockReset();
	});

	afterEach(() => {
		cleanup();
	});

	it('falls back to rendering the value when the FDS renderer is missing', () => {
		mockGetFDSInternalRenderer.mockReturnValue(undefined);

		const FDSItemTitle = loadFDSItemTitle();

		render(
			<FDSItemTitle
				actions={[]}
				itemData={null}
				itemId={1}
				value="Fallback Value"
			/>
		);

		expect(screen.queryByRole('link')).not.toBeInTheDocument();
		expect(screen.getByText('Fallback Value')).toBeInTheDocument();
	});

	it('queries the FDS registry for the actionLink renderer', () => {
		mockGetFDSInternalRenderer.mockReturnValue(undefined);

		loadFDSItemTitle();

		expect(mockGetFDSInternalRenderer).toHaveBeenCalledWith('actionLink');
	});

	it('renders the FDS action link renderer when available with actionId=view', () => {
		const ActionLinkRenderer = ({
			actions,
			itemData,
			itemId,
			options,
			value,
		}: any) => (
			<a
				data-action-count={actions.length}
				data-action-id={options.actionId}
				data-item-data={JSON.stringify(itemData)}
				data-item-id={itemId}
				href="#"
			>
				{String(value)}
			</a>
		);

		mockGetFDSInternalRenderer.mockReturnValue({
			component: ActionLinkRenderer,
		});

		const FDSItemTitle = loadFDSItemTitle();

		render(
			<FDSItemTitle
				actions={[{id: 'view'}, {id: 'delete'}]}
				itemData={{title: 'My Template'}}
				itemId={42}
				value="My Template"
			/>
		);

		const link = screen.getByRole('link');

		expect(link).toHaveTextContent('My Template');
		expect(link.getAttribute('data-action-id')).toBe('view');
		expect(link.getAttribute('data-action-count')).toBe('2');
		expect(link.getAttribute('data-item-id')).toBe('42');
		expect(link.getAttribute('data-item-data')).toBe(
			JSON.stringify({title: 'My Template'})
		);
	});
});
