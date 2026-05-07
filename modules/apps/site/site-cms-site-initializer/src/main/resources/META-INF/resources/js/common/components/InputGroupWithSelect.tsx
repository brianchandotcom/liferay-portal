/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '../../../css/components/InputGroupWithSelect.scss';

import ClayForm, {ClayInput, ClaySelectWithOption} from '@clayui/form';
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

export function InputGroupWithSelect({
	children,
	className,
	label,
	onSelectChange,
	options,
	selectValue,
}: InputGroupWithSelectProps) {
	const selectId = useId();

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
					<ClaySelectWithOption
						className="font-weight-semi-bold form-control form-control-select-secondary rounded-left"
						id={selectId}
						onChange={(event) => {
							onSelectChange?.(event.target.value);
						}}
						options={options}
						value={selectValue}
					/>
				</ClayInput.GroupItem>

				<ClayInput.GroupItem append>{children}</ClayInput.GroupItem>
			</ClayInput.Group>
		</ClayForm.Group>
	);
}
