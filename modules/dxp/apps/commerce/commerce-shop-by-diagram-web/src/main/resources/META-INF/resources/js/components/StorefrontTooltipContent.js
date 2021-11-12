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

import ClayLabel from '@clayui/label';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClaySticker from '@clayui/sticker';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import AddToCart from 'commerce-frontend-js/components/add_to_cart/AddToCart';
import React, {useEffect, useState} from 'react';

import {getProduct} from '../utilities/data';
import {formatProductOptions, getProductURL} from '../utilities/index';
import Price from './Price';

function SkuContent({
	accountId,
	cartId,
	channelGroupId,
	channelId,
	currencyCode,
	orderUUID,
	product,
	productBaseURL,
	quantity,
	quantityDetails,
	sku,
}) {
	return (
		<div className="row">
			{product.urlImage && (
				<div className="col-auto">
					<ClaySticker className="fill-cover" size="xl">
						<ClaySticker.Image
							alt={product.name}
							src={product.urlImage}
						/>
					</ClaySticker>
				</div>
			)}

			<div className="col">
				<div className="mb-1">
					{sku?.availability && (
						<ClayLabel
							displayType={
								sku.availability.label === 'available'
									? 'success'
									: 'danger'
							}
						>
							{sku.availability.label_i18n}
						</ClayLabel>
					)}
				</div>

				<h4 className="component-title mb-1">
					<a href={getProductURL(productBaseURL, product.urls)}>
						{product.name}
					</a>
				</h4>

				<p>
					{Liferay.Language.get('quantity')}: {quantity}
				</p>
			</div>

			{sku && (
				<div className="col-auto text-right">
					<Price className="mb-1" {...sku.price} />

					<AddToCart
						channel={{
							currencyCode,
							groupId: channelGroupId,
							id: channelId,
						}}
						cpInstance={{
							accountId,
							isInCart: false,
							options: formatProductOptions(
								sku.options,
								product.productOptions
							),
							skuId: sku.id,
						}}
						orderId={cartId}
						orderUUID={orderUUID}
						quantity={quantity}
						settings={{
							alignment: 'full-width',
							block: true,
							iconOnly: true,
							withQuantity: {
								allowedQuantities:
									quantityDetails.allowedOrderQuantities,
								maxQuantity: quantityDetails.maxOrderQuantity,
								minQuantity: quantityDetails.minOrderQuantity,
								multipleQuantity:
									quantityDetails.multipleOrderQuantity,
							},
						}}
					/>
				</div>
			)}
		</div>
	);
}

function DiagramContent({product, productBaseURL}) {
	const productURL = getProductURL(productBaseURL, product.urls);

	return (
		<div className="row">
			{product.urlImage && (
				<div className="col-auto">
					<ClaySticker className="fill-cover" size="xl">
						<ClaySticker.Image
							alt={product.name}
							src={product.urlImage}
						/>
					</ClaySticker>
				</div>
			)}

			<div className="col">
				<h4 className="component-title">
					<a href={productURL}>{product.name}</a>
				</h4>
			</div>

			<div className="col-auto">
				<a className="btn btn-secondary" href={productURL}>
					{Liferay.Language.get('view')}
				</a>
			</div>
		</div>
	);
}

function ExternalContent({product, quantity}) {
	return (
		<>
			<h4 className="mb-1">{product.sku || product.name}</h4>

			{!!quantity && (
				<p className="mb-0">
					{Liferay.Language.get('quantity')}: {quantity}
				</p>
			)}
		</>
	);
}

const ContentsMap = {
	diagram: DiagramContent,
	external: ExternalContent,
	sku: SkuContent,
};

function StorefrontTooltipContent({
	accountId,
	cartId,
	channelGroupId,
	channelId,
	currencyCode,
	orderUUID,
	productBaseURL,
	selectedPin,
}) {
	const [product, updateProduct] = useState(null);
	const [loading, updateLoading] = useState(false);
	const isMounted = useIsMounted();

	useEffect(() => {
		if (selectedPin.mappedProduct.type === 'external') {
			updateProduct(selectedPin.mappedProduct);

			return;
		}

		updateLoading(true);

		getProduct(selectedPin.mappedProduct.productId, channelId, accountId)
			.then((product) => {
				if (!isMounted()) {
					return;
				}

				updateProduct({
					type: selectedPin.mappedProduct.type,
					...product,
				});
			})
			.catch(() => {
				updateProduct({
					...selectedPin.mappedProduct,
					type: 'external',
				});
			})
			.finally(() => {
				updateLoading(false);
			});
	}, [accountId, channelId, isMounted, selectedPin]);

	const currentSku = product
		? product?.skus?.find(
				(skuData) => skuData.sku === selectedPin.mappedProduct.sku
		  )
		: null;
	const Renderer = product && ContentsMap[product.type];

	return (
		<div>
			{loading && <ClayLoadingIndicator className="my-3" small />}

			{!loading && product && (
				<Renderer
					accountId={accountId}
					cartId={cartId}
					channelGroupId={channelGroupId}
					channelId={channelId}
					currencyCode={currencyCode}
					orderUUID={orderUUID}
					product={product}
					productBaseURL={productBaseURL}
					quantity={selectedPin.mappedProduct.quantity}
					quantityDetails={product?.productConfiguration || {}}
					sku={currentSku}
				/>
			)}
		</div>
	);
}

export default StorefrontTooltipContent;
