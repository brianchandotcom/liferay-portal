/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {cleanup, render, screen, waitFor} from '@testing-library/react';
import {openModal} from 'frontend-js-components-web';
import React from 'react';

import OrderActions from '../../../../src/main/resources/META-INF/resources/js/OrderActions';
import {getOrder} from '../../../../src/main/resources/META-INF/resources/js/util';

const mockGetCartTransitionsById = jest.fn();

jest.mock('commerce-frontend-js', () => ({
	CommerceServiceProvider: {
		DeliveryCartAPI: () => ({
			getCartTransitionsById: (...args) =>
				mockGetCartTransitionsById(...args),
		}),
		DeliveryOrderAPI: () => ({
			getOrderTransitionsById: () => Promise.resolve({items: []}),
		}),
	},
	RequestQuote: () => null,
	commerceEvents: {
		CART_UPDATED: 'cartUpdated',
		ORDER_INFORMATION_ALTERED: 'orderInformationAltered',
	},
}));

jest.mock('../../../../src/main/resources/META-INF/resources/js/util', () => ({
	PAYMENT_METHOD_TYPE_OFFLINE: 2,
	getOrder: jest.fn(),
}));

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/orderActionRedirectHelper',
	() => ({
		handleOrderActionRedirect: jest.fn(),
	})
);

jest.mock('frontend-js-components-web', () => ({
	openModal: jest.fn(),
	openToast: jest.fn(),
}));

const ORDER_RULE_MESSAGE = 'Minimum order amount of $10,000.00 not reached.';

function renderOrderActions() {
	return render(
		<OrderActions
			checkoutURL="/checkout"
			isOpen={true}
			isPriceOnApplication={false}
			orderId={38280}
			quickCheckoutEnabled={false}
		/>
	);
}

describe('OrderActions', () => {
	afterEach(() => {
		jest.clearAllMocks();

		cleanup();
	});

	it('opens a warning modal with the order validator message and hides the checkout button when the checkout transition is unavailable', async () => {
		mockGetCartTransitionsById.mockResolvedValue({items: []});
		getOrder.mockResolvedValue({
			errorMessages: [ORDER_RULE_MESSAGE],
			valid: false,
		});

		renderOrderActions();

		await waitFor(() => {
			expect(openModal).toHaveBeenCalledWith(
				expect.objectContaining({
					bodyHTML: ORDER_RULE_MESSAGE,
					status: 'warning',
				})
			);
		});

		expect(
			screen.queryByRole('button', {name: 'checkout'})
		).not.toBeInTheDocument();
	});

	it('shows the checkout button and opens no modal when the order is valid', async () => {
		mockGetCartTransitionsById.mockResolvedValue({
			items: [{label: 'checkout', name: 'checkout'}],
		});
		getOrder.mockResolvedValue({errorMessages: [], valid: true});

		renderOrderActions();

		await waitFor(() => {
			expect(
				screen.getByRole('button', {name: 'checkout'})
			).toBeInTheDocument();
		});

		expect(openModal).not.toHaveBeenCalled();
	});
});
