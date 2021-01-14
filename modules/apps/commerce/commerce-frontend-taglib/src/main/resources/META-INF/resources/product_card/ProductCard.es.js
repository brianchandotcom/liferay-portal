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

'use strict';

import {fetch} from 'frontend-js-web';
import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';

import template from './ProductCard.soy';

const CookieUtil = {
	COOKIE_SCOPE: 'COMMERCE_COMPARE_cpDefinitionIds_',

	getValue(groupId) {
		const [, value] = document.cookie.split(
			`${this.COOKIE_SCOPE}${groupId}=`
		);

		return !value ? null : value.split(';')[0];
	},

	setValue(groupId, value) {
		const cookieValue = `${this.COOKIE_SCOPE}${groupId}=${value};`;

		document.cookie = `${cookieValue};path=/;`;
	},
};

function liferayNavigation(url) {
	if (Liferay.SPA) {
		Liferay.SPA.app.navigate(url);
	}
	else {
		window.location.href = url;
	}
}

class ProductCard extends Component {
	_handleCardKeypress(e) {
		if (e.key === 'Enter' && e.target === this.element) {
			liferayNavigation(this.element.dataset.href);
		}

		if (['A', 'a'].includes(e.key)) {
			e.preventDefault();
			let next = this.element.closest('.minium-product-tiles__item');
			if (e.target !== this.element) {
				next = e.shiftKey
					? next.previousElementSibling
					: next.nextElementSibling;
			}
			if (next) {
				setTimeout(
					() => next.querySelector('.commerce-button').focus(),
					100
				);
			}
		}
	}

	_handleCheckboxCompareUpdate(newCompareState) {
		this.compareState = {
			checkboxVisible: this.compareState.checkboxVisible,
			...newCompareState,
		};

		return this.compareState;
	}

	_handleRemoveProduct() {
		const value = CookieUtil.getValue(this.commerceChannelGroupId);

		const cpDefinitionIds = value ? value.split(':') : [];

		const index = cpDefinitionIds.indexOf(this.productId);

		if (index !== -1) {
			cpDefinitionIds.splice(index, 1);
		}

		CookieUtil.setValue(
			this.commerceChannelGroupId,
			cpDefinitionIds.join(':')
		);

		liferayNavigation(window.location.href);
	}

	_handleWishListButtonClick() {
		this._toggleFavorite();
	}

	_toggleFavorite() {
		if (!this.wishlistAPI) {
			throw new Error('No wishlist API defined.');
		}

		const formData = new FormData();

		formData.append(
			'commerceAccountId',
			this.accountId ? this.accountId : 0
		);
		formData.append('groupId', themeDisplay.getScopeGroupId());
		formData.append('productId', this.productId);
		formData.append('skuId', this.skuId ? this.skuId : 0);
		formData.append('options', '[]');

		fetch(this.wishlistAPI, {
			body: formData,
			method: 'POST',
		})
			.then((response) => response.json())
			.then((jsonresponse) => {
				this.addedToWishlist = jsonresponse.success;

				return this.addedToWishlist;
			});
	}
}

Soy.register(ProductCard, template);

ProductCard.STATE = {
	accountId: Config.oneOfType([Config.string(), Config.number()]).value(null),
	addToCartButtonVisible: Config.bool().value(true),
	addToWishlistButtonVisible: Config.bool().value(true),
	addedToWishlist: Config.bool().value(false),
	availability: Config.string().oneOf([
		'inStock',
		'available',
		'notAvailable',
	]),
	cartAPI: Config.string(),
	categories: Config.array(
		Config.shapeOf({
			link: Config.string().required(),
			name: Config.string().required(),
		})
	),
	commerceChannelGroupId: Config.oneOfType([
		Config.string(),
		Config.number(),
	]),
	compareContentNamespace: Config.string(),
	compareState: Config.shapeOf({
		checkboxVisible: Config.bool(),
		compareAvailable: Config.bool(),
		inCompare: Config.bool(),
	}).value({
		checkboxVisible: true,
		compareAvailable: true,
		inCompare: false,
	}),
	deleteButtonVisible: Config.bool(),
	description: Config.string(),
	detailsLink: Config.string(),
	minQuantity: Config.number(),
	name: Config.string().required(),
	orderId: Config.oneOfType([Config.string(), Config.number()]),
	pictureUrl: Config.string(),
	price: Config.shapeOf({
		formattedPrice: Config.string().required(),
		formattedPromoPrice: Config.string(),
	}),
	productId: Config.oneOfType([Config.string(), Config.number()]).required(),
	settings: Config.shapeOf({
		allowedOptions: Config.array(Config.number()),
		maxQuantity: Config.number(),
		minQuantity: Config.number(),
		multipleQuantity: Config.number(),
	}).value({}),
	sku: Config.string(),
	skuId: Config.oneOfType([Config.string(), Config.number()]),
	spritemap: Config.string(),
	wishlistAPI: Config.string(),
};

export {ProductCard};
export default ProductCard;
