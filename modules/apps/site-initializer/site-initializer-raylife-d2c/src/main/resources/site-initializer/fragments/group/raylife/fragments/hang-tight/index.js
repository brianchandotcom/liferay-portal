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

const {pathname} = new URL(Liferay.ThemeDisplay.getCanonicalURL());
const urlPaths = pathname.split('/').filter(Boolean);
const siteName = `/${urlPaths.slice(0, urlPaths.length - 1).join('/')}`;
const applicationId = localStorage.getItem('raylife-application-id');

const fetchHeadless = async (url, options) => {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(`${window.location.origin}/${url}`, {
		...options,
		headers: {
			'Content-Type': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
	});

	return await response.json();
};

const fetchHeadlessWithToken = async (url) => {
	const token = sessionStorage.getItem('raylife-guest-permission-token');

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(`${window.location.origin}/${url}`, {
		headers: {
			'Authorization': `Bearer ${token}`,
			'Content-Type': 'application/json',
		},
	});

	return await response.json();
};

const addQuoteEntryData = async (payload) => {
	await fetchHeadless(`o/c/raylifequotes/`, {
		body: JSON.stringify(payload),
		method: 'POST',
	});
};

const main = async () => {
	const [quote, quoteComparison] = await Promise.all([
		fetchHeadless(
			`o/c/raylifequotes/?filter=r_applicationToQuotes_c_raylifeApplicationId eq '${applicationId}'&fields=id`
		),
		fetchHeadlessWithToken(
			`o/c/quotecomparisons/scopes/${Liferay.ThemeDisplay.getScopeGroupId()}`
		),
	]);

	if (quote.totalCount === 0) {
		quoteComparison.items.forEach((item) => {
			const payload = {
				dataJSON: JSON.stringify({
					aggregateLimit: item.aggregateLimit,
					businessPersonalProperty: item.businessPersonalProperty,
					category: item.category,
					id: item.id,
					moneyAndSecurities: item.moneyAndSecurities,
					mostPopular: item.mostPopular,
					perOccuranceLimit: item.perOccuranceLimit,
					price: item.price,
					productRecallOrReplacement: item.productRecallOrReplacement,
					promo: item.promo,
				}),
				policyTerm: {
					key: 'yearToYear',
					name: 'Year to Year',
				},
				quoteCreateDate: new Date().toISOString().split('T')[0],
				r_applicationToQuotes_c_raylifeApplicationId: applicationId,
			};

			addQuoteEntryData(payload);
		});
	}

	window.location.href = `${siteName}/quote-comparison`;
};

main();
