/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {IFDSTableProps} from '../../utils/fds';
interface RelationshipsProps extends IFDSTableProps {
	isApproved: boolean;
}
export default function Relationships({
	apiURL,
	creationMenu,
	formName,
	id,
	isApproved,
	items,
	objectDefinitionExternalReferenceCode,
	style,
	url,
}: RelationshipsProps): JSX.Element;
export {};
