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

import {createContext} from 'react';

const DEFAULT_STATE = {
	createExperimentModal: {active: false},
	editExperimentModal: {active: false},
	errors: {},
	experiences: [],
	experiment: null,
	experimentHistory: [],
	reviewExperimentModal: {active: false},
	selectedExperienceId: null,
	variants: []
};

export function getInitialState(firstState) {
	const {
		initialExperimentHistory,
		initialSegmentsExperiment,
		initialSegmentsVariants,
		initialSelectedSegmentsExperienceId,
		viewSegmentsExperimentDetailsURL,
		winnerSegmentsVariantId
	} = firstState;

	const state = {
		experiment: initialSegmentsExperiment,
		experimentHistory: initialExperimentHistory || [],
		selectedExperienceId: initialSelectedSegmentsExperienceId,
		variants: initialSegmentsVariants.map(initialVariant => {
			if (
				winnerSegmentsVariantId === initialVariant.segmentsExperienceId
			) {
				return {...initialVariant, winner: true};
			}

			return initialVariant;
		}),
		viewExperimentURL: viewSegmentsExperimentDetailsURL
	};

	return {
		...DEFAULT_STATE,
		...state
	};
}

export const DispatchContext = createContext();
export const StateContext = createContext(DEFAULT_STATE);
