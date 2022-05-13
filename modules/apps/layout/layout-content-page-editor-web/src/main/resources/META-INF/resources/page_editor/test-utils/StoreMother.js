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

import React from 'react';

import {StoreAPIContextProvider} from '../app/contexts/StoreContext';

const DEFAULT_STATE = {
	availableSegmentsExperiences: {
		0: {
			hasLockedSegmentsExperiment: false,
			name: 'Default Experience',
			priority: -1,
			segmentsEntryId: 'test-segment-id-00',
			segmentsExperienceId: '0',
			segmentsExperimentStatus: undefined,
			segmentsExperimentURL: 'https//:default-experience.com',
		},
	},
	languageId: 'en_US',
	layoutData: {
		items: [],
	},
	permissions: {},
	segmentsExperienceId: '0',
	selectedViewportSize: 'desktop',
};

const StoreMother = {
	Component: ({children, dispatch = undefined, getState = undefined}) => (
		<StoreAPIContextProvider
			dispatch={StoreMother.getDefaultDispatch(dispatch)}
			getState={StoreMother.getDefaultGetState(getState)}
		>
			{children}
		</StoreAPIContextProvider>
	),

	getDefaultDispatch(dispatch = () => {}) {
		return dispatch;
	},

	getDefaultGetState(getState = () => ({})) {
		return () => ({
			...DEFAULT_STATE,
			...getState(),
		});
	},
};

export default StoreMother;
