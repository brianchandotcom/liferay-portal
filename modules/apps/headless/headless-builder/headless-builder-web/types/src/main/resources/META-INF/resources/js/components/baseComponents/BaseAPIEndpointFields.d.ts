/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Dispatch, SetStateAction} from 'react';
interface BaseAPIApplicationFieldsProps {
	apiApplicationBaseURL: string;
	basePath: string;
	data: Partial<APIEndpointUIData>;
	displayError: EndpointDataError;
	editMode?: boolean;
	setData: Dispatch<SetStateAction<APIEndpointUIData>>;
}
export default function BaseAPIEndpointFields({
	apiApplicationBaseURL,
	basePath,
	data,
	displayError,
	editMode,
	setData,
}: BaseAPIApplicationFieldsProps): JSX.Element;
export {};
