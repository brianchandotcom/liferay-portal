/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import React from 'react';
declare const SelectDateType: React.FC<IProps>;
export default SelectDateType;
interface IProps {
	dateFieldOptions: IDateFieldOption[];
	type: Type;
	dateFieldName?: string;
	options: IOptions[];
	onChange: (value: string | number, dateFieldName?: string) => void;
	label: string;
	tooltip?: string;
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
declare type DateType = 'customDate' | 'responseDate';
