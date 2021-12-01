/* eslint-disable @liferay/portal/no-global-fetch */
/* eslint-disable no-undef */
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

const getAccountSubscriptionGroupsByFilter = (filter) => ({
	query: `{
        c {
          accountSubscriptionGroups(filter: "accountKey eq '${filter}' and hasActivation eq true") {
              items {
                name
              }
            }
          }
    }`,
});

const doFetch = async (query) => {
	const queryString = JSON.stringify(query);

	const response = await fetch(`${window.location.origin}/o/graphql`, {
		body: queryString,
		headers: {
			'Content-Type': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
		method: 'POST',
	});

	const {data} = await response.json();

	return data.c.accountSubscriptionGroups.items;
};

const getSubscriptionKey = (name) => {
	return name.split(' ')[0].toLowerCase();
};

const htmlElement = (name, key) => {
	return `<li><a href="#" class="btn btn-sm btn-menu">
    <img class="mr-2" width="16" src="${window.location.origin}/webdav/${pathSplit[1]}/document_library/assets/navigation-menu/${key}_icon_gray.svg" alt="" />
    ${name}
  </a></li>`;
};

const setSrcIcon = (keybutton) => {
	let srcIcon = keybutton.children[0].src;
	if (srcIcon.substr(srcIcon.length - 3) !== 'svg') {
		srcIcon = srcIcon.split('/');
		srcIcon.pop();
		srcIcon = srcIcon.join('/');
	}

	return srcIcon;
};

const arrowToggleElementKey = 'customer-portal-arrow';
const productsElementKey = '#customer-portal-products';

const projectExternalReferenceCode = new URLSearchParams(
	window.location.search
).get('kor_id');
const currentProducts = fragmentElement.querySelector(productsElementKey);
let expandedHeightProducts;

const {pathname} = new URL(Liferay.ThemeDisplay.getCanonicalURL());
const pathSplit = pathname.split('/').filter(Boolean);

(async () => {
	try {
		if (projectExternalReferenceCode) {
			const accountSubscriptionGroups =
				(await doFetch(
					getAccountSubscriptionGroupsByFilter(
						projectExternalReferenceCode
					)
				)) || [];
			expandedHeightProducts = accountSubscriptionGroups.length * 40;

			currentProducts.innerHTML = accountSubscriptionGroups
				.map(({name}) => htmlElement(name, getSubscriptionKey(name)))
				.join('\n');
		}
	} catch (error) {
		console.error(error.message);
	}
})();

fragmentElement.addEventListener('click', (event) => {
	const lastButton = fragmentElement.querySelector('.active');
	let currentButton = event.target;

	if (currentButton.tagName === 'IMG') {
		currentButton = currentButton.parentElement;
	}

	if (
		event.target.id === 'customer-portal-toggle-products' ||
		event.target.id === arrowToggleElementKey
	) {
		const products = fragmentElement.querySelector(productsElementKey);
		const heightProducts = products.offsetHeight;

		if (heightProducts < expandedHeightProducts) {
			currentProducts.style.height = `${expandedHeightProducts}px`;
		} else {
			currentProducts.style.height = '0px';
		}

		const arrow = fragmentElement.querySelector(
			`#${arrowToggleElementKey}`
		);
		arrow.classList.toggle('left');
		arrow.classList.toggle('down');
	} else if (lastButton !== currentButton && currentButton.tagName === 'A') {
		currentButton.classList.toggle('active');
		lastButton.classList.toggle('active');

		if (currentButton.children[0] && currentButton.children[0].src) {
			currentButton.children[0].src = setSrcIcon(currentButton).replace(
				'_gray',
				''
			);
		}

		if (lastButton.children[0] && lastButton.children[0].src) {
			lastButton.children[0].src = setSrcIcon(lastButton).replace(
				'.svg',
				'_gray.svg'
			);
		}

		const toggleProducts = fragmentElement.querySelector(
			'#customer-portal-toggle-products'
		);
		const grandParentElementId =
			currentButton.parentElement.parentElement.id;

		if (
			(grandParentElementId === 'customer-portal-products' &&
				toggleProducts.classList.contains('text-neutral-10')) ||
			(toggleProducts.classList.contains('text-brand-primary') &&
				grandParentElementId !== 'customer-portal-products')
		) {
			toggleProducts.classList.toggle('text-neutral-10');
			toggleProducts.classList.toggle('text-brand-primary');
		}
	}
});
