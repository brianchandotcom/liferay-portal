/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const {pathname} = new URL(Liferay.ThemeDisplay.getCanonicalURL());

const urlPaths = pathname.split('/').filter(Boolean);

const siteName = `/${urlPaths.slice(0, urlPaths.length - 1).join('/')}`;
const applicationId = Liferay.Util.LocalStorage.getItem(
	'raylife-application-id',
	Liferay.Util.LocalStorage.TYPES.NECESSARY
);

const baseURL = window.location.origin + Liferay.ThemeDisplay.getPathContext();

const NEXT_STEP_DELAY = 1000;

const fetchHeadless = async (url, options) => {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(`${baseURL}/${url}`, {
		...options,
		headers: {
			'Content-Type': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
	});

	return response.json();
};

const fetchHeadlessWithToken = async (url) => {
	if (Liferay.ThemeDisplay.getUserName()) {
		return fetchHeadless(url);
	}

	const token = Liferay.Util.SessionStorage.getItem(
		'raylife-guest-permission-token',
		Liferay.Util.SessionStorage.TYPES.NECESSARY
	);

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(`${baseURL}/${url}`, {
		headers: {
			'Authorization': `Bearer ${token}`,
			'Content-Type': 'application/json',
		},
	});

	return response.json();
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
			`o/c/raylifequotes?filter=r_applicationToQuotes_c_raylifeApplicationId eq '${applicationId}'&fields=id`
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

	setTimeout(() => {
		window.location.href = `${siteName}/quote-comparison`;
	}, NEXT_STEP_DELAY);
};

main();
