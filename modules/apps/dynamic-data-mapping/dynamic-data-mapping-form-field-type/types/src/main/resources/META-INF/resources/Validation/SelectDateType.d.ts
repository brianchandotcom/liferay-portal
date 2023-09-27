/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
declare const SelectDateType: React.FC<IProps>;
export default SelectDateType;
interface IProps {
	dateFieldName?: string;
	dateFieldOptions: IDateFieldOption[];
	label: string;
	onChange: (value: Type, dateFieldName?: string) => void;
	options: IOptions[];
	tooltip?: string;
	type: Type;
}
interface IDateFieldOption {
	label: string;
	name: string;
}
interface IOptions {
	label: string;
	name: DateType;
	value: DateType;
}
