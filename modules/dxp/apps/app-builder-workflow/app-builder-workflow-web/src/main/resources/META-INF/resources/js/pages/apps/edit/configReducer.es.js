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

export const UPDATE_DATA_OBJECT = 'UPDATE_DATA_OBJECT';
export const UPDATE_FORM_VIEW = 'UPDATE_FORM_VIEW';
export const UPDATE_STEP = 'UPDATE_STEP';
export const UPDATE_STEP_INDEX = 'UPDATE_STEP_INDEX';
export const UPDATE_TABLE_VIEW = 'UPDATE_TABLE_VIEW';
export const UPDATE_WORKFLOW_APP = 'UPDATE_WORKFLOW_APP';

const initialSteps = [
	{
		appWorkflowTransitions: [
			{
				name: Liferay.Language.get('submit'),
				primary: true,
				transitionTo: Liferay.Language.get('final-step'),
			},
		],
		initial: true,
		name: Liferay.Language.get('initial-step'),
	},
	{initial: false, name: Liferay.Language.get('final-step')},
];

export const initialConfig = {
	currentStep: initialSteps[0],
	dataObject: {},
	formView: {},
	stepIndex: 0,
	steps: initialSteps,
	tableView: {},
};

export default (state, action) => {
	switch (action.type) {
		case UPDATE_DATA_OBJECT: {
			return {
				...state,
				dataObject: action.dataObject,
			};
		}
		case UPDATE_FORM_VIEW: {
			return {
				...state,
				formView: action.formView,
			};
		}
		case UPDATE_STEP: {
			const {step: currentStep, stepIndex} = action;

			if (stepIndex === 1) {
				state.steps[0].appWorkflowTransitions[0].transitionTo =
					currentStep.name;
			}

			state.steps[stepIndex] = currentStep;

			return {
				...state,
				currentStep,
			};
		}
		case UPDATE_STEP_INDEX: {
			return {
				...state,
				currentStep: state.steps[action.stepIndex],
				stepIndex: action.stepIndex,
			};
		}
		case UPDATE_TABLE_VIEW: {
			return {
				...state,
				tableView: action.tableView,
			};
		}
		case UPDATE_WORKFLOW_APP: {
			const {appWorkflowStates = []} = action;

			const initialState = appWorkflowStates.find(({initial}) => initial);
			const finalState = appWorkflowStates.find(({initial}) => !initial);

			return {
				...state,
				currentStep: initialState,
				steps: [initialState, finalState],
			};
		}
		default: {
			return state;
		}
	}
};
