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

import '@testing-library/jest-dom/extend-expect';
import {cleanup, render} from '@testing-library/react';
import React from 'react';

import AddToCart from '../../../src/main/resources/META-INF/resources/components/add_to_cart/AddToCart';

describe('AddToCart', () => {
	const INITIAL_PROPS = {
		channel: {
			currencyCode: 'USD',
			id: 12345,
		},
		cpInstance: {
			skuId: 12345,
		},
	};

	describe('by display settings', () => {
		beforeEach(() => {
			jest.resetAllMocks();

			window.Liferay.Language.get = jest.fn();
		});

		afterEach(() => {
			cleanup();
		});

		it('renders a Button with the appropriate settings wrapped with its quantity selector elements', () => {
			const props = {
				...INITIAL_PROPS,
				settings: {
					withQuantity: {
						allowedQuantities: [],
						maxQuantity: 10,
						minQuantity: 1,
					},
				},
			};

			const {container} = render(<AddToCart {...props} />);

			const WrapperWithQuantityContainer = container.querySelector(
				'.add-to-cart-wrapper'
			);
			const QuantitySelectorInputElement = container.querySelector(
				'input'
			);
			const AddToCartButtonElement = container.querySelector('button');

			expect(WrapperWithQuantityContainer).toBeInTheDocument();
			expect(QuantitySelectorInputElement).toBeInTheDocument();
			expect(AddToCartButtonElement).toBeInTheDocument();
		});

		it('renders a Button alone if no quantity settings are provided', () => {
			const props = {
				...INITIAL_PROPS,
				settings: {
					withQuantity: false,
				},
			};

			const {container} = render(<AddToCart {...props} />);

			const QuantitySelectorSelectElement = container.querySelector(
				'input'
			);
			const AddToCartButtonElement = container.querySelector('button');

			expect(QuantitySelectorSelectElement).not.toBeInTheDocument();
			expect(AddToCartButtonElement).toBeInTheDocument();
		});
	});
});
