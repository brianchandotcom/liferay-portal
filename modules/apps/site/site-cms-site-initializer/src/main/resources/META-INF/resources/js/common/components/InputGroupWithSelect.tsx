/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '../../../css/components/InputGroupWithSelect.scss';

import {Option, Picker} from '@clayui/core';
import ClayForm, {ClayInput} from '@clayui/form';
import classNames from 'classnames';
import React, {useId} from 'react';

export interface InputGroupWithSelectProps {
	children: React.ReactNode;
	className?: string;
	label?: string;
	onSelectChange?: (value: string) => void;
	options: {label: string; value: string}[];
	selectValue: string;
}

const SelectTrigger = React.forwardRef(
	(
		{
			id,
			label,
			...otherProps
		}: {
			id: string;
			label: string;
		},
		ref: React.Ref<HTMLButtonElement>
	) => {
		return (
			<button
				{...otherProps}
				className="btn font-weight-semi-bold form-control form-control-select form-control-select-secondary rounded-left"
				id={id}
				ref={ref}
			>
				{label}
			</button>
		);
	}
);

export function InputGroupWithSelect({
	children,
	className,
	label,
	onSelectChange,
	options,
	selectValue,
}: InputGroupWithSelectProps) {
	const selectId = useId();

	const selectedOption = options.find(
		(option) => option.value === selectValue
	);

	return (
		<ClayForm.Group
			className={classNames('input-group-with-select', className)}
		>
			{label && (
				<label className="d-block" htmlFor={selectId}>
					{label}
				</label>
			)}

			<ClayInput.Group>
				<ClayInput.GroupItem prepend shrink>
					<Picker
						as={SelectTrigger}
						id={selectId}
						items={options}
						label={selectedOption?.label ?? ''}
						onSelectionChange={(key: React.Key) => {
							onSelectChange?.(key as string);
						}}
						selectedKey={selectValue}
					>
						{(item: {label: string; value: string}) => (
							<Option key={item.value}>{item.label}</Option>
						)}
					</Picker>
				</ClayInput.GroupItem>

				<ClayInput.GroupItem append>{children}</ClayInput.GroupItem>
			</ClayInput.Group>
		</ClayForm.Group>
	);
}
