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
import {act, cleanup, fireEvent, render, wait} from '@testing-library/react';
import React from 'react';

import WrapperWithQuantity from '../../../src/main/resources/META-INF/resources/components/add_to_cart/WrapperWithQuantity';

describe('WrapperWithQuantity', () => {
	const AddToCartButton = (props) => {
		return <button data-test-quantity={props.quantity} />;
	};

	const INITIAL_PROPS = {
		AddToCartButton,
		channel: {
			currencyCode: 'USD',
			id: 12345,
		},
		cpInstance: {
			skuId: 54321,
		},
		settings: {
			withQuantity: {
				forceDropdown: true,
			},
		},
	};

	describe('by display settings', () => {
		let Component;

		beforeEach(() => {
			jest.resetAllMocks();
		});

		afterEach(() => {
			cleanup();
		});

		it('renders with the correct default class names', () => {
			const DEFAULT_CLASSES = [
				'add-to-cart',
				'align-items-center',
				'd-flex',
			];

			Component = render(<WrapperWithQuantity {...INITIAL_PROPS} />);

			const DivContainerElement = Component.container.querySelector(
				'div'
			);
			const QuantitySelectorSelectElement = Component.container.querySelector(
				'select'
			);
			const AddToCartButtonElement = Component.container.querySelector(
				'button'
			);

			expect(DivContainerElement).toBeInTheDocument();
			expect(QuantitySelectorSelectElement).toBeInTheDocument();
			expect(AddToCartButtonElement).toBeInTheDocument();

			DEFAULT_CLASSES.forEach((className) => {
				expect(DivContainerElement.classList.contains(className)).toBe(
					true
				);
			});
		});

		it('renders a select element and a button element in column, if button is block-styled', () => {
			const DEFAULT_CLASSES = [
				'add-to-cart',
				'align-items-center',
				'd-flex',
			];

			const FLEX_COLUMN_CLASS = 'flex-column';

			const settings = {
				block: true,
				withQuantity: {
					forceDropdown: true,
				},
			};

			Component = render(
				<WrapperWithQuantity {...{...INITIAL_PROPS, settings}} />
			);

			const DivContainerElement = Component.container.querySelector(
				'div'
			);
			const QuantitySelectorSelectElement = Component.container.querySelector(
				'select'
			);
			const AddToCartButtonElement = Component.container.querySelector(
				'button'
			);

			expect(DivContainerElement).toBeInTheDocument();
			expect(QuantitySelectorSelectElement).toBeInTheDocument();
			expect(AddToCartButtonElement).toBeInTheDocument();

			[...DEFAULT_CLASSES, FLEX_COLUMN_CLASS].forEach((className) => {
				expect(DivContainerElement.classList.contains(className)).toBe(
					true
				);
			});

			expect(
				QuantitySelectorSelectElement.classList.contains(
					'quantity-selector'
				)
			).toBe(true);
		});

		it('renders a disabled select element, if required via prop', () => {
			const settings = {
				disabled: true,
				withQuantity: {
					forceDropdown: true,
				},
			};

			Component = render(
				<WrapperWithQuantity {...{...INITIAL_PROPS, settings}} />
			);

			const QuantitySelectorSelectElement = Component.container.querySelector(
				'select'
			);

			expect(QuantitySelectorSelectElement).toBeDisabled();
		});

		it('renders a large select element, if required via prop', () => {
			const settings = {
				block: false,
				withQuantity: {
					forceDropdown: true,
				},
			};

			const LARGE_CLASS_NAME = 'form-control-lg';

			Component = render(
				<WrapperWithQuantity {...{...INITIAL_PROPS, settings}} />
			);

			const QuantitySelectorSelectElement = Component.container.querySelector(
				'select'
			);

			expect(
				QuantitySelectorSelectElement.classList.contains(
					LARGE_CLASS_NAME
				)
			).toBe(true);
		});
	});

	describe('by data flow', () => {
		let Component;

		beforeEach(() => {
			jest.resetAllMocks();
		});

		afterEach(() => {
			cleanup();
		});

		it("renders a disabled select element, if no 'accountId' is provided", () => {
			const settings = {
				withQuantity: {
					forceDropdown: true,
				},
			};

			Component = render(
				<WrapperWithQuantity {...{...INITIAL_PROPS, settings}} />
			);

			const QuantitySelectorSelectElement = Component.container.querySelector(
				'select'
			);

			expect(QuantitySelectorSelectElement).toBeDisabled();
		});
	});

	describe('by interaction', () => {
		let Component;

		beforeEach(() => {
			jest.resetAllMocks();
		});

		afterEach(() => {
			cleanup();
		});

		it('on quantity change via QuantitySelector component, renders the button with the correctly updated quantity', async () => {
			const settings = {
				withQuantity: {
					forceDropdown: true,
				},
			};

			Component = render(
				<WrapperWithQuantity {...{...INITIAL_PROPS, settings}} />
			);

			const QuantitySelectorSelectElement = Component.container.querySelector(
				'select'
			);
			const AddToCartButtonElement = Component.container.querySelector(
				'button'
			);

			const updatedValue = 4;

			await act(async () => {
				fireEvent.change(QuantitySelectorSelectElement, {
					target: {value: updatedValue},
				});
			});

			await wait(() => {
				expect(
					AddToCartButtonElement.getAttribute('data-test-quantity')
				).toEqual('4');
			});
		});
	});
});
