/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Label from '@clayui/label';
import React from 'react';

import InfoSummary from '../../../common/components/InfoSummary';
import StateSelector, {State} from '../../../common/components/StateSelector';
import {patchProjectById} from '../../../utils/api';
import {DISPLAY_TYPES} from '../../../utils/constants';
import User, {UserProps} from './User';

interface ProjectInfoSummaryProps {
	dueDate: string;
	initialState: string;
	manager: UserProps;
	projectId: string;
	sponsor: UserProps;
	states: State[];
	tags: string[];
}

export default function ProjectInfoSummary({
	dueDate,
	initialState,
	manager,
	projectId,
	sponsor,
	states,
	tags,
}: ProjectInfoSummaryProps) {
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
								await patchProjectById({
									body: {state: key},
									projectId,
								});
							}}
							states={states}
						/>
					),
				},
				{label: 'Manager', value: <User {...manager} />},
				{label: 'Sponsor', value: <User {...sponsor} />},
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
