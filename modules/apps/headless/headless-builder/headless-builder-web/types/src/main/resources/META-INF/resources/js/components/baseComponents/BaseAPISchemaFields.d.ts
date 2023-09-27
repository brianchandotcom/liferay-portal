/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Dispatch, SetStateAction} from 'react';
interface BaseAPIApplicationFieldsProps {
	data: Partial<APISchemaUIData>;
	disableObjectSelect?: boolean;
	displayError: SchemaDataError;
	setData: Dispatch<SetStateAction<APISchemaUIData>>;
}
export default function BaseAPISchemaFields({
	data,
	disableObjectSelect,
	displayError,
	setData,
}: BaseAPIApplicationFieldsProps): JSX.Element;
export {};
