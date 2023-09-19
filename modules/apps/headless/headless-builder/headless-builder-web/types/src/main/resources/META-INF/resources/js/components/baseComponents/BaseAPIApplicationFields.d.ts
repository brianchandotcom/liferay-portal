/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Dispatch, SetStateAction} from 'react';
interface BaseAPIApplicationFieldsProps {
	basePath: string;
	data: Partial<APIApplicationUIData>;
	disableURLAutoFill?: boolean;
	displayError: ApplicationDataError;
	setData: Dispatch<SetStateAction<APIApplicationUIData>>;
}
export default function BaseAPIApplicationFields({
	basePath,
	data,
	disableURLAutoFill,
	displayError,
	setData,
}: BaseAPIApplicationFieldsProps): JSX.Element;
export {};
