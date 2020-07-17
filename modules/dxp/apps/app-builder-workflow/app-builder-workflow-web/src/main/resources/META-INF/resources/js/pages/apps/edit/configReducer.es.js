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
export const ADD_STEP_FORM_VIEW = 'ADD_STEP_FORM_VIEW';
export const REMOVE_STEP = 'REMOVE_STEP';
export const REMOVE_STEP_ACTION = 'REMOVE_STEP_ACTION';
export const REMOVE_STEP_FORM_VIEW = 'REMOVE_STEP_FORM_VIEW';
export const UPDATE_CONFIG = 'UPDATE_CONFIG';
export const UPDATE_DATA_OBJECT = 'UPDATE_DATA_OBJECT';
export const UPDATE_FORM_VIEW = 'UPDATE_FORM_VIEW';
export const UPDATE_LIST_ITEMS = 'UPDATE_LIST_ITEMS';
export const UPDATE_STEP = 'UPDATE_STEP';
export const UPDATE_STEP_ACTION = 'UPDATE_STEP_ACTION';
export const UPDATE_STEP_FORM_VIEW = 'UPDATE_STEP_FORM_VIEW';
export const UPDATE_STEP_INDEX = 'UPDATE_STEP_INDEX';
export const UPDATE_TABLE_VIEW = 'UPDATE_TABLE_VIEW';

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
		listItems: {
			assigneeRoles: [],
			dataObjects: [],
			fetching: false,
			formViews: [],
			tableViews: [],
		},
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
		case ADD_STEP_FORM_VIEW: {
			const workflowSteps = [...state.steps];

			workflowSteps[state.stepIndex].appWorkflowDataLayoutLinks.push({
				dataLayoutId: undefined,
				readOnly: true,
			});

			return {...state, steps: [...workflowSteps]};
		}
		case REMOVE_STEP: {
			let currentStep;
			const workflowSteps = [...state.steps];
			const previousStep = workflowSteps[action.stepIndex - 1];
			const nextStep = workflowSteps[action.stepIndex + 1];

			previousStep.appWorkflowTransitions[0].transitionTo = nextStep.name;

			if (nextStep.appWorkflowTransitions.length > 1) {
				if (action.stepIndex > 1) {
					nextStep.appWorkflowTransitions.forEach((action) => {
						if (!action.primary) {
							action.transitionTo = previousStep.name;
						}
					});
				} else {
					nextStep.appWorkflowTransitions.forEach((_, index) => {
						if (!action.primary) {
							nextStep.appWorkflowTransitions.splice(index, 1);
						}
					});
				}
			}

			if (state.stepIndex === action.stepIndex) {
				currentStep = previousStep;
			}

			workflowSteps.splice(action.stepIndex, 1);

			return {
				...state,
				currentStep,
				stepIndex: state.stepIndex - 1,
				steps: [...workflowSteps],
			};
		}
		case REMOVE_STEP_ACTION: {
			state.steps[state.stepIndex].appWorkflowTransitions.pop();

			return {...state, currentStep: state.steps[state.stepIndex]};
		}
		case REMOVE_STEP_FORM_VIEW: {
			state.steps[state.stepIndex].appWorkflowDataLayoutLinks.splice(
				action.index,
				1
			);

			return {...state, steps: [...state.steps]};
		}
		case UPDATE_CONFIG: {
			return {
				...state,
				...action.config,
			};
		}
		case UPDATE_DATA_OBJECT: {
			return {
				...state,
				dataObject: action.dataObject,
			};
		}
		case UPDATE_FORM_VIEW: {
			let workflowSteps = [...state.steps];

			const initialStep = workflowSteps.shift();
			const finalStep = workflowSteps.pop();

			if (workflowSteps.length > 0) {
				workflowSteps = workflowSteps.map((step) => ({
					...step,
					appWorkflowDataLayoutLinks: [
						{
							dataLayoutId: action.formView.id,
							name: action.formView.name,
							readOnly: true,
						},
					],
				}));
			}

			return {
				...state,
				formView: action.formView,
				steps: [initialStep, ...workflowSteps, finalStep],
			};
		}
		case UPDATE_LIST_ITEMS: {
			return {
				...state,
				listItems: {
					...state.listItems,
					...action.listItems,
				},
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

			appWorkflowDataLayoutLinks[action.index].dataLayoutId =
				action.formView.id;
			appWorkflowDataLayoutLinks[action.index].name =
				action.formView.name;

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
		default: {
			return state;
		}
	}
};
