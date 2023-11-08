/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Dispatch, SetStateAction} from 'react';
interface PathParameterConfigurationProps {
	data: Partial<APIEndpointUIData>;
	displayError: EndpointDataError;
	selectedResponseBodySchema: SelectOption | undefined;
	setData: Dispatch<SetStateAction<Partial<APIEndpointUIData>>>;
}
export default function PathParameterConfiguration({
	data,
	displayError,
	selectedResponseBodySchema,
	setData,
}: PathParameterConfigurationProps): JSX.Element;
export {};
