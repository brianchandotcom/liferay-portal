/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Metadata} from 'next';

import ProductDetail from '../../../components/product/product-detail';
import {liferay} from '../../../liferay/index';
import {Product} from '../../../types/product';
import {getServerURL} from '../../../utils/server';
import {getProductDetails} from './data';

type Props = PageProps<'/product/[id]'>;

export async function generateMetadata(props: Props): Promise<Metadata> {
	const url = await getServerURL();

	const {id} = await props.params;

	const {data: product} = await getProductDetails({
		friendlyUrlPath: id,
		liferay,
		nestedFields: 'skus',
	});

	const defaultMetadata = {
		description: product?.description,
		title: `${product!.name} | ${liferay.getChannel().siteName}`,
	};

	return {
		...defaultMetadata,
		metadataBase: new URL(url),
		openGraph: {
			...defaultMetadata,
			images: [`/product/${id}/opengraph-image`],
			locale: 'en_US',
			siteName: liferay.getChannel().siteName,
			type: 'website',
		},
	};
}

export default async function PageDetail(props: Props) {
	const {id} = await props.params;

	const {data, error} = await getProductDetails({
		friendlyUrlPath: id,
		liferay,
		nestedFields: 'images,skus,productSpecifications',
	});

	if (error) {
		console.error(error);

		return <h1>Error</h1>;
	}

	return <ProductDetail product={data as Product} />;
}

export const dynamic = 'force-static';
export const revalidate = 1800; // 30 minutes;
