/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Label from '@clayui/label';
import {
	Assignee,
	AssigneeValue,
} from '@liferay/object-dynamic-data-mapping-form-field-type';
import React from 'react';

import {patchTaskById} from '../../utils/api';
import {DISPLAY_TYPES} from '../../utils/constants';
import InfoSummary from '../InfoSummary';
import StateSelector, {State} from '../StateSelector';

interface TaskInfoSummaryProps {
	assignTo: AssigneeValue;
	dueDate: string;
	initialState: string;
	states: State[];
	tags: string[];
	taskId: string;
}

export default function TaskInfoSummary({
	assignTo,
	dueDate,
	initialState,
	states,
	tags,
	taskId,
}: TaskInfoSummaryProps) {
	const displayTypes = DISPLAY_TYPES.filter(
		(displayType) => displayType !== 'unstyled'
	);

	return (
		<InfoSummary
			defaultOpen={true}
			items={[
				{
					label: 'State',
					value: (
						<StateSelector
							initialSelectedKey={initialState}
							onChange={async (key: string) => {
								await patchTaskById({
									body: {state: key},
									taskId,
								});
							}}
							states={states}
						/>
					),
				},
				{
					label: 'Assignee',
					value: (
						<Assignee
							name="assignee"
							onChange={async (value: any) => {
								await patchTaskById({
									body: {assignTo: value},
									taskId,
								});
							}}
							searchURL={
								Liferay.ThemeDisplay.getPortalURL() +
								'/o/cmp/assignee-context/'
							}
							showLabel={false}
							value={assignTo}
							visible={true}
						/>
					),
				},
				{label: 'Due Date', value: dueDate},
				{
					label: 'Tags',
					value: (
						<div>
							{tags.map((tag, index) => (
								<Label
									displayType={
										displayTypes[
											index % displayTypes.length
										]
									}
									key={tag}
								>
									{tag}
								</Label>
							))}
						</div>
					),
				},
			]}
		/>
	);
}
