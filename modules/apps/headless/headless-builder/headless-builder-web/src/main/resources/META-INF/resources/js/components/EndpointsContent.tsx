/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {Dispatch, SetStateAction, useState} from 'react';

import APIEndpointsTable from '../components/FDS/APIEndpointsTable';

interface EndpointsContentProps {
	apiApplicationData: APIApplicationUIData;
	apiURLPaths: APIURLPaths;
	basePath: string;
	currentAPIApplicationId: string;
	portletId: string;
	setManagementButtonsProps: Dispatch<SetStateAction<ManagementButtonsProps>>;
	setStatus: Dispatch<SetStateAction<ApplicationStatusKeys>>;
	setTitle: Dispatch<SetStateAction<string>>;
}

export default function EndpointsContent({
	apiApplicationData,
	apiURLPaths,
	basePath,
	currentAPIApplicationId,
	portletId,
	setManagementButtonsProps,
	setStatus,
	setTitle,
}: EndpointsContentProps) {
	const [mainEndpointNav, setMainEndpointNav] = useState<MainEndpointNav>(
		'list'
	);

	return (
		<>
			{mainEndpointNav === 'list' ? (
				<APIEndpointsTable
					apiApplicationBaseURL={apiApplicationData.baseURL}
					apiURLPaths={apiURLPaths}
					basePath={basePath}
					currentAPIApplicationId={currentAPIApplicationId}
					portletId={portletId}
					readOnly={false}
					setMainEndpointNav={setMainEndpointNav}
				/>
			) : (
				mainEndpointNav.edit && (
				)
			)}
		</>
	);
}
