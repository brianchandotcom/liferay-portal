/* eslint-disable no-console */
/* eslint-disable no-undef */
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

var locked = false;

function runOnScroll() {
	if (locked) {
		return;
	}
	locked = true;

	if (fragmentElement) {
		var isVisible = checkVisible(fragmentElement);

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
	var rect = elm.getBoundingClientRect();
	var viewHeight = Math.max(
		document.documentElement.clientHeight,
		window.innerHeight
	);

	return !(rect.bottom < 0 || rect.top - viewHeight >= 0);
}

const setCookie = function (cname, cvalue, expires) {
	document.cookie = cname + '=' + cvalue + '; ' + expires;
};
const runCommissionedRobots = function () {
	var d = new Date();
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
