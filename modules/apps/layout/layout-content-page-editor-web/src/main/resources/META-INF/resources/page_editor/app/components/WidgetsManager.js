/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
