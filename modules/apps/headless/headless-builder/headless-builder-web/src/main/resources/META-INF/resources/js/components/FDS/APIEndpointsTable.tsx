/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import React, {useContext, useEffect} from 'react';

import {EditAPIApplicationContext} from '../EditAPIApplicationContext';
import {getFilterRelatedItemURL} from '../utils/urlUtil';
import {getAPIEndpointsFDSProps} from './fdsUtils/endpointsFDSProps';

interface APIApplicationsTableProps {
	apiApplicationBaseURL: string;
	apiURLPaths: APIURLPaths;
	currentAPIApplicationId: string | null;
	portletId: string;
	readOnly: boolean;
}

export default function APIEndpointsTable({
	apiApplicationBaseURL,
	apiURLPaths,
	currentAPIApplicationId,
	portletId,
}: APIApplicationsTableProps) {
	const {setHideManagementButtons} = useContext(EditAPIApplicationContext);

	const createAPIEndpoint = {
		label: Liferay.Language.get('add-api-endpoint'),
	};

	const endpointAPIURLPath = getFilterRelatedItemURL({
		apiURLPath: apiURLPaths.endpoints,
		filterQuery: `r_apiApplicationToAPIEndpoints_c_apiApplicationId eq '${currentAPIApplicationId}'`,
	});

	function onActionDropdownItemClick({
		action,
		itemData,
	}: FDSItem<APIEndpointItem>) {
		if (action.id === 'copyEndpointURL') {
			navigator.clipboard.writeText(
				`${window.location.origin}/o/${apiApplicationBaseURL}${itemData.path}/`
			);
		}
	}

	useEffect(() => {
		setHideManagementButtons(true);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<FrontendDataSet
			{...getAPIEndpointsFDSProps(endpointAPIURLPath, portletId)}
			creationMenu={{
				primaryItems: [createAPIEndpoint],
			}}
			onActionDropdownItemClick={onActionDropdownItemClick}
		/>
	);
}
