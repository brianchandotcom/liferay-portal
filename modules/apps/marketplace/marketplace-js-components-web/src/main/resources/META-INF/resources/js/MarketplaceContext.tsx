/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useModal} from '@clayui/modal';
import React, {
	ReactNode,
	createContext,
	useContext,
	useEffect,
	useMemo,
	useState,
} from 'react';

import {MarketplaceRest} from './core/MarketplaceRest';
import {useMarketplaceConfiguration} from './hooks/useMarketplaceConfiguration';
import {APIResponse, MarketplaceConfiguration, Product} from './types';

import './style/index.scss';

export enum MarketplaceView {
	PRODUCTS,
	PURCHASE,
	STOREFRONT,
}

const productSearchParamsDefault = {
	page: 1,
	pageSize: 8,
	search: '',
	sortDirection: 'asc' as 'asc' | 'desc',
	sortKey: 'createDate',
};

type State = {
	marketplaceConfiguration: ReturnType<typeof useMarketplaceConfiguration>;
	marketplaceRest: MarketplaceRest;
	modal: ReturnType<typeof useModal>;
	product: Product;
	productListView: {
		loading: boolean;
		productsResponse?: APIResponse<Product>;
		searchParams: typeof productSearchParamsDefault;
		setProductSearchParams: React.Dispatch<
			React.SetStateAction<typeof productSearchParamsDefault>
		>;
	};
	setProduct: React.Dispatch<Product>;
	setView: React.Dispatch<MarketplaceView>;
	view: MarketplaceView;
};

const defaultState = {
	marketplaceConfiguration: {authorized: false, data: null, loading: true},
} as State;

export const MarketplaceContext = createContext<State>(defaultState);

export type MarketplaceContextProviderProps = {
	baseResourceURL: string;
	children: ReactNode;
	className?: string;
	defaultView?: MarketplaceView;
	onCloseModal?: () => void;
	settings: {
		productFilter?: 'all' | 'fragments' | 'payments';
		productFilterCustom?: string;
	};
};

function getProductFilter(
	references: MarketplaceConfiguration['settings']['references'],
	settings: MarketplaceContextProviderProps['settings']
) {
	if (settings.productFilterCustom) {
		return settings.productFilterCustom;
	}

	if (settings.productFilter === 'fragments') {
		return references?.fragmentsFilter;
	}

	if (settings.productFilter === 'payments') {
		return references?.paymentMethodFilter;
	}

	return '';
}

export function MarketplaceContextProvider({
	baseResourceURL,
	children,
	defaultView = MarketplaceView.PRODUCTS,
	className,
	settings,
	onCloseModal,
}: MarketplaceContextProviderProps) {
	const [loading, setLoading] = useState(false);
	const [product, setProduct] = useState<Product>();
	const [productSearchParams, setProductSearchParams] = useState(
		productSearchParamsDefault
	);
	const [productsResponse, setProductsResponse] =
		useState<APIResponse<Product>>();
	const [view, setView] = useState(defaultView);

	const modal = useModal({
		onClose: () => {
			setView(defaultView);
			onCloseModal?.();
		},
	});

	const marketplaceConfiguration =
		useMarketplaceConfiguration(baseResourceURL);

	const marketplaceRest = useMemo(
		() =>
			new MarketplaceRest(
				baseResourceURL,
				marketplaceConfiguration.data as MarketplaceConfiguration
			),
		[baseResourceURL, marketplaceConfiguration.data]
	);

	const authorized = marketplaceConfiguration.authorized;

	useEffect(() => {
		if (!authorized || !modal.open) {
			return;
		}

		setLoading(true);

		const urlSearchParams = new URLSearchParams({
			'accountId': '-1',
			'attachments.accountId': '-1',
			'filter': getProductFilter(
				marketplaceRest.settings.references,
				settings
			),
			'images.accountId': '-1',
			'nestedFields':
				'attachments,productSpecifications,skus,categories,images',
			'page': String(productSearchParams.page),
			'pageSize': String(productSearchParams.pageSize),
			'search': productSearchParams.search,
			'skus.accountId': '-1',
			...(productSearchParams.sortKey && {
				sort: `${productSearchParams.sortKey}:${productSearchParams.sortDirection}`,
			}),
		});

		marketplaceRest
			.getProducts(urlSearchParams)
			.then(setProductsResponse)
			.catch((error) => console.error('Failed to fetch products:', error))
			.finally(() => setLoading(false));
	}, [
		authorized,
		marketplaceRest,
		modal.open,
		productSearchParams.page,
		productSearchParams.pageSize,
		productSearchParams.search,
		productSearchParams.sortKey,
		productSearchParams.sortDirection,
		settings,
	]);

	return (
		<div className={className}>
			<MarketplaceContext.Provider
				value={{
					...defaultState,
					marketplaceConfiguration,
					marketplaceRest,
					modal,
					product: product || ({} as Product),
					productListView: {
						loading,
						productsResponse,
						searchParams: productSearchParams,
						setProductSearchParams,
					},
					setProduct,
					setView,
					view,
				}}
			>
				{children}
			</MarketplaceContext.Provider>
		</div>
	);
}

export function useMarketplaceContext() {
	return useContext(MarketplaceContext);
}
