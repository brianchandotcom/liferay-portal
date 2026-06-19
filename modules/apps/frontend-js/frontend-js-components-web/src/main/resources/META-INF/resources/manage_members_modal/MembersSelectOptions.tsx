/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import './MembersSelectOptions.scss';

import ClayForm, {ClayInput, ClaySelectWithOption} from '@clayui/form';
import classNames from 'classnames';
import React, {useId} from 'react';

import {SelectOptions} from './types';

export interface MembersSelectOptionsProps {
	children: React.ReactNode;
	className?: string;
	label?: string;
	onSelectChange?: (value: SelectOptions) => void;
	selectValue: SelectOptions;
}

export function MembersSelectOptions({
	children,
	className,
	label,
	onSelectChange,
	selectValue,
}: MembersSelectOptionsProps) {
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
							onSelectChange?.(
								event.target.value as SelectOptions
							);
						}}
						options={[
							{
								label: Liferay.Language.get('users'),
								value: SelectOptions.USERS,
							},
							{
								label: Liferay.Language.get('groups'),
								value: SelectOptions.GROUPS,
							},
						]}
						value={selectValue}
					/>
				</ClayInput.GroupItem>

				<ClayInput.GroupItem append>{children}</ClayInput.GroupItem>
			</ClayInput.Group>
		</ClayForm.Group>
	);
}
