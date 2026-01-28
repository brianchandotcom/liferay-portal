/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	Assignee,
	AssigneeValue,
} from '@liferay/object-dynamic-data-mapping-form-field-type';
import React, {useState} from 'react';

import AssigneeTrigger from './AssigneeTrigger';

import './AssigneeTrigger.scss';

interface ICustomAssignee {
	name?: string;
	onChange?: (value: AssigneeValue | {}) => void;
	showLabel?: boolean;
	triggerClassName?: string;
	value: AssigneeValue | {} | null;
}

export default function CustomAssignee({
	name,
	onChange,
	showLabel = true,
	triggerClassName,
	value: initialValue,
}: ICustomAssignee) {
	const [value, setValue] = useState<AssigneeValue | null | {}>(initialValue);

	return (
		<Assignee
			label={Liferay.Language.get('assignee')}
			name={name ?? ''}
			onChange={async (event: {target: {value: AssigneeValue | {}}}) => {
				setValue(event.target.value);
				onChange?.(event.target.value);
			}}
			searchURL={
				Liferay.ThemeDisplay.getPortalURL() +
				'/o/headless-cmp/v1.0/task-assignees/'
			}
			showLabel={showLabel}
			triggerClassName={triggerClassName}
			triggerComponent={AssigneeTrigger}
			value={value}
			visible={true}
		/>
	);
}
