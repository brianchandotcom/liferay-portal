/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import EditAppContext from 'app-builder-web/js/pages/apps/edit/EditAppContext.es';
import React, {useContext} from 'react';

import {ADD_STEP, REMOVE_STEP, UPDATE_STEP_INDEX} from '../configReducer.es';
import WorkflowStep from './WorkflowStep.es';

export default function WorkflowBuilder() {
	const {
		config: {dataObject, stepIndex, steps},
		dispatchConfig,
	} = useContext(EditAppContext);

	const badgeLabel = (stepIndex) => {
		if (stepIndex === 0) {
			return Liferay.Language.get('start');
		}
		else if (stepIndex === steps.length - 1) {
			return Liferay.Language.get('end');
		}

		return stepIndex;
	};

	const onClickStep = (index) => {
		if (index !== stepIndex) {
			dispatchConfig({stepIndex: index, type: UPDATE_STEP_INDEX});
		}
	};

	const stepInfo = [
		[
			{
				...dataObject,
				label: Liferay.Language.get('data-object'),
			},
		],
		...steps
			.filter(({initial}) => initial === undefined)
			.map(({appWorkflowRoleAssignments = []}) =>
				appWorkflowRoleAssignments.length > 0
					? [
							{
								label: Liferay.Language.get('assignee'),
								name: appWorkflowRoleAssignments
									.map(({roleName}) => roleName)
									.reduce((acc, cur) => `${acc}, ${cur}`),
							},
					  ]
					: []
			),
	];

	return (
		<div className="app-builder-workflow-app__builder">
			{steps.map((step, index) => (
				<WorkflowStep
					actions={[
						{
							label: Liferay.Language.get('delete-step'),
							onClick: () =>
								dispatchConfig({
									stepIndex: index,
									type: REMOVE_STEP,
								}),
						},
					]}
					addStep={() =>
						dispatchConfig({stepIndex: index, type: ADD_STEP})
					}
					badgeLabel={badgeLabel(index)}
					{...step}
					key={index}
					onClick={() => onClickStep(index)}
					selected={stepIndex === index}
					stepInfo={
						index < steps.length - 1 && stepInfo[index]
							? stepInfo[index]
							: []
					}
				/>
			))}
		</div>
	);
}
