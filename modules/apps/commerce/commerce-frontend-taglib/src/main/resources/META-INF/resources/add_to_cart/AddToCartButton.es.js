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

import {fetch} from 'frontend-js-web';
import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';

import template from './AddToCartButton.soy';

import '../quantity_selector/QuantitySelector.es';

let notificationDidShow = false;

const ALL = 'all';
const CURRENT_ACCOUNT_UPDATED = 'current-account-updated';
const CURRENT_ORDER_UPDATED = 'current-order-updated';
const CURRENT_PRODUCT_STATUS_CHANGED = 'current-product-status-changed';
const PRODUCT_REMOVED_FROM_CART = 'product-removed-from-cart';

function showNotification(message, type) {
	if (!notificationDidShow) {
		AUI().use('liferay-notification', () => {
			new window.Notification({
				closeable: true,
				delay: {
					hide: 5000,
					show: 0,
				},
				duration: 500,
				message,
				render: true,
				title: '',
				type,
			});
		});
	}

	notificationDidShow = true;

	setTimeout(() => {
		notificationDidShow = false;
	}, 500);
}

class AddToCartButton extends Component {
	created() {
		this._handleMarkerAnimation = this._handleMarkerAnimation.bind(this);
	}

	_animateMarker(prevQuantity) {
		if (prevQuantity === 0) {
			this.updatingTransition = 'adding';
		}
		else {
			this.updatingTransition = 'incrementing';
		}

		this.refs.marker.addEventListener(
			'animationend',
			this._handleMarkerAnimation,
			this
		);
	}

	_handleMarkerAnimation() {
		this.updatingTransition = null;
		this.refs.marker.removeEventListener(
			'animationend',
			this._handleMarkerAnimation,
			this
		);
	}

	attached() {
		Liferay.on(CURRENT_ACCOUNT_UPDATED, this._handleAccountChange, this);

		Liferay.on(CURRENT_ORDER_UPDATED, this._handleOrderChange, this);

		Liferay.on(
			PRODUCT_REMOVED_FROM_CART,
			this._handleCartProductRemoval,
			this
		);

		Liferay.on(
			CURRENT_PRODUCT_STATUS_CHANGED,
			this._handleCurrentProductStatusChange,
			this
		);
	}

	detached() {
		Liferay.detach(
			CURRENT_ACCOUNT_UPDATED,
			this._handleAccountChange,
			this
		);

		Liferay.detach(CURRENT_ORDER_UPDATED, this._handleOrderChange, this);

		Liferay.detach(
			PRODUCT_REMOVED_FROM_CART,
			this._handleCartProductRemoval,
			this
		);

		Liferay.detach(
			CURRENT_PRODUCT_STATUS_CHANGED,
			this._handleCurrentProductStatusChange,
			this
		);
	}

	_handleUpdateQuantity(quantity) {
		this.inputQuantity = quantity;
	}

	_handleSubmitQuantity(quantity) {
		this._handleUpdateQuantity(quantity);
		this._handleSubmitClick();
	}

	_handleCurrentProductStatusChange(e) {
		if (this.id) {
			if (this.id !== e.addToCartId) {
				return;
			}
			if (e.productId) {
				this.productId = e.productId;
				this.options = e.options;
				this.quantity = e.quantity;
				this.settings = e.settings;
				this.disabled = false;
			}
			else {
				this.disabled = true;
			}
		}
	}

	_handleOrderChange(evt) {
		if (this.orderId === evt.id) {
			return;
		}

		this.orderId = evt.id;
		this.quantity = 0;
		this._resetInputQuantity();
	}

	_handleAccountChange({id}) {
		this.accountId = id;
	}

	_handleCartProductRemoval({skuId}) {
		if (skuId === parseInt(this.productId, 10) || skuId === ALL) {
			this.quantity = 0;
			this._resetInputQuantity();
		}
	}

	_handleSubmitClick() {
		if (!this.accountId) {
			const message = window.Language.get('no-account-selected');
			const type = 'danger';

			showNotification(message, type);
		}

		if (this.disabled) {
			return null;
		}

		this._doSubmit();
	}

	_doSubmit() {
		const formData = new FormData();

		formData.append('commerceAccountId', this.accountId);
		formData.append('groupId', themeDisplay.getScopeGroupId());
		formData.append('productId', this.productId);
		formData.append('languageId', themeDisplay.getLanguageId());
		formData.append('quantity', this.inputQuantity);
		formData.append('options', this.options);

		if (this.orderId) {
			formData.append('orderId', this.orderId);
		}

		return fetch(this.cartAPI, {
			body: formData,
			method: 'POST',
		})
			.then((response) => response.json())
			.then((jsonResponse) => {
				if (jsonResponse.success) {
					if (jsonResponse.orderId !== this.orderId) {
						this.orderId = jsonResponse.orderId;
					}

					Liferay.fire(CURRENT_ORDER_UPDATED, {
						id: jsonResponse.orderId,
						orderStatusInfo: jsonResponse.orderStatusInfo,
					});

					this._animateMarker(this.quantity);
					this.quantity = this.inputQuantity;
					this._resetInputQuantity(this);
				}
				else if (jsonResponse.errorMessages) {
					showNotification(jsonResponse.errorMessages[0], 'danger');
				}
				else {
					const validatorErrors = jsonResponse.validatorErrors;

					if (validatorErrors) {
						validatorErrors.forEach((validatorError) => {
							showNotification(validatorError.message, 'danger');
						});
					}
					else {
						showNotification(jsonResponse.error, 'danger');
					}
				}
			})
			.catch(console.error);
	}

	_resetInputQuantity() {
		this.inputQuantity =
			this.settings.allowedQuantities &&
			this.settings.allowedQuantities.length
				? this.settings.allowedQuantities[0]
				: this.settings.minQuantity;
	}
}

Soy.register(AddToCartButton, template);

AddToCartButton.STATE = {
	accountId: Config.oneOfType([Config.number(), Config.string()]),
	buttonStyle: Config.oneOf(['block', 'inline']).value('inline'),
	cartAPI: Config.string().required(),
	disabled: Config.bool().value(false),
	id: Config.string(),
	inputQuantity: Config.number(),
	options: Config.oneOfType([Config.object(), Config.string()]).value('[]'),
	orderId: Config.oneOfType([Config.number(), Config.string()]),
	productId: Config.oneOfType([Config.number(), Config.string()]).required(),
	quantity: Config.number(),
	settings: Config.shapeOf({
		allowedQuantity: Config.array(Config.number()),
		maxQuantity: Config.number(),
		minQuantity: Config.number(),
		multipleQuantity: Config.number(),
	}).value({}),
	textContent: Config.string(),
	updatingTransition: Config.oneOf(['adding', 'incrementing']),
};

export {AddToCartButton};
export default AddToCartButton;
