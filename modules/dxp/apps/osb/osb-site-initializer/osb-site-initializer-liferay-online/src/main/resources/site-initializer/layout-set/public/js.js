/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

var copySaved = '';

var starterkitList = document.getElementsByClassName('liferay-online-item');

function addActiveClass(event) {
	event.target.classList.add('active');
}

if (starterkitList) {
	for (var i = 0, len = starterkitList.length; i < len; i++) {
		starterkitList[i].addEventListener('focus', addActiveClass);
	}
}

/* eslint-disable */
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

function openItem(
	cpInstanceId,
	commerceChannelId,
	commerceAccountId,
	groupId,
	redirectURL
) {
	Liferay.Util.openModal({
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
		buttons: [
			{
				displayType: 'secondary',
				label: Liferay.Language.get('cancel'),
				onClick() {
					Liferay.Util.getOpener().Liferay.fire('closeModal', {
						id: 'selectStarterkit',
					});
				},
			},
			{
				id: 'createSite',
				label: 'Select',
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
						groupId,
						redirectURL
					);
				},
			},
		],
		id: 'selectStarterkit',
		onOpen() {
			document.getElementById('createSite').disabled = true;
		},
		size: 'md',
		title: 'Select your starterkit',
	});
}

function createOrder(
	cpInstanceId,
	commerceChannelId,
	commerceAccountId,
	domainName,
	siteName,
	groupId,
	redirectURL
) {
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

			var cartURL =
				themeDisplay.getPortalURL() +
				'/o/headless-commerce-delivery-cart/v1.0/carts/' +
				cartId +
				'/items';

			return Liferay.Util.fetch(cartURL, {
				body: JSON.stringify({
					options:
						'[{"key":"domain","value":["' +
						domainName +
						'"]},{"key":"name","value":["' +
						siteName +
						'"]}]',
					quantity: 1,
					skuId: cpInstanceId,
					subscription: true,
				}),
				headers: {
					'Accept': 'application/json',
					'Content-Type': 'application/json',
				},
				method: 'POST',
			})
				.then((response) => response.json())
				.then(() => {
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
					var orderUUID = data.orderUUID;

					var url =
						themeDisplay.getPortalURL() +
						'/o/commerce-payment?groupId=' +
						groupId +
						'&uuid=' +
						orderUUID +
						'&nextStep=' +
						redirectURL;

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
