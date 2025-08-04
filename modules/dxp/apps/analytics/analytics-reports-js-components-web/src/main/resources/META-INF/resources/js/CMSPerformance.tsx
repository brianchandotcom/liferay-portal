/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {ContextProvider} from './Context';
import {CheckPermissions} from './components/cms/CheckPermissions';
import GlobalFilters from './components/cms/GlobalFilters';
import OverviewMetrics from './components/cms/OverviewMetrics';
import {AssetMetrics} from './components/cms/asset-metrics/AssetMetrics';

import '../css/cms_performance.scss';
interface ICMSPerformanceProps extends React.HTMLAttributes<HTMLElement> {
	depotEntryId: number;
	externalReferenceCode: string;
	objectEntryFolderExternalReferenceCode: string;
}

const CMSPerformance: React.FC<ICMSPerformanceProps> = ({
	depotEntryId,
	externalReferenceCode,
	objectEntryFolderExternalReferenceCode,
}) => {
	return (
		<div className="cms-performance">
			<CheckPermissions depotEntryId={String(depotEntryId)}>
				<ContextProvider
					customState={{
						depotEntryId,
						externalReferenceCode,
						objectEntryFolderExternalReferenceCode,
					}}
				>
					<GlobalFilters />

					<OverviewMetrics />

					<AssetMetrics />
				</ContextProvider>
			</CheckPermissions>
		</div>
	);
};

export default CMSPerformance;
