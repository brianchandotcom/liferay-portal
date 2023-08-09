/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Dispatch, SetStateAction} from 'react';
declare type DataError = {
	description: boolean;
	mainObjectDefinitionERC: boolean;
	name: boolean;
};
interface BaseAPIApplicationFieldsProps {
	disableObjectSelect?: boolean;
	displayError: DataError;
	setData: Dispatch<SetStateAction<Partial<APIApplicationSchemaItem>>>;
}
export default function BaseAPISchemaFields({
	data,
	disableObjectSelect,
	displayError,
	setData,
}: BaseAPIApplicationFieldsProps): JSX.Element;
export {};
