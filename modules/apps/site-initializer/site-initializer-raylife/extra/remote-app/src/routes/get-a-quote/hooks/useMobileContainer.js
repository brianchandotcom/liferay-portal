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
import {DEVICES} from '../../../common/utils/constants';
import {AppContext} from '../context/AppContextProvider';

const useMobileContainer = () => {
	const {
		state: {
			dimensions,
			selectedStep: {mobileSubSections = []},
		},
	} = useContext(AppContext);

	const isMobile = dimensions.deviceSize === DEVICES.PHONE;

	const activeMobileSubSection = mobileSubSections.find(({active}) => active);

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
	};
};

export default useMobileContainer;
