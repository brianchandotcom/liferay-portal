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

getQuoteForm.onsubmit = function (event) {
	event.preventDefault();
	const formData = new FormData(event.target);
	const formProps = Object.fromEntries(formData);
	const maxCharactersZIP = 5;

	zipContainer.classList.remove('has-error');
	productContainer.classList.remove('has-error');

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
	} else {
		document.cookie = 'raylife-zip=' + formProps.zip;
		document.cookie = 'raylife-product=' + formProps.product;
		window.location.href = '/web/raylife/get-a-quote';
	}
};

fragmentElement.querySelector('#zip').onkeypress = (event) => {
	var charCode = event.which ? event.which : event.keyCode;

	return !(charCode > 31 && (charCode < 48 || charCode > 57));
};

Liferay.Service(
	'/assetcategory/search-categories-display',
	{
		'+sort': 'com.liferay.portal.kernel.search.Sort',
		'sort.fieldName': 'name',
		'sort.type': 6,
		end: 50,
		groupIds: 0,
		parentCategoryIds: 0,
		start: 0,
		title: '',
		vocabularyIds: null,
	},
	(response) => {
		const product = fragmentElement.querySelector('#product');

		response.categories.forEach((category) => {
			product.add(new Option(category.name, category.categoryId));
		});
	}
);
