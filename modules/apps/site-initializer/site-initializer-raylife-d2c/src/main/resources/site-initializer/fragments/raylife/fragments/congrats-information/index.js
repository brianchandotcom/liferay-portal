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

const applicationId = localStorage.getItem('raylife-application-id');
const productId = localStorage.getItem('raylife-product-id');
const raylifeApplicationForm = JSON.parse(
	localStorage.getItem('raylife-application-form')
);

const fetchHeadless = async (url, options) => {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(`${window.location.origin}/${url}`, {
		...options,
		headers: {
			'Content-Type': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
	});

	const data = await response.json();

	return data;
};

const sendDigitalSignaturePolicy = async ({email, firstName, lastName}) => {
	await fetchHeadless(
		`o/digital-signature-rest/v1.0/sites/${themeDisplay.getSiteGroupId()}/ds-envelopes`,
		{
			body: JSON.stringify({
				dsDocument: [
					{
						fileEntryExternalReferenceCode: 'RAY001',
						fileExtension: 'pdf',
						id: '123',
						name: 'RaylifePolicy.pdf',
					},
				],
				dsRecipient: [
					{
						emailAddress: email,
						id: '123',
						name: `${firstName} ${lastName}`,
						status: 'sent',
					},
				],
				emailBlurb:
					'Thank you for purchasing Raylife Insurance for your business.  Please sign your policy document using DocuSign to complete your transaction and officially bind your policy.',
				emailSubject: 'Please Sign Your Raylife Policy Document',
				name: 'Raylife Insurance',
				senderEmailAddress: email,
				status: 'sent',
			}),
			method: 'POST',
		}
	);
};

const updateObjectPolicySent = async () => {
	await fetchHeadless(`o/c/raylifeapplications/${applicationId}`, {
		body: JSON.stringify({policySent: true}),
		method: 'PATCH',
	});
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

	return `$${currencyIntl.format(value || 0)}`;
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
				   <span class="ml-1">${title}</span>
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

	const quoteDate = application.dateCreated
		? new Date(application.dateCreated)
		: new Date();

	const quoteDateNextYear = new Date(
		new Date(quoteDate).setFullYear(quoteDate.getFullYear() + 1)
	);

	setValueToElement(
		fragmentElement.querySelector('#congrats-info-title'),
		raylifeApplicationForm?.basics?.productQuoteName
	);
	setValueToElement(
		fragmentElement.querySelector('#congrats-info-policy'),
		`Policy: #${applicationId}`
	);
	setValueToElement(
		fragmentElement.querySelector('#congrats-price'),
		`$${Number(quoteComparison.price || 0).toLocaleString('en-US')}`
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

	if (!application.policySent) {
		sendDigitalSignaturePolicy(application);
		updateObjectPolicySent();
	}
};

main();
