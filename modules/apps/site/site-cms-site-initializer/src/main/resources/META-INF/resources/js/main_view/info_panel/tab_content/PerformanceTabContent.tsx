/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CMSPerformance} from '@liferay/analytics-reports-js-components-web';
import React, {useContext} from 'react';

import {
	AssetTypeInfoPanelContext,
	IAssetTypeInfoPanelContext,
} from '../context';

const PerformanceTabContent = () => {
	const selectedAsset = useContext<IAssetTypeInfoPanelContext>(
		AssetTypeInfoPanelContext
	);

	return (
		<CMSPerformance
			depotEntryId={
				(selectedAsset?.objectEntries?.[0]?.embedded.scopeId ?? 0) - 1
			}
			externalReferenceCode={
				selectedAsset?.objectEntries?.[0]?.embedded
					.externalReferenceCode ?? ''
			}
			objectEntryFolderExternalReferenceCode={
				selectedAsset?.objectEntries?.[0]?.embedded
					.objectEntryFolderExternalReferenceCode ?? ''
			}
		/>
	);
};

export default PerformanceTabContent;
