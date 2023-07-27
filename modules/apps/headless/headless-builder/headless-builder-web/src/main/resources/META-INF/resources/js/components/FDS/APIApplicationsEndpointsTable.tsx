/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import React from 'react';

import {getAPIApplicationsEndpointsFDSProps} from './fdsUtils/endpointsFDSProps';

interface APIApplicationsTableProps {
	apiApplicationBaseURL: string;
	apiURLPaths: APIURLPaths;
	portletId: string;
	readOnly: boolean;
}

export default function APIApplicationsEndpointsTable({
	apiApplicationBaseURL,
	apiURLPaths,
	portletId,
}: APIApplicationsTableProps) {
	const createAPIApplicationEndpoint = {
		label: Liferay.Language.get('add-api-endpoint'),
	};

	function onActionDropdownItemClick({
		action,
		itemData,
	}: FDSItem<APIApplicationEndpointItem>) {
		if (action.id === 'copyEndpointURL') {
			navigator.clipboard.writeText(
				`${window.location.origin}/o/${apiApplicationBaseURL}${itemData.path}/`
			);
		}
	}

	return (
		<FrontendDataSet
			{...getAPIApplicationsEndpointsFDSProps(
				apiURLPaths.endpoints,
				portletId
			)}
			creationMenu={{
				primaryItems: [createAPIApplicationEndpoint],
			}}
			onActionDropdownItemClick={onActionDropdownItemClick}
		/>
	);
}
