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

function showNotification(message, type) {
	if (!notificationDidShow) {
		AUI().use('liferay-notification', () => {
			new Liferay.Notification({
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

function resetInputQuantity() {
	this.inputQuantity =
		this.settings.allowedQuantities &&
		this.settings.allowedQuantities.length
			? this.settings.allowedQuantities[0]
			: this.settings.minQuantity;
}

function doSubmit() {
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
		.then((jsonresponse) => {
			if (jsonresponse.success) {
				if (jsonresponse.products) {
					Liferay.fire('refreshCartUsingData', {
						detailsUrl: jsonresponse.detailsUrl,
						orderId: jsonresponse.orderId,
						products: jsonresponse.products,
						summary: jsonresponse.summary,
						valid: jsonresponse.valid,
					});
				}

				if (this.orderId !== jsonresponse.orderId) {
					Liferay.fire('currentOrderChanged', {
						id: jsonresponse.orderId
					});
					this.orderId = jsonresponse.orderId;
				}

				this._animateMarker(this.quantity);
				this.quantity = this.inputQuantity;
				resetInputQuantity.call(this);

				Liferay.fire('commerce:productAddedToCart', jsonresponse);
			}
			else if (jsonresponse.errorMessages) {
				showNotification(jsonresponse.errorMessages[0], 'danger');
			}
			else {
				const validatorErrors = jsonresponse.validatorErrors;

				if (validatorErrors) {
					validatorErrors.forEach((validatorError) => {
						showNotification(validatorError.message, 'danger');
					});
				}
				else {
					showNotification(jsonresponse.error, 'danger');
				}
			}
		})
		.catch((_weShouldHandleErrors) => {});
}

class AddToCartButton extends Component {
	created() {
		resetInputQuantity.call(this);
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
		window.Liferay.on('currentAccountChanged', this._handleAccountChange, this);

		window.Liferay.on(
			'productRemovedFromCart',
			this._handleCartProductRemoval,
			this
		);

		// TODO: event definition to be imported as a constant

		window.Liferay.on(
			'current-product-status-changed',
			this._handleCurrentProductStatusChange,
			this
		);
	}

	detached() {
		window.Liferay.detach(
			'currentAccountChanged',
			this._handleAccountChange,
			this
		);
		window.Liferay.detach(
			'productRemovedFromCart',
			this._handleCartProductRemoval,
			this
		);

		// TODO: event definition to be imported as a constant

		window.Liferay.detach(
			'current-product-status-changed',
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

	_handleAccountChange({account}) {
		this.accountId = account ? account.id : null;
		this.orderId = null;

		// TODO: quantity should be imported from the outside

		this.quantity = 0;
		resetInputQuantity.call(this);
	}

	_handleCartProductRemoval({skuId}) {
		if (skuId === parseInt(this.productId, 10) || skuId === ALL) {
			this.quantity = 0;
			resetInputQuantity.call(this);
		}
	}

	_handleSubmitClick() {
		if (!this.accountId) {
			const message = Liferay.Language.get('no-account-selected');
			const type = 'danger';

			showNotification(message, type);
		}

		if (this.disabled) {
			return null;
		}

		doSubmit.call(this);
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
