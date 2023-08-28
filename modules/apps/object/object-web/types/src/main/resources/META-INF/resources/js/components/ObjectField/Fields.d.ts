/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {IFDSTableProps} from '../../utils/fds';
interface FieldsProps extends IFDSTableProps {
	baseResourceURL: string;
	objectFieldTypes: ObjectFieldType[];
}
export default function Fields({
	apiURL,
	baseResourceURL,
	creationMenu,
	formName,
	id,
	items,
	objectDefinitionExternalReferenceCode,
	objectFieldTypes,
	style,
	url,
}: FieldsProps): JSX.Element;
export {};
