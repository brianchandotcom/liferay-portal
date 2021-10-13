/* eslint-disable react-hooks/exhaustive-deps */
import {useContext, useEffect} from 'react';
import {useFormContext, useWatch} from 'react-hook-form';
import {useCustomEvent} from '~/common/hooks/useCustomEvent';
import {STORAGE_KEYS, Storage} from '~/common/services/liferay/storage';
import {calculatePercentage, countCompletedFields} from '~/common/utils';
import {TIP_EVENT} from '~/common/utils/events';

import {AppContext} from '../context/AppContext';
import {setSelectedStep} from '../context/actions';
import {businessTotalFields} from '../utils/businessFields';
import {AVAILABLE_STEPS, TOTAL_OF_FIELD} from '../utils/constants';
import {propertyTotalFields} from '../utils/propertyFields';

export const useStepWizard = () => {
	const form = useWatch();
	const [dispatchEvent] = useCustomEvent(TIP_EVENT);
	const {dispatch, state} = useContext(AppContext);
	const {
		control: {_fields},
	} = useFormContext();

	useEffect(() => {
		_updateStepPercentage();
	}, [form]);

	useEffect(() => {
		dispatchEvent({
			hide: true,
		});
	}, [state.selectedStep.section]);

	useEffect(() => {
		calculateAllSteps();
	}, []);

	const calculateAllSteps = () => {
		if (
			Storage.getItem(STORAGE_KEYS.BACK_TO_EDIT) &&
			JSON.parse(Storage.getItem(STORAGE_KEYS.BACK_TO_EDIT))
		) {
			const stepName = Object.keys(form)[
				Object.keys(form).length - 1
			].toLowerCase();

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
		}
	};

	const _updateStepPercentage = () => {
		switch (state.selectedStep.section) {
			case AVAILABLE_STEPS.BASICS_BUSINESS_TYPE.section:
				if (Storage.getItem(STORAGE_KEYS.BACK_TO_EDIT)) {
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
							state.selectedStep.percentage.basics,
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
							state.selectedStep.percentage.basics,
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
		dispatch(
			setSelectedStep({
				...state.selectedStep,
				...step,
			})
		);

	const setPercentage = (
		percentage = 0,
		step = AVAILABLE_STEPS.BASICS_BUSINESS_TYPE.section
	) => {
		dispatch(
			setSelectedStep({
				...state.selectedStep,
				percentage: {
					...state.selectedStep.percentage,
					[step]: percentage,
				},
			})
		);
	};

	const setAllPercentages = (
		step = {basics: 0, business: 0, employees: 0, property: 0}
	) => {
		dispatch(
			setSelectedStep({
				...state.selectedStep,
				percentage: step,
			})
		);
	};

	return {
		selectedStep: state.selectedStep,
		setPercentage,
		setSection,
	};
};
