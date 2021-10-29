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

var copySaved = '';

var starterkitList = document.getElementsByClassName('provisioning-item');

if (starterkitList) {
	function addActiveClass(event) {
		event.target.classList.add('active');
	}

	for (var i = 0, len = starterkitList.length; i < len; i++) {
		starterkitList[i].addEventListener('focus', addActiveClass);
	}
}

function copySiteName() {
	var letterNumber = /^[0-9a-zA-Z]+$/;

	copyFrom = document.getElementById('sn');
	copyTo = document.getElementById('lod');
	snGroup = document.getElementById('snGroup');
	createSite = document.getElementById('createSite');

	if (copyFrom.value.match(letterNumber)) {
		copyTo.value = copyFrom.value.toLowerCase() + '.liferay.online';
		copySaved = copyFrom.value;
	}
	else if (copyFrom.value === '') {
		copySaved = '';
		copyTo.value = 'liferay.online';
	}
	else {
		copyFrom.value = copySaved;
	}

	if (copyFrom.value.length < 5) {
		snGroup.classList.add('has-error');
		createSite.disabled = true;
	}
	else {
		snGroup.classList.remove('has-error');
		createSite.disabled = false;
	}
}

function openItem(cpInstanceId, commerceChannelId, commerceAccountId, groupId) {
	Liferay.Util.openModal({
		id: 'selectStarterkit',
		title: 'Select your starterkit',
		bodyHTML: `<div class="form-group" id="snGroup">
				 <label for="sn">Site name
					 <small> (more than 4 characters)</small>
				 </label>

				 <input class="form-control" id="sn" maxlength="30" onKeyUp="copySiteName()" placeholder="Site name" type="text" />
			 </div>

			 <div class="form-group">
				 <label for="lod">Liferay Online Domain</label>

				 <input class="form-control" readonly id="lod" placeholder="liferay.online" type="text" />
			 </div>

			 <p class="alert alert-feedback alert-info">You can later manage custom domains from site settings.</p>
		 `,
		size: 'md',
		buttons: [
			{
				displayType: 'secondary',
				label: Liferay.Language.get('Cancel'),
				onClick() {
					Liferay.Util.getOpener().Liferay.fire('closeModal', {
						id: 'selectStarterkit',
					});
				},
			},
			{
				label: 'Select',
				id: 'createSite',
				onClick() {
					Liferay.Util.getOpener().Liferay.fire('closeModal', {
						id: 'selectStarterkit',
					});

					var domainName = document.getElementById('lod').value;
					var siteName = document.getElementById('sn').value;

					createOrder(
						cpInstanceId,
						commerceChannelId,
						commerceAccountId,
						domainName,
						siteName,
						groupId
					);
				},
			},
		],
		onOpen() {
			document.getElementById('createSite').disabled = true;
		},
	});
}

function createOrder(
	cpInstanceId,
	commerceChannelId,
	commerceAccountId,
	domainName,
	siteName,
	groupId
) {

	// console.log('Account ID: ' + commerceAccountId);
	// console.log('Channel ID: ' + commerceChannelId);
	// console.log('Instance ID: ' + cpInstanceId);
	// console.log('Domain name: ' + domainName);
	// console.log('Site name: ' + siteName);

	var cartsURL =
		themeDisplay.getPortalURL() +
		'/o/headless-commerce-delivery-cart/v1.0/channels/' +
		commerceChannelId +
		'/carts';

	Liferay.Util.fetch(cartsURL, {
		body: JSON.stringify({
			accountId: commerceAccountId,
			channelId: commerceChannelId,
			currencyCode: 'USD',
		}),
		headers: {
			'Accept': 'application/json',
			'Content-Type': 'application/json',
		},
		method: 'POST',
	})
		.then((response) => response.json())
		.then((data) => {
			var cartId = data.id;

			console.log(data);

			// console.log('Cart ID: ' + cartId);

			var cartURL =
				themeDisplay.getPortalURL() +
				'/o/headless-commerce-delivery-cart/v1.0/carts/' +
				cartId +
				'/items';

			return Liferay.Util.fetch(cartURL, {
				body: JSON.stringify({
					quantity: 1,
					skuId: cpInstanceId,
					subscription: true,
					options:
						'[{"key":"domain","value":["' +
						domainName +
						'"]},{"key":"name","value":["' +
						siteName +
						'"]}]',
				}),
				headers: {
					'Accept': 'application/json',
					'Content-Type': 'application/json',
				},
				method: 'POST',
			})
				.then((response) => response.json())
				.then((data) => {
					console.log(data);

					var checkoutURL =
						themeDisplay.getPortalURL() +
						'/o/headless-commerce-delivery-cart/v1.0/carts/' +
						cartId +
						'/checkout';

					return Liferay.Util.fetch(checkoutURL, {
						headers: {
							'Accept': 'application/json',
							'Content-Type': 'application/json',
						},
						method: 'POST',
					});
				})
				.then((response) => response.json())
				.then((data) => {
					console.log(data);

					var orderUUID = data.orderUUID;

					var url =
						themeDisplay.getPortalURL() +
						'/o/commerce-payment?groupId=' +
						groupId +
						'&uuid=' +
						orderUUID +
						'&nextStep=http%3A//localhost%3A8080/web/blank/my-site';

					alert(
						'Order completed! you will be redirected to your dashboard'
					);

					window.location.href = url;
				});
		})
		.catch((error) => {
			var errorMsg = 'Sorry, an error occured ' + error;

			return errorMsg;
		});
}
