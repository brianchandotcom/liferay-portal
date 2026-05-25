/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	useBrowserTabVisibility,
	useInterval,
	useIsMounted,
} from '@liferay/frontend-js-react-web';
import {useCallback, useEffect, useState} from 'react';

import {POLL_INTERVAL_MS} from '../constants';
import {getActivityMetrics} from '../services/ActivityMetricsService';
import {ActivityMetrics} from '../types/ActivityMetrics';

type Result = {
	data: ActivityMetrics | null;
	error: Error | null;
	loading: boolean;
	refetch: () => Promise<void>;
};

export default function useActivityMetrics(
	accountEntryExternalReferenceCode: string | undefined
): Result {
	const [data, setData] = useState<ActivityMetrics | null>(null);
	const [error, setError] = useState<Error | null>(null);
	const [loading, setLoading] = useState(true);

	const isMounted = useIsMounted();
	const isVisible = useBrowserTabVisibility();
	const schedule = useInterval();

	const fetchMetrics = useCallback(async () => {
		if (accountEntryExternalReferenceCode === undefined) {
			return;
		}

		try {
			const result = await getActivityMetrics(
				accountEntryExternalReferenceCode
			);

			if (isMounted()) {
				setData(result);
				setError(null);
			}
		}
		catch (caught) {
			if (isMounted()) {
				setError(caught as Error);
			}
		}
		finally {
			if (isMounted()) {
				setLoading(false);
			}
		}
	}, [accountEntryExternalReferenceCode, isMounted]);

	useEffect(() => {
		if (accountEntryExternalReferenceCode === undefined) {
			setLoading(false);

			return;
		}

		if (!isVisible) {
			return;
		}

		fetchMetrics();

		return schedule(fetchMetrics, POLL_INTERVAL_MS);
	}, [accountEntryExternalReferenceCode, fetchMetrics, isVisible, schedule]);

	return {data, error, loading, refetch: fetchMetrics};
}
