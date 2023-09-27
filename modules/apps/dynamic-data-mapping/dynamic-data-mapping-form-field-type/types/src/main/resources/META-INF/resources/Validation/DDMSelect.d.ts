/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
declare function DDMSelect({
	className,
	disabled,
	label,
	name,
	onChange,
	options,
	value: selectedValue,
}: IProps): JSX.Element;
export default DDMSelect;
interface IProps {
	className?: string;
	disabled?: boolean;
	label: string;
	name: string;
	onChange: React.ChangeEventHandler<HTMLInputElement>;
	options: IOption[];
	value: string;
}
interface IOption {
	label: string;
	onClick?: React.ChangeEvent;
	value: string;
}
