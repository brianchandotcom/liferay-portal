/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {ISelectionFilter} from '../../../../../utils/types';
interface IApiRestApplicationModalContentProps {
	filter?: ISelectionFilter;
	namespace: string;
	onChange: ({
		selectedItemKey,
		selectedItemLabel,
		selectedRESTApplication,
		selectedRESTEndpoint,
		selectedRESTSchema,
	}: {
		selectedItemKey: string;
		selectedItemLabel: string;
		selectedRESTApplication: string | undefined;
		selectedRESTEndpoint: string | undefined;
		selectedRESTSchema: string | undefined;
	}) => void;
	requiredRESTApplicationValidationError: boolean;
	restApplications: string[];
	restEndpointValidationError: boolean;
	restSchemaValidationError: boolean;
}
declare function ApiRestApplication({
	filter,
	namespace,
	onChange,
	requiredRESTApplicationValidationError,
	restApplications,
	restEndpointValidationError,
	restSchemaValidationError,
}: IApiRestApplicationModalContentProps): JSX.Element;
export default ApiRestApplication;
