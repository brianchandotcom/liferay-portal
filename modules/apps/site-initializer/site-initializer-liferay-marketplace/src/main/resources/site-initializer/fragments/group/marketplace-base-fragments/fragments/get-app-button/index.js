/* eslint-disable no-undef */
/* eslint-disable @liferay/portal/no-global-fetch */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const contactPublisherButtonElement = fragmentElement.querySelector(
	'button#contact-publisher'
);
const getAppButtonElement = fragmentElement.querySelector('button#get-app');
const getAppDescriptionElement = fragmentElement.querySelector(
	'#get-app-description'
);
const tooltipElement = fragmentElement.querySelector('.clay-tooltip-bottom');

const productId = fragmentElement
	.querySelector('.product-id')
	.innerText.replace(/[\n\r]+|[\s]{2,}/g, ' ')
	.trim();

const getSkuOptionValue = (sku, optionValue) =>
	sku.toLowerCase() === optionValue ||
	(sku?.skuOptions?.some(
		(skuOption) => skuOption.skuOptionValueKey === optionType
	) &&
		sku.purchasable);

const getProductPrice = (product) => {
	const {productSpecifications = []} = product;

	const priceModel = productSpecifications.find(
		(productSpecification) =>
			productSpecification.specificationKey === 'price-model'
	);

	if (priceModel?.value === 'Free') {
		return 'Free';
	}

	const licenseType = productSpecifications.find(
		(productSpecification) =>
			productSpecification.specificationKey === 'license-type'
	);

	const licenseTypeText =
		licenseType?.value === 'Perpetual' ? 'One-Time' : 'Annually';

	const hasTrialSku = product?.skus?.some(({sku}) =>
		getSkuOptionValue(sku, 'trial')
	);

	const standardSku =
		product?.skus?.find(({sku}) => getSkuOptionValue(sku, 'standard')) ??
		product?.skus[0];

	const standardPrice = standardSku
		? standardSku?.price?.priceFormatted?.replace(' ', '').replace(',', '.')
		: '';

	const price = `${hasTrialSku ? '30-day trial or' : ''} ${standardPrice}`;

	return `${price} ${licenseTypeText}`;
};

const customizeGetAppButton = (product) => {
	getAppButtonElement.onclick = () => {
		window.location.href = `${getSiteURL()}/get-app?productId=${productId}`;
	};

	getAppDescriptionElement.innerText = getProductPrice(product);
};

const getCommerceProduct = async (channelId) => {
	try {
		const response = await fetch(
			`/o/headless-commerce-delivery-catalog/v1.0/channels/${channelId}/products/${productId}?nestedFields=productSpecifications,skus&accountId=-1`
		);

		const product = await response.json();

		return product ?? {skus: []};
	}
	catch {
		return {skus: []};
	}
};

const getSiteURL = () => {
	const layoutRelativeURL = Liferay.ThemeDisplay.getLayoutRelativeURL();

	if (layoutRelativeURL.includes('web')) {
		return layoutRelativeURL.split('/').slice(0, 3).join('/');
	}

	return '';
};

const getModalTemplate = ({accountName, email, logoURL, website}) => `
<div class="d-flex">
	<div class="mr-2" style="width:24px;">
		${
			logoURL &&
			`<img class="rounded" src="${logoURL}" style="height: 24px; width: 24px;" />`
		}
	</div>

	<div style="color: #282934; font-size: 20px; font-weight: 600;">${accountName}</div>
</div>

${email && `<p className="my-2">${email}</p>`}

${
	website &&
	`<a href="${website}" target="_blank" style="font-weight: 600;">${website}</a>`
}
`;

const customizeUnavailableButton = async (product) => {
	contactPublisherButtonElement.onmouseover = () =>
		tooltipElement.classList.replace('hide', 'show');

	contactPublisherButtonElement.onmouseout = () =>
		tooltipElement.classList.replace('show', 'hide');

	if (!themeDisplay.isSignedIn()) {
		contactPublisherButtonElement.onclick = () => {
			sessionStorage.setItem(
				'@marketplace/redirect-to',
				window.location.href
			);

			location.href = `${getSiteURL()}/sign-in`;
		};

		return;
	}

	const customFields = product.customFields ?? [];

	const getCustomFieldValue = (name) =>
		customFields.find((customField) => customField.name === name)
			?.customValue?.data ?? '';

	contactPublisherButtonElement.onclick = () =>
		Liferay.Util.openModal({
			bodyHTML: getModalTemplate({
				accountName: product.catalogName || product.name,
				email: getCustomFieldValue('Support'),
				logoURL:
					getCustomFieldValue('Publisher Icon') ||
					`/o/${product.urlImage.split('/o/')[1]}`,
				website: getCustomFieldValue('Developer Website'),
			}),
			buttons: [
				{
					displayType: 'secondary',
					label: 'Close',
					type: 'cancel',
				},
			],
			center: true,
			headerHTML: 'Publisher Contact Info',
			size: 'md',
		});

	if (sessionStorage.getItem('@marketplace/redirect-to')) {
		contactPublisherButtonElement.click();

		sessionStorage.removeItem('@marketplace/redirect-to');
	}
};

const main = async () => {
	const channelId = Liferay.CommerceContext.commerceChannelId;

	if (!channelId) {
		return;
	}

	const product = await getCommerceProduct(channelId);
	const skuPublished = product.skus.some((sku) => sku.purchasable);

	if (skuPublished) {
		getAppButtonElement.classList.remove('d-none');

		return customizeGetAppButton(product);
	}

	contactPublisherButtonElement.classList.remove('d-none');

	customizeUnavailableButton(product);
};

main();
