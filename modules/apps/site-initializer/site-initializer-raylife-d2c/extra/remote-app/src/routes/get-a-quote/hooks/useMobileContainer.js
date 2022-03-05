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

import {useCallback, useContext} from 'react';
import {ActionTypes, AppContext} from '../context/AppContextProvider';

const NEXT_STEP_DELAY = 500;

const useMobileContainer = () => {
	const {
		dispatch,
		state: {
			dimensions: {
				device: {isMobile},
			},
			activeMobileSubSection,
			selectedStep: {mobileSubSections = []},
		},
	} = useContext(AppContext);

	const mobileContainerProps = {
		activeMobileSubSection,
		isMobile,
	};

	const getMobileSubSection = useCallback(
		(sectionTitle) =>
			mobileSubSections.find(({title}) => title === sectionTitle),
		[mobileSubSections]
	);

	return {
		getMobileSubSection,
		mobileContainerProps,
		nextStep: (delay = NEXT_STEP_DELAY) => {
			setTimeout(() => {
				dispatch({
					payload: {nextStep: true},
					type: ActionTypes.SET_MOBILE_SUBSECTION_ACTIVE,
				});
			}, delay);
		},
	};
};

export default useMobileContainer;
