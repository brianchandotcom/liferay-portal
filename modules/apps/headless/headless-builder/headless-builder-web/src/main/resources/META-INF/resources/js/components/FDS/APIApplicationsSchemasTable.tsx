/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import React from 'react';

import {getFilterRelatedItemURL} from '../utils/urlUtil';
import {getAPIApplicationsSchemasFDSProps} from './fdsUtils/schemasFDSProps.';

interface APIApplicationsTableProps {
	apiURLPaths: APIURLPaths;
	currentApplicationId: string | null;
	portletId: string;
	readOnly: boolean;
}

export default function APIApplicationsSchemasTable({
	apiURLPaths,
	currentApplicationId,
	portletId,
}: APIApplicationsTableProps) {
	const createAPIApplicationSchema = {
		label: Liferay.Language.get('add-new-schema'),
	};

	const schemaAPIURLPath = getFilterRelatedItemURL({
		apiURLPath: apiURLPaths.schemas,
		filterQuery: `r_apiApplicationToAPISchemas_c_apiApplicationId eq '${currentApplicationId}'`,
	});

	function onActionDropdownItemClick({
		action,
		itemData,
	}: FDSItem<APIApplicationEndpointItem>) {
		if (action.id === 'editAPIApplicationSchema') {
			return void itemData;
		}
	}

	return (
		<FrontendDataSet
			{...getAPIApplicationsSchemasFDSProps(schemaAPIURLPath, portletId)}
			creationMenu={{
				primaryItems: [createAPIApplicationSchema],
			}}
			onActionDropdownItemClick={onActionDropdownItemClick}
		/>
	);
}
