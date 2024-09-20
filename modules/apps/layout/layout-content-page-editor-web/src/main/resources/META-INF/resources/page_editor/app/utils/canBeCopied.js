/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openToast} from 'frontend-js-web';

import {FRAGMENT_ENTRY_TYPES} from '../config/constants/fragmentEntryTypes';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import selectLayoutDataItemLabel from '../selectors/selectLayoutDataItemLabel';
import checkAllowedChild from './drag_and_drop/checkAllowedChild';
import {formIsMapped} from './formIsMapped';
import isItemWidget from './isItemWidget';
import {isUnmappedCollection} from './isUnmappedCollection';

const PARENT_TYPES = [
	LAYOUT_DATA_ITEM_TYPES.container,
	LAYOUT_DATA_ITEM_TYPES.dropZone,
	LAYOUT_DATA_ITEM_TYPES.form,
	LAYOUT_DATA_ITEM_TYPES.root,
];

function getFragmentEntryLink(item, fragmentEntryLinks) {
	if (!item.type === LAYOUT_DATA_ITEM_TYPES.fragment) {
		return null;
	}

	return fragmentEntryLinks[item.config?.fragmentEntryLinkId];
}

function getItemTargetToPaste(item, layoutData) {
	if (PARENT_TYPES.some((type) => type === item.type)) {
		return item;
	}

	const parent = layoutData?.items?.[item.parentId];

	return getItemTargetToPaste(parent, layoutData);
}

function normalizeSourceItem(itemId, layoutData, fragmentEntryLinks) {
	const item = layoutData.items[itemId];

	const fragmentEntryLink = getFragmentEntryLink(item, fragmentEntryLinks);
	const fieldTypes = fragmentEntryLink?.fieldTypes ?? [];
	const fragmentEntryType = fragmentEntryLink?.fragmentEntryType ?? null;

	const isWidget = isItemWidget(item, fragmentEntryLinks);

	const name = selectLayoutDataItemLabel(
		{
			fragmentEntryLinks,
			layoutData,
		},
		item
	);

	return {
		...item,
		fieldTypes,
		fragmentEntryType,
		isWidget,
		name,
	};
}

export default function canBeCopied(
	copiedItemId,
	fragmentEntryLinks,
	parentItemId,
	layoutData
) {
	const source = normalizeSourceItem(
		copiedItemId,
		layoutData,
		fragmentEntryLinks
	);

	const target = normalizeSourceItem(
		parentItemId,
		layoutData,
		fragmentEntryLinks
	);

	const parent = getItemTargetToPaste(target, layoutData);

	const isChildAllowed = checkAllowedChild(
		source,
		parent,
		layoutData,
		fragmentEntryLinks
	);
	if (!isChildAllowed || isUnmappedCollection(target)) {
		let error = Liferay.Language.get(
			'element-can-not-be-copied-please-try-again'
		);

		const isForm = (item) => item.type === LAYOUT_DATA_ITEM_TYPES.form;

		const isUnmappedForm = (item) => {
			return isForm(item) && !formIsMapped(item);
		};

		if (parent.type === LAYOUT_DATA_ITEM_TYPES.dropZone) {
			error = Liferay.Language.get(
				'fragments-and-widgets-cannot-be-placed-inside-this-area'
			);
		}
		else if (
			isUnmappedCollection(parent) ||
			isUnmappedCollection(target)
		) {
			error = Liferay.Language.get(
				'fragments-cannot-be-placed-inside-an-unmapped-collection-display-fragment'
			);
		}
		else if (
			isUnmappedForm(parent) ||
			source.fragmentEntryType === FRAGMENT_ENTRY_TYPES.input
		) {
			error = Liferay.Language.get(
				'form-components-can-only-be-placed-inside-a-mapped-form-container'
			);
		}
		else if (source.isWidget && isForm(parent)) {
			error = Liferay.Language.get(
				'widgets-cannot-be-placed-inside-a-form-container'
			);
		}

		openToast({
			message: error,
			type: 'danger',
		});

		return false;
	}

	return true;
}
