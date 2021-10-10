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

const product = fragmentElement.querySelector('#product');

const fetchHeadless = async (url) => {
	const response = await fetch(`${window.location.origin}/${url}`, {
		headers: {
			'Content-Type': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
	});

	const data = await response.json();

	return data;
};

const retrieveQuoteContainer = fragmentElement.querySelector('#retrieve-quote');
const newQuoteContainer = fragmentElement.querySelector('#new-quote');
const businessEmailDeliveredContainer = fragmentElement.querySelector(
	'.business-email-delivered'
);
const newQuoteFormContainer = fragmentElement.querySelector('.new-quote-form');

// Action Buttons

const retrieveQuoteButton = fragmentElement.querySelector(
	'#retrieve-quote-button'
);
const newQuoteButton = fragmentElement.querySelector('#new-quote-button');
const continueQuoteButton = fragmentElement.querySelector('#continue-quote');
const getQuoteForm = fragmentElement.querySelector('#get-quote-form');
const zipContainer = fragmentElement.querySelector('#zip-container');
const productContainer = fragmentElement.querySelector('#product-container');

retrieveQuoteButton.onclick = function () {
	retrieveQuoteContainer.classList.add('d-none', 'invisible');
	newQuoteContainer.classList.remove('d-none', 'invisible');
};

newQuoteButton.onclick = function () {
	newQuoteContainer.classList.add('d-none', 'invisible');
	retrieveQuoteContainer.classList.remove('d-none', 'invisible');
};

continueQuoteButton.onclick = function () {
	const email = newQuoteFormContainer.querySelector('input').value;

	fetch(`https://jsonplaceholder.typicode.com/users/1`)
		.then((response) => response.json())
		.then((json) => {
			const title = businessEmailDeliveredContainer.querySelector('h2');
			const paragraph = businessEmailDeliveredContainer.querySelector(
				'p'
			);

			title.innerHTML = title.textContent.replace('{name}', json.name);

			paragraph.innerHTML = paragraph.textContent.replace(
				'{email}',
				email
			);

			businessEmailDeliveredContainer.classList.remove(
				'd-none',
				'invisible'
			);
			newQuoteFormContainer.classList.remove('d-flex', 'invisible');
			newQuoteFormContainer.classList.add('d-none', 'invisible');
		});
};

const getProductName = (productId) => {
	const options = product.options;

	for (let i = 0; i < options.length; i++) {
		if (options[i].value === productId) {
			return options[i].label;
		}
	}
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
