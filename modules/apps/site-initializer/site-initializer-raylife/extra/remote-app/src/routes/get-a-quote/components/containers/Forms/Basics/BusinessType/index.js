import React, {useContext, useState} from 'react';
import {useFormContext} from 'react-hook-form';
import {CardFormActionsWithSave} from '~/common/components/fragments/Card/FormActionsWithSave';
import {LiferayService} from '~/common/services/liferay';
import {STORAGE_KEYS, Storage} from '~/common/services/liferay/storage';
import {smoothScroll} from '~/common/utils/scroll';
import {AppContext} from '~/routes/get-a-quote/context/AppContext';
import {setSelectedProduct} from '~/routes/get-a-quote/context/actions';
import {useStepWizard} from '~/routes/get-a-quote/hooks/useStepWizard';

import {AVAILABLE_STEPS} from '~/routes/get-a-quote/utils/constants';
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
		window.location.href = LiferayService.getLiferaySiteName();

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
