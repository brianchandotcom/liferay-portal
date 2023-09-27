/* eslint-disable no-undef */
/* eslint-disable no-unused-expressions */
/* eslint-disable @liferay/portal/no-global-fetch */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const baseURL = Liferay.ThemeDisplay.getPortalURL();

const fetcher = async (url, {method = 'GET', ...options} = {}) => {
	const response = await fetch(`${baseURL}${url}`, {
		headers: {
			'content-type': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
		method,
		...options,
	});

	if (response.ok) {
		if (method === 'DELETE' || response.status === 204) {
			return;
		}

		return response.json();
	}

	console.error('Failed to fetch user data:', response.status);

	throw new Error(response.json());
};

Liferay.on('copy-link', () => {
	const url = window.location.href;
	navigator.clipboard.writeText(url);
	Liferay.Util.openToast({
		message: 'Copied link to the clipboard',
		type: 'success',
	});
});

Liferay.on('contact-publisher', () => {
	const emailAddress = fragmentElement.querySelector(
		'.banner__contact-button'
	).value;
	const mailtoLink = `mailto: ${emailAddress}`;

	window.location.href = mailtoLink;
});

Liferay.on('start-trial', () => {
	const finalURL = `purchase-product-form?productId=${configuration.productId}`;
	window.location.href = `${Liferay.ThemeDisplay.getPortalURL()}${getSiteURL()}/${finalURL}`;
});

const getSiteURL = () => {
	const layoutRelativeURL = Liferay.ThemeDisplay.getLayoutRelativeURL();

	if (layoutRelativeURL.includes('web')) {
		return layoutRelativeURL.split('/').slice(0, 3).join('/');
	}

	return '';
};

const getProducts = async () => {
	const products = fetcher(
		`/o/headless-commerce-admin-catalog/v1.0/products`
	);

	return products;
};

const getUrlString = (url) => {
	const urlSplit = url.split('/');
	const filteredUrl = urlSplit[urlSplit.length - 1];

	return filteredUrl;
};

const setCategoryLabels = async () => {
	const currentUrl = Liferay.currentURL;
	const labelDiv = fragmentElement.querySelector('#category-label');

	const products = (await getProducts()).items;

	const filteredLabels = products.reduce((labels, product) => {
		if (product.urls.en_US === getUrlString(currentUrl)) {
			const categoryTags = product.categories
				.filter(
					(category) =>
						category.vocabulary === 'marketplace solution category'
				)
				.map(
					(category) =>
						`<span class="banner__product-tag rounded mb-2 mr-2 mt-2 py-2 px-3">${category.name}</span>`
				)
				.join('');

			if (categoryTags) {
				labels.push(categoryTags);
			}
		}

		return labels;
	}, []);

	labelDiv.innerHTML = filteredLabels;
};

setCategoryLabels();
