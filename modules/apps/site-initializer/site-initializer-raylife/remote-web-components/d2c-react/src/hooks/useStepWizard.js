/* eslint-disable react-hooks/exhaustive-deps */
import {useContext, useEffect} from 'react';
import {useWatch} from 'react-hook-form';

import {AppContext} from '../context/AppContext';
import {setSelectedStep} from '../context/actions';
import {TIP_EVENT} from '../events';
import {calculatePercentage, countCompletedFields} from '../utils';
import {businessTotalFields} from '../utils/businessFields';
import {AVAILABLE_STEPS, TOTAL_OF_FIELD} from '../utils/constants';
import {propertyTotalFields} from '../utils/propertyFields';
import {useCustomEvent} from './useCustomEvent';

export const useStepWizard = () => {
	const form = useWatch();
	const [dispatchEvent] = useCustomEvent(TIP_EVENT);
	const {dispatch, state} = useContext(AppContext);

	useEffect(() => {
		_updateStepPercentage();
	}, [form]);

	useEffect(() => {
		dispatchEvent({
			hide: true,
		});
	}, [state.selectedStep.section]);

	const _updateStepPercentage = () => {
		switch (state.selectedStep.section) {
			case AVAILABLE_STEPS.BASICS_BUSINESS_TYPE.section:
				return setPercentage(
					calculatePercentage(
						countCompletedFields(form?.basics || {}),
						TOTAL_OF_FIELD.BASICS
					)
				);

			case AVAILABLE_STEPS.BUSINESS.section:
				return setPercentage(
					calculatePercentage(
						countCompletedFields(form?.business || {}),
						businessTotalFields(form?.basics?.properties)
					)
				);

			case AVAILABLE_STEPS.EMPLOYEES.section:
				return setPercentage(
					calculatePercentage(
						countCompletedFields(form?.employees || {}),
						TOTAL_OF_FIELD.EMPLOYEES
					)
				);

			case AVAILABLE_STEPS.PROPERTY.section:
				return setPercentage(
					calculatePercentage(
						countCompletedFields(form?.property || {}),
						propertyTotalFields(form)
					)
				);

			default:
				return setPercentage(0);
		}
	};

	const setSection = (step) =>
		dispatch(
			setSelectedStep({
				...state.selectedStep,
				...step,
			})
		);

	const setPercentage = (percentage = 0) =>
		dispatch(
			setSelectedStep({
				...state.selectedStep,
				percentage,
			})
		);

	return {
		selectedStep: state.selectedStep,
		setPercentage,
		setSection,
	};
};
