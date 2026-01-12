/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Label from '@clayui/label';
import React from 'react';

import InfoSummary from '../../../common/components/InfoSummary';
import StateSelector, {State} from '../../../common/components/StateSelector';
import {patchTaskById} from '../../../utils/api';
import {DISPLAY_TYPES} from '../../../utils/constants';

interface TaskInfoSummaryProps {
	dueDate: string;
	initialState: string;
	states: State[];
	tags: string[];
	taskId: string;
}

export default function TaskInfoSummary({
	dueDate,
	initialState,
	states,
	tags,
	taskId,
}: TaskInfoSummaryProps) {
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
				{label: 'Due Date', value: dueDate},
				{
					label: 'Tags',
					value: (
						<div>
							{tags.map((tag, index) => (
								<Label
									displayType={DISPLAY_TYPES[index % 6]}
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
