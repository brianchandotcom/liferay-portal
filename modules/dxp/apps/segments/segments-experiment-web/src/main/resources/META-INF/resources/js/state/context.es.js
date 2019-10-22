import {createContext} from 'react';

const DEFAULT_STATE = {
	createExperimentModal: {active: false},
	editExperimentModal: {active: false},
	experiences: [],
	experiment: null,
	experimentHistory: [],
	selectedExperienceId: null,
	variants: []
};

export function getInitialState(firstState) {
	const {
		initialExperimentHistory,
		initialSegmentsExperiment,
		initialSelectedSegmentsExperienceId,
		initialSegmentsVariants,
		viewSegmentsExperimentDetailsURL,
		winnerSegmentsVariantId
	} = firstState;

	const state = {
		experiment: initialSegmentsExperiment,
		experimentHistory: initialExperimentHistory || [],
		selectedExperienceId: initialSelectedSegmentsExperienceId,
		variants: initialSegmentsVariants.map(initialVariant => {
			if (winnerSegmentsVariantId === initialVariant.segmentsExperienceId)
				return {...initialVariant, winner: true};
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
