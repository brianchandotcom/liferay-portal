/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {InputGroupWithSelect} from '../../common/components/InputGroupWithSelect';

export enum SelectOptions {
	USERS = 'users',
	GROUPS = 'groups',
}

export interface SpaceMembersSelectOptionsProps {
	children: React.ReactNode;
	className?: string;
	label?: string;
	onSelectChange?: (value: SelectOptions) => void;
	selectValue: SelectOptions;
}

export function SpaceMembersSelectOptions({
	children,
	className,
	label,
	onSelectChange,
	selectValue,
}: SpaceMembersSelectOptionsProps) {
	return (
		<InputGroupWithSelect
			className={className}
			label={label}
			onSelectChange={(value) => onSelectChange?.(value as SelectOptions)}
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
			selectValue={selectValue}
		>
			{children}
		</InputGroupWithSelect>
	);
}
