/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {IFilter} from '../../../../../utils/types';
interface IObjectPicklistProps {
	filter?: IFilter;
	namespace: string;
	onChange: Function;
	sourceValidationError: boolean;
}
declare function ObjectPicklist({
	filter,
	namespace,
	onChange,
	sourceValidationError,
}: IObjectPicklistProps): JSX.Element;
export default ObjectPicklist;
