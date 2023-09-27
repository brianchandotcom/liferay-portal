/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom/extend-expect';

import {
	DEFAULT_ORDER_DETAILS_PORTLET_ID,
	ORDER_DETAILS_ENDPOINT,
	ORDER_UUID_PARAMETER,
} from '../../../../src/main/resources/META-INF/resources/components/mini_cart/util/constants';
import {
	hasErrors,
	parseOptions,
	regenerateOrderDetailURL,
	summaryDataMapper,
} from '../../../../src/main/resources/META-INF/resources/components/mini_cart/util/index';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/ServiceProvider/index'
);

describe('MiniCart tests_utilities', () => {
	describe('hasErrors', () => {
		it('returns true if at least one cart item contains error messages', () => {
			const CART_ITEMS = [
				{id: 1},
				{errorMessages: 'Error', id: 2},
				{id: 3},
			];

			expect(hasErrors(CART_ITEMS)).toBe(true);
		});

		it('returns false if no cart item contains error messages', () => {
			const CART_ITEMS = [{id: 1}, {id: 2}, {id: 3}];

			expect(hasErrors(CART_ITEMS)).toBe(false);
		});
	});

	describe('parseOptions', () => {
		it('parses and formats a JSON string input to an options list string', () => {
			const VALID_JSON_INPUT = `[
				{
					"key": "package-quantity", 
					"value": "24"
				},
				{
					"key": "size", 
					"value": "L"
				}
			]`;

			expect(parseOptions(VALID_JSON_INPUT)).toEqual('24, L');
		});

		it('returns an empty string when input is neither valid JSON nor a JSON-parsed array', () => {
			expect(parseOptions(null)).toEqual('');
			expect(parseOptions('/fail]')).toEqual('');
		});
	});

	describe('regenerateOrderDetailURL', () => {
		const VALID_ORDER_UUID = '00000-00000-22222-213jd-qwerty';
		const VALID_SITE_DEFAULT_URL = 'http://localhost:3333/group/name';

		const errorMessage = (argName) =>
			`Cannot generate a new Order Detail URL. Invalid "${argName}"`;

		it('returns a new valid Order Detail URL string', () => {
			expect(
				regenerateOrderDetailURL(
					VALID_ORDER_UUID,
					VALID_SITE_DEFAULT_URL
				)
			).toEqual(
				`${VALID_SITE_DEFAULT_URL}${ORDER_DETAILS_ENDPOINT}` +
					`?p_p_id=${DEFAULT_ORDER_DETAILS_PORTLET_ID}` +
					`&p_p_lifecycle=0` +
					`&_${DEFAULT_ORDER_DETAILS_PORTLET_ID}_mvcRenderCommandName=%2Fcommerce_open_order_content%2Fedit_commerce_order` +
					`&_${DEFAULT_ORDER_DETAILS_PORTLET_ID}_${ORDER_UUID_PARAMETER}=${VALID_ORDER_UUID}`
			);
		});

		it('throws if the "orderUUID" string argument is empty or null', () => {
			try {
				expect(
					regenerateOrderDetailURL('', VALID_SITE_DEFAULT_URL)
				).toThrow();
				expect(
					regenerateOrderDetailURL(null, VALID_SITE_DEFAULT_URL)
				).toThrow();
			}
			catch (error) {
				expect(error.message).toEqual(errorMessage`orderUUID`);
			}
		});

		it('throws if the "siteDefaultURL" string argument is empty or null', () => {
			try {
				expect(
					regenerateOrderDetailURL(VALID_ORDER_UUID, '')
				).toThrow();
				expect(
					regenerateOrderDetailURL(VALID_ORDER_UUID, null)
				).toThrow();
			}
			catch (error) {
				expect(error.message).toEqual(errorMessage`siteDefaultURL`);
			}
		});

		it('throws if the "siteDefaultURL" string argument is a malformed URL', () => {
			const MALFORMED_SITE_DEFAULT_URL = 'malformed';

			try {
				expect(
					regenerateOrderDetailURL(
						VALID_ORDER_UUID,
						MALFORMED_SITE_DEFAULT_URL
					)
				).toThrow(TypeError);
			}
			catch (error) {
				expect(error.message.includes('Invalid URL')).toBe(true);
			}
		});
	});

	describe('summaryDataMapper', () => {
		const SUMMARY_SAMPLE = {
			currency: 'US Dollar',
			itemsQuantity: 48,
			shippingDiscountPercentages: ['0.00', '0.00', '0.00', '0.00'],
			shippingDiscountValue: 0.0,
			shippingDiscountValueFormatted: '$ 0.00',
			shippingValue: 0.0,
			shippingValueFormatted: '$ 0.00',
			shippingValueWithTaxAmount: 0.0,
			shippingValueWithTaxAmountFormatted: '$ 0.00',
			subtotal: 1858.5,
			subtotalDiscountPercentages: ['0.00', '0.00', '0.00', '0.00'],
			subtotalDiscountValue: 0.0,
			subtotalDiscountValueFormatted: '$ 0.00',
			subtotalFormatted: '$ 1,858.50',
			taxValue: 0.0,
			taxValueFormatted: '$ 0.00',
			total: 1858.5,
			totalDiscountPercentages: ['0.00', '0.00', '0.00', '0.00'],
			totalDiscountValue: 0.0,
			totalDiscountValueFormatted: '$ 0.00',
			totalFormatted: '$ 1,858.50',
		};

		it('converts a DeliveryCart API summary payload to a label/value map list for the Summary used in the MiniCart', () => {
			expect(summaryDataMapper(SUMMARY_SAMPLE)).toEqual([
				{label: 'quantity', value: 48},
				{label: 'subtotal', value: '$ 1,858.50'},
				{label: 'subtotal-discount', value: '$ 0.00'},
				{label: 'order-discount', value: '$ 0.00'},
				{label: 'total', style: 'big', value: '$ 1,858.50'},
			]);
		});
	});
});
