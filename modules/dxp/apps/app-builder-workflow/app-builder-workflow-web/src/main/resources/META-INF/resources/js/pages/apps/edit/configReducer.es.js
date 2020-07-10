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

import {sub} from 'app-builder-web/js/utils/lang.es';

export const ADD_STEP = 'ADD_STEP';
export const ADD_STEP_ACTION = 'ADD_STEP_ACTION';
export const REMOVE_STEP_ACTION = 'REMOVE_STEP_ACTION';
export const UPDATE_CONFIG = 'UPDATE_CONFIG';
export const UPDATE_DATA_OBJECT = 'UPDATE_DATA_OBJECT';
export const UPDATE_FORM_VIEW = 'UPDATE_FORM_VIEW';
export const UPDATE_STEP = 'UPDATE_STEP';
export const UPDATE_STEP_ACTION = 'UPDATE_STEP_ACTION';
export const UPDATE_STEP_FORM_VIEW = 'UPDATE_STEP_FORM_VIEW';
export const UPDATE_STEP_INDEX = 'UPDATE_STEP_INDEX';
export const UPDATE_TABLE_VIEW = 'UPDATE_TABLE_VIEW';
export const UPDATE_WORKFLOW_APP = 'UPDATE_WORKFLOW_APP';

export const getInitialConfig = () => {
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

	return {
		currentStep: initialSteps[0],
		dataObject: {},
		formView: {},
		stepIndex: 0,
		steps: initialSteps,
		tableView: {},
	};
};

export default (state, action) => {
	switch (action.type) {
		case ADD_STEP: {
			const workflowSteps = [...state.steps];

			const finalStep = workflowSteps.pop();

			let dataLayoutLinks = [{dataLayoutId: '', name: ''}];

			if (action.stepIndex > 0) {
				dataLayoutLinks =
					workflowSteps[action.stepIndex].appWorkflowDataLayoutLinks;
			}

			const stepDataLayoutInfo = {
				false: {
					id: dataLayoutLinks[0].dataLayoutId,
					name: dataLayoutLinks[0].name,
				},
				true: {
					id: state.formView.id,
					name: state.formView.name,
				},
			};
			const stepIndex = action.stepIndex + 1;

			const currentStep = {
				appWorkflowDataLayoutLinks: [
					{
						dataLayoutId: stepDataLayoutInfo[stepIndex === 1].id,
						name: stepDataLayoutInfo[stepIndex === 1].name,
						readOnly: true,
					},
				],
				appWorkflowRoleAssignments: [],
				appWorkflowTransitions: [
					{
						name: Liferay.Language.get('submit'),
						primary: true,
						transitionTo: finalStep.name,
					},
				],
				name: sub(Liferay.Language.get('step-x'), [
					state.steps.length - 1,
				]),
			};
			workflowSteps.splice(stepIndex, 0, currentStep);
			workflowSteps[
				action.stepIndex
			].appWorkflowTransitions[0].transitionTo = currentStep.name;

			return {
				...state,
				currentStep,
				stepIndex,
				steps: [...workflowSteps, finalStep],
			};
		}
		case ADD_STEP_ACTION: {
			state.steps[state.stepIndex].appWorkflowTransitions.push({
				name: Liferay.Language.get('secondary-action'),
				primary: false,
				transitionTo: state.steps[state.stepIndex - 1].name,
			});

			return {...state, currentStep: state.steps[state.stepIndex]};
		}
		case UPDATE_CONFIG: {
			const {
				dataObject = {},
				formView = {},
				tableView = {},
			} = action.config;

			return {
				...state,
				dataObject,
				formView,
				tableView,
			};
		}
		case REMOVE_STEP_ACTION: {
			state.steps[state.stepIndex].appWorkflowTransitions.pop();

			return {...state, currentStep: state.steps[state.stepIndex]};
		}
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
			const {step, stepIndex} = {...action};

			if (stepIndex > 0) {
				state.steps[
					stepIndex - 1
				].appWorkflowTransitions[0].transitionTo = step.name;
			}

			state.steps[stepIndex] = step;

			return {
				...state,
				currentStep: step,
			};
		}
		case UPDATE_STEP_ACTION: {
			const {name, primary} = action;

			const appWorkflowTransitions =
				state.steps[state.stepIndex].appWorkflowTransitions;

			const transitionIndex = appWorkflowTransitions.findIndex(
				(transition) => transition.primary === primary
			);

			appWorkflowTransitions[transitionIndex].name = name;

			return {...state, currentStep: state.steps[state.stepIndex]};
		}
		case UPDATE_STEP_FORM_VIEW: {
			const appWorkflowDataLayoutLinks =
				state.steps[state.stepIndex].appWorkflowDataLayoutLinks;

			appWorkflowDataLayoutLinks[0].dataLayoutId = action.formView.id;
			appWorkflowDataLayoutLinks[0].name = action.formView.name;

			return {...state, currentStep: state.steps[state.stepIndex]};
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
			const {appWorkflowStates = [], appWorkflowTasks = []} = action;

			const initialState = appWorkflowStates.find(({initial}) => initial);
			const finalState = appWorkflowStates.find(({initial}) => !initial);

			return {
				...state,
				currentStep: initialState,
				steps: [initialState, ...appWorkflowTasks, finalState],
			};
		}
		default: {
			return state;
		}
	}
};
