import {Metadata} from 'next';
import {Product} from 'liferay-headless-rest-client/headless-commerce-delivery-catalog-v1.0';
import ProductDetail from '@/components/product/product-detail';

import {getServerURL} from '@/lib/server';
import {getProductDetails} from './data';
import {liferay} from '@/liferay/server';

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
		title: `${product!.name} | ${liferay.getChannel().siteName}`,
		description: product?.description,
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
