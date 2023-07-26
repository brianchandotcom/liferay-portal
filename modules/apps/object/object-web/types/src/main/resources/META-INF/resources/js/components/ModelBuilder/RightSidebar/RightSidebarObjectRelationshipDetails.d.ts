/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import './RightSidebarObjectRelationshipDetails.scss';
import {TDeletionType} from '../../ObjectRelationship/EditRelationship';
interface RightSidebarObjectRelationshipDetailsProps {
	deletionTypes: TDeletionType[];
}
export declare function RightSidebarObjectRelationshipDetails({
	deletionTypes,
}: RightSidebarObjectRelationshipDetailsProps): JSX.Element;
export {};
