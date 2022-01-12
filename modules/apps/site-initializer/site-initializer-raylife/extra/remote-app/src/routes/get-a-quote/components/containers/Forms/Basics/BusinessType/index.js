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

import React, {useContext, useState} from 'react';
import {useFormContext} from 'react-hook-form';
import {DEVICES} from '../../../../../../../common/utils/constants';
import {smoothScroll} from '../../../../../../../common/utils/scroll';
import {
	ActionTypes,
	AppContext,
} from '../../../../../context/AppContextProvider';
import {useStepWizard} from '../../../../../hooks/useStepWizard';
import {AVAILABLE_STEPS} from '../../../../../utils/constants';
import FormCard from '../../../../card/FormCard';
import {CardFormActions} from '../../../../form-actions/FormAction';
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
		<FormCard
			Footer={(footerProps) => (
				<CardFormActions
					{...footerProps}
					isMobileDevice={isMobileDevice}
					isValid={!!form?.basics?.businessCategoryId}
					onNext={goToNextForm}
					onPrevious={goToPreviousPage}
				/>
			)}
		>
			<div className="d-flex flex-column mb-5">
				<BusinessTypeSearch
					form={form}
					isMobileDevice={isMobileDevice}
					setNewSelectedProduct={setNewSelectedProduct}
					taxonomyVocabularyId={state.taxonomyVocabulary.id}
				/>
			</div>
		</FormCard>
	);
}
