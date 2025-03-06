/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayEmptyState from '@clayui/empty-state';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {
	MarketplaceModal,
	SearchResultsMessage,
} from '@liferay/layout-js-components-web';
import {
	APIResponse,
	MarketplaceRest,
	Product,
	useMarketplaceConfiguration,
} from '@liferay/marketplace-js-components-web';
import React, {useEffect, useRef, useState} from 'react';

import MarketplaceTabItem from './MarketplaceTabItem';

export default function MarketplaceSearchResults({
	searchValue,
}: {
	searchValue: string;
}) {
	const baseResourceURL = MarketplaceRest.getBaseResourceURL();

	const marketplaceConfiguration =
		useMarketplaceConfiguration(baseResourceURL);

	const [showResults, setShowResults] = useState(false);

	useEffect(() => {
		setShowResults(false);
	}, [searchValue, setShowResults]);

	return (
		<>
			{marketplaceConfiguration.authorized ? (
				<div className="page-editor__fragments-widgets__search-results-panel__marketplace-results">
					{showResults ? (
						<SearchResultsPanel searchValue={searchValue} />
					) : (
						<ClayButton
							className="p-3"
							displayType="link"
							onClick={() => {
								setShowResults(true);
							}}
							size="sm"
						>
							{Liferay.Language.get('see-marketplace-results')}
						</ClayButton>
					)}
				</div>
			) : null}
		</>
	);
}

function SearchResultsPanel({searchValue}: {searchValue: string}) {
	const baseResourceURL = MarketplaceRest.getBaseResourceURL();

	const marketplaceConfiguration =
		useMarketplaceConfiguration(baseResourceURL);

	const [loading, setLoading] = useState(marketplaceConfiguration?.loading);
	const [page, setPage] = useState(1);
	const [results, setResults] = useState<APIResponse<Product>>();

	const searchValueRef = useRef(searchValue);

	const hasMoreResults = results?.lastPage && results.lastPage > page;

	useEffect(() => {
		const marketplaceRest = marketplaceConfiguration?.data
			? new MarketplaceRest(
					baseResourceURL,
					marketplaceConfiguration.data
				)
			: null;

		if (!marketplaceRest || !marketplaceConfiguration?.authorized) {
			return;
		}

		setLoading(true);

		const urlSearchParams = new URLSearchParams({
			'accountId': '-1',
			'attachments.accountId': '-1',
			'filter': marketplaceRest.settings?.references?.fragmentsFilter,
			'images.accountId': '-1',
			'nestedFields': 'productSpecifications,skus,categories,images',
			'page': String(page),
			'pageSize': '20',
			'search': searchValueRef.current,
			'skus.accountId': '-1',
			'sort': 'name:asc',
		});

		marketplaceRest
			.getProducts(urlSearchParams)
			.then((nextResults) => {
				setResults((prevResults) => {
					if (prevResults?.items) {
						nextResults.items = prevResults.items.concat(
							nextResults.items
						);
					}

					return nextResults;
				});
				setLoading(false);
			})
			.catch((error: Error) =>
				console.error('Failed to fetch products:', error)
			)
			.finally(() => setLoading(false));
	}, [
		baseResourceURL,
		marketplaceConfiguration?.authorized,
		marketplaceConfiguration?.data,
		page,
	]);

	return (
		<>
			<p className="pb-2 pl-3 pt-3 text-3 text-secondary">
				{Liferay.Language.get('showing-results-from-marketplace')}
			</p>

			<SearchResults loading={loading} results={results} />

			{hasMoreResults && (
				<ClayButton
					className="p-3 text-secondary"
					displayType="link"
					onClick={() => {
						setPage((prevPage) => prevPage + 1);
					}}
					size="sm"
				>
					{Liferay.Language.get('load-more-results')}
				</ClayButton>
			)}
		</>
	);
}

function SearchResults({
	loading,
	results,
}: {
	loading: boolean | undefined;
	results?: APIResponse<Product>;
}) {
	return (
		<>
			{!loading && results?.items.length ? (
				<div className="px-3">
					{results.items.map((item: Product) => (
						<MarketplaceModal
							key={item.id}
							trigger={<MarketplaceTabItem item={item} />}
						/>
					))}
				</div>
			) : null}

			{!loading && !results?.items.length ? (
				<ClayEmptyState
					description={Liferay.Language.get(
						'try-again-with-a-different-search'
					)}
					imgSrc={`${Liferay.ThemeDisplay.getPathThemeImages()}/states/search_state.svg`}
					small
					title={Liferay.Language.get('no-results-found')}
				/>
			) : null}

			{loading ? (
				<ClayLoadingIndicator className="mt-3" size="sm" />
			) : null}

			{!loading && results ? (
				<SearchResultsMessage numberOfResults={results.items.length} />
			) : null}
		</>
	);
}
