/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import {openModal} from 'frontend-js-web';
import React, {Dispatch, SetStateAction, useContext, useEffect} from 'react';

import {EditAPIApplicationContext} from '../EditAPIApplicationContext';
import {CreateAPIEndpointModalContent} from '../modals/CreateAPIEndpointModalContent';
import {DeleteAPIEndpointModalContent} from '../modals/DeleteAPIEndpointModalContent';
import {getFilterRelatedItemURL} from '../utils/urlUtil';
import {getAPIEndpointsFDSProps} from './fdsUtils/endpointsFDSProps';

interface APIApplicationsTableProps {
	apiApplicationBaseURL: string;
	apiURLPaths: APIURLPaths;
	basePath: string;
	currentAPIApplicationId: string | null;
	portletId: string;
	readOnly: boolean;
	setMainEndpointNav: Dispatch<SetStateAction<MainEndpointNav>>;
}

export default function APIEndpointsTable({
	apiApplicationBaseURL,
	apiURLPaths,
	basePath,
	currentAPIApplicationId,
	portletId,
	setMainEndpointNav,
}: APIApplicationsTableProps) {
	const {setHideManagementButtons} = useContext(EditAPIApplicationContext);

	const createAPIEndpoint = {
		label: Liferay.Language.get('add-api-endpoint'),
		onClick: ({loadData}: {loadData: voidReturn}) => {
			openModal({
				center: true,
				contentComponent: ({closeModal}: {closeModal: voidReturn}) =>
					CreateAPIEndpointModalContent({
						apiApplicationBaseURL,
						apiEndpointsURLPath: apiURLPaths.endpoints,
						basePath,
						closeModal,
						currentAPIApplicationId,
						loadData,
						setMainEndpointNav,
					}),
				id: 'createAPIEndpointModal',
				size: 'md',
			});
		},
	};

	const endpointAPIURLPath = getFilterRelatedItemURL({
		apiURLPath: apiURLPaths.endpoints,
		filterQuery: `r_apiApplicationToAPIEndpoints_c_apiApplicationId eq '${currentAPIApplicationId}'`,
	});

	const deleteAPIEnpoint = (
		itemData: APIEndpointItem,
		loadData: voidReturn
	) => {
		openModal({
			center: true,
			contentComponent: ({closeModal}: {closeModal: voidReturn}) =>
				DeleteAPIEndpointModalContent({
					closeModal,
					itemData,
					loadData,
				}),
			id: 'deleteAPIEnpointModal',
			size: 'md',
			status: 'danger',
		});
	};

	function onActionDropdownItemClick({
		action,
		itemData,
		loadData,
	}: FDSItem<APIEndpointItem>) {
		if (action.id === 'copyEndpointURL') {
			navigator.clipboard.writeText(
				`${window.location.origin}/o/${apiApplicationBaseURL}${itemData.path}/`
			);
		}

		if (action.id === 'deleteAPIEndpoint') {
			deleteAPIEnpoint(itemData, loadData);
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
