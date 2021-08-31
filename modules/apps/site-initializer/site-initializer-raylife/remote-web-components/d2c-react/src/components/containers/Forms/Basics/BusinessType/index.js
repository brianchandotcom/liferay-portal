import React from 'react';

import {useStepWizard} from '../../../../../hooks/useStepWizard';
import {AVAILABLE_STEPS} from '../../../../../utils/constants';
import {CardFormActionsWithSave} from '../../../../fragments/Card/FormActionsWithSave';
import {BusinessTypeSearch} from './Search';

export const FormBasicBusinessType = ({form}) => {
	const {setSection} = useStepWizard();

	const goToNextForm = () => {
		setSection(AVAILABLE_STEPS.BASICS_BUSINESS_INFORMATION);
	};

	const goToPreviousPage = () => {
		window.history.back();
	};

	return (
		<div className="card">
			<div className="card-content">
				<BusinessTypeSearch form={form} />
			</div>
			<CardFormActionsWithSave
				isValid={!!form?.basics?.businessCategoryId}
				onNext={goToNextForm}
				onPrevious={goToPreviousPage}
			/>
		</div>
	);
};
