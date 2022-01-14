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

import {useContext, useEffect} from 'react';
import {useFormContext, useWatch} from 'react-hook-form';
import {useCustomEvent} from '../../../common/hooks/useCustomEvent';
import {STORAGE_KEYS, Storage} from '../../../common/services/liferay/storage';
import {calculatePercentage, countCompletedFields} from '../../../common/utils';
import {TIP_EVENT} from '../../../common/utils/events';

import {ActionTypes, AppContext} from '../context/AppContextProvider';
import {businessTotalFields} from '../utils/businessFields';
import {AVAILABLE_STEPS, TOTAL_OF_FIELD} from '../utils/constants';
import {propertyTotalFields} from '../utils/propertyFields';
import {getLoadedContentFlag} from '../utils/util';

export function useStepWizard() {
	const form = useWatch();
	const [dispatchEvent] = useCustomEvent(TIP_EVENT);
	const {dispatch, state} = useContext(AppContext);
	const {applicationId, backToEdit} = getLoadedContentFlag();
	const currentPercentage = state.percentage;

	const loadInitialData = applicationId || backToEdit;

	const dispatchSelectedStep = (payload) => {
		dispatch({
			payload,
			type: ActionTypes.SET_SELECTED_STEP,
		});
	};

	const dispatchPercentage = (payload) => {
		dispatch({
			payload,
			type: ActionTypes.SET_PERCENTAGE,
		});
	};

	const {
		control: {_fields},
	} = useFormContext();

	useEffect(() => {
		_updateStepPercentage();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [form]);

	useEffect(() => {
		dispatchEvent({
			hide: true,
		});
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [state.selectedStep.section]);

	useEffect(() => {
		if (loadInitialData) {
			calculateAllSteps();
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [loadInitialData]);

	const calculateAllSteps = () => {
		const stepName = Object.keys(form)[
			Object.keys(form).length - 1
		]?.toLowerCase();

		switch (stepName) {
			case AVAILABLE_STEPS.BUSINESS.section:
				setAllPercentages({
					basics: 100,
					business: 100,
				});
				break;
			case AVAILABLE_STEPS.EMPLOYEES.section:
				setAllPercentages({
					basics: 100,
					business: 100,
					employees: 100,
				});
				break;
			case AVAILABLE_STEPS.PROPERTY.section:
				setAllPercentages({
					basics: 100,
					business: 100,
					employees: 100,
					property: 100,
				});
				break;
			default:
				break;
		}
	};

	const _updateStepPercentage = () => {
		switch (state.selectedStep.section) {
			case AVAILABLE_STEPS.BASICS_BUSINESS_TYPE.section:
				if (loadInitialData) {
					if (
						state.selectedStep.subsection ===
						AVAILABLE_STEPS.BASICS_BUSINESS_INFORMATION.subsection
					) {
						return setPercentage(
							calculatePercentage(
								countCompletedFields(_fields?.basics || {}),
								TOTAL_OF_FIELD.BASICS - 1
							),
							AVAILABLE_STEPS.BASICS_BUSINESS_TYPE.section
						);
					}

					if (
						(Storage.getItem(STORAGE_KEYS.BASIC_STEP_CLICKED) ||
							form?.basics?.businessSearch) &&
						!form?.basics?.businessCategoryId
					) {
						return setPercentage(
							currentPercentage.basics,
							AVAILABLE_STEPS.BASICS_BUSINESS_TYPE.section
						);
					} else {
						if (form?.basics?.businessCategoryId) {
							return setPercentage(
								100,
								AVAILABLE_STEPS.BASICS_BUSINESS_TYPE.section
							);
						}

						return setPercentage(
							currentPercentage.basics,
							AVAILABLE_STEPS.BASICS_BUSINESS_TYPE.section
						);
					}
				}

				return setPercentage(
					calculatePercentage(
						countCompletedFields(_fields?.basics || {}),
						TOTAL_OF_FIELD.BASICS
					),
					AVAILABLE_STEPS.BASICS_BUSINESS_TYPE.section
				);

			case AVAILABLE_STEPS.BUSINESS.section:
				return setPercentage(
					calculatePercentage(
						countCompletedFields(_fields?.business || {}),
						businessTotalFields(form?.basics?.properties)
					),
					AVAILABLE_STEPS.BUSINESS.section
				);

			case AVAILABLE_STEPS.EMPLOYEES.section:
				let total = TOTAL_OF_FIELD.EMPLOYEES;

				if (form?.employees?.hasFein === 'true') {
					total++;
				}

				return setPercentage(
					calculatePercentage(
						countCompletedFields(_fields?.employees || {}),
						total
					),
					AVAILABLE_STEPS.EMPLOYEES.section
				);

			case AVAILABLE_STEPS.PROPERTY.section:
				return setPercentage(
					calculatePercentage(
						countCompletedFields(_fields?.property || {}),
						propertyTotalFields(form)
					),
					AVAILABLE_STEPS.PROPERTY.section
				);

			default:
				return setPercentage(
					0,
					AVAILABLE_STEPS.BASICS_BUSINESS_TYPE.section
				);
		}
	};

	const setSection = (step) =>
		dispatchSelectedStep({
			...state.selectedStep,
			...step,
		});

	const setPercentage = (
		percentage = 0,
		step = AVAILABLE_STEPS.BASICS_BUSINESS_TYPE.section
	) => {
		dispatchPercentage({
			...currentPercentage,
			[step]: percentage,
		});
	};

	const setAllPercentages = (
		step = {basics: 0, business: 0, employees: 0, property: 0}
	) => {
		dispatchPercentage({
			...currentPercentage,
			...step,
		});
	};

	return {
		selectedStep: state.selectedStep,
		setPercentage,
		setSection,
	};
}
