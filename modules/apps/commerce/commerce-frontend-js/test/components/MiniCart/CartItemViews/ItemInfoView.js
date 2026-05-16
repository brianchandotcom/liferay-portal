/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render} from '@testing-library/react';
import React from 'react';

import ItemInfoView from '../../../../src/main/resources/META-INF/resources/components/mini_cart/CartItemViews/ItemInfoView';

describe('MiniCart Item Info View', () => {
	const BASE_PROPS = {
		name: 'An Item',
		sku: 'ITEM001',
	};

	const COMPONENT_SELECTOR_BASE = '.item-info-base';
	const COMPONENT_SELECTOR_BUNDLE = '.child-items';
	const COMPONENT_SELECTOR_OPTIONS = '.item-info-extra';

	beforeEach(() => {
		window.Liferay = {
			Language: {
				get: jest.fn((text) => text),
			},
		};
	});

	afterEach(() => {
		jest.resetAllMocks();
	});

	describe('Base', () => {
		it('renders the name and the SKU of an item', () => {
			const {container} = render(<ItemInfoView {...BASE_PROPS} />);

			const BaseViewElement = container.querySelector(
				COMPONENT_SELECTOR_BASE
			);
			const ItemNameElement = BaseViewElement.querySelector('.item-name');
			const ItemSKUElement = BaseViewElement.querySelector('.item-sku');

			expect(BaseViewElement).toBeInTheDocument();
			expect(ItemNameElement).toBeInTheDocument();
			expect(ItemSKUElement).toBeInTheDocument();

			expect(ItemNameElement.innerHTML).toEqual(BASE_PROPS.name);
			expect(ItemSKUElement.innerHTML).toEqual(BASE_PROPS.sku);
			expect(BaseViewElement.innerHTML).toMatchSnapshot();
		});

		describe('+ Bundle', () => {
			const BUNDLE_PROPS = {
				...BASE_PROPS,
				childItems: [
					{
						name: 'Child Item 1',
						quantity: 1,
					},
					{
						name: 'Child Item 2',
						quantity: 3,
					},
				],
			};

			it('renders the name and the SKU of a bundle item, plus its child items with their related quantities', () => {
				const {asFragment, container} = render(
					<ItemInfoView {...BUNDLE_PROPS} />
				);

				const BaseViewElement = container.querySelector(
					COMPONENT_SELECTOR_BASE
				);
				const ItemNameElement =
					BaseViewElement.querySelector('.item-name');
				const ItemSKUElement =
					BaseViewElement.querySelector('.item-sku');

				expect(BaseViewElement).toBeInTheDocument();
				expect(ItemNameElement).toBeInTheDocument();
				expect(ItemSKUElement).toBeInTheDocument();

				expect(ItemNameElement.innerHTML).toEqual(BASE_PROPS.name);
				expect(ItemSKUElement.innerHTML).toEqual(BASE_PROPS.sku);

				const BundleViewElement = container.querySelector(
					COMPONENT_SELECTOR_BUNDLE
				);
				const ChildItemsElements =
					BundleViewElement.querySelectorAll('.child-item span');

				expect(BundleViewElement).toBeInTheDocument();
				expect(ChildItemsElements.length).toEqual(2);

				Array.from(ChildItemsElements).forEach((element) => {
					expect(element.innerHTML).toMatchSnapshot();
				});

				expect(asFragment()).toMatchSnapshot();
			});
		});

		describe('+ Options', () => {
			const OPTIONS_PROPS = {
				...BASE_PROPS,
				options: [
					{
						skuId: '0',
						skuOptionName: 'Size',
						skuOptionValueNames: 'XL',
						value: 'XL',
					},
				],
			};

			it('renders the name and the SKU of an item, plus its options', () => {
				const {asFragment, container} = render(
					<ItemInfoView {...OPTIONS_PROPS} />
				);

				const BaseViewElement = container.querySelector(
					COMPONENT_SELECTOR_BASE
				);
				const ItemNameElement =
					BaseViewElement.querySelector('.item-name');
				const ItemSKUElement =
					BaseViewElement.querySelector('.item-sku');

				expect(BaseViewElement).toBeInTheDocument();
				expect(ItemNameElement).toBeInTheDocument();
				expect(ItemSKUElement).toBeInTheDocument();

				expect(ItemNameElement.innerHTML).toEqual(BASE_PROPS.name);
				expect(ItemSKUElement.innerHTML).toEqual(BASE_PROPS.sku);

				const OptionsViewElement = container.querySelector(
					COMPONENT_SELECTOR_OPTIONS
				);
				const OptionNameElement =
					OptionsViewElement.querySelector('.item-name');
				const OptionValueElement =
					OptionsViewElement.querySelector('.item-sku');

				expect(OptionsViewElement).toBeInTheDocument();
				expect(OptionNameElement.innerHTML).toEqual(
					OPTIONS_PROPS.options[0].skuOptionName
				);
				expect(OptionValueElement.innerHTML).toEqual(
					OPTIONS_PROPS.options[0].skuOptionValueNames
				);

				expect(asFragment()).toMatchSnapshot();
			});
		});

		describe('+ Bundle + Options', () => {
			const BUNDLE_OPTIONS_PROPS = {
				...BASE_PROPS,
				childItems: [
					{
						name: 'Child Item 1',
						quantity: 1,
						skuId: 1,
					},
					{
						name: 'Child Item 2',
						quantity: 3,
						skuId: 2,
					},
					{
						name: 'Child Item 3',
						quantity: 5,
						skuId: 3,
					},
				],
				options: [
					{
						skuId: '1',
						skuOptionName: 'Variant 1',
						skuOptionValueNames: 'V1',
						value: 'V1',
					},
					{
						skuId: '2',
						skuOptionName: 'Variant 2',
						skuOptionValueNames: 'V2',
						value: 'V2',
					},
					{
						skuId: '3',
						skuOptionName: 'Variant 3',
						skuOptionValueNames: 'V3',
						value: 'V3',
					},
				],
			};

			it('renders the name and the SKU of an item, plus its child items resolved through their related options', () => {
				const {asFragment, container} = render(
					<ItemInfoView {...BUNDLE_OPTIONS_PROPS} />
				);

				const BaseViewElement = container.querySelector(
					COMPONENT_SELECTOR_BASE
				);
				const ItemNameElement =
					BaseViewElement.querySelector('.item-name');
				const ItemSKUElement =
					BaseViewElement.querySelector('.item-sku');

				expect(BaseViewElement).toBeInTheDocument();
				expect(ItemNameElement).toBeInTheDocument();
				expect(ItemSKUElement).toBeInTheDocument();

				expect(ItemNameElement.innerHTML).toEqual(BASE_PROPS.name);
				expect(ItemSKUElement.innerHTML).toEqual(BASE_PROPS.sku);

				const OptionElements = container.querySelectorAll(
					COMPONENT_SELECTOR_OPTIONS
				);

				expect(OptionElements.length).toEqual(
					BUNDLE_OPTIONS_PROPS.options.length
				);

				Array.from(OptionElements).forEach((element) => {
					expect(element.innerHTML).toMatchSnapshot();
				});

				expect(asFragment()).toMatchSnapshot();
			});
		});
	});
});
