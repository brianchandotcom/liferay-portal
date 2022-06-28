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

import Form from '..';
import {InputHTMLAttributes, useState} from 'react';
import ReactSelect from 'react-select';

type Option = {label: string; value: string};

type MultiSelectProps = {
	label?: string;
	options: Option[];
} & InputHTMLAttributes<HTMLInputElement>;

type SelectValue = Option | Option[] | undefined;

const MultiSelect: React.FC<MultiSelectProps> = ({
	disabled,
	label,
	name = '',
	onChange,
	options,
	value,
}) => {
	const [selectValue, setSelectValue] = useState<SelectValue>(
		value as SelectValue
	);

	return (
		<Form.BaseWrapper label={label}>
			<ReactSelect
				closeMenuOnSelect={false}
				isDisabled={disabled}
				isMulti
				name={name}
				onChange={(value) => {
					setSelectValue(value as Option[]);

					if (onChange) {
						onChange({target: {name, value: value as any}} as any);
					}
				}}
				options={options}
				value={selectValue}
			/>
		</Form.BaseWrapper>
	);
};

export default MultiSelect;
