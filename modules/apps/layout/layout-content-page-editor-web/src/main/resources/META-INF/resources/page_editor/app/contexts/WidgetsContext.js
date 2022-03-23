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

import React, {useContext, useEffect, useRef, useState} from 'react';

import selectSegmentsExperienceId from '../selectors/selectSegmentsExperienceId';
import WidgetService from '../services/WidgetService';
import {useSelector} from './StoreContext';

const WidgetsContext = React.createContext([]);

export function useWidgets() {
	return useContext(WidgetsContext);
}

function normalizePortlets(portlets, fragmentEntryLinks) {
	return portlets.map((portlet) => {
		const normalizedPortlet = {
			...portlet,
			used: Object.values(fragmentEntryLinks).some(
				({portletId}) => portlet.portletId === portletId
			),
		};

		if (portlet.portletItems?.length) {
			normalizedPortlet.portletItems = normalizePortlets(
				portlet.portletItems,
				fragmentEntryLinks
			);
		}

		return normalizedPortlet;
	});
}

function normalizeCategories(categories, fragmentEntryLinks) {
	return categories.map((category) => {
		const normalizedCategory = {
			...category,
			portlets: normalizePortlets(category.portlets, fragmentEntryLinks),
		};

		if (category.categories?.length) {
			normalizedCategory.categories = normalizeCategories(
				category.categories,
				fragmentEntryLinks
			);
		}

		return normalizedCategory;
	});
}

export function WidgetsContextProvider({children}) {
	const [widgets, setWidgets] = useState([]);

	const fragmentEntryLinksRef = useRef();

	const fragmentEntryLinksIds = useSelector((state) => {
		const nextSegmentsExperienceId = selectSegmentsExperienceId(state);

		return Object.values(state.fragmentEntryLinks)
			.filter(
				({portletId, removed, ...fragmentEntryLink}) =>
					portletId &&
					!removed &&
					fragmentEntryLink.segmentsExperienceId ===
						nextSegmentsExperienceId
			)
			.map(({fragmentEntryLinkId}) => fragmentEntryLinkId)
			.join(',');
	});

	useSelector((state) => {
		const nextSegmentsExperienceId = selectSegmentsExperienceId(state);

		fragmentEntryLinksRef.current = Object.values(
			state.fragmentEntryLinks
		).filter(
			({portletId, removed, ...fragmentEntryLink}) =>
				portletId &&
				!removed &&
				fragmentEntryLink.segmentsExperienceId ===
					nextSegmentsExperienceId
		);

		return null;
	});

	useEffect(() => {
		WidgetService.getWidgets().then((categories) =>
			setWidgets(
				normalizeCategories(categories, fragmentEntryLinksRef.current)
			)
		);
	}, []);

	useEffect(() => {
		setWidgets((currentWidgets) =>
			normalizeCategories(currentWidgets, fragmentEntryLinksRef.current)
		);
	}, [fragmentEntryLinksIds]);

	useEffect(() => {
		const handler = Liferay.on('addPortletConfigurationTemplate', () => {
			WidgetService.getWidgets().then((categories) =>
				setWidgets(
					normalizeCategories(
						categories,
						fragmentEntryLinksRef.current
					)
				)
			);
		});

		return () => {
			handler.detach();
		};
	}, []);

	return (
		<WidgetsContext.Provider value={widgets}>
			{children}
		</WidgetsContext.Provider>
	);
}
