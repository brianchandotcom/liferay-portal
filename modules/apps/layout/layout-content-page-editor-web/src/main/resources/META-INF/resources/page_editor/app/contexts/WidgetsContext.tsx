/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {
	ReactNode,
	createContext,
	useCallback,
	useContext,
	useEffect,
	useRef,
} from 'react';

import {FragmentEntryLink} from '../actions/addFragmentEntryLinks';
import updateWidgets from '../actions/updateWidgets';
import {
	Thunk,
	useDispatch,
	useSelector,
	useSelectorRef,
} from '../contexts/StoreContext';
import {Action} from '../reducers';
import selectSegmentsExperienceId from '../selectors/selectSegmentsExperienceId';
import selectWidgetFragmentEntryLinks from '../selectors/selectWidgetFragmentEntryLinks';
import loadWidgetsThunk from '../thunks/loadWidgets';

type Status = 'not-loaded' | 'loading' | 'loaded';

const WidgetsContext = createContext({
	getWidgets: () => {},
	loadWidgets: () => {},
});

function WidgetsContextProvider({children}: {children: ReactNode}) {
	const dispatch = useDispatch() as (
		thunkOrAction: Thunk | Action
	) => Promise<void>;

	const widgets = useSelector((state) => state.widgets);

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

	const fragmentEntryLinksRef = useSelectorRef<FragmentEntryLink[]>(
		selectWidgetFragmentEntryLinks
	);

	const statusRef = useRef<Status>('not-loaded');

	const loadWidgets = useCallback(() => {
		if (fragmentEntryLinksRef.current) {
			dispatch(
				loadWidgetsThunk({
					fragmentEntryLinks: fragmentEntryLinksRef.current,
				})
			).then(() => {
				statusRef.current = 'loaded';
			});
		}
	}, [dispatch, fragmentEntryLinksRef]);

	const getWidgets = useCallback(() => {
		if (statusRef.current === 'not-loaded') {
			statusRef.current = 'loading';

			loadWidgets();
		}

		return widgets || [];
	}, [loadWidgets, widgets]);

	useEffect(() => {
		if (fragmentEntryLinksRef.current) {
			dispatch(
				updateWidgets({
					fragmentEntryLinks: fragmentEntryLinksRef.current,
				})
			);
		}
	}, [fragmentEntryLinksIds, fragmentEntryLinksRef, dispatch]);

	useEffect(() => {
		Liferay.on('addPortletConfigurationTemplate', loadWidgets);

		return () => {
			Liferay.detach('addPortletConfigurationTemplate', loadWidgets);
		};
	}, [dispatch, loadWidgets]);

	return (
		<WidgetsContext.Provider value={{getWidgets, loadWidgets}}>
			{children}
		</WidgetsContext.Provider>
	);
}

function useGetWidgets() {
	const {getWidgets} = useContext(WidgetsContext);

	return getWidgets;
}

function useLoadWidgets() {
	const {loadWidgets} = useContext(WidgetsContext);

	return loadWidgets;
}

export {WidgetsContextProvider, useGetWidgets, useLoadWidgets};
