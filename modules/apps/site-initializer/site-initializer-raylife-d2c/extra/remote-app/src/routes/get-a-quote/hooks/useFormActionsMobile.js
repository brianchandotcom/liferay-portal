/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useContext} from 'react';
import {ActionTypes, AppContext} from '../context/AppContextProvider';

const useFormActionsMobile = (formActionsDefault, redirectToHomePage) => {
	const {
		dispatch,
		state: {
			selectedStep: {index: currentStepIndex, mobileSubSections},
		},
	} = useContext(AppContext);

	const hasMobileSubSections = Array.isArray(mobileSubSections);
	const activeIndex = mobileSubSections?.findIndex(({active}) => active);

	const setMobileSubSectionActive = (nextStep) => {
		dispatch({
			payload: {nextStep},
			type: ActionTypes.SET_MOBILE_SUBSECTION_ACTIVE,
		});
	};

	const onNext = () => {
		if (
			hasMobileSubSections &&
			activeIndex !== mobileSubSections.length - 1
		) {
			return setMobileSubSectionActive(true);
		}

		formActionsDefault.onNext();
	};

	const onPrevious = () => {
		if (currentStepIndex === 0 && !hasMobileSubSections) {
			return redirectToHomePage();
		}

		if (activeIndex && hasMobileSubSections) {
			return setMobileSubSectionActive(false);
		}

		formActionsDefault.onPrevious();
	};

	return {
		...formActionsDefault,
		onNext,
		onPrevious,
	};
};

export default useFormActionsMobile;
