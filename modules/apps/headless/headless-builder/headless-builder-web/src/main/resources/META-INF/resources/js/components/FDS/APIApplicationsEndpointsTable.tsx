/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import React from 'react';

import {getFilterRelatedItemURL} from '../utils/urlUtil';
import {getAPIApplicationsEndpointsFDSProps} from './fdsUtils/endpointsFDSProps';

interface APIApplicationsTableProps {
	apiApplicationBaseURL: string;
	apiURLPaths: APIURLPaths;
	currentApplicationId: string | null;
	portletId: string;
	readOnly: boolean;
}

export default function APIApplicationsEndpointsTable({
	apiApplicationBaseURL,
	apiURLPaths,
	currentApplicationId,
	portletId,
}: APIApplicationsTableProps) {
	const createAPIApplicationEndpoint = {
		label: Liferay.Language.get('add-api-endpoint'),
	};

	const endpointAPIURLPath = getFilterRelatedItemURL({
		apiURLPath: apiURLPaths.endpoints,
		filterQuery: `r_apiApplicationToAPIEndpoints_c_apiApplicationId eq '${currentApplicationId}'`,
	});

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
				endpointAPIURLPath,
				portletId
			)}
			creationMenu={{
				primaryItems: [createAPIApplicationEndpoint],
			}}
			onActionDropdownItemClick={onActionDropdownItemClick}
		/>
	);
}
