/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {
	MutableRefObject,
	ReactNode,
	useContext,
	useEffect,
	useRef,
} from 'react';

import {updateFragmentEntryLinksContent} from '../actions';
import selectSegmentsExperienceId from '../selectors/selectSegmentsExperienceId';
import FragmentService from '../services/FragmentService';
import {useDispatch, useSelector, useSelectorRef} from './StoreContext';

type PendingItemsRef = MutableRefObject<string[]>;

const INITIAL_STATE: {
	pendingItemsRef: PendingItemsRef;
} = {
	pendingItemsRef: {current: []},
};

const PortletContentContext = React.createContext(INITIAL_STATE);

function PortletContentContextProvider({children}: {children: ReactNode}) {
	const pendingItemsRef = useRef<string[]>([]);

	const dispatch = useDispatch();

	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);

	const fragmentEntryLinksRef = useSelectorRef(
		(state) => state.fragmentEntryLinks
	);

	const languageIdRef = useSelectorRef((state) => state.languageId);

	useEffect(() => {
		const updateContents = async () => {
			const pendingItems = pendingItemsRef.current;

			pendingItemsRef.current = [];

			if (!pendingItems.length) {
				return;
			}

			FragmentService.renderFragmentEntryLinksContent({
				data: pendingItems.map((fragmentEntryLinkId) => ({
					fragmentEntryLinkId,
				})),
				languageId: languageIdRef.current!,
				segmentsExperienceId: segmentsExperienceId!,
			}).then((response) => {
				dispatch(
					updateFragmentEntryLinksContent({
						fragmentEntryLinksContent: response,
					})
				);
			});
		};

		updateContents();
	}, [
		dispatch,
		fragmentEntryLinksRef,
		languageIdRef,
		segmentsExperienceId,
		selectedViewportSize,
	]);

	return (
		<PortletContentContext.Provider value={{pendingItemsRef}}>
			{children}
		</PortletContentContext.Provider>
	);
}

function useAddPendingItem() {
	const {pendingItemsRef} = useContext(PortletContentContext);

	return (fragmentEntryLinkId: string) => {
		if (!pendingItemsRef.current?.includes(fragmentEntryLinkId)) {
			pendingItemsRef.current = [
				...pendingItemsRef.current,
				fragmentEntryLinkId,
			];
		}
	};
}

export {PortletContentContextProvider, useAddPendingItem};
