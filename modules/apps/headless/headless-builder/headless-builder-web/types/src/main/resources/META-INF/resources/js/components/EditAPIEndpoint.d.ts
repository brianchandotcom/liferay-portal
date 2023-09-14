/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Dispatch, SetStateAction} from 'react';
import '../../css/main.scss';
interface EditAPIEndpointProps {
	apiApplicationBaseURL: string;
	apiURLPaths: APIURLPaths;
	basePath: string;
	currentAPIApplicationId: string;
	endpointId: number;
	setMainEndpointNav: Dispatch<SetStateAction<MainEndpointNav>>;
	setManagementButtonsProps: Dispatch<SetStateAction<ManagementButtonsProps>>;
	setStatus: Dispatch<SetStateAction<ApplicationStatusKeys>>;
	setTitle: Dispatch<SetStateAction<string>>;
}
export default function EditAPIEndpoint({
	apiApplicationBaseURL,
	apiURLPaths,
	basePath,
	currentAPIApplicationId,
	endpointId,
	setMainEndpointNav,
	setManagementButtonsProps,
	setStatus,
	setTitle,
}: EditAPIEndpointProps): JSX.Element;
export {};
