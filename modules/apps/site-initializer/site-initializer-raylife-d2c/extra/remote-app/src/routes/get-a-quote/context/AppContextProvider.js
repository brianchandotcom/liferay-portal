/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {createContext, useEffect, useReducer} from 'react';
import useWindowDimensions from '../../../common/hooks/useWindowDimensions';
import {STORAGE_KEYS, Storage} from '../../../common/services/liferay/storage';
import {TIP_EVENT_DISMISS} from '../../../common/utils/events';
import {getTaxonomyVocabularies} from '../services/TaxonomyVolucabularies';
import {AVAILABLE_STEPS, STEP_ORDERED} from '../utils/constants';

const initialState = {
	dimensions: {
		device: {},
	},
	percentage: {
		[AVAILABLE_STEPS.BASICS_BUSINESS_INFORMATION.section]: 0,
		[AVAILABLE_STEPS.BUSINESS.section]: 0,
		[AVAILABLE_STEPS.EMPLOYEES.section]: 0,
		[AVAILABLE_STEPS.PROPERTY.section]: 0,
	},
	selectedProduct: JSON.parse(Storage.getItem(STORAGE_KEYS.APPLICATION_FORM))
		?.basics?.businessCategoryId,
	selectedTrigger: '',
	steps: STEP_ORDERED,
	taxonomyVocabulary: {},
};

export const ActionTypes = {
	SET_DIMENSIONS: 'SET_DIMENSIONS',
	SET_MOBILE_SUBSECTION_ACTIVE: 'SET_MOBILE_SUBSECTION_ACTIVE',
	SET_MOBILE_SUBSECTION_DISABLE: 'SET_MOBILE_SUBSECTION_DISABLE',
	SET_PERCENTAGE: 'SET_PERCENTAGE',
	SET_SELECTED_PRODUCT: 'SET_SELECTED_PRODUCT',
	SET_SELECTED_TRIGGER: 'SET_SELECTED_TRIGGER',
	SET_STEP_ACTIVE: 'SET_STEP_ACTIVE',
	SET_TAXONOMY_VOCABULARY: 'SET_TAXONOMY_VOCABULARY',
};

function AppContextReducer(state, action) {
	switch (action.type) {
		case ActionTypes.SET_DIMENSIONS:
			return {
				...state,
				dimensions: action.payload,
			};

		case ActionTypes.SET_PERCENTAGE:
			return {
				...state,
				percentage: action.payload,
			};

		case ActionTypes.SET_SELECTED_TRIGGER:
			return {
				...state,
				selectedTrigger: action.payload,
			};

		case ActionTypes.SET_SELECTED_PRODUCT:
			return {
				...state,
				selectedProduct: action.payload,
			};

		case ActionTypes.SET_MOBILE_SUBSECTION_ACTIVE:
			return {
				...state,
				steps: state.steps.map((step) => {
					const mobileSubSections = step.mobileSubSections;

					if (step.active && Array.isArray(mobileSubSections)) {
						const currentIndex = mobileSubSections.findIndex(
							({active}) => active
						);

						const _currentIndex = action.payload.nextStep
							? currentIndex + 1
							: currentIndex - 1;

						return {
							...step,
							mobileSubSections: mobileSubSections.map(
								(mobileSubSection, index) => ({
									...mobileSubSection,
									active: index === _currentIndex,
								})
							),
						};
					}

					return step;
				}),
			};

		case ActionTypes.SET_MOBILE_SUBSECTION_DISABLE:
			const disableSections = action.payload;

			return {
				...state,
				steps: state.steps.map((step) => {
					const mobileSubSections = step.mobileSubSections;

					if (Array.isArray(mobileSubSections)) {
						return {
							...step,
							mobileSubSections: mobileSubSections.filter(
								(mobileSubSection) =>
									!(Array.isArray(disableSections)
										? disableSections.includes(
												mobileSubSection.title
										  )
										: disableSections ===
										  mobileSubSection.title)
							),
						};
					}

					return step;
				}),
			};

		case ActionTypes.SET_STEP_ACTIVE:
			return {
				...state,
				steps: state.steps.map((step) => {
					return {
						...step,
						active: step.id === action.payload.id,
					};
				}),
			};

		case ActionTypes.SET_TAXONOMY_VOCABULARY:
			return {
				...state,
				taxonomyVocabulary: action.payload,
			};

		default:
			return state;
	}
}

export const AppContext = createContext({});

export function AppContextProvider({children}) {
	const dimensions = useWindowDimensions();
	const [state, dispatch] = useReducer(AppContextReducer, initialState);

	const selectedStep = state.steps.find(({active}) => active);

	useEffect(() => {
		const onDismiss = () =>
			dispatch({
				payload: '',
				type: ActionTypes.SET_SELECTED_TRIGGER,
			});

		window.addEventListener(TIP_EVENT_DISMISS, onDismiss);

		return () => window.removeEventListener(TIP_EVENT_DISMISS, onDismiss);
	}, []);

	useEffect(() => {
		getTaxonomyVocabularies()
			.then(({data}) =>
				dispatch({
					payload: data.items[0],
					type: ActionTypes.SET_TAXONOMY_VOCABULARY,
				})
			)
			.catch((error) => console.error(error));
	}, []);

	useEffect(() => {
		dispatch({
			payload: dimensions,
			type: ActionTypes.SET_DIMENSIONS,
		});
	}, [dimensions]);

	return (
		<AppContext.Provider
			value={{
				dispatch,
				state: {
					...state,
					activeMobileSubSection: selectedStep.mobileSubSections?.find(
						({active, blocked = false}) => active && !blocked
					),
					selectedStep,
				},
			}}
		>
			{children}
		</AppContext.Provider>
	);
}
