/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	MarketplaceContext,
	MarketplacePurchase,
	States,
} from '@liferay/marketplace-js-components-web';
import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

describe('CommerceMarketplacePaymentMethod', () => {
	afterAll(() => {
		jest.useRealTimers();
	});

	afterEach(() => {
		cleanup();

		jest.clearAllTimers();
		jest.restoreAllMocks();
	});

	beforeEach(() => {
		cleanup();
		global.open = jest.fn();
		jest.useFakeTimers();
	});

	it('render success and click on setview', () => {
		const setViewMock = jest.fn();

		const {queryByText} = render(
			<MarketplaceContext.Provider
				value={
					{
						setView: setViewMock,
					} as any
				}
			>
				<MarketplacePurchase
					onClickInstall={function (): void {}}
					state={States.SUCCESS}
				/>
			</MarketplaceContext.Provider>
		);

		expect(queryByText('success')).toBeTruthy();

		const cancelButton = queryByText('cancel');

		fireEvent.click(cancelButton as HTMLButtonElement);

		expect(setViewMock).toHaveBeenCalledTimes(1);
	});

	it('render error', () => {
		const {queryByText} = render(
			<MarketplacePurchase
				onClickInstall={function (): void {
					throw new Error('Function not implemented.');
				}}
				state={States.ERROR}
			/>
		);

		expect(queryByText('there-was-an-unknown-error')).toBeTruthy();
	});

	it('render no project', () => {
		const {queryByText} = render(
			<MarketplacePurchase
				onClickInstall={function (): void {}}
				state={States.NO_PROJECT}
			/>
		);

		expect(queryByText('no-cloud-project-available')).toBeTruthy();
	});

	it('render no resource', () => {
		const {queryByText} = render(
			<MarketplacePurchase
				onClickInstall={function (): void {}}
				state={States.NO_RESOURCES}
			/>
		);

		expect(queryByText('insufficient-resources')).toBeTruthy();

		const contactSupportButton = queryByText('contact-support');

		fireEvent.click(contactSupportButton as HTMLButtonElement);
	});

	it('render in progress', () => {
		const {queryByText} = render(
			<MarketplacePurchase
				onClickInstall={function (): void {}}
				state={States.IN_PROGRESS}
			/>
		);

		expect(queryByText('installation-in-progress')).toBeTruthy();
	});

	it('render confirm instalation', () => {
		const {queryByText} = render(
			<MarketplacePurchase
				onClickInstall={function (): void {}}
				state={States.CONFIRM_INSTALLATION}
			/>
		);

		expect(queryByText('confirmation-required')).toBeTruthy();
	});

	it('render null status', () => {
		const {queryByText} = render(
			<MarketplacePurchase
				onClickInstall={function (): void {}}
				state={undefined as any}
			/>
		);

		expect(queryByText('confirmation-required')).toBeFalsy();
		expect(queryByText('installation-in-progress')).toBeFalsy();
		expect(queryByText('no-cloud-project-available')).toBeFalsy();
		expect(queryByText('there-was-an-unknown-error')).toBeFalsy();
	});
});
