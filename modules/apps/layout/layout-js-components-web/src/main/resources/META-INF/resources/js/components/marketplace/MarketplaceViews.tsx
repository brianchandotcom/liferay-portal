/* eslint-disable no-console */

/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {
	Marketplace,
	MarketplaceRest,
	MarketplaceView,
	Product,
	useMarketplaceContext,
} from '@liferay/marketplace-js-components-web';
import {fetch} from 'frontend-js-web';
import React from 'react';

import {InstallFragmentModalBody} from './InstallFragmentModal';

async function fetchFragmentBlob(marketplaceRest: MarketplaceRest, url: URL) {
	const response = await marketplaceRest.fetchMarketplace<Response>(
		url.pathname,
		{
			earlyReturn: true,
		}
	);

	return response.blob();
}

function getProductAttachmentBlob(
	marketplaceRest: MarketplaceRest,
	product: Product
) {
	return fetchFragmentBlob(
		marketplaceRest,
		new URL(product.attachments[0].src)
	);
}

async function uploadFragment(blob: Blob, name: string) {
	const formData = new FormData();

	formData.append(
		'file',
		blob,
		`${name.replace(' ', '-').toLowerCase()}.zip`
	);

	// Within the blob upload the .zip to the fragments endpoint
	// This is an example of import.

	await fetch('/o/headless-delivery/upload', {
		body: formData,
		method: 'POST',
	});
}

export default function MarketplaceViews() {
	const {marketplaceRest, product, setProduct, setView, view} =
		useMarketplaceContext();

	async function onClickInstall(product: Product) {
		setView(MarketplaceView.PURCHASE);
		setProduct(product);

		try {
			const cart = await marketplaceRest.createCart(product as Product, {
				orderTypeExternalReferenceCode: 'FRAGMENT',
			});

			await marketplaceRest.checkoutCart(cart);

			const blob = await getProductAttachmentBlob(
				marketplaceRest,
				product
			);

			if (blob) {
				await uploadFragment(blob, product.name);
			}

			Liferay.Util.openToast({
				message: Liferay.Language.get(
					'your-request-completed-successfully'
				),
				title: Liferay.Language.get('success'),
				type: 'success',
			});
		}
		catch (error) {
			console.error(error);

			Liferay.Util.openToast({
				message: Liferay.Language.get('an-unexpected-error-occurred'),
				title: Liferay.Language.get('danger'),
				type: 'danger',
			});
		}
	}

	return (
		<>
			{view === MarketplaceView.PRODUCTS && (
				<Marketplace.Products
					onClickProduct={(product) => {
						setProduct(product);

						setView(MarketplaceView.STOREFRONT);
					}}
				>
					{(product) => (
						<ClayButton
							className="w-100"
							onClick={() => {
								onClickInstall(product);
							}}
						>
							{Liferay.Language.get('install')}
						</ClayButton>
					)}
				</Marketplace.Products>
			)}

			{view === MarketplaceView.STOREFRONT && (
				<Marketplace.Storefront
					primaryButton={
						<ClayButton
							className="ml-auto mt-3 rounded"
							onClick={() => onClickInstall(product)}
						>
							{Liferay.Language.get('install')}
						</ClayButton>
					}
				/>
			)}

			{view === MarketplaceView.PURCHASE && (
				<div className="p-4">
					<InstallFragmentModalBody />
				</div>
			)}
		</>
	);
}
