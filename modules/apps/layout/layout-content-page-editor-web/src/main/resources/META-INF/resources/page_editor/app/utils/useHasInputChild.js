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

import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../config/constants/freemarkerFragmentEntryProcessor';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {useSelectorCallback, useSelectorRef} from '../contexts/StoreContext';
import selectFormConfiguration from '../selectors/selectFormConfiguration';
import FormService from '../services/FormService';
import useCache from './useCache';

function isMappedToRequiredInput(fragmentEntryLink, formFields) {
	const flattenedFields = formFields.flatMap((fieldSet) => fieldSet.fields);

	const fieldId =
		fragmentEntryLink.editableValues[FREEMARKER_FRAGMENT_ENTRY_PROCESSOR]
			.inputFieldId;

	return flattenedFields.some(
		(field) => field.key === fieldId && field.required
	);
}

function hasRequiredInputChild(
	item,
	layoutData,
	fragmentEntryLinks,
	formFields
) {
	return item.children.some((childId) => {
		const child = layoutData.items[childId];

		if (child.type !== LAYOUT_DATA_ITEM_TYPES.fragment) {
			return hasRequiredInputChild(
				child,
				layoutData,
				fragmentEntryLinks,
				formFields
			);
		}

		const fragmentEntryLink =
			fragmentEntryLinks[child.config.fragmentEntryLinkId];

		return (
			isMappedToRequiredInput(fragmentEntryLink, formFields) ||
			hasRequiredInputChild(
				child,
				layoutData,
				fragmentEntryLinks,
				formFields
			)
		);
	});
}

export default function useHasInputChild(itemId) {
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
		key: ['formFields', classNameId, classTypeId],
	});

	return useCallback(
		() =>
			formFields
				? hasRequiredInputChild(
						layoutDataRef.current?.items[itemId],
						layoutDataRef.current,
						fragmentEntryLinksRef.current,
						formFields
				  )
				: false,
		[formFields, layoutDataRef, fragmentEntryLinksRef, itemId]
	);
}
