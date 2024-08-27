/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {Provider as ClayIconProvider} from '@clayui/core';
import ClayLink from '@clayui/link';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {AnalyticsReportsProvider} from '../AnalyticsReportsContext';
import EmptyState from './EmptyState';

interface IAppSetupProps extends React.HTMLAttributes<HTMLElement> {
	contentPerformanceDataFetchURL: string;
}

type Data = {
	analyticsSettingsPortletURL: string;
	assetLibrary: boolean;
	connectedToAnalyticsCloud: boolean;
	connectedToAssetLibrary: boolean;
	siteEditDepotEntryDepotAdminPortletURL: string;
	siteSyncedToAnalyticsCloud: boolean;
};

const AppSetup: React.FC<IAppSetupProps> = ({
	children,
	contentPerformanceDataFetchURL,
}) => {
	const [data, setData] = useState<Data | null>(null);
	const [loading, setLoading] = useState(true);
	const [error, setError] = useState('');

	useEffect(() => {
		async function fetchData() {
			try {
				const response = await fetch(contentPerformanceDataFetchURL, {
					method: 'GET',
				});

				const data = await response.json();

				if (data.error) {
					throw new Error(data.error);
				}

				setData(data);
			}
			catch (error: any) {
				console.error(error);

				setError(error.toString());
			}

			setLoading(false);
		}

		fetchData();
	}, [contentPerformanceDataFetchURL]);

	if (loading) {
		return (
			<ClayLoadingIndicator
				className="mt-10"
				displayType="primary"
				shape="squares"
				size="md"
			/>
		);
	}

	if (error) {
		return <ClayAlert displayType="danger" title={error} />;
	}

	if (data && !data.connectedToAnalyticsCloud) {
		return (
			<EmptyState
				description={Liferay.Language.get(
					'in-order-to-view-asset-performance,-your-liferay-dxp-instance-has-to-be-connected-with-liferay-analytics-cloud'
				)}
				title={Liferay.Language.get(
					'connect-to-liferay-analytics-cloud'
				)}
			>
				<ClayLink
					button
					displayType="secondary"
					href={data.analyticsSettingsPortletURL}
				>
					{Liferay.Language.get('connect')}
				</ClayLink>
			</EmptyState>
		);
	}

	if (data && data.assetLibrary && !data.connectedToAssetLibrary) {
		return (
			<EmptyState
				description={Liferay.Language.get(
					'in-order-to-view-asset-performance,-connect-sites-that-are-synced-to-analytics-cloud-to-your-asset-library'
				)}
				imgSrc={`${Liferay.ThemeDisplay.getPathThemeImages()}/states/search_state.svg`}
				title={Liferay.Language.get(
					'there-are-no-sites-connected-to-this-asset-library'
				)}
			>
				<ClayLink
					button
					displayType="secondary"
					href={data.siteEditDepotEntryDepotAdminPortletURL}
				>
					{Liferay.Language.get('connect')}
				</ClayLink>
			</EmptyState>
		);
	}

	if (data && !data.siteSyncedToAnalyticsCloud) {
		return (
			<EmptyState
				description={Liferay.Language.get(
					'in-order-to-view-asset-performance,-your-sites-have-to-be-synced-to-liferay-analytics-cloud'
				)}
				title={Liferay.Language.get('sync-to-analytics-cloud')}
			>
				<ClayLink
					button
					displayType="secondary"
					href={`${data.analyticsSettingsPortletURL}&currentPage=PROPERTIES`}
				>
					{Liferay.Language.get('sync')}
				</ClayLink>
			</EmptyState>
		);
	}

	return (
		<ClayIconProvider
			spritemap={`${Liferay.ThemeDisplay.getPathThemeImages()}/clay/icons.svg`}
		>
			<AnalyticsReportsProvider>{children}</AnalyticsReportsProvider>
		</ClayIconProvider>
	);
};

export default AppSetup;
