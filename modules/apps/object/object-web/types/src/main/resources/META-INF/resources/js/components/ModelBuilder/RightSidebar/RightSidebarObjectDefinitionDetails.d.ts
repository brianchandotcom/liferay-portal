/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {SiteCompanyJSONArray} from '../../ObjectDetails/EditObjectDetails';
import './RightSidebarObjectDefinitionDetails.scss';
interface RightSidebarObjectDefinitionDetailsProps {
	companyJSONArray: SiteCompanyJSONArray[];
	siteJSONArray: SiteCompanyJSONArray[];
}
export declare function RightSidebarObjectDefinitionDetails({
	companyJSONArray,
	siteJSONArray,
}: RightSidebarObjectDefinitionDetailsProps): JSX.Element;
export {};
