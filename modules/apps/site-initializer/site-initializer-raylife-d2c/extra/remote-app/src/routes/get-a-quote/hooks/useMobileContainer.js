/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
