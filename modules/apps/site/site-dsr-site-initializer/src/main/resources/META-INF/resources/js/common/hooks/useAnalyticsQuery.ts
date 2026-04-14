/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useIsMounted} from '@liferay/frontend-js-react-web';
import {useCallback, useEffect, useState} from 'react';

import {TAnalyticsFilter} from '../../main_view/analytics/types';
import AnalyticsService from '../services/AnalyticsService';
import formatVariables from '../utils/formatVariables';
import useAnalyticsFilters from './useAnalyticsFilters';
import useIsInViewport from './useIsInViewport';

export default function useAnalyticsQuery({
	element,
	query,
	settings = {checkViewportVisibility: true, useDevEnvData: true},
	variables,
}: {
	element: HTMLElement | null;
	query: {devEnvData: any; query: string};
	settings?: {
		checkViewportVisibility: boolean;
		useDevEnvData: boolean;
	};
	variables: any;
}) {
	const [isLoading, setIsLoading] = useState(true);
	const isMounted = useIsMounted();
	const isVisible = useIsInViewport(element);

	const [filters] = useAnalyticsFilters(null);
	const [response, setResponse] = useState(null);

	const sendRequest = useCallback(
		async (filters: TAnalyticsFilter) => {
			setIsLoading(true);

			if (settings.checkViewportVisibility && isVisible) {
				const {devEnvData, query: queryString} = query;

				if (settings.useDevEnvData) {
					setResponse(devEnvData);

					setIsLoading(false);

					return;
				}

				try {
					const response = await AnalyticsService.post(
						JSON.stringify({
							query: queryString,
							variables: formatVariables(
								filters,
								queryString,
								variables
							),
						})
					);

					setResponse(response?.data?.[0]);
				}
				catch (_ignore) {
					setResponse(null);
				}

				setIsLoading(false);
			}
		},
		[isVisible, query, setResponse, settings, variables]
	);

	useEffect(() => {
		if (isVisible && !response) {
			sendRequest(filters as TAnalyticsFilter);
		}
	}, [filters, isVisible, response, sendRequest]);

	useEffect(() => {
		const handleFiltersUpdate = () => {
			setIsLoading(true);
			setResponse(null);
		};

		if (isMounted()) {
			Liferay.on('dsr-filters-updated', handleFiltersUpdate);
		}

		return () => {
			if (isMounted()) {
				Liferay.detach('dsr-filters-updated', handleFiltersUpdate);
			}
		};
	}, [isMounted, sendRequest]);

	return {isLoading, response, sendRequest};
}
