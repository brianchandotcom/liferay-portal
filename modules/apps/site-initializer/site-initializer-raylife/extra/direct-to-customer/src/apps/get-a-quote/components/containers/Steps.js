import React from 'react';
import {useStepWizard} from '~/apps/get-a-quote/hooks/useStepWizard';
import {AVAILABLE_STEPS} from '~/apps/get-a-quote/utils/constants';
import {StepItem} from '~/shared/components/fragments/Step/Item';
import {StepList} from '~/shared/components/fragments/Step/List';
import {STORAGE_KEYS, Storage} from '~/shared/services/liferay/storage';

export const Steps = () => {
	const {selectedStep, setSection} = useStepWizard();

	return (
		<StepList>
			<StepItem
				onClick={() => {
					Storage.setItem(STORAGE_KEYS.BASIC_STEP_CLICKED, true);
					setSection(AVAILABLE_STEPS.BASICS_BUSINESS_TYPE);
				}}
				percentage={
					selectedStep.percentage[
						AVAILABLE_STEPS.BASICS_BUSINESS_TYPE.section
					]
				}
				selected={
					selectedStep.section ===
					AVAILABLE_STEPS.BASICS_BUSINESS_TYPE.section
				}
			>
				Basics
			</StepItem>

			<StepItem
				onClick={() => setSection(AVAILABLE_STEPS.BUSINESS)}
				percentage={
					selectedStep.percentage[AVAILABLE_STEPS.BUSINESS.section]
				}
				selected={
					selectedStep.section === AVAILABLE_STEPS.BUSINESS.section
				}
			>
				Business
			</StepItem>

			<StepItem
				onClick={() => setSection(AVAILABLE_STEPS.EMPLOYEES)}
				percentage={
					selectedStep.percentage[AVAILABLE_STEPS.EMPLOYEES.section]
				}
				selected={
					selectedStep.section === AVAILABLE_STEPS.EMPLOYEES.section
				}
			>
				Employees
			</StepItem>

			<StepItem
				onClick={() => setSection(AVAILABLE_STEPS.PROPERTY)}
				percentage={
					selectedStep.percentage[AVAILABLE_STEPS.PROPERTY.section]
				}
				selected={
					selectedStep.section === AVAILABLE_STEPS.PROPERTY.section
				}
			>
				Property
			</StepItem>
		</StepList>
	);
};
