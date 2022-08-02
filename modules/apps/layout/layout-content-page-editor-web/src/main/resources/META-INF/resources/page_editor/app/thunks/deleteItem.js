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

import {openToast} from 'frontend-js-web';

import deleteItemAction from '../actions/deleteItem';
import updatePageContents from '../actions/updatePageContents';
import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../config/constants/freemarkerFragmentEntryProcessor';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import selectEditableValue from '../selectors/selectEditableValue';
import selectFormConfiguration from '../selectors/selectFormConfiguration';
import FormService from '../services/FormService';
import InfoItemService from '../services/InfoItemService';
import LayoutService from '../services/LayoutService';
import {CACHE_KEYS, getCacheItem, getCacheKey} from '../utils/cache';
import {
	FORM_ERROR_TYPES,
	getFormValidationData,
} from '../utils/getFormValidationData';
import getFragmentEntryLinkIdsFromItemId from '../utils/getFragmentEntryLinkIdsFromItemId';
import {hasFormParent} from '../utils/hasFormParent';
import hasRequiredInputChild from '../utils/hasRequiredInputChild';
import {isRequiredFormInput} from '../utils/isRequiredFormInput';

export default function deleteItem({itemId, selectItem = () => {}}) {
	return (dispatch, getState) => {
		const {
			fragmentEntryLinks,
			layoutData,
			segmentsExperienceId,
		} = getState();

		return markItemForDeletion({
			fragmentEntryLinks,
			itemId,
			layoutData,
			onNetworkStatus: dispatch,
			segmentsExperienceId,
		})
			.then(({portletIds = [], layoutData}) => {
				selectItem(null);

				const fragmentEntryLinkIds = getFragmentEntryLinkIdsFromItemId({
					itemId,
					layoutData,
				});

				dispatch(
					deleteItemAction({
						fragmentEntryLinkIds,
						itemId,
						layoutData,
						portletIds,
					})
				);

				maybeShowAlert(layoutData, itemId, fragmentEntryLinks);
			})
			.then(() => {
				InfoItemService.getPageContents({
					onNetworkStatus: dispatch,
					segmentsExperienceId,
				}).then((pageContents) => {
					dispatch(
						updatePageContents({
							pageContents,
						})
					);
				});
			});
	};
}

function markItemForDeletion({
	fragmentEntryLinks,
	itemId,
	layoutData,
	onNetworkStatus: dispatch,
	segmentsExperienceId,
}) {
	const portletIds = findPortletIds(itemId, layoutData, fragmentEntryLinks);

	return LayoutService.markItemForDeletion({
		itemId,
		onNetworkStatus: dispatch,
		portletIds,
		segmentsExperienceId,
	}).then((response) => {
		return {...response, portletIds};
	});
}

function findPortletIds(itemId, layoutData, fragmentEntryLinks) {
	const item = layoutData.items[itemId];

	const {config = {}, children = []} = item;

	if (
		item.type === LAYOUT_DATA_ITEM_TYPES.fragment &&
		config.fragmentEntryLinkId
	) {
		const {editableValues = {}} = fragmentEntryLinks[
			config.fragmentEntryLinkId
		];

		if (editableValues.portletId) {
			return [
				editableValues.instanceId
					? `${editableValues.portletId}_INSTANCE_${editableValues.instanceId}`
					: editableValues.portletId,
			];
		}
	}

	const deletedWidgets = [];

	children.forEach((itemId) => {
		deletedWidgets.push(
			...findPortletIds(itemId, layoutData, fragmentEntryLinks)
		);
	});

	return deletedWidgets;
}

function maybeShowAlert(layoutData, itemId, fragmentEntryLinks) {
	const item = layoutData?.items?.[itemId];

	if (!item || !hasFormParent(item, layoutData)) {
		return null;
	}

	const {classNameId, classTypeId} = selectFormConfiguration(
		item,
		layoutData
	);

	const cacheKey = getCacheKey([
		CACHE_KEYS.formFields,
		classNameId,
		classTypeId,
	]);

	const {data: fields} = getCacheItem(cacheKey);

	const promise = fields
		? Promise.resolve(fields)
		: FormService.getFormFields({
				classNameId,
				classTypeId,
		  });

	promise.then((formFields) => {
		if (
			item.type === LAYOUT_DATA_ITEM_TYPES.fragment &&
			isRequiredFormInput(item, fragmentEntryLinks, formFields)
		) {
			const fieldId = selectEditableValue(
				{fragmentEntryLinks},
				item.config.fragmentEntryLinkId,
				'inputFieldId',
				FREEMARKER_FRAGMENT_ENTRY_PROCESSOR
			);

			const {message} = getFormValidationData({
				name: getFieldLabel(fieldId, formFields),
				type: FORM_ERROR_TYPES.deletedField,
			});

			openToast({
				message,
				type: 'warning',
			});
		}
		else if (
			hasRequiredInputChild({
				formFields,
				fragmentEntryLinks,
				itemId,
				layoutData,
			})
		) {
			const {message} = getFormValidationData({
				type: FORM_ERROR_TYPES.deletedFragment,
			});

			openToast({
				message,
				type: 'warning',
			});
		}
	});
}

function getFieldLabel(fieldId, formFields) {
	const flattenedFields = formFields.flatMap((fieldSet) => fieldSet.fields);

	return flattenedFields.find((field) => field.key === fieldId).label;
}
