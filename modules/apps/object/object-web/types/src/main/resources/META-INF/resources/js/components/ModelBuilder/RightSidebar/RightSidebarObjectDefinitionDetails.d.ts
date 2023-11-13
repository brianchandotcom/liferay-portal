/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {Scope} from '../../ObjectDetails/EditObjectDetails';
import './RightSidebarObjectDefinitionDetails.scss';
interface RightSidebarObjectDefinitionDetailsProps {
	companies: Scope[];
	sites: Scope[];
}
export declare function RightSidebarObjectDefinitionDetails({
	companies,
	sites,
}: RightSidebarObjectDefinitionDetailsProps): JSX.Element;
export {};
