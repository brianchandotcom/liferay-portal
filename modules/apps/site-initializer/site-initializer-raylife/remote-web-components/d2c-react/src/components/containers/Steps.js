import React from 'react';

import {useStepWizard} from '../../hooks/useStepWizard';
import {AVAILABLE_STEPS} from '../../utils/constants';
import {StepItem} from '../fragments/Step/Item';
import {StepList} from '../fragments/Step/List';

export const Steps = () => {
	const {selectedStep} = useStepWizard();

	return (
		<StepList>
			<StepItem
				percentage={selectedStep.percentage}
				selected={
					selectedStep.section ===
					AVAILABLE_STEPS.BASICS_BUSINESS_TYPE.section
				}
			>
				Basics
			</StepItem>
			<StepItem
				percentage={selectedStep.percentage}
				selected={
					selectedStep.section === AVAILABLE_STEPS.BUSINESS.section
				}
			>
				Business
			</StepItem>
			<StepItem
				percentage={selectedStep.percentage}
				selected={
					selectedStep.section === AVAILABLE_STEPS.EMPLOYEES.section
				}
			>
				Employees
			</StepItem>
			<StepItem
				percentage={selectedStep.percentage}
				selected={
					selectedStep.section === AVAILABLE_STEPS.PROPERTY.section
				}
			>
				Property
			</StepItem>
		</StepList>
	);
};
