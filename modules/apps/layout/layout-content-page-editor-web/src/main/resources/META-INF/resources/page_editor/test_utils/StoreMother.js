/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	sidebar: {hidden: false, panelId: 'browser'},
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
