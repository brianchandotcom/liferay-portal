/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback} from 'react';

import {
	useSelector,
	useSelectorCallback,
	useSelectorRef,
} from '../contexts/StoreContext';
import selectFormConfiguration from '../selectors/selectFormConfiguration';
import FormService from '../services/FormService';
import {CACHE_KEYS} from './cache';
import hasRequiredInputChild from './hasRequiredInputChild';
import {hasVisibleFormButtonChild} from './hasVisibleFormButtonChild';
import useCache from './useCache';

export default function useHasRequiredChild(itemId) {
	const layoutDataRef = useSelectorRef((state) => state.layoutData);
	const fragmentEntryLinksRef = useSelectorRef(
		(state) => state.fragmentEntryLinks
	);
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const formConfiguration = useSelectorCallback(
		(state) =>
			selectFormConfiguration(
				state.layoutData?.items[itemId],
				state.layoutData
			),
		[itemId]
	);

	const {classNameId, classTypeId} = formConfiguration;

	const formFields = useCache({
		fetcher: () => FormService.getFormFields({classNameId, classTypeId}),
		key: [CACHE_KEYS.formFields, classNameId, classTypeId],
	});

	return useCallback(() => {
		if (!formFields) {
			return false;
		}

		return (
			hasVisibleFormButtonChild({
				fragmentEntryLinks: fragmentEntryLinksRef.current,
				itemId,
				layoutData: layoutDataRef.current,
				type: 'submit',
				viewportSize: selectedViewportSize,
			}) ||
			hasRequiredInputChild({
				formFields,
				fragmentEntryLinks: fragmentEntryLinksRef.current,
				itemId,
				layoutData: layoutDataRef.current,
			})
		);
	}, [
		formFields,
		layoutDataRef,
		fragmentEntryLinksRef,
		itemId,
		selectedViewportSize,
	]);
}
