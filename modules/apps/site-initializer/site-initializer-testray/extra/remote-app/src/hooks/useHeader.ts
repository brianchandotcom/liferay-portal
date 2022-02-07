/* eslint-disable @typescript-eslint/no-unused-vars */
/* eslint-disable no-console */
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

import {useCallback, useContext, useEffect} from 'react';

import {
	HeaderContext,
	HeaderTabs,
	HeaderTitle,
	HeaderTypes,
} from '../context/HeaderContext';
import usePrevious from './usePrevious';

const useHeader = () => {
	const [{heading}, dispatch] = useContext(HeaderContext);

	const prevHeading = usePrevious(heading);

	const setHeading = useCallback(
		(newHeading: HeaderTitle[] = [], append?: boolean) => {
			dispatch({
				payload: {append, heading: newHeading},
				type: HeaderTypes.SET_HEADING,
			});
		},
		[dispatch]
	);

	const setTabs = useCallback(
		(newTabs: HeaderTabs[] = []) =>
			dispatch({payload: newTabs, type: HeaderTypes.SET_TABS}),
		[dispatch]
	);

	return {
		prevHeading,
		setHeading,
		setTabs,
	};
};

export default useHeader;
