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

import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../config/constants/freemarkerFragmentEntryProcessor';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {useSelectorRef} from '../contexts/StoreContext';
import FormService from '../services/FormService';
import {getCacheItem, getCacheKey} from './cache';
import getFragmentItem from './getFragmentItem';
import {isLayoutDataItemDeleted} from './isLayoutDataItemDeleted';

function getDescendantIds(layoutData, itemId) {
	const item = layoutData.items[itemId];

	const descendantIds = [...item.children];

	item.children.forEach((childId) => {
		if (!isLayoutDataItemDeleted(layoutData, childId)) {
			descendantIds.push(...getDescendantIds(layoutData, childId));
		}
	});

	return descendantIds;
}

function getFormItems(layoutData) {
	return Object.values(layoutData.items).filter(
		(item) =>
			item.type === LAYOUT_DATA_ITEM_TYPES.form &&
			item.config.classNameId !== '0' &&
			!isLayoutDataItemDeleted(layoutData, item.itemId)
	);
}

export default function useIsSomeFormIncomplete() {
	const stateRef = useSelectorRef((state) => state);

	return () => {
		const {fragmentEntryLinks, layoutData} = stateRef.current;

		const forms = getFormItems(layoutData);

		if (!forms.length) {
			return Promise.resolve(false);
		}

		const promises = forms.map((form) => {
			const {
				config: {classNameId, classTypeId},
				itemId,
			} = form;

			const payload = {
				classNameId,
				classTypeId,
			};

			const cacheKey = getCacheKey([
				'formFields',
				classNameId,
				classTypeId,
			]);

			const {data: fields} = getCacheItem(cacheKey);

			const promise = fields
				? Promise.resolve({fields, itemId})
				: FormService.getFormFields(payload).then((fields) => ({
						fields,
						itemId,
				  }));

			return promise;
		});

		return Promise.all(promises).then((forms) =>
			forms.some(({fields, itemId}) => {
				const requiredFields = fields
					.flatMap((fieldSet) => fieldSet.fields)
					.filter((field) => field.required);

				const descendantIds = getDescendantIds(layoutData, itemId);

				return requiredFields.some(
					(field) =>
						!Object.values(fragmentEntryLinks).some(
							(fragmentEntryLink) => {
								const item = getFragmentItem(
									layoutData,
									fragmentEntryLink.fragmentEntryLinkId
								);

								return (
									fragmentEntryLink.editableValues?.[
										FREEMARKER_FRAGMENT_ENTRY_PROCESSOR
									]?.inputFieldId === field.key &&
									descendantIds.includes(item.itemId)
								);
							}
						)
				);
			})
		);
	};
}
