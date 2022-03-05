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

import {useContext} from 'react';
import {STORAGE_KEYS} from '../../../common/services/liferay/storage';
import {RAYLIFE_PAGES} from '../../../common/utils/constants';
import {clearExitAlert} from '../../../common/utils/exitAlert';
import {redirectTo} from '../../../common/utils/liferay';
import {AppContext} from '../context/AppContextProvider';
import useFormActionsDefault from './useFormActionsDefault';
import useFormActionsMobile from './useFormActionsMobile';

const redirectToHomePage = () => {
	clearExitAlert();

	redirectTo(RAYLIFE_PAGES.HOME);

	Storage.removeItem(STORAGE_KEYS.BACK_TO_EDIT);
};

const useFormActions = (params) => {
	const {
		state: {
			dimensions: {
				device: {isMobile},
			},
			selectedStep: {index: currentStepIndex = 0},
		},
	} = useContext(AppContext);

	const formActionsDefault = useFormActionsDefault(params);
	const formActionsMobile = useFormActionsMobile(
		formActionsDefault,
		redirectToHomePage
	);

	if (isMobile) {
		return formActionsMobile;
	}

	return {
		...formActionsDefault,
		onPrevious: () => {
			if (currentStepIndex !== 0) {
				return formActionsDefault.onPrevious();
			}

			redirectToHomePage();
		},
	};
};

export default useFormActions;
