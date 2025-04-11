/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {
	Marketplace,
	MarketplaceRest,
	MarketplaceView,
	PlacedOrder,
	Product,
	useMarketplaceContext,
} from '@liferay/marketplace-js-components-web';
import {openToast} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useCallback} from 'react';

import importZipFile from '../import/importZipFile';
import {InstallFragmentModalBody} from './InstallFragmentModal';

async function fetchFragmentBlob(
	marketplaceRest: MarketplaceRest,
	url: string
) {
	const response = await marketplaceRest.fetchMarketplace<Response>(url, {
		earlyReturn: true,
	});

	return response.blob();
}

function getProductVirtualEntryBlob(
	marketplaceRest: MarketplaceRest,
	placedOrder: PlacedOrder
): Promise<Blob> {
	const hasPlacedOrderItems = placedOrder.placedOrderItems.some(
		(placedOrderItem) => placedOrderItem?.virtualItems?.length
	);

	if (!hasPlacedOrderItems) {
		throw new Error('Product has no virtual entries.');
	}

	const [virtualItemURL] =
		placedOrder.placedOrderItems[0].virtualItemURLs ?? [];

	return fetchFragmentBlob(marketplaceRest, virtualItemURL);
}

interface MarketplaceViewsProps {
	fragmentPortletNamespace: string;
	fragmentsImportURL: string;
	hideBackButton?: boolean;
}

export default function MarketplaceViews({
	fragmentPortletNamespace,
	fragmentsImportURL,
	hideBackButton,
}: MarketplaceViewsProps) {
	const {
		marketplaceRest,
		modal: {onOpenChange},
		product,
		setProduct,
		setView,
		view,
	} = useMarketplaceContext();

	const handleImportFile = useCallback(
		async (file: File) => {
			try {
				await importZipFile({
					file,
					handleResponse: ({importResults}, file) => {
						if (!Object.keys(importResults).length) {
							openToast({
								message: sub(
									Liferay.Language.get(
										'no-new-items-were-imported'
									),
									file.name
								),
								type: 'info',
							});
						}
						else {
							window.location.reload();
						}
					},
					importURL: fragmentsImportURL,
					marketplace: true,
					overwriteStrategy: 'keep_both',
					portletNamespace: fragmentPortletNamespace,
				});
			}
			catch (error) {
				console.error('Import failed:', error);
			}
		},
		[fragmentsImportURL, fragmentPortletNamespace]
	);

	const handleInstallProduct = useCallback(
		async (product: Product) => {
			setView(MarketplaceView.PURCHASE);
			setProduct(product);

			try {
				const cart = await marketplaceRest.createCart(
					product as Product,
					{
						orderTypeExternalReferenceCode:
							'LOW_CODE_CONFIGURATION',
					}
				);

				await marketplaceRest.checkoutCart(cart);

				const placedOrder = await marketplaceRest.getPlacedOrder(
					cart.id,
					new URLSearchParams({nestedFields: 'placedOrderItems'})
				);

				const blob = await getProductVirtualEntryBlob(
					marketplaceRest,
					placedOrder
				);

				if (blob) {
					const file = new File(
						[blob],
						`${product.name.replace(' ', '-').toLowerCase()}.zip`,
						{type: 'application/zip'}
					);

					await handleImportFile(file);

					openToast({
						message: Liferay.Language.get(
							'your-request-completed-successfully'
						),
						title: Liferay.Language.get('success'),
						type: 'success',
					});
				}
			}
			catch (error) {
				console.error('Installation failed:', error);
				openToast({
					message: Liferay.Language.get(
						'an-unexpected-error-occurred'
					),
					title: Liferay.Language.get('danger'),
					type: 'danger',
				});
				onOpenChange(false);
			}
		},
		[marketplaceRest, setProduct, setView, handleImportFile, onOpenChange]
	);

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
							onClick={() => handleInstallProduct(product)}
						>
							{Liferay.Language.get('install')}
						</ClayButton>
					)}
				</Marketplace.Products>
			)}

			{view === MarketplaceView.STOREFRONT && (
				<Marketplace.Storefront
					onClickBack={
						hideBackButton
							? undefined
							: () => setView(MarketplaceView.PRODUCTS)
					}
					primaryButton={
						<ClayButton
							className="ml-auto mt-3 rounded"
							onClick={() => handleInstallProduct(product)}
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
