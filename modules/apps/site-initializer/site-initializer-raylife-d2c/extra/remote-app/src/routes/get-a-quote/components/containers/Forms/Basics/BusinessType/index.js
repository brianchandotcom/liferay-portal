/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext, useEffect, useState} from 'react';
import {useFormContext} from 'react-hook-form';
import {
	STORAGE_KEYS,
	Storage,
} from '../../../../../../../common/services/liferay/storage';
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
				isMobileDevice={state.dimensions.device.isMobile}
				setNewSelectedProduct={(value) => {
					setNewSelectedProduct(value);

					Storage.setItem(STORAGE_KEYS.SELECTED_PRODUCT, value);
				}}
				taxonomyVocabularyId={state.taxonomyVocabulary.id}
			/>
		</div>
	);
}
