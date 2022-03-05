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
