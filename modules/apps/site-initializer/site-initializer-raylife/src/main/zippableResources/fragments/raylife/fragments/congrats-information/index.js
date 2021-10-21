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

const applicationId = localStorage.getItem('raylife-application-id');
const productId = localStorage.getItem('raylife-product-id');
const businessType = JSON.parse(localStorage.getItem('raylife-product'));

const fetchHeadless = async (url) => {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(`${window.location.origin}/${url}`, {
		headers: {
			'Content-Type': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
	});

	const data = await response.json();

	return data;
};

const setValueToElement = (element, value) => {
	if (element) {
		element.innerHTML = value;
	}
};

var currencyIntl = new Intl.NumberFormat('en-US', {
	currency: 'USD',
});

const formatValue = (value) => {
	if (value === 'true' || value === 'false' || typeof value === 'boolean') {
		return JSON.parse(value);
	}

	return `$${currencyIntl.format(value)}`;
};

const buildList = (items = []) => {
	const tbody = fragmentElement.querySelector('.congrats-table-info tbody');

	tbody.innerHTML = items
		.map(({title, value}) => {
			const formattedValue = formatValue(value);
			const imageSrc = formattedValue
				? fragmentElement.querySelector('.congrats-information #check')
						?.currentSrc
				: fragmentElement.querySelector('.congrats-information #close')
						?.currentSrc;

			return `<tr>
			<td>
				<img alt="icon" src="${imageSrc}" />
				${title}
			</td>
			<td class="text-right">${
				typeof formattedValue === 'string' ? formattedValue : ''
			}</td>
			</tr>`;
		})
		.join('');
};

const main = async () => {
	const [application, quoteComparison] = await Promise.all([
		fetchHeadless(`o/c/raylifeapplications/${applicationId}`),
		fetchHeadless(`o/c/quotecomparisons/${productId}`),
	]);

	const quoteDate = new Date(application.dateCreated);
	const quoteDateNextYear = new Date(
		new Date(quoteDate).setFullYear(quoteDate.getFullYear() + 1)
	);

	setValueToElement(
		fragmentElement.querySelector('#congrats-info-title'),
		businessType.productName
	);
	setValueToElement(
		fragmentElement.querySelector('#congrats-info-policy'),
		`Policy: #${applicationId}`
	);
	setValueToElement(
		fragmentElement.querySelector('#congrats-price'),
		`$${Number(quoteComparison.price).toLocaleString('en-US')}`
	);
	setValueToElement(
		fragmentElement.querySelector('#congrats-info-date'),
		`${quoteDate.toLocaleDateString()} - ${quoteDateNextYear.toLocaleDateString()}`
	);

	buildList([
		{
			title: 'Per Occurrence Limit',
			value: quoteComparison.perOccuranceLimit,
		},
		{
			title: 'Aggregate Limit',
			value: quoteComparison.aggregateLimit,
		},
		{
			title: 'Business Personal Property',
			value: quoteComparison.businessPersonalProperty || false,
		},
		{
			title: 'Product Recall or Replacement',
			value: quoteComparison.productRecallOrReplacement,
		},
		{
			title: 'Money & Securities',
			value: quoteComparison.moneyAndSecurities || false,
		},
	]);
};

main();
