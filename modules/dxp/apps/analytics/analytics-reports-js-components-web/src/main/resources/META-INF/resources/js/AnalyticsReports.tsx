/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect, useRef} from 'react';

import AppSetup from './components/AppSetup';
import GlobalFilters from './components/GlobalFilters';
import OverviewMetrics from './components/OverviewMetrics';

import '../css/main.scss';

interface AnalyticsReports {
	contentPerformanceDataFetchURL: string;
}

const AnalyticsReports: React.FC<AnalyticsReports> = ({
	contentPerformanceDataFetchURL,
}) => {
	const analyticsReportsRef = useRef<HTMLDivElement>(null);

	// workaround to avoid a blink when the user clicks on the
	// performance tab to load the Analytics Reports component.

	useEffect(() => {
		setTimeout(() => {
			if (analyticsReportsRef.current) {
				analyticsReportsRef.current.style.opacity = '1';
			}
		}, 500);
	}, []);

	return (
		<div
			className="analytics-reports"
			ref={analyticsReportsRef}
			style={{opacity: 0}}
		>
			<AppSetup
				contentPerformanceDataFetchURL={contentPerformanceDataFetchURL}
			>
				<GlobalFilters />

				<OverviewMetrics />
			</AppSetup>
		</div>
	);
};

export default AnalyticsReports;
