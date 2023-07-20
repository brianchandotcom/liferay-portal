/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import {openModal} from 'frontend-js-web';
import React from 'react';

import {CreateAPISchemaModalContent} from '../modals/CreateAPISchemaModalContent';
import {getAPISchemasFDSProps} from './fdsUtils/schemasFDSProps';

interface APIApplicationsTableProps {
	apiURLPaths: APIURLPaths;
	currentAPIApplicationID: string;
	portletId: string;
}

export default function APIApplicationsSchemasTable({
	apiURLPaths,
	currentAPIApplicationID,
	portletId,
}: APIApplicationsTableProps) {
	const createAPIApplicationSchema = {
		label: Liferay.Language.get('add-new-schema'),
		onClick: ({loadData}: {loadData: voidReturn}) => {
			openModal({
				center: true,
				contentComponent: ({closeModal}: {closeModal: voidReturn}) =>
					CreateAPISchemaModalContent({
						apiSchemasURLPath: apiURLPaths.schemas,
						closeModal,
						currentAPIApplicationID,
						loadData,
					}),
				id: 'createAPISchemaModal',
				size: 'md',
			});
		},
	};

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
			{...getAPISchemasFDSProps(apiURLPaths.schemas, portletId)}
			creationMenu={{
				primaryItems: [createAPIApplicationSchema],
			}}
			onActionDropdownItemClick={onActionDropdownItemClick}
		/>
	);
}
