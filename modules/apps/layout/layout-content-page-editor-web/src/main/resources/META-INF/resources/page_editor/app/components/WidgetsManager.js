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

import {useEffect} from 'react';

import updateWidgets from '../actions/updateWidgets';
import {
	useDispatch,
	useSelector,
	useSelectorRef,
} from '../contexts/StoreContext';
import selectSegmentsExperienceId from '../selectors/selectSegmentsExperienceId';
import selectWidgetFragmentEntryLinks from '../selectors/selectWidgetFragmentEntryLinks';
import loadWidgets from '../thunks/loadWidgets';

export default function WidgetsManager() {
	const dispatch = useDispatch();

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

	const fragmentEntryLinksRef = useSelectorRef(
		selectWidgetFragmentEntryLinks
	);

	useEffect(() => {
		dispatch(
			updateWidgets({
				fragmentEntryLinks: fragmentEntryLinksRef.current,
			})
		);
	}, [fragmentEntryLinksIds, fragmentEntryLinksRef, dispatch]);

	useEffect(() => {
		const handler = Liferay.on('addPortletConfigurationTemplate', () => {
			dispatch(
				loadWidgets({
					fragmentEntryLinks: fragmentEntryLinksRef.current,
				})
			);
		});

		return () => {
			handler.detach();
		};
	}, [fragmentEntryLinksRef, dispatch]);

	return null;
}
