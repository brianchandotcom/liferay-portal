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

import {useCallback} from 'react';

import {useGlobalContext} from '../contexts/GlobalContext';
import {useSelectorCallback, useSelectorRef} from '../contexts/StoreContext';
import selectFormConfiguration from '../selectors/selectFormConfiguration';
import FormService from '../services/FormService';
import {CACHE_KEYS} from './cache';
import hasRequiredInputChild from './hasRequiredInputChild';
import hasSubmitChild from './hasSubmitChild';
import useCache from './useCache';

export default function useHasRequiredChild(itemId) {
	const globalContext = useGlobalContext();

	const layoutDataRef = useSelectorRef((state) => state.layoutData);
	const fragmentEntryLinksRef = useSelectorRef(
		(state) => state.fragmentEntryLinks
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
			hasSubmitChild(itemId, globalContext) ||
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
		globalContext,
	]);
}
