/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Dispatch, SetStateAction} from 'react';
interface CreateAPIEndpointModalProps {
	apiApplicationBaseURL: string;
	apiEndpointsURLPath: string;
	basePath: string;
	closeModal: voidReturn;
	currentAPIApplicationId: string | null;
	loadData: voidReturn;
	setMainEndpointNav: Dispatch<SetStateAction<MainNav>>;
}
export declare function CreateAPIEndpointModalContent({
	apiApplicationBaseURL,
	apiEndpointsURLPath,
	basePath,
	closeModal,
	currentAPIApplicationId,
	loadData,
	setMainEndpointNav,
}: CreateAPIEndpointModalProps): JSX.Element;
export {};
