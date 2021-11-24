/* eslint-disable @liferay/portal/no-global-fetch */
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

const fetchHeadless = async (url, options) => {
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

const businessEmailDeliveredContainer = fragmentElement.querySelector(
	'.business-email-delivered'
);
const continueQuoteButton = fragmentElement.querySelector('#continue-quote');
const emailContainer = fragmentElement.querySelector('#email-container');
const getQuoteForm = fragmentElement.querySelector('#get-quote-form');
const newQuoteButton = fragmentElement.querySelector('#new-quote-button');
const newQuoteContainer = fragmentElement.querySelector('#new-quote');
const newQuoteFormContainer = fragmentElement.querySelector('.new-quote-form');
const product = fragmentElement.querySelector('#product');
const productContainer = fragmentElement.querySelector('#product-container');
const retrieveQuoteButton = fragmentElement.querySelector(
	'#retrieve-quote-button'
);
const retrieveQuoteContainer = fragmentElement.querySelector('#retrieve-quote');
const scopeGroupId = Liferay.ThemeDisplay.getScopeGroupId();
const zipContainer = fragmentElement.querySelector('#zip-container');

window.onload = function () {
	document.getElementById('zip').focus();
};

document.getElementById('zip').focus();

retrieveQuoteButton.onclick = function () {
	retrieveQuoteContainer.classList.add('d-none', 'invisible');
	newQuoteContainer.classList.remove('d-none', 'invisible');
};

newQuoteButton.onclick = function () {
	newQuoteContainer.classList.add('d-none', 'invisible');
	retrieveQuoteContainer.classList.remove('d-none', 'invisible');
};

continueQuoteButton.onclick = async function () {
	const email = newQuoteFormContainer.querySelector('input').value;
	const errorFeedback = fragmentElement.querySelector(
		'#email-container .error-feedback span'
	);

	if (!email) {
		errorFeedback.innerText = `Type an email address to retrieve a quote.`;

		return emailContainer.classList.add('has-error');
	}

	const raylifeApplicationResponse = await fetchHeadless(
		`o/c/raylifeapplications/scopes/${scopeGroupId}?filter=contains(email, '${email}')`
	);

	if (!raylifeApplicationResponse.items.length) {
		errorFeedback.innerHTML = `We’re unable to find a quote associated with this address. Please try again or <u>Start a New Quote</u>.`;

		return;
	}

	const raylifeApplication = raylifeApplicationResponse.items[0];
	const title = businessEmailDeliveredContainer.querySelector('h2');
	const paragraph = businessEmailDeliveredContainer.querySelector('p');

	title.innerHTML = title.textContent.replace(
		'{name}',
		`${raylifeApplication.firstName} ${raylifeApplication.lastName}`
	);

	paragraph.innerHTML = paragraph.textContent.replace('{email}', email);

	businessEmailDeliveredContainer.classList.remove('d-none', 'invisible');
	newQuoteFormContainer.classList.remove('d-flex', 'invisible');
	newQuoteFormContainer.classList.add('d-none', 'invisible');

	await fetchHeadless(`o/c/quoteretrieves/scopes/${scopeGroupId}`, {
		body: JSON.stringify({
			productName: 'Business Home Cover',
			quoteRetrieveLink: `${origin}${window.location.pathname}/get-a-quote?applicationId=${raylifeApplication.id}`,
			retrieveEmail: email,
		}),
		method: 'POST',
	});
};

getQuoteForm.onsubmit = function (event) {
	event.preventDefault();

	const formData = new FormData(event.target);
	const formProps = Object.fromEntries(formData);
	const maxCharactersZIP = 5;

	zipContainer.classList.remove('has-error');
	productContainer.classList.remove('has-error');

	if (localStorage.getItem('raylife-back-to-edit')) {
		localStorage.removeItem('raylife-back-to-edit');
	}

	const getProductName = (productId) => {
		const options = product.options;

		for (let i = 0; i < options.length; i++) {
			if (options[i].value === productId) {
				return options[i].label;
			}
		}
	};

	if (
		!formProps.zip ||
		formProps.zip.length !== maxCharactersZIP ||
		!formProps.product
	) {
		if (!formProps.zip || formProps.zip.length !== maxCharactersZIP) {
			zipContainer.classList.add('has-error');
		}
		if (!formProps.product) {
			productContainer.classList.add('has-error');
		}
	}
	else {
		localStorage.setItem(
			'raylife-product',
			JSON.stringify({
				...formProps,
				productName: getProductName(formProps.product),
			})
		);

		const {pathname} = new URL(Liferay.ThemeDisplay.getCanonicalURL());

		window.location.href = `${pathname}/get-a-quote`;
	}
};

fragmentElement.querySelector('#zip').onkeypress = (event) => {
	var charCode = event.which ? event.which : event.keyCode;

	return !(charCode > 31 && (charCode < 48 || charCode > 57));
};

(async () => {
	try {
		const taxonomyVocabularies = await fetchHeadless(
			`/o/headless-admin-taxonomy/v1.0/sites/${themeDisplay.getCompanyGroupId()}/taxonomy-vocabularies?filter=name eq 'Raylife'`
		);

		if (!taxonomyVocabularies?.items[0]) {
			return console.error('No Taxonomy Vocabulary found');
		}

		const taxonomyCategories = await fetchHeadless(
			`/o/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/${taxonomyVocabularies.items[0].id}/taxonomy-categories`
		);

		taxonomyCategories?.items.forEach((taxonomyVocabulary) => {
			product.add(
				new Option(taxonomyVocabulary.name, taxonomyVocabulary.id)
			);
		});
	}
	catch (error) {
		console.error(error.message);
	}
})();
