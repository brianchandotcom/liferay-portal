/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import React, {useCallback, useEffect, useMemo, useState} from 'react';

import {MarketplacePurchase, States} from './MarketplacePurchase';
import {
	CloudUserProject,
	Marketplace,
	MarketplaceProduct,
	MarketplaceView,
	PlacedOrder,
	Product,
	useMarketplaceContext,
} from './index';

import './style/index.scss';

export default function MarketplaceViews() {
	const [cloudUserProject, setCloudUserProject] =
		useState<CloudUserProject>();
	const [placedOrders, setPlacedOrders] = useState<PlacedOrder[]>([]);
	const [state, setState] = useState(States.CONFIRM_INSTALLATION);

	const {
		marketplaceConfiguration,
		marketplaceRest,
		product,
		setProduct,
		setView,
		view,
	} = useMarketplaceContext();

	const marketplaceProduct = useMemo(
		() => new MarketplaceProduct(product),
		[product]
	);

	const isProductInstalled = useCallback(
		(product: Product) =>
			placedOrders.some((placedOrder) =>
				placedOrder.placedOrderItems.some((placedOrderItem) => {
					let hasDeployment = false;

					try {
						const cloudProvisioningJSON = JSON.parse(
							placedOrder.customFields['cloud-provisioning']
						);

						hasDeployment =
							cloudProvisioningJSON?.length &&
							cloudProvisioningJSON[0]?.deployments?.some(
								(deployment: {id: string}) => deployment.id
							);
					}
					catch {}

					return (
						placedOrderItem.productId === product.productId &&
						hasDeployment
					);
				})
			),
		[placedOrders]
	);

	useEffect(() => {
		marketplaceRest
			.getPlacedOrders(
				new URLSearchParams({
					filter: "orderTypeExternalReferenceCode eq 'CLOUDAPP'",
					nestedFields: 'placedOrderItems',
				})
			)
			.then((response) => setPlacedOrders(response.items))
			.catch(console.error);
	}, [marketplaceRest]);

	const cloudProject = marketplaceConfiguration.data?.settings
		?.cloudProject as string;

	useEffect(() => {
		if (!cloudProject) {
			return console.warn('No cloud project available');
		}

		marketplaceRest
			.getProjectUsage()
			.then(setCloudUserProject)
			.catch(console.error);
	}, [cloudProject, marketplaceRest, product]);
	const getState = useCallback(() => {
		if (!cloudProject) {
			return States.NO_PROJECT;
		}

		if (
			!marketplaceProduct.hasEnoughResources(
				cloudUserProject as CloudUserProject
			)
		) {
			return States.NO_RESOURCES;
		}

		return state;
	}, [cloudProject, cloudUserProject, marketplaceProduct, state]);

	async function onClickInstall() {
		setState(States.IN_PROGRESS);

		try {
			const order = await marketplaceRest.createCart(product as Product);

			await marketplaceRest.consoleProvisioningOrder(order);

			setState(States.SUCCESS);
		}
		catch (error) {
			console.error(error);

			setState(States.ERROR);
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
							{...(isProductInstalled(product) && {
								disabled: true,
								title: Liferay.Language.get('installed'),
							})}
							className="w-100"
							onClick={() => {
								setProduct(product);

								setState(States.CONFIRM_INSTALLATION);
								setView(MarketplaceView.PURCHASE);
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
							{...(isProductInstalled(product) && {
								disabled: true,
								title: Liferay.Language.get('installed'),
							})}
							className="ml-auto mt-3 rounded"
							onClick={() => {
								setState(States.CONFIRM_INSTALLATION);
								setView(MarketplaceView.PURCHASE);
							}}
						>
							{Liferay.Language.get('install')}
						</ClayButton>
					}
				/>
			)}

			{view === MarketplaceView.PURCHASE && (
				<Marketplace.Purchase
					rightTitle={
						<small className="align-items-end d-flex flex-column">
							<span className="font-weight-bold">
								{cloudProject}
							</span>

							<span>
								{marketplaceProduct?.getCloudResourceLabel(
									cloudUserProject as CloudUserProject
								)}
							</span>
						</small>
					}
				>
					<MarketplacePurchase
						onClickInstall={onClickInstall}
						projectId={cloudProject}
						state={getState()}
					/>
				</Marketplace.Purchase>
			)}
		</>
	);
}
