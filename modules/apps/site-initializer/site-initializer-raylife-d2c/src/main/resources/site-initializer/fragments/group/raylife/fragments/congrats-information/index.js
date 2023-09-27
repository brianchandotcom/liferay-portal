/* eslint-disable no-undef */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const consentType = Liferay.Util.LocalStorage.TYPES.NECESSARY;

const applicationId = Liferay.Util.LocalStorage.getItem(
	'raylife-application-id',
	consentType
);

const quoteId = Liferay.Util.LocalStorage.getItem(
	'raylife-quote-id',
	consentType
);

const raylifeApplicationForm = JSON.parse(
	Liferay.Util.LocalStorage.getItem('raylife-application-form', consentType)
);

const orderId = Liferay.Util.LocalStorage.getItem('orderId', consentType);

const nowDate = new Date().toISOString().split('T')[0];

const fetchHeadless = async (url, options) => {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${
			window.location.origin
		}${Liferay.ThemeDisplay.getPathContext()}/${url}`,
		{
			...options,
			headers: {
				'Content-Type': 'application/json',
				'x-csrf-token': Liferay.authToken,
			},
		}
	);

	const data = await response.json();

	return data;
};

const addPolicyEntryData = async ({
	email,
	firstName,
	lastName,
	price,
	productName,
}) => {
	await fetchHeadless(`o/c/raylifepolicies/`, {
		body: JSON.stringify({
			commission: (price * 0.2).toString(),
			currencyType: 'USD',
			dataJSON: JSON.stringify({monthlyPremium: price / 12}),
			policyCreateDate: nowDate,
			policyOwnerEmail: email,
			policyOwnerName: `${firstName} ${lastName}`,
			policyStatus: {
				key: 'executed',
				name: 'Executed',
			},
			productName,
			r_quoteToPolicies_c_raylifeQuoteId: quoteId,
			r_userToPolicies_userId: Liferay.ThemeDisplay.getUserId(),
			startDate: nowDate,
			termPremium: price,
		}),
		method: 'POST',
	});
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
		body: JSON.stringify({
			applicationStatus: {
				key: 'bound',
				name: 'Bound',
			},
		}),
		method: 'PATCH',
	});
};

const setValueToElement = (element, value) => {
	if (element) {
		element.innerHTML = value;
	}
};

const currencyIntl = new Intl.NumberFormat('en-US', {
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
									typeof formattedValue === 'string'
										? formattedValue
										: ''
								}</td>
								</tr>`;
		})
		.join('');
};

const main = async () => {
	const [application, quote, orderItem] = await Promise.all([
		fetchHeadless(`o/c/raylifeapplications/${applicationId}`),
		fetchHeadless(`o/c/raylifequotes/${quoteId}`),
		fetchHeadless(`o/headless-commerce-admin-order/v1.0/orders/${orderId}`),
	]);

	const quoteDataJSON = JSON.parse(quote.dataJSON);

	const quoteDate = application.applicationCreateDate
		? new Date(application.applicationCreateDate)
		: new Date();

	const quoteDateNextYear = new Date(
		new Date(quoteDate).setFullYear(quoteDate.getFullYear() + 1)
	);

	setValueToElement(
		fragmentElement.querySelector('#congrats-info-title'),
		raylifeApplicationForm?.basics?.productName
	);

	setValueToElement(
		fragmentElement.querySelector('#congrats-info-policy'),
		`Policy: #${applicationId}`
	);

	if (Number(quoteDataJSON.price) === Number(orderItem.totalAmount * 2)) {
		setValueToElement(
			fragmentElement.querySelector('#congrats-price'),
			`$${Number(quoteDataJSON.price || 0).toLocaleString('en-US')}`
		);
	}
	else {
		const discountInPrice = quoteDataJSON.price * 0.05;

		const discountDescription = `You saved 5% ($${Number(
			discountInPrice || 0
		).toLocaleString('en-US')})`;

		setValueToElement(
			fragmentElement.querySelector('#congrats-price'),
			`$${Number(quoteDataJSON.price - discountInPrice || 0)
				.toFixed(2)
				.toLocaleString('en-US')}`
		);

		setValueToElement(
			fragmentElement.querySelector('#congrats-discount'),
			discountDescription
		);
	}
	setValueToElement(
		fragmentElement.querySelector('#congrats-info-date'),
		`${quoteDate.toLocaleDateString()} - ${quoteDateNextYear.toLocaleDateString()}`
	);

	buildList([
		{
			title: 'Per Occurrence Limit',
			value: quoteDataJSON.perOccuranceLimit,
		},
		{
			title: 'Aggregate Limit',
			value: quoteDataJSON.aggregateLimit,
		},
		{
			title: 'Business Personal Property',
			value: quoteDataJSON.businessPersonalProperty || false,
		},
		{
			title: 'Product Recall or Replacement',
			value: quoteDataJSON.productRecallOrReplacement,
		},
		{
			title: 'Money & Securities',
			value: quoteDataJSON.moneyAndSecurities || false,
		},
	]);

	if (application.applicationStatus.key !== 'bound') {
		addPolicyEntryData({...application, ...quoteDataJSON});
		sendDigitalSignaturePolicy(application);
		updateObjectPolicySent();
	}
};

main();
