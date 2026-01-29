/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Label from '@clayui/label';
import React from 'react';

import {patchProjectById} from '../../utils/api';
import {displayStateSuccessToast} from '../../utils/toastUtil';
import InfoSummary from '../InfoSummary';
import StateSelector, {State} from '../StateSelector';
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
								const response = await patchProjectById({
									body: {state: key},
									projectId,
								});

								if (response.ok) {
									displayStateSuccessToast();
								}
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
							{tags.map((tag) => (
								<Label key={tag}>{tag}</Label>
							))}
						</div>
					),
				},
			]}
		/>
	);
}
