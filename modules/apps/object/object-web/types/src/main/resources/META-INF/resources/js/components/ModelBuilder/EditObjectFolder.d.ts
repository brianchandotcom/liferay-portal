/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {Scope} from '../ObjectDetails/EditObjectDetails';
import './EditObjectFolder.scss';
interface EditObjectFolder {
	companies: Scope[];
	objectRelationshipDeletionTypes: LabelValueObject[];
	sites: Scope[];
}
export default function EditObjectFolder({
	companies,
	objectRelationshipDeletionTypes,
	sites,
}: EditObjectFolder): JSX.Element;
export {};
