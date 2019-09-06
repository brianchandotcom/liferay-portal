/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {createContext} from 'react';

const DEFAULT_STATE = {
	createExperimentModal: {active: false},
	editExperimentModal: {active: false},
	experiences: [],
	experiment: null,
	selectedExperienceId: null,
	variants: [],
	winnerVariant: null
};

export function getInitialState(firstState) {
	const {
		initialSegmentsExperiment,
		initialSelectedSegmentsExperienceId,
		initialSegmentsVariants
	} = firstState;

	const state = {
		experiment: initialSegmentsExperiment,
		selectedExperienceId: initialSelectedSegmentsExperienceId,
		variants: initialSegmentsVariants.map(initialVariant => {
			if (
				firstState.winnerSegmentsVariantId ===
				initialVariant.segmentsExperienceId
			)
				return {...initialVariant, winner: true};
			return initialVariant;
		}),
		winnerVariant: firstState.winnerSegmentsVariantId
	};

	return {
		...DEFAULT_STATE,
		...state
	};
}

export const DispatchContext = createContext();
export const StateContext = createContext(DEFAULT_STATE);
