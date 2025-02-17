/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';
import {Observer} from '@clayui/modal/lib/types';
import {fireEvent, render} from '@testing-library/react';
import React from 'react';

import {MarketplaceContext} from '../../../../src/main/resources/META-INF/resources/js/MarketplaceContext';
import {MarketplaceModal} from '../../../../src/main/resources/META-INF/resources/js/views/Modal';

const observer = {
	dispatch: () => null,
	mutation: [true, true],
} as Observer;

const mockEvent = jest.fn();
const onClick = jest.fn().mockImplementation(mockEvent);
const onOpenChange = jest.fn();
const open = true;
const reactTrigger = <button onClick={() => onClick}>trigger</button>;

describe('MarketplaceModal', () => {
	it('render component without connection to marketplace', () => {
		const {getByText, queryByText} = render(
			<MarketplaceContext.Provider
				value={
					{
						marketplaceConfiguration: {
							authorized: true,
							loading: false,
						},
						modal: {observer, onOpenChange, open},
					} as any
				}
			>
				<MarketplaceModal
					noConnectionMessage="you have no connection"
					trigger={reactTrigger}
				>
					children
				</MarketplaceModal>
			</MarketplaceContext.Provider>
		);

		fireEvent.click(getByText('trigger'));

		expect(onOpenChange).toHaveBeenCalled();
		expect(queryByText('children')).toBeInTheDocument();
	});

	it('render component with connection to marketplace', () => {
		const {queryByText} = render(
			<MarketplaceContext.Provider
				value={
					{
						marketplaceConfiguration: {
							authorized: true,
							loading: false,
						},
						modal: {observer, onOpenChange, open: false},
					} as any
				}
			>
				<MarketplaceModal trigger={reactTrigger}>
					children
				</MarketplaceModal>
			</MarketplaceContext.Provider>
		);

		expect(queryByText('children')).toBeFalsy();
	});

	it('render component while loading', () => {
		const emptyMockTrigger = <p></p>;
		
		const {container} = render(
			<MarketplaceContext.Provider
				value={
					{
						marketplaceConfiguration: {
							loading: true,
						},
						modal: {observer, onOpenChange, open},
					} as any
				}
			>
				<MarketplaceModal trigger={emptyMockTrigger}>
					children
				</MarketplaceModal>
			</MarketplaceContext.Provider>
		);

		expect(container.querySelectorAll('div')).toHaveLength(0);
	});
});
