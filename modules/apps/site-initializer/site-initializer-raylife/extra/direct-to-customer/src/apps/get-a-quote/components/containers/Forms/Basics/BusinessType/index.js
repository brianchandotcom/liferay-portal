import React, {useContext, useState} from 'react';
import {useFormContext} from 'react-hook-form';
import {AppContext} from '~/apps/get-a-quote/context/AppContext';
import {setSelectedProduct} from '~/apps/get-a-quote/context/actions';
import {useStepWizard} from '~/apps/get-a-quote/hooks/useStepWizard';
import {AVAILABLE_STEPS} from '~/apps/get-a-quote/utils/constants';
import {CardFormActionsWithSave} from '~/shared/components/fragments/Card/FormActionsWithSave';
import {STORAGE_KEYS, Storage} from '~/shared/services/liferay/storage';
import {smoothScroll} from '~/shared/utils/scroll';

import {BusinessTypeSearch} from './Search';

export const FormBasicBusinessType = ({form}) => {
	const {setSection} = useStepWizard();
	const [newSelectedProduct, setNewSelectedProduct] = useState('');
	const {dispatch, state} = useContext(AppContext);
	const {setValue} = useFormContext();

	const goToNextForm = () => {
		setSection(AVAILABLE_STEPS.BASICS_BUSINESS_INFORMATION);

		if (state.selectedProduct !== newSelectedProduct) {
			setValue('business', '');
			dispatch(setSelectedProduct(newSelectedProduct));
		}

		smoothScroll();
	};

	const goToPreviousPage = () => {
		window.location.href = '/web/raylife';
		if (Storage.itemExist(STORAGE_KEYS.BACK_TO_EDIT)) {
			Storage.removeItem(STORAGE_KEYS.BACK_TO_EDIT);
		}
	};

	return (
		<div className="card">
			<div className="card-content">
				<BusinessTypeSearch
					form={form}
					setNewSelectedProduct={setNewSelectedProduct}
				/>
			</div>

			<CardFormActionsWithSave
				isValid={!!form?.basics?.businessCategoryId}
				onNext={goToNextForm}
				onPrevious={goToPreviousPage}
			/>
		</div>
	);
};
