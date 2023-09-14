/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Dispatch, SetStateAction} from 'react';
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
}: APIApplicationsTableProps): JSX.Element;
export {};
