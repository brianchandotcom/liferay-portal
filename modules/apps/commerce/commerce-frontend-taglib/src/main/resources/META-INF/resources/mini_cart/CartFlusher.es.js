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

import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';

import template from './CartFlusher.soy';

let notificationDidShow = false;

function showNotification(message, type) {
	if (!notificationDidShow) {
		AUI().use('liferay-notification', () => {
			new Liferay.Notification({
				closeable: true,
				delay: {
					hide: 5000,
					show: 0
				},
				duration: 500,
				message,
				render: true,
				title: '',
				type
			});
		});
	}

	notificationDidShow = true;

	setTimeout(() => {
		notificationDidShow = false;
	}, 500);
}

class CartFlusher extends Component {
	_handleAskConfirmation(e) {
		e.preventDefault();

		this.isAsking = true;
	}

	_handleCancel() {
		this.isAsking = false;
	}

	_handleConfirm() {
		fetch(this.apiEndpoint, {
			credentials: 'include',
			headers: new Headers({'x-csrf-token': Liferay.authToken}),
			method: 'DELETE'
		})
			.then(response => response.json())
			.then(({products, success, summary}) => {
				this.isAsking = false;

				if (success && (!products.length || !products)) {
					this.emit('deleteAllItems', {products: null, summary});
				} else {
					throw new Error(
						Liferay.Language.get('unable-to-empty-the-cart')
					);
				}
			})
			.catch(e => {
				this.isAsking = false;

				showNotification(e, 'danger');
			});
	}
}

Soy.register(CartFlusher, template);

CartFlusher.STATE = {
	apiEndpoint: Config.string(),
	isAsking: Config.bool().value(false)
};

export {CartFlusher};
export default CartFlusher;
