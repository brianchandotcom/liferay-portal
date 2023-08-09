/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import {openModal} from 'frontend-js-web';
import React, {Dispatch, SetStateAction, useContext, useEffect} from 'react';

import {CreateAPISchemaModalContent} from '../modals/CreateAPISchemaModalContent';
import {DeleteAPIApplicationModalContent} from '../modals/DeleteAPISchemaModalContent';
import {getFilterRelatedItemURL} from '../utils/urlUtil';
import {getAPISchemasFDSProps} from './fdsUtils/schemasFDSProps';

interface APIApplicationsTableProps {
	apiURLPaths: APIURLPaths;
	currentAPIApplicationId: string | null;
	portletId: string;
}

export default function APIApplicationsSchemasTable({
	apiURLPaths,
	currentAPIApplicationId,
	portletId,
}: APIApplicationsTableProps) {
	const {setHideManagementButtons} = useContext(EditAPIApplicationContext);

	const createAPIApplicationSchema = {
		label: Liferay.Language.get('add-new-schema'),
		onClick: ({loadData}: {loadData: voidReturn}) => {
			openModal({
				center: true,
				contentComponent: ({closeModal}: {closeModal: voidReturn}) =>
					CreateAPISchemaModalContent({
						apiSchemasURLPath: apiURLPaths.schemas,
						closeModal,
						currentAPIApplicationId,
						loadData,
					}),
				id: 'createAPISchemaModal',
				size: 'md',
			});
		},
	};

	const schemaAPIURLPath = getFilterRelatedItemURL({
		apiURLPath: apiURLPaths.schemas,
		filterQuery: `r_apiApplicationToAPISchemas_c_apiApplicationId eq '${currentAPIApplicationId}'`,
	});

	const deleteAPISchema = (
		itemData: APIApplicationSchemaItem,
		loadData: voidReturn
	) => {
		openModal({
			center: true,
			contentComponent: ({closeModal}: {closeModal: voidReturn}) =>
				DeleteAPIApplicationModalContent({
					closeModal,
					itemData,
					loadData,
				}),
			id: 'deleteAPISchemaModal',
			size: 'md',
			status: 'danger',
		});
	};

	function onActionDropdownItemClick({
		action,
		itemData,
		loadData,
	}: FDSItem<APIApplicationSchemaItem>) {
		if (action.id === 'deleteAPIApplicationSchema') {
			deleteAPISchema(itemData, loadData);
		}
	}

	useEffect(() => {
		setHideManagementButtons(true);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<FrontendDataSet
			{...getAPISchemasFDSProps(schemaAPIURLPath, portletId)}
			creationMenu={{
				primaryItems: [createAPIApplicationSchema],
			}}
			onActionDropdownItemClick={onActionDropdownItemClick}
		/>
	);
}
