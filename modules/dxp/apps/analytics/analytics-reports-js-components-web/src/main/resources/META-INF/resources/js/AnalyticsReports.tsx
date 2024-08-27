/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import AppSetup from './components/AppSetup';
import GlobalFilters from './components/GlobalFilters';

interface AnalyticsReports {
	contentPerformanceDataFetchURL: string;
}

const AnalyticsReports: React.FC<AnalyticsReports> = ({
	contentPerformanceDataFetchURL,
}) => {
	return (
		<AppSetup
			contentPerformanceDataFetchURL={contentPerformanceDataFetchURL}
		>
			<GlobalFilters />
		</AppSetup>
	);
};

export default AnalyticsReports;
