/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayEmptyState from '@clayui/empty-state';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import {sub} from 'frontend-js-web';
import React, {ReactNode} from 'react';

import {useMarketplaceContext} from '../MarketplaceContext';
import {ManagementToolbar} from '../components/ManagementToolbar';
import {ProductCard} from '../components/ProductCard';
import {Product} from '../types';

const pageSizeDeltas = [
	{
		label: 8,
	},
	{
		label: 16,
	},
	{
		label: 24,
	},
	{
		label: 32,
	},
];

type MarketplaceProductsProps = {
	children: (item: Product) => ReactNode;
};

type ProductListViewProps = {
	onClickProduct: (product: Product) => void;
} & MarketplaceProductsProps;

const ProductListView: React.FC<MarketplaceProductsProps> = ({children}) => {
	const {
		productListView: {
			loading,
			productsResponse,
			searchParams,
			setProductSearchParams,
		},
	} = useMarketplaceContext();

	const {search} = searchParams;

	const products = productsResponse?.items ?? [];

	if (loading) {
		return (
			<>
				<ClayLoadingIndicator
					displayType="primary"
					shape="squares"
					size="lg"
				/>

				<div />
			</>
		);
	}

	if (!products.length) {
		return (
			<ClayEmptyState
				description={
					search
						? sub(
								Liferay.Language.get(
									'there-are-no-results-for-the-search-term-x'
								),
								search
							)
						: Liferay.Language.get('no-products-were-found')
				}
				imgSrc="/o/admin-theme/images/states/search_state.svg"
				title={Liferay.Language.get('no-results-were-found')}
			/>
		);
	}

	return (
		<>
			<div className="d-flex flex-wrap h-100 marketplace-search-results p-4">
				{products.map((product) => children(product))}
			</div>

			<div className="d-flex justify-content-end px-4 py-4 w-100">
				<ClayPaginationBarWithBasicItems
					activeDelta={searchParams.pageSize}
					className="w-100"
					defaultActive={searchParams.page}
					deltas={pageSizeDeltas}
					ellipsisBuffer={1}
					ellipsisProps={{
						'aria-label': Liferay.Language.get('more'),
						'title': Liferay.Language.get('more'),
					}}
					onActiveChange={(page: number) =>
						setProductSearchParams({...searchParams, page})
					}
					onDeltaChange={(pageSize: number) =>
						setProductSearchParams({...searchParams, pageSize})
					}
					totalItems={productsResponse?.totalCount ?? 0}
				/>
			</div>
		</>
	);
};

const MarketplaceProducts: React.FC<ProductListViewProps> = ({
	children,
	onClickProduct,
}) => (
	<div className="d-flex flex-column h-100 justify-content-between payment-methods-modal-body">
		<ManagementToolbar/>

		<ProductListView>
			{(product) => (
				<ProductCard onClick={onClickProduct} product={product}>
					{children(product)}
				</ProductCard>
			)}
		</ProductListView>
	</div>
);

export {MarketplaceProducts};
