/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import {openModal} from 'frontend-js-web';
import React from 'react';

import {CreateAPIApplicationModalContent} from '../modals/CreateAPIApplicationModalContent';
import {DeleteAPIApplicationModalContent} from '../modals/DeleteAPIApplicationModalContent';
import {getAPIApplicationsFDSProps} from './fdsUtils/fdsProps';

interface APIApplicationsTableProps {
	apiURLPaths: APIURLPaths;
	portletId: string;
	readOnly: boolean;
}

export default function APIApplicationsTable({
	apiURLPaths,
	portletId,
	readOnly,
}: APIApplicationsTableProps) {
	const createAPIApplication = {
		onClick: ({loadData}: {loadData: voidReturn}) => {
			openModal({
				center: true,
				contentComponent: ({closeModal}: {closeModal: voidReturn}) =>
					CreateAPIApplicationModalContent({
						apiApplicationsURLPath: apiURLPaths.applications,
						closeModal,
						loadData,
					}),
				id: 'createAPIApplicationModal',
				size: 'md',
			});
		},
	};

	const deleteAPIApplication = (itemData: ItemData, loadData: voidReturn) => {
		openModal({
			center: true,
			contentComponent: ({closeModal}: {closeModal: voidReturn}) =>
				DeleteAPIApplicationModalContent({
					closeModal,
					itemData,
					loadData,
				}),
			id: 'deleteAPIApplicationModal',
			size: 'md',
			status: 'danger',
		});
	};

	function onActionDropdownItemClick({action, itemData, loadData}: FDSItem) {
		if (action.id === 'deleteAPIApplication') {
			deleteAPIApplication(itemData, loadData);
		}
	}

	return (
		<FrontendDataSet
			{...getAPIApplicationsFDSProps(apiURLPaths.applications, portletId)}
			creationMenu={{
				primaryItems: readOnly ? [] : ([createAPIApplication] as any),
			}}
			onActionDropdownItemClick={onActionDropdownItemClick}
		/>
	);
}
