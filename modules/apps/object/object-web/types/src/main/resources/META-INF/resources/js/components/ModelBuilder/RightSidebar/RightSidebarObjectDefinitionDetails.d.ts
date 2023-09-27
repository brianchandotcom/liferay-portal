/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {KeyValuePair} from '../../ObjectDetails/EditObjectDetails';
import './RightSidebarObjectDefinitionDetails.scss';
interface RightSidebarObjectDefinitionDetailsProps {
	companyKeyValuePairs: KeyValuePair[];
	siteKeyValuePairs: KeyValuePair[];
}
export declare function RightSidebarObjectDefinitionDetails({
	companyKeyValuePairs,
	siteKeyValuePairs,
}: RightSidebarObjectDefinitionDetailsProps): JSX.Element;
export {};
