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

import ClayAlert from '@clayui/alert';
import ClayLabel from '@clayui/label';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClaySticker from '@clayui/sticker';
import {useIsMounted} from '@liferay/frontend-js-react-web';
import AddToCart from 'commerce-frontend-js/components/add_to_cart/AddToCart';
import {isProductPurchasable} from 'commerce-frontend-js/utilities/index';
import React, {useEffect, useState} from 'react';

import {getCartItems} from '../utilities/data';
import {
	formatProductOptions,
	getProductName,
	getProductURL,
} from '../utilities/index';
import Price from './Price';

function SkuContent({
	accountId,
	cartId,
	channelGroupId,
	channelId,
	currencyCode,
	orderUUID,
	product: mappedProduct,
	productBaseURL,
}) {
	const product =
		mappedProduct.firstAvailableReplacementMappedProduct || mappedProduct;
	const isMounted = useIsMounted();
	const productURL = getProductURL(productBaseURL, product.urls);
	const productName = getProductName(product);
	const [inCart, setInCart] = useState(false);
	const [loading, setLoading] = useState(true);

	useEffect(() => {
		if (!cartId) {
			setInCart(false);

			setLoading(false);

			return;
		}

		setLoading(true);

		getCartItems(cartId, product.skuId)
			.then((jsonResponse) => {
				if (isMounted()) {
					setInCart(Boolean(jsonResponse.items?.length));

					setLoading(false);
				}
			})
			.catch(() => {
				if (isMounted()) {
					setLoading(false);
				}
			});
	}, [cartId, isMounted, product.skuId]);

	const productPurchasable = isProductPurchasable(
		product.availability,
		product.productConfiguration,
		product.purchasable
	);

	return (
		<div className="row">
			{mappedProduct.firstAvailableReplacementMappedProduct && (
				<div className="col-12">
					<ClayAlert
						className="p-2"
						displayType="warning"
						title={Liferay.Language.get('alert')}
					>
						{Liferay.Util.sub(
							Liferay.Language.get('x-has-been-replaced-by-x'),
							mappedProduct.sku,
							product.sku
						)}
					</ClayAlert>
				</div>
			)}

			{product.thumbnail && (
				<div className="col-auto">
					<ClaySticker className="fill-cover" size="xl">
						<ClaySticker.Image
							alt={productName}
							src={product.thumbnail}
						/>
					</ClaySticker>
				</div>
			)}

			<div className="col">
				<div className="mb-1">
					<ClayLabel
						displayType={productPurchasable ? 'success' : 'danger'}
					>
						{productPurchasable
							? Liferay.Language.get('available')
							: Liferay.Language.get('unavailable')}
					</ClayLabel>
				</div>

				<h4 className="component-title mb-1">
					<a href={productURL}>{product.sku}</a>
				</h4>

				<p className="component-subtitle mb-1">
					<a href={productURL}>{productName}</a>
				</p>

				<p>
					{Liferay.Language.get('quantity')}: {product.quantity}
				</p>
			</div>

			<div className="col-3 text-right">
				{loading ? (
					<ClayLoadingIndicator className="my-3" small />
				) : (
					<>
						<Price className="mb-1" {...product.price} />

						<AddToCart
							accountId={accountId}
							cartId={cartId}
							cartUUID={orderUUID}
							channel={{
								currencyCode,
								groupId: channelGroupId,
								id: channelId,
							}}
							cpInstance={{
								inCart,
								options: formatProductOptions(
									product.options,
									product.productOptions
								),
								quantity: product.quantity,
								skuId: product.skuId,
							}}
							disabled={!productPurchasable}
							settings={{
								alignment: 'full-width',
								iconOnly: true,
								inline: false,
								quantityDetails: {
									allowedQuantities:
										product.productConfiguration
											.allowedOrderQuantities,
									maxQuantity:
										product.productConfiguration
											.maxOrderQuantity,
									minQuantity:
										product.productConfiguration
											.minOrderQuantity,
									multipleQuantity:
										product.productConfiguration
											.multipleOrderQuantity,
								},
								size: 'sm',
							}}
						/>
					</>
				)}
			</div>
		</div>
	);
}

function DiagramContent({product, productBaseURL}) {
	const productURL = getProductURL(productBaseURL, product.urls);
	const productName = getProductName(product);

	return (
		<div className="row">
			{product.thumbnail && (
				<div className="col-auto">
					<ClaySticker className="fill-cover" size="xl">
						<ClaySticker.Image
							alt={productName}
							src={product.thumbnail}
						/>
					</ClaySticker>
				</div>
			)}

			<div className="col">
				<h4 className="component-title">
					<a href={productURL}>{productName}</a>
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

function ExternalContent({product}) {
	return (
		<>
			<h4 className="mb-1">{product.sku || product.name}</h4>

			{!!product.quantity && (
				<p className="mb-0">
					{Liferay.Language.get('quantity')}: {product.quantity}
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
	const Renderer = ContentsMap[selectedPin.mappedProduct.type];

	return (
		<div className="diagram-storefront-tooltip">
			<Renderer
				accountId={accountId}
				cartId={cartId}
				channelGroupId={channelGroupId}
				channelId={channelId}
				currencyCode={currencyCode}
				orderUUID={orderUUID}
				product={selectedPin.mappedProduct}
				productBaseURL={productBaseURL}
			/>
		</div>
	);
}

export default StorefrontTooltipContent;
