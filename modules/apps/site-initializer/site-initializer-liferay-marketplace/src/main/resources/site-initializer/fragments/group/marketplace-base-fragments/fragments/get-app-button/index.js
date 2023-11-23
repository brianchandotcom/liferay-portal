/* eslint-disable no-undef */
/* eslint-disable @liferay/portal/no-global-fetch */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const baseURL = themeDisplay.getPortalURL();
const channelId = Liferay.CommerceContext.commerceChannelId;

const productId = fragmentElement
	.querySelector('.product-id')
	.innerText.replace(/[\n\r]+|[\s]{2,}/g, ' ')
	.trim();

const buttonElement = fragmentElement.querySelector('button');
const tooltipElement = fragmentElement.querySelector('.clay-tooltip-bottom');

const fetcher = async (url) => {
	const response = await fetch(`${baseURL}${url}`, {
		headers: {
			'content-type': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
	});

	if (response.ok) {
		if (response.status === 204) {
			return;
		}

		return response.json();
	}

	console.error('Failed to fetch user data:', response.status);

	throw new Error(response.json());
};

const redirectPage = () => {
	if (layoutMode !== 'edit') {
		buttonElement.onclick = () => {
			window.location.href = `${getSiteURL()}/get-app?productId=${productId}`;
		};
	}
};

const getProductSkus = async (channelId) => {
	try {
		const response = await fetch(
			`/o/headless-commerce-delivery-catalog/v1.0/channels/${channelId}/products/${productId}?nestedFields=skus&accountId=-1`
		);

		const product = await response.json();

		return product?.skus ?? [];
	}
	catch {
		return [];
	}
};

const getAccounts = async () => {
	const response = await fetcher(
		`/o/headless-commerce-admin-account/v1.0/accounts`
	);

	return (await response.items) ?? [];
};

const getCatalogPerProduct = async () => {
	return fetcher(
		`/o/headless-commerce-admin-catalog/v1.0/products/${productId}/catalog`
	);
};

const getAccountAddress = async (accountId) => {
	return fetcher(
		`/o/headless-commerce-admin-account/v1.0/accounts/${accountId}/accountAddresses`
	);
};

const getSiteURL = () => {
	const layoutRelativeURL = Liferay.ThemeDisplay.getLayoutRelativeURL();

	if (layoutRelativeURL.includes('web')) {
		return layoutRelativeURL.split('/').slice(0, 3).join('/');
	}

	return '';
};

const openModal = () => {
	Liferay.Util.openModal({
		bodyHTML: `<div class="body-modal-container">
			<div class="body-content">
				<div class="body-modal-header d-flex" style="align-items: center; gap: 8px;">
					<div class="d-flex body-modal-logo" style="width:24px;">
					</div>
					<div class="body-modal-account" style="color: #282934; font-size: 20px; font-weight: 600;"></div>
				</div>
		
				<div class="body-modal-address pt-2">
					<div>
						<span class="street"></span>&nbsp;<span class="city"></span>
					</div>
					<div>
						<span class="zip"></span>
						<span class="countryISOCode"></span>
					</div>
				</div>
		
				<div class="body-modal-email d-flex pt-2"> 
					<span class="email"></span>
				</div>   
		
				<div class="body-modal-site-account d-flex pt-2"> 
					<a href=""></a>
				</div>   
			</div>  
		</div>`,
		buttons: [
			{
				displayType: 'secondary',
				label: 'Close',
				type: 'cancel',
			},
		],
		center: true,
		headerHTML: `Publisher Contact Info`,
		size: 'md',
	});
};

const setItemsInModal = (accountData, accountAdress) => {
	const accountImage = accountData?.logoURL;
	const accountName = accountData?.name;
	const accountEmail = accountData?.customFields['Contact Email'];
	const homePageUrlAccount = `${getSiteURL()}/publisher-dashboard#/${
		accountData.id
	}`;
	const accountAddresses = accountAdress;

	const addressStreet = document.querySelector(
		'.body-modal-container .body-content .body-modal-address .street'
	);
	const addressCity = document.querySelector(
		'.body-modal-container .body-content .body-modal-address .city'
	);
	const addressZip = document.querySelector(
		'.body-modal-container .body-content .body-modal-address .zip'
	);
	const addressCountry = document.querySelector(
		'.body-modal-container .body-content .body-modal-address .countryISOCode'
	);

	if (accountAddresses.length >= 1) {
		for (const address of accountAddresses) {
			addressStreet.textContent = `${address?.street1},`;
			addressCity.textContent = `${address?.city},`;
			addressZip.textContent = `${address?.zip},`;
			addressCountry.textContent = address?.countryISOCode;
			break;
		}
	}
	const containerImage = document.querySelector(
		'.body-modal-container .body-content .body-modal-header .body-modal-logo'
	);
	const image = document.createElement('img');

	image.setAttribute('alt', '');
	image.setAttribute('src', accountImage);
	image.setAttribute('class', 'logo');
	image.style.width = '100%';
	image.style.height = '24px';
	image.style.borderRadius = '72px';
	image.style.objectFit = 'cover';

	containerImage.appendChild(image);

	const account = document.querySelector(
		'.body-modal-container .body-content .body-modal-header .body-modal-account'
	);
	account.textContent = accountName;

	const email = document.querySelector(
		'.body-modal-container .body-content .body-modal-email .email'
	);
	email.textContent = accountEmail;

	const urlAccount = document.querySelector(
		'.body-modal-container .body-content .body-modal-site-account a'
	);
	const nameSplit = accountName.split(' ');
	const firstName = nameSplit[0].toLowerCase();
	const siteAccountName = firstName + '.com';
	urlAccount.innerHTML = siteAccountName;
	urlAccount.setAttribute('href', homePageUrlAccount);
};

const openContactPublisherModal = async () => {
	openModal();

	const [productCatalog, accounts] = await Promise.all([
		getCatalogPerProduct(),
		getAccounts(),
	]);

	const accountData = accounts.find(
		(account) =>
			account?.customFields?.CatalogId === productCatalog.id.toString()
	);

	const accountAddress = await getAccountAddress(accountData?.id);

	if (accountData) {
		setTimeout(() => {
			setItemsInModal(accountData, accountAddress?.items);
		}, 500);
	}
};

const customizeUnavailableButton = async () => {
	tooltipElement.querySelector('.tooltip-inner').textContent =
		'This app is not available for purchase in the Marketplace. It is either no longer available or supported. Please click to get further information on how to contact the publisher.';

	buttonElement.innerText = 'Contact Publisher';

	buttonElement.onmouseover = () =>
		tooltipElement.classList.replace('hide', 'show');

	buttonElement.onmouseout = () =>
		tooltipElement.classList.replace('show', 'hide');

	if (!themeDisplay.isSignedIn()) {
		const url = Liferay.ThemeDisplay.getLayoutRelativeURL();
		const path = url.split('/').pop();

		buttonElement.onclick = () => {
			sessionStorage.setItem('@marketplace/product-name', path);
			location.href = `${getSiteURL()}/sign-in`;
		};

		return;
	}

	if (sessionStorage.getItem('@marketplace/product-name')) {
		openContactPublisherModal();

		sessionStorage.removeItem('@marketplace/product-name');

		return;
	}

	buttonElement.onclick = () => {
		openContactPublisherModal();
	};
};

const main = async () => {
	const skus = await getProductSkus(channelId);
	const skuPublished = skus.some((sku) => sku.purchasable);

	buttonElement.classList.remove('d-none');

	if (skuPublished) {
		return redirectPage();
	}

	customizeUnavailableButton();
};

if (channelId) {
	main();
}
