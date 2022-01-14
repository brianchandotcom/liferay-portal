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

import React, {useContext, useEffect, useState} from 'react';
import {useFormContext} from 'react-hook-form';
import {
	STORAGE_KEYS,
	Storage,
} from '../../../../../../../common/services/liferay/storage';
import {DEVICES} from '../../../../../../../common/utils/constants';
import {
	ActionTypes,
	AppContext,
} from '../../../../../context/AppContextProvider';
import {BusinessTypeSearch} from './Search';

export function FormBasicBusinessType({form}) {
	const {dispatch, state} = useContext(AppContext);
	const {setValue} = useFormContext();
	const [newSelectedProduct, setNewSelectedProduct] = useState(
		Storage.getItem(STORAGE_KEYS.SELECTED_PRODUCT)
	);

	const isMobileDevice = state.dimensions.deviceSize === DEVICES.PHONE;

	useEffect(() => {
		if (state.selectedProduct !== newSelectedProduct) {
			setValue('business', '');
			dispatch({
				payload: newSelectedProduct,
				type: ActionTypes.SET_SELECTED_PRODUCT,
			});
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [state.selectedProduct, newSelectedProduct]);

	return (
		<div className="d-flex flex-column mb-5">
			<BusinessTypeSearch
				form={form}
				isMobileDevice={isMobileDevice}
				setNewSelectedProduct={(value) => {
					setNewSelectedProduct(value);

					Storage.setItem(STORAGE_KEYS.SELECTED_PRODUCT, value);
				}}
				taxonomyVocabularyId={state.taxonomyVocabulary.id}
			/>
		</div>
	);
}
