/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import React, {ReactNode, useMemo, useState} from 'react';

import {MarketplaceView, useMarketplaceContext} from '../MarketplaceContext';
import Card from '../components/Card';
import Carousel from '../components/Carousel';
import PublisherSupportModal from '../components/Storefront/PublisherSupportModal';
import {MarketplaceProduct} from '../core/MarketplaceProduct';
import {sanitizeHTML} from '../util';

type MarketplaceStorefrontProps = {
	onClickBack?: () => void;
	primaryButton?: ReactNode;
};

export async function copyToClipboard(link: string) {
	await navigator.clipboard.writeText(link);

	Liferay.Util.openToast({
		message: Liferay.Language.get('copied-link-to-the-clipboard'),
		type: 'success',
	});
}

export function MarketplaceStorefront({
	onClickBack,
	primaryButton,
}: MarketplaceStorefrontProps) {
	const {marketplaceConfiguration, product, setView} =
		useMarketplaceContext();

	const [publisherSupportModalVisible, setPublisherSupportModalVisible] =
		useState(false);

	const marketplaceProduct = useMemo(
		() => new MarketplaceProduct(product),
		[product]
	);

	const storefrontItems = useMemo(() => {
		const editions = marketplaceProduct
			.getEditions()
			.map(({name}) => name)
			.join(', ');

		const platformOfferings = marketplaceProduct
			.getPlatformOfferings()
			.map(({name}) => name)
			.join(', ');

		return [
			{
				title: Liferay.Language.get('developer'),
				value: marketplaceProduct.catalogName,
			},
			{
				title: Liferay.Language.get('published-date'),
				value: new Intl.DateTimeFormat('en-US', {
					day: '2-digit',
					month: 'short',
					year: 'numeric',
				}).format(new Date(marketplaceProduct.createDate)),
			},
			{
				title: Liferay.Language.get('supported-offerings'),
				value: platformOfferings,
			},
			{
				title: Liferay.Language.get('supported-versions'),
				value: marketplaceProduct.specificationValues.LATEST_VERSION,
			},
			{
				title: Liferay.Language.get('edition'),
				value: editions,
			},
			{
				title: Liferay.Language.get('price'),
				value: marketplaceProduct.getPrice(),
			},
			{
				title: Liferay.Language.get('help-and-share'),
				value: (
					<div className="d-flex flex-wrap mt-2">
						{[
							{
								leftIcon: 'envelope-closed',
								onClick: () =>
									setPublisherSupportModalVisible(true),
								rightIcon: 'angle-right',
								text: Liferay.Language.get('publisher-support'),
							},
							{
								href: 'https://www.liferay.com/en/legal/marketplace-terms-of-service',
								leftIcon: 'document',
								rightIcon: 'angle-right',
								text: Liferay.Language.get(
									'terms-and-conditions'
								),
							},
						].map((button, index) => (
							<div
								className="align-items-center card-buttons d-flex w-100"
								key={index}
							>
								<ClayIcon
									className="mr-2"
									symbol={button.leftIcon}
								/>

								<a
									className="align-items-center d-flex justify-content-between text-decoration-none text-reset w-100"
									href={button.href}
									onClick={button.onClick}
									target="_blank"
								>
									<span className="text-truncate">
										{button.text}
									</span>

									<ClayIcon
										className="ml-2"
										symbol={button.rightIcon}
									/>
								</a>
							</div>
						))}
					</div>
				),
			},
			{
				title: Liferay.Language.get('share-link'),
				value: (
					<div className="align-items-center card-buttons d-flex w-100">
						<ClayIcon className="mr-2" symbol="link" />

						<a
							className="align-items-center d-flex justify-content-between text-decoration-none text-reset w-100"
							onClick={() =>
								copyToClipboard(
									`${marketplaceConfiguration.data?.url}/p/${marketplaceProduct.friendlyURL}`
								)
							}
							target="_blank"
						>
							<span className="text-truncate">
								{Liferay.Language.get('copy-and-share')}
							</span>
						</a>
					</div>
				),
			},
		];
	}, [marketplaceConfiguration.data?.url, marketplaceProduct]);

	const {icon: productTypeIcon, label: productTypeLabel} =
		marketplaceProduct.getProductType();

	return (
		<div className="p-4">
			<div>
				<ClayButton
					className="back-button mb-3"
					displayType="unstyled"
					onClick={() => {
						if (onClickBack) {
							return onClickBack();
						}

						setView(MarketplaceView.PRODUCTS);
					}}
				>
					<ClayIcon symbol="angle-left" />

					<span className="ml-1">
						{Liferay.Language.get('back-to-list')}
					</span>
				</ClayButton>

				<div className="align-items-center d-flex justify-content-between mt-2">
					<div className="d-flex">
						<img
							alt="app-icon"
							className="object-fit-contain rounded"
							draggable={false}
							height={70}
							src={marketplaceProduct.productImage}
							width={70}
						/>

						<div className="d-flex flex-column justify-content-center ml-3">
							<h1 className="mb-1">{product.name}</h1>

							<div className="align-items-start categories-container d-flex">
								<div className="align-items-center app-type d-flex px-2 py-1 rounded text-nowrap">
									<ClayIcon
										className="mr-2"
										symbol={productTypeIcon}
									/>

									<span className="text-capitalize">
										{productTypeLabel}
									</span>
								</div>

								<div className="categories-container d-flex flex-wrap">
									{marketplaceProduct
										.getAppCategories()
										.map((category, index) => (
											<span
												className="category-tag px-2 py-1 text-nowrap"
												key={index}
											>
												{category.name}
											</span>
										))}
								</div>
							</div>
						</div>
					</div>

					{primaryButton}
				</div>
			</div>

			<div className="card-description-text d-flex h-100 justify-content-between mt-4 w-100">
				<div className="storefront-section">
					<Carousel images={marketplaceProduct.getProductImages()} />

					<div className="mt-4">
						<Card
							highlight
							title={Liferay.Language.get('description')}
						>
							<div
								dangerouslySetInnerHTML={{
									__html: sanitizeHTML(product.description),
								}}
							/>
						</Card>
					</div>
				</div>

				<div className="ml-4 storefront-cards">
					{storefrontItems.map((storefrontItem, index) => (
						<Card key={index} title={storefrontItem.title}>
							{storefrontItem.value}
						</Card>
					))}
				</div>
			</div>

			{publisherSupportModalVisible && (
				<PublisherSupportModal
					onClose={() => setPublisherSupportModalVisible(false)}
					product={product}
				/>
			)}
		</div>
	);
}
