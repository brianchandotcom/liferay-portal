/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Dispatch, SetStateAction} from 'react';
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
}: EndpointsContentProps): JSX.Element;
export {};
