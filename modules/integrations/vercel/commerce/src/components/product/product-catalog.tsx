/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

'use client';

import {Grid, List, Search} from 'lucide-react';
import {useRouter, useSearchParams} from 'next/navigation';

import {PageProduct} from '../../types/product';
import EmptyState from '../empty-state';
import {PaginationBar} from '../pagination-bar';
import {Button} from '../ui/button';
import {Input} from '../ui/input';
import {ProductCard} from './product-card';

type Props = {
	keywords: string;
	pageProduct: PageProduct;
	viewMode: 'grid' | 'list';
};

export function ProductCatalog({keywords, pageProduct, viewMode}: Props) {
	const {items: products, totalCount} = pageProduct;

	const searchParams = useSearchParams();
	const router = useRouter();

	const setViewMode = (mode: Props['viewMode']) => {
		const url = new URL(location.href);
		url.searchParams.set('viewMode', mode);
		router.push(url.href);
	};

	const specificationValues = searchParams.getAll('specificationValues');

	if ((keywords || specificationValues.length) && totalCount === 0) {
		return (
			<main className="flex-1">
				<EmptyState />
			</main>
		);
	}

	return (
		<main className="flex-1">
			<div className="flex-1 max-w-m mb-4 relative">
				<Search className="-translate-y-1/2 absolute bg-white h-4 left-3 top-1/2 transform w-4" />

				<Input
					className="bg-white pl-10"
					defaultValue={keywords}
					onKeyDown={(event) => {
						if (event.key !== 'Enter') {
							return;
						}

						const _searchParams = new URLSearchParams(searchParams);

						_searchParams.set(
							'keywords',
							event.currentTarget.value
						);

						router.push(`/?${_searchParams.toString()}`);
					}}
					placeholder="Search products..."
				/>
			</div>

			<div className="flex items-center justify-between mb-6">
				<div className="text-sm">
					{totalCount}
					&nbsp; Products Available &nbsp;
					{keywords && (
						<span>
							for <b>{keywords}</b>
						</span>
					)}
				</div>

				<div className="flex items-center rounded-md">
					<Button
						className="rounded-r-none"
						onClick={() => setViewMode('grid')}
						size="sm"
						variant={viewMode === 'grid' ? 'default' : 'outline'}
					>
						<Grid className="h-4 w-4" />
					</Button>

					<Button
						className="rounded-l-none"
						onClick={() => setViewMode('list')}
						size="sm"
						variant={viewMode === 'list' ? 'default' : 'outline'}
					>
						<List className="h-4 w-4" />
					</Button>
				</div>
			</div>

			<div
				className={`${
					viewMode === 'list'
						? 'space-y-4'
						: 'grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4'
				}`}
			>
				{products.map((product) => (
					<ProductCard
						key={product.id}
						product={product}
						viewMode={viewMode}
					/>
				))}
			</div>

			<PaginationBar
				currentPage={pageProduct.page}
				onPageChange={(page) =>
					router.push(
						`/?${new URLSearchParams({
							keywords: keywords ?? '',
							page: String(page),
							pageSize: String(pageProduct.pageSize),
						})}`
					)
				}
				onPageSizeChange={(pageSize) => {
					router.push(
						`/?${new URLSearchParams({
							keywords: keywords ?? '',
							page: String(pageProduct.page),
							pageSize: String(pageSize),
						})}`
					);
				}}
				pageSize={pageProduct.pageSize}
				totalCount={pageProduct.totalCount}
			/>
		</main>
	);
}
