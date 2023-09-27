/* eslint-disable no-console */
/* eslint-disable no-undef */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

if (!fragmentNamespace) {
	return;
}

if (layoutMode === 'edit') {
	return;
}

const enableDebug = configuration.enableDebug;
const enableCommissionedRobots = configuration.enableCommissionedRobots;
const simulateIot = configuration.simulateIot;
const cookieExpiry = parseFloat(configuration.cookieExpiry);
const productId = configuration.productId;
const productPurchased = configuration.productPurchased;
const redirectDelay = parseInt(configuration.redirectDelay, 10);
const productPageUrl = configuration.productPageUrl;

let locked = false;

function runOnScroll() {
	if (locked) {
		return;
	}
	locked = true;

	if (fragmentElement) {
		const isVisible = checkVisible(fragmentElement);

		if (enableDebug) {
			console.debug(
				isVisible ? 'fragment is visible' : 'fragment is not visible'
			);
		}

		if (!isVisible) {
			const badge = fragmentElement
				.querySelector('div.first-robot')
				.querySelector('span.label');

			if (badge.classList.contains('label-success')) {
				badge.classList.replace('label-success', 'label-danger');
				badge.firstElementChild.textContent = 'Issue';
				if (enableDebug) {
					console.debug('removing listener');
				}
				window.removeEventListener('scroll', runOnScroll);
			}
		}
	}

	locked = false;
}

function checkVisible(elm) {
	const rect = elm.getBoundingClientRect();
	const viewHeight = Math.max(
		document.documentElement.clientHeight,
		window.innerHeight
	);

	return !(rect.bottom < 0 || rect.top - viewHeight >= 0);
}

const setCookie = function (cname, cvalue, expires) {
	document.cookie = cname + '=' + cvalue + '; ' + expires;
};
const runCommissionedRobots = function () {
	const d = new Date();
	if (isNaN(cookieExpiry)) {
		d.setTime(d.getTime() + 10000);
	}
	else {
		d.setTime(d.getTime() + cookieExpiry * 1000);
	}
	const expires = d.toUTCString();
	if (enableDebug) {
		console.debug('The product id is ' + productId);
		console.debug('Product purchased is ' + productPurchased);
		console.debug('The cookie will expire at ' + expires);
		console.debug(
			'Page will redirect to ' +
				productPageUrl +
				' in ' +
				redirectDelay +
				' milliseconds'
		);
	}

	const expiryStr = 'expires=' + expires;
	setCookie('product', productId, expiryStr);
	setCookie('purchased', productPurchased, expiryStr);

	setTimeout(() => {
		const currentLocation = window.location;
		location.href =
			currentLocation.protocol +
			'//' +
			currentLocation.host +
			productPageUrl;
	}, redirectDelay);
};

if (enableCommissionedRobots) {
	const a = fragmentElement.querySelector('div.first-robot');
	if (a) {
		a.addEventListener('click', runCommissionedRobots, false);
		if (enableDebug) {
			console.debug('Event handled added for commissioned robots');
		}
	}
}

if (simulateIot) {
	window.addEventListener('scroll', runOnScroll, {passive: true});
	if (enableDebug) {
		console.debug('Event handled added for IoT simulation');
	}
}
