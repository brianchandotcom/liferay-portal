/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {act, render, screen} from '@testing-library/react';
import React from 'react';

import {StoreContextProvider} from '../../../src/main/resources/META-INF/resources/js/context/StoreContext';

import '@testing-library/jest-dom/extend-expect';

import PageAudit from '../../../src/main/resources/META-INF/resources/js/components/PageAudit';
import {ConstantsContextProvider} from '../../../src/main/resources/META-INF/resources/js/context/ConstantsContext';

jest.mock('frontend-js-web', () => ({
	...jest.requireActual('frontend-js-web'),
	fetch: () => Promise.resolve({json: () => ({})}),
}));

const renderPageAudit = ({panelIsOpen = true, selectedItem} = {}) =>
	render(
		<StoreContextProvider
			value={{
				selectedItem,
			}}
		>
			<ConstantsContextProvider
				constants={{
					layoutReportsDataURL: 'url',
				}}
			>
				<PageAudit panelIsOpen={panelIsOpen} />
			</ConstantsContextProvider>
		</StoreContextProvider>
	);

describe('PageAudit', () => {
	it('does not render tabs or experience selector if there is an issue selected', async () => {
		const selectedItem = {
			description: 'This is a description',
			failingElements: [],
			key: 'key',
			tips: 'Tips',
			title: 'This is a title',
			total: '1',
		};

		await act(async () => renderPageAudit({selectedItem}));

		expect(
			screen.queryByText('Experience Default')
		).not.toBeInTheDocument();

		expect(screen.queryByText('First Tab')).not.toBeInTheDocument();
	});

	it('renders detail view if there is a fragment selected', async () => {
		const selectedItem = {
			title: 'Selected Item',
			type: 'fragment',
		};

		await act(async () => renderPageAudit({selectedItem}));

		expect(screen.getByText('Selected Item')).toBeInTheDocument();
	});

	it('shows message saying no issues were found when there is no warnings', async () => {
		const selectedItem = {
			title: 'Selected Item',
			type: 'fragment',
			warnings: [],
		};

		await act(async () => renderPageAudit({selectedItem}));

		expect(
			screen.getByText('no-issues-found-in-this-component')
		).toBeInTheDocument();
	});

	it('shows message saying no issues were found when there is no warnings', async () => {
		const selectedItem = {
			title: 'Selected Item',
			type: 'fragment',
			warnings: [],
		};

		await act(async () => renderPageAudit({selectedItem}));

		expect(
			screen.getByText('no-issues-found-in-this-component')
		).toBeInTheDocument();
	});

	it('shows alerts according to the number of warnings', async () => {
		const warnings = ['Warning 1', 'Warning 2'];

		const selectedItem = {
			title: 'Selected Item',
			type: 'fragment',
			warnings,
		};

		await act(async () => renderPageAudit({selectedItem}));

		for (const warning of warnings) {
			const element = screen.getByText(warning);

			expect(element).toBeInTheDocument();

			expect(element.closest('.alert-warning')).not.toBeNull();
		}
	});
});
