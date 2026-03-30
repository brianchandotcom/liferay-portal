/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Metadata} from 'next';

import {ProductCatalog} from '../components/product/product-catalog';
import {ProductFilters} from '../components/product/product-filters';
import {SetupGuide} from '../components/setup-guide';
import {liferay} from '../liferay/index';
import {getServerURL} from '../utils/server';
import {getProductsPage} from './data';

const defaultMetadata = {
	description:
		'Browse our product catalog using Liferay DXP as a Commerce Platform',
	title: `${liferay.getChannel().siteName} | Products`,
};

export async function generateMetadata(): Promise<Metadata> {
	const url = await getServerURL();

	return {
		...defaultMetadata,
		metadataBase: new URL(url),
		openGraph: {
			...defaultMetadata,
			locale: 'en_US',
			siteName: liferay.getChannel().siteName,
			type: 'website',
			url,
		},
	};
}

const getUniqueValue = <T,>(value: T | T[] | undefined, defaultValue: T) => {
	if (Array.isArray(value)) {
		return value[0];
	}

	return value || defaultValue;
};

const getArrayValue = <T,>(value: T | T[] | undefined, defaultValue: T[]) => {
	if (Array.isArray(value)) {
		return value;
	}

	if (!value) {
		return defaultValue;
	}

	return [value];
};

const getNormalizedSearchParams = async ({
	searchParams,
}: {
	searchParams: PageProps<'/'>['searchParams'];
}) => {
	const params = await searchParams;

	const page = getUniqueValue(params.page, '1');
	const pageSize = getUniqueValue(params.pageSize, '15');
	const keywords = getUniqueValue(params.keywords, undefined);
	const viewMode = getUniqueValue(params.viewMode, 'grid') as 'grid' | 'list';

	const specificationValues = getArrayValue(params.specificationValues, []);

	return {
		keywords,
		page,
		pageSize,
		specificationValues,
		viewMode,
	};
};

export default async function ProductCatalogPage(props: PageProps<'/'>) {
	const missingEnvVars = liferay.getMissingEnvVars();

	if (missingEnvVars.length) {
		return <SetupGuide envVars={missingEnvVars} />;
	}

	const {keywords, page, pageSize, specificationValues, viewMode} =
		await getNormalizedSearchParams({searchParams: props.searchParams});

	const {data, error} = await getProductsPage({
		keywords,
		liferay,
		page,
		pageSize,
		specificationValues,
	});

	if (error || !data) {
		return <h1>Error...</h1>;
	}

	return (
		<div className="flex flex-col gap-4 md:flex-row">
			<aside>
				<ProductFilters />
			</aside>

			<ProductCatalog
				keywords={keywords || ''}
				pageProduct={data}
				viewMode={viewMode}
			/>
		</div>
	);
}
