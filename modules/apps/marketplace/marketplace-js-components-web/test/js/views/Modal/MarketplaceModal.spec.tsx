/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {act, cleanup, render} from '@testing-library/react';
import React from 'react';

import {
	MarketplaceContext,
	MarketplaceView,
} from '../../../../src/main/resources/META-INF/resources/js/MarketplaceContext';
import MarketplaceModalView from '../../../../src/main/resources/META-INF/resources/js/views/Modal/MarketplaceModal';

const observer = {
	dispatch: () => null,
	mutation: [true, true] as [boolean, boolean],
};

describe('MarketplaceModalView', () => {
	afterAll(() => {
		jest.useRealTimers();
	});

	afterEach(() => {
		jest.clearAllTimers();

		cleanup();
	});

	beforeEach(() => {
		jest.useFakeTimers();
	});

	it('rendering marketplace modal', async () => {
		const {queryByRole, queryByText, rerender} = render(
			<MarketplaceContext.Provider
				value={{view: MarketplaceView.PURCHASE} as any}
			>
				<MarketplaceModalView observer={observer} open={true}>
					children
				</MarketplaceModalView>
			</MarketplaceContext.Provider>
		);

		await act(async () => {
			jest.runAllTimers();
		});

		const modalParentElement = queryByRole('dialog')?.parentElement;

		expect(modalParentElement).toHaveClass('modal-lg');
		expect(queryByText('add-from-marketplace')).toBeInTheDocument();
		expect(queryByText('children')).toBeTruthy();

		rerender(
			<MarketplaceContext.Provider value={{view: 0} as any}>
				<MarketplaceModalView observer={observer} open={true}>
					children
				</MarketplaceModalView>
			</MarketplaceContext.Provider>
		);

		await act(async () => {
			jest.runAllTimers();
		});

		expect(modalParentElement).toHaveClass('modal-full-screen');

		rerender(
			<MarketplaceModalView observer={observer} open={false}>
				children
			</MarketplaceModalView>
		);

		expect(queryByText('add-from-marketplace')).toBeFalsy();
	});
});
