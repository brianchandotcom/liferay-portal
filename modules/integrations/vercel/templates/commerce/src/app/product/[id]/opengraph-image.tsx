/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Product} from 'liferay-headless-rest-client/headless-commerce-delivery-catalog-v1.0';
import {ImageResponse} from 'next/og';

import {getSkuDetails} from '../../../lib/product';
import {liferay} from '../../../liferay/server';
import {getProductDetails} from './data';

export const runtime = 'nodejs';
export const size = {
	height: 630,
	width: 1200,
};
export const contentType = 'image/png';

export default async function OGImage({params}: {params: {id: string}}) {
	const response = await getProductDetails({
		friendlyUrlPath: params.id,
		liferay,
		nestedFields: 'skus',
	});

	const product = response.data as Product;

	const {availability, finalPrice} = getSkuDetails(product);

	return new ImageResponse(
		(
			<div
				style={{
					backgroundColor: 'white',
					borderBottom: '10px',
					borderBottomColor: '#80ACFF',
					display: 'flex',
					flexDirection: 'column',
					fontFamily: 'system-ui, sans-serif',
					height: '100%',
					padding: '48px',
					width: '100%',
				}}
			>
				<div
					style={{
						alignItems: 'center',
						display: 'flex',
						gap: '24px',
					}}
				>
					<img
						alt={product.name}
						src={product.urlImage}
						style={{
							borderRadius: '12px',
							height: 96,
							objectFit: 'cover',
							width: 96,
						}}
					/>

					<div style={{display: 'flex', flexDirection: 'column'}}>
						<span
							style={{
								color: '#111827',
								fontSize: 42,
								fontWeight: 700,
								lineHeight: 1.2,
							}}
						>
							{product.name}
						</span>

						<span
							style={{
								color: '#4B5563',
								fontSize: 28,
								marginTop: '8px',
							}}
						>
							{product.catalogName}
						</span>
					</div>
				</div>

				<div
					style={{
						color: '#374151',
						display: 'flex',
						fontSize: 28,
						marginTop: '32px',
						maxWidth: '90%',
					}}
				>
					{product.description}
				</div>

				<div
					style={{
						color: '#4B5563',
						display: 'flex',
						fontSize: 24,
						gap: '32px',
						marginTop: 'auto',
					}}
				>
					<div style={{display: 'flex'}}>
						💲 {finalPrice?.replace('$', '')}
					</div>

					<div style={{display: 'flex'}}>
						📦 {availability?.stockQuantity ?? 0} Available
					</div>

					<div style={{display: 'flex'}}>⭐ {100} Stars</div>
				</div>
			</div>
		),
		size
	);
}
