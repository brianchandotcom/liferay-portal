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

import {getFormViewFields, validateSelectedFormViews} from './utils.es';

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
export const UPDATE_STEP_FORM_VIEW_READONLY = 'UPDATE_STEP_FORM_VIEW_READONLY';
export const UPDATE_STEP_INDEX = 'UPDATE_STEP_INDEX';
export const UPDATE_TABLE_VIEW = 'UPDATE_TABLE_VIEW';

export const getInitialConfig = () => {
	const defaultLanguageId = themeDisplay.getLanguageId();
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
		dataObject: {
			availableLanguageIds: [defaultLanguageId],
			defaultLanguageId,
		},
		draftConfig: {},
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
			const appWorkflowDataLayoutLinks = [];
			const stepIndex = action.stepIndex + 1;
			const workflowSteps = [...state.steps];

			const finalStep = workflowSteps.pop();
			const nextStep = workflowSteps[stepIndex];
			const previousStep = workflowSteps[action.stepIndex];

			if (state.formView.id) {
				appWorkflowDataLayoutLinks.push({
					...state.formView,
					dataLayoutId: state.formView.id,
					readOnly: true,
				});
			}

			const currentStep = {
				appWorkflowDataLayoutLinks,
				appWorkflowRoleAssignments: [],
				appWorkflowTransitions: [
					{
						name: Liferay.Language.get('submit'),
						primary: true,
						transitionTo: nextStep?.name ?? finalStep.name,
					},
				],
				errors: {
					formViews: {
						duplicatedFields: [],
						errorIndexes: [],
					},
				},
				name: sub(Liferay.Language.get('step-x'), [
					state.steps.length - 1,
				]),
			};

			if (stepIndex > 1) {
				currentStep.appWorkflowDataLayoutLinks = previousStep.appWorkflowDataLayoutLinks.map(
					(dataLayout) => ({
						...dataLayout,
						readOnly: true,
					})
				);
			}

			previousStep.appWorkflowTransitions.forEach((action) => {
				if (action.primary) {
					action.transitionTo = currentStep.name;
				}
			});

			if (nextStep) {
				nextStep.appWorkflowTransitions.forEach((action) => {
					if (!action.primary) {
						action.transitionTo = currentStep.name;
					}
				});
			}

			workflowSteps.splice(stepIndex, 0, currentStep);

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
			state.steps[state.stepIndex].appWorkflowDataLayoutLinks.push({
				dataLayoutId: undefined,
				fields: [],
				readOnly: false,
			});

			return {...state};
		}
		case REMOVE_STEP: {
			let currentStep;
			const workflowSteps = [...state.steps];
			const previousStep = workflowSteps[action.stepIndex - 1];
			const nextStep = workflowSteps[action.stepIndex + 1];

			previousStep.appWorkflowTransitions[0].transitionTo = nextStep.name;

			if (nextStep?.appWorkflowTransitions?.length > 1) {
				if (action.stepIndex > 1) {
					nextStep.appWorkflowTransitions.forEach((transition) => {
						if (!transition.primary) {
							transition.transitionTo = previousStep.name;
						}
					});
				}
				else {
					nextStep.appWorkflowTransitions.forEach(
						(transition, index) => {
							if (!transition.primary) {
								nextStep.appWorkflowTransitions.splice(
									index,
									1
								);
							}
						}
					);
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
			const currentStep = state.steps[state.stepIndex];

			currentStep.appWorkflowDataLayoutLinks.splice(action.index, 1);
			currentStep.errors.formViews = validateSelectedFormViews(
				currentStep.appWorkflowDataLayoutLinks
			);

			return {...state, currentStep};
		}
		case UPDATE_CONFIG: {
			return {
				...state,
				...action.config,
				draftConfig: JSON.parse(JSON.stringify(action.config)),
			};
		}
		case UPDATE_DATA_OBJECT: {
			state.steps.forEach((step) => {
				if (step.appWorkflowDataLayoutLinks) {
					step.appWorkflowDataLayoutLinks = [];
				}

				if (step?.errors?.formViews) {
					step.errors.formViews = {
						duplicatedFields: [],
						errorIndexes: [],
					};
				}
			});

			return {
				...state,
				dataObject: action.dataObject,
				formView: {},
				tableView: {},
			};
		}
		case UPDATE_FORM_VIEW: {
			const workflowSteps = [...state.steps];

			const initialStep = workflowSteps.shift();
			const finalStep = workflowSteps.pop();

			const formView = {
				...action.formView,
				fields: getFormViewFields(action.formView),
			};

			workflowSteps.forEach((step) => {
				if (step.appWorkflowDataLayoutLinks.length === 0) {
					step.appWorkflowDataLayoutLinks.push({
						...formView,
						dataLayoutId: formView.id,
						readOnly: true,
					});
				}
			});

			return {
				...state,
				formView,
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
			const currentStep = state.steps[state.stepIndex];

			const formViews = currentStep.appWorkflowDataLayoutLinks;

			formViews[action.index] = {
				...formViews[action.index],
				dataLayoutId: action.formView.id,
				fields: getFormViewFields(action.formView),
				name: action.formView.name,
			};

			currentStep.errors.formViews = validateSelectedFormViews(formViews);

			return {...state, currentStep};
		}
		case UPDATE_STEP_FORM_VIEW_READONLY: {
			const currentStep = state.steps[state.stepIndex];

			currentStep.appWorkflowDataLayoutLinks[action.index] = {
				...currentStep.appWorkflowDataLayoutLinks[action.index],
				readOnly: action.readOnly,
			};

			return {...state, currentStep};
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
