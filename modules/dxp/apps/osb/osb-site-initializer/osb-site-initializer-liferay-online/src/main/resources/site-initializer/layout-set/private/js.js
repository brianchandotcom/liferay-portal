/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch, getOpener, openModal} from 'frontend-js-web';

let copySaved = '';

const starterkitList = document.getElementsByClassName('liferay-online-item');

function addActiveClass(event) {
	event.target.classList.add('active');
}

if (starterkitList) {
	for (let i = 0, len = starterkitList.length; i < len; i++) {
		starterkitList[i].addEventListener('focus', addActiveClass);
	}
}

/* eslint-disable */
function copySiteName() {
	var letterNumber = /^[0-9a-zA-Z]+$/;

	copyFrom = document.getElementById('siteName');
	copyTo = document.getElementById('siteDomain');
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
	adminEmailAddress,
	adminFirstName,
	adminLastName,
	commerceChannelId,
	commerceAccountId,
	cpInstanceId,
	groupId,
	redirectURL,
	siteInitializerKey
) {
	openModal({
		bodyHTML: `<div class="form-group" id="snGroup">
				 <label for="siteName">Site name
					 <small> (more than 4 characters)</small>
				 </label>

				 <input class="form-control" id="siteName" maxlength="30" onKeyUp="copySiteName()" placeholder="Site name" type="text" />
			 </div>

			 <div class="form-group">
				 <label for="siteDomain">Liferay Online Domain</label>

				 <input class="form-control" readonly id="siteDomain" placeholder="liferay.online" type="text" />
			 </div>

			 <p class="alert alert-feedback alert-info">You can later manage custom domains from site settings.</p>
		 `,
		buttons: [
			{
				displayType: 'secondary',
				label: Liferay.Language.get('cancel'),
				onClick() {
					getOpener().Liferay.fire('closeModal', {
						id: 'selectStarterkit',
					});
				},
			},
			{
				id: 'createSite',
				label: 'Select',
				onClick() {
					getOpener().Liferay.fire('closeModal', {
						id: 'selectStarterkit',
					});

					var siteDomain = document.getElementById('siteDomain')
						.value;
					var siteName = document.getElementById('siteName').value;

					createOrder(
						adminEmailAddress,
						adminFirstName,
						adminLastName,
						commerceChannelId,
						commerceAccountId,
						cpInstanceId,
						groupId,
						redirectURL,
						'',
						siteDomain,
						siteInitializerKey,
						siteName,
						''
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
	adminEmailAddress,
	adminFirstName,
	adminLastName,
	commerceChannelId,
	commerceAccountId,
	cpInstanceId,
	groupId,
	redirectURL,
	siteCertificate,
	siteDomain,
	siteInitializerKey,
	siteName,
	sitePrivateKey
) {
	var cartsURL =
		themeDisplay.getPortalURL() +
		'/o/headless-commerce-delivery-cart/v1.0/channels/' +
		commerceChannelId +
		'/carts';

	fetch(cartsURL, {
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

			return fetch(cartURL, {
				body: JSON.stringify({
					options:
						'[{"key":"admin-email-address","value":["' +
						adminEmailAddress +
						'"]},{"key":"admin-first-name","value":["' +
						adminFirstName +
						'"]},{"key":"admin-last-name","value":["' +
						adminLastName +
						'"]},{"key":"site-certificate","value":["' +
						siteCertificate +
						'"]},{"key":"site-domain","value":["' +
						siteDomain +
						'"]},{"key":"site-initializer-key","value":["' +
						siteInitializerKey +
						'"]},{"key":"site-name","value":["' +
						siteName +
						'"]},{"key":"site-private-key","value":["' +
						sitePrivateKey +
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

					return fetch(checkoutURL, {
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
