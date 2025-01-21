/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {
	Marketplace,
	MarketplaceContextProvider,
	MarketplaceRest,
	MarketplaceView,
	Product,
	useMarketplaceContext,
} from '@liferay/marketplace-js-components-web';
import React, {useState} from 'react';

import {MarketplacePurchase, States} from './MarketplacePurchase';

import './style/index.scss';

function MarketplaceViews() {
	const [state, setState] = useState(States.CONFIRM_INSTALLATION);

	const {
		marketplaceConfiguration,
		marketplaceRest,
		product,
		setProduct,
		setView,
		view,
	} = useMarketplaceContext();

	const cloudProject = marketplaceConfiguration.data?.settings
		?.cloudProject as string;

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
							className="w-100"
							onClick={() => {
								setProduct(product);

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
							className="ml-auto mt-3 rounded"
							onClick={() => setView(MarketplaceView.PURCHASE)}
						>
							{Liferay.Language.get('install')}
						</ClayButton>
					}
				/>
			)}

			{view === MarketplaceView.PURCHASE && (
				<Marketplace.Purchase rightTitle={cloudProject}>
					<MarketplacePurchase
						onClickInstall={onClickInstall}
						state={cloudProject ? state : States.NO_PROJECT}
					/>
				</Marketplace.Purchase>
			)}
		</>
	);
}

const CommerceChannelAddPaymentMethod = () => (
	<MarketplaceContextProvider
		baseResourceURL={MarketplaceRest.getBaseResourceURL()}
		className="d-flex justify-content-end my-2 px-2 py-2"
		settings={{productFilter: 'payments'}}
	>
		<Marketplace.Modal
			noConnectionMessage={Liferay.Language.get(
				'you-are-trying-to-add-a-new-payment-method-through-the-marketplace,-but-the-connection-has-not-been-established-yet'
			)}
			trigger={
				<ClayButton size="sm">
					<ClayIcon className="mr-2" symbol="marketplace" />

					{Liferay.Language.get('add')}
				</ClayButton>
			}
		>
			<MarketplaceViews />
		</Marketplace.Modal>
	</MarketplaceContextProvider>
);

export default CommerceChannelAddPaymentMethod;
