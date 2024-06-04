/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import '../../css/FDSEntries.scss';
import {ISelectionFilter} from '../utils/types';
interface IRestSchemaSelectionProps {
	filter?: ISelectionFilter;
	namespace: string;
	onChange: Function;
	requiredRESTApplicationValidationError: boolean;
	restApplications: string[];
	restEndpointValidationError: boolean;
	restSchemaValidationError: boolean;
}
declare function RestSchemaSelection({
	filter,
	namespace,
	onChange,
	requiredRESTApplicationValidationError,
	restApplications,
	restEndpointValidationError,
	restSchemaValidationError,
}: IRestSchemaSelectionProps): JSX.Element;
export default RestSchemaSelection;
