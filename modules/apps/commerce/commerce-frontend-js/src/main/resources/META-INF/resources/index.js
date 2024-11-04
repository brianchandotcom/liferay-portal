/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as commerceEvents from './utilities/eventsDefinitions';

export {default as CommerceServiceProvider} from './ServiceProvider/index';

export * as MiniCartUtils from './components/mini_cart/util';
export {default as PriceRenderer} from './components/data_renderers/PriceRenderer';
export {
	accountSelector,
	AddToCart,
	AddToCartButtonComponent,
	AddToCartComponent,
	AddToWishList,
	Autocomplete,
	AutocompleteComponent,
	compareCheckbox,
	DropdownMenu,
	DropdownMenuComponent,
	Gallery,
	GalleryComponent,
	InfiniteScrollerComponent,
	ItemFinder,
	MiniCart,
	MiniCartContext,
	Modal,
	Multishipping,
	Price,
	ProductOptionCheckbox,
	ProductOptionCheckboxMultiple,
	ProductOptionDate,
	ProductOptionNumeric,
	ProductOptionRadio,
	ProductOptionSelect,
	ProductOptionText,
	QuantitySelector,
	QuantitySelectorComponent,
	RequestQuote,
	StepTracker,
	Summary,
	SummaryComponent,
	TierPrice,
	UnitOfMeasureSelector,
} from './components/index';

// This is to provide a layer indirection for internal modules so that we are
// not directly relying on a global value and can import `CommerceContext`
// instead.

export const CommerceContext = Liferay.CommerceContext;

export {commerceEvents};

export {default as MiniCompare} from './components/mini_compare/entry';
export {default as PriceComponent} from './components/price/Price';
export {default as FormUtils} from './utilities/forms/index';
export {
	useLiferayModule,
	useCommerceAccount,
	useCommerceCart,
} from './utilities/hooks';
export {
	fetchHeaders,
	fetchParams,
	getData,
	liferayNavigate,
	getObjectFromPath,
	formatAutocompleteItem,
	getValueFromItem,
	getLabelFromItem,
	formatActionUrl,
	getRandomId,
	sortByKey,
	isProductPurchasable,
} from './utilities/index';
export {default as CommerceFrontendUtils} from './utilities/interface/index';
export * as modalUtils from './utilities/modals/index';

export {
	getMinQuantity,
	getProductMaxQuantity,
	getProductMinQuantity,
	getNumberOfDecimals,
	isMultiple,
} from './utilities/quantities';
export {default as slugify} from './utilities/slugify';
