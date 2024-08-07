/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openToast} from 'frontend-js-web';

import deleteItemAction from '../actions/deleteItem';
import {FREEMARKER_FRAGMENT_ENTRY_PROCESSOR} from '../config/constants/freemarkerFragmentEntryProcessor';
import {ITEM_ACTIVATION_ORIGINS} from '../config/constants/itemActivationOrigins';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import selectEditableValue from '../selectors/selectEditableValue';
import selectFormConfiguration from '../selectors/selectFormConfiguration';
import FormService from '../services/FormService';
import LayoutService from '../services/LayoutService';
import {CACHE_KEYS, getCacheItem, getCacheKey} from '../utils/cache';
import {
	FORM_ERROR_TYPES,
	getFormErrorDescription,
} from '../utils/getFormErrorDescription';
import getFragmentEntryLinkIdsFromItemId from '../utils/getFragmentEntryLinkIdsFromItemId';
import getPortletId from '../utils/getPortletId';
import {hasFormParent} from '../utils/hasFormParent';
import {isRequiredFormInput} from '../utils/isRequiredFormInput';
import {clearPageContents} from '../utils/usePageContents';

export function getPreviousItemId(itemId, layoutData, nextLayoutData) {
	const {items} = layoutData;
	const {items: nextItems} = nextLayoutData;
	const parentId = items[itemId].parentId;

	const parent = items[parentId];

	if (nextItems[parentId].children.length) {
		const index = parent.children.indexOf(itemId);

		return nextItems[parentId].children[index ? index - 1 : index];
	}
	else if (
		parent.type === LAYOUT_DATA_ITEM_TYPES.collectionItem ||
		parent.type === LAYOUT_DATA_ITEM_TYPES.column
	) {
		return nextItems[parent.parentId].itemId;
	}
	else if (parent.type === LAYOUT_DATA_ITEM_TYPES.root) {
		return null;
	}

	return parentId;
}

export default function deleteItem({itemIds, selectItem = () => {}}) {
	return (dispatch, getState) => {
		const {fragmentEntryLinks, layoutData, segmentsExperienceId} =
			getState();

		return markItemForDeletion({
			fragmentEntryLinks,
			itemIds,
			layoutData,
			onNetworkStatus: dispatch,
			segmentsExperienceId,
		}).then(({portletIds = [], layoutData: nextLayoutData}) => {
			const nextItemId = getPreviousItemId(
				itemIds[0],
				layoutData,
				nextLayoutData
			);

			if (!nextItemId) {
				document
					.querySelector('button[data-panel-id="browser"]')
					.focus();
			}

			selectItem(nextItemId, {
				origin: ITEM_ACTIVATION_ORIGINS.itemActions,
			});

			const fragmentEntryLinkIds = itemIds.flatMap((itemId) =>
				getFragmentEntryLinkIdsFromItemId({
					itemId,
					layoutData: nextLayoutData,
				})
			);

			dispatch(
				deleteItemAction({
					fragmentEntryLinkIds,
					itemIds,
					layoutData: nextLayoutData,
					portletIds,
				})
			);

			clearPageContents();

			maybeShowAlert(layoutData, itemIds, fragmentEntryLinks);
		});
	};
}

function markItemForDeletion({
	fragmentEntryLinks,
	itemIds,
	layoutData,
	onNetworkStatus: dispatch,
	segmentsExperienceId,
}) {

	// We just need to remove the parents of the selected items

	const parentItemIds = getParentItemIds(itemIds, layoutData);

	const portletIds = parentItemIds.flatMap((itemId) =>
		findPortletIds(itemId, layoutData, fragmentEntryLinks)
	);

	return LayoutService.markItemForDeletion({
		itemIds: parentItemIds,
		onNetworkStatus: dispatch,
		portletIds,
		segmentsExperienceId,
	}).then((response) => {
		return {...response, portletIds};
	});
}

function getParentItemIds(itemIds, layoutData) {
	const {items: layoutDataItems} = layoutData;
	const itemsToRemoveFromSelected = [];

	const hasParentSelected = (itemId) => {
		const parentId = layoutDataItems[itemId].parentId;

		if (!parentId) {
			return false;
		}

		if (itemIds.includes(parentId)) {
			return true;
		}

		return hasParentSelected(parentId);
	};

	itemIds.forEach((itemId) => {
		if (hasParentSelected(itemId)) {
			itemsToRemoveFromSelected.push(itemId);
		}
	});

	return itemIds.filter(
		(itemId) => !itemsToRemoveFromSelected.includes(itemId)
	);
}

function findPortletIds(itemId, layoutData, fragmentEntryLinks) {
	const item = layoutData.items[itemId];

	const {config = {}, children = []} = item;

	if (
		item.type === LAYOUT_DATA_ITEM_TYPES.fragment &&
		config.fragmentEntryLinkId
	) {
		const {editableValues = {}} =
			fragmentEntryLinks[config.fragmentEntryLinkId];

		if (editableValues.portletId) {
			return [getPortletId(editableValues)];
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

function maybeShowAlert(layoutData, itemIds, fragmentEntryLinks) {
	const [itemId] = itemIds;
	const item = layoutData?.items?.[itemId];

	if (
		!item ||
		item.type !== LAYOUT_DATA_ITEM_TYPES.fragment ||
		!hasFormParent(item, layoutData)
	) {
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

			const {message} = getFormErrorDescription({
				name: getFieldLabel(fieldId, formFields),
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
