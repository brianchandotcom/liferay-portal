/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MarketplaceContext} from '@liferay/marketplace-js-components-web';
import {cleanup, fireEvent, render} from '@testing-library/react';
import React from 'react';

import {
	MarketplacePurchase,
	States,
} from '../../../src/main/resources/META-INF/resources/js/commerce_marketplace_payment_method/MarketplacePurchase';

describe('CommerceMarketplacePaymentMethod', () => {
	beforeEach(() => {
		jest.useFakeTimers();
		global.open = jest.fn();
		cleanup();
	});

	afterEach(() => {
		cleanup();

		jest.clearAllTimers();
		jest.restoreAllMocks();
	});

	afterAll(() => {
		jest.useRealTimers();
	});

	it('render success and click on setview', () => {
		const setViewMock = jest.fn();

		const {queryByText} = render(
			<MarketplaceContext.Provider
				value={{
					setView: setViewMock,
				}}
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
		render(
			<MarketplacePurchase
				onClickInstall={function (): void {}}
				state={undefined}
			/>
		);
	});
});
