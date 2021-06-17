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

import React, {useContext, useEffect, useState} from 'react';

import selectSegmentsExperienceId from '../selectors/selectSegmentsExperienceId';
import WidgetService from '../services/WidgetService';
import {useSelector} from './StoreContext';

const WidgetsContext = React.createContext([]);

export const useWidgets = () => useContext(WidgetsContext);

export const WidgetsContextProvider = ({children}) => {
	const fragmentEntryLinksIds = useSelector((state) =>
		Object.values(state.fragmentEntryLinks)
			.filter(({portletId, removed}) => portletId && !removed)
			.map(({fragmentEntryLinkId}) => fragmentEntryLinkId)
			.join(',')
	);

	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);
	const [widgets, setWidgets] = useState([]);

	useEffect(() => {
		WidgetService.getWidgets(segmentsExperienceId).then(setWidgets);
	}, [fragmentEntryLinksIds, segmentsExperienceId]);

	useEffect(() => {
		const handler = Liferay.on('addPortletConfigurationTemplate', () => {
			WidgetService.getWidgets(segmentsExperienceId).then(setWidgets);
		});

		return () => {
			handler.detach();
		};
	}, [segmentsExperienceId]);

	return (
		<WidgetsContext.Provider value={widgets}>
			{children}
		</WidgetsContext.Provider>
	);
};
