import React, {useContext, useState} from 'react';
import {useFormContext} from 'react-hook-form';
import {CardFormActions} from '../../../../../../../common/components/fragments/Card/FormActions';
import FormCard from '../../../../../../../common/components/fragments/Card/FormCard';
import {DEVICES} from '../../../../../../../common/utils/constants';
import {smoothScroll} from '../../../../../../../common/utils/scroll';
import {
	ActionTypes,
	AppContext,
} from '../../../../../context/AppContextProvider';
import {useStepWizard} from '../../../../../hooks/useStepWizard';
import {AVAILABLE_STEPS} from '../../../../../utils/constants';
import {BusinessTypeSearch} from './Search';

export function FormBasicBusinessType({form}) {
	const {setSection} = useStepWizard();
	const [newSelectedProduct, setNewSelectedProduct] = useState('');
	const {dispatch, state} = useContext(AppContext);
	const {setValue} = useFormContext();

	const isMobileDevice = state.dimensions.deviceSize === DEVICES.PHONE;

	const goToNextForm = () => {
		setSection(AVAILABLE_STEPS.BASICS_BUSINESS_INFORMATION);

		if (state.selectedProduct !== newSelectedProduct) {
			setValue('business', '');
			dispatch({
				payload: newSelectedProduct,
				type: ActionTypes.SET_SELECTED_PRODUCT,
			});
		}

		smoothScroll();
	};

	const goToPreviousPage = () => {
		setSection(AVAILABLE_STEPS.BASICS_PRODUCT_QUOTE);

		smoothScroll();
	};

	return (
		<FormCard>
			<div className="d-flex flex-column mb-5">
				<BusinessTypeSearch
					form={form}
					isMobileDevice={isMobileDevice}
					setNewSelectedProduct={setNewSelectedProduct}
					taxonomyVocabularyId={state.taxonomyVocabulary.id}
				/>
			</div>

			<CardFormActions
				isMobileDevice={isMobileDevice}
				isValid={!!form?.basics?.businessCategoryId}
				onNext={goToNextForm}
				onPrevious={goToPreviousPage}
			/>
		</FormCard>
	);
}
