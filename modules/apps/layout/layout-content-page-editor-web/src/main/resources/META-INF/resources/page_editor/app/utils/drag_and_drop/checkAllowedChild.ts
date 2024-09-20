/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	FragmentEntryLink,
	FragmentEntryLinkMap,
} from '../../../app/actions/addFragmentEntryLinks';
import {FormLayoutDataItem} from '../../../types/layout_data/FormLayoutDataItem';
import {
	LayoutData,
	LayoutDataItem,
} from '../../../types/layout_data/LayoutData';
import {FRAGMENT_ENTRY_TYPES} from '../../config/constants/fragmentEntryTypes';
import {LAYOUT_DATA_ITEM_TYPES} from '../../config/constants/layoutDataItemTypes';
import {getStepperChild} from '../../utils/getStepperChild';
import {formIsMapped} from '../formIsMapped';
import {getFormParent} from '../getFormParent';
import {hasFormStepParent} from '../hasFormStepParent';
import {isMultistepForm} from '../isMultistepForm';
import {isUnmappedCollection} from '../isUnmappedCollection';

type DragAndDropItem = LayoutDataItem & {
	fieldTypes: FragmentEntryLink['fieldTypes'];
	fragmentEntryType: FragmentEntryLink['fragmentEntryType'];
	isWidget: boolean;
};

const LAYOUT_DATA_CHECK_ALLOWED_CHILDREN = {
	[LAYOUT_DATA_ITEM_TYPES.root]: (child: LayoutDataItem) =>
		[
			LAYOUT_DATA_ITEM_TYPES.collection,
			LAYOUT_DATA_ITEM_TYPES.dropZone,
			LAYOUT_DATA_ITEM_TYPES.container,
			LAYOUT_DATA_ITEM_TYPES.row,
			LAYOUT_DATA_ITEM_TYPES.fragment,
			LAYOUT_DATA_ITEM_TYPES.form,
		].some((type) => type === child.type),
	[LAYOUT_DATA_ITEM_TYPES.collection]: () => false,
	[LAYOUT_DATA_ITEM_TYPES.collectionItem]: (child: LayoutDataItem) =>
		[
			LAYOUT_DATA_ITEM_TYPES.collection,
			LAYOUT_DATA_ITEM_TYPES.container,
			LAYOUT_DATA_ITEM_TYPES.row,
			LAYOUT_DATA_ITEM_TYPES.fragment,
		].some((type) => type === child.type),
	[LAYOUT_DATA_ITEM_TYPES.dropZone]: () => false,
	[LAYOUT_DATA_ITEM_TYPES.container]: (child: LayoutDataItem) =>
		[
			LAYOUT_DATA_ITEM_TYPES.collection,
			LAYOUT_DATA_ITEM_TYPES.container,
			LAYOUT_DATA_ITEM_TYPES.dropZone,
			LAYOUT_DATA_ITEM_TYPES.row,
			LAYOUT_DATA_ITEM_TYPES.fragment,
			LAYOUT_DATA_ITEM_TYPES.form,
		].some((type) => type === child.type),
	[LAYOUT_DATA_ITEM_TYPES.form]: (
		child: LayoutDataItem,
		parent: LayoutDataItem
	) =>
		formIsMapped(parent as FormLayoutDataItem)
			? [
					LAYOUT_DATA_ITEM_TYPES.collection,
					LAYOUT_DATA_ITEM_TYPES.container,
					LAYOUT_DATA_ITEM_TYPES.dropZone,
					LAYOUT_DATA_ITEM_TYPES.row,
					LAYOUT_DATA_ITEM_TYPES.fragment,
				].some((type) => type === child.type)
			: false,
	[LAYOUT_DATA_ITEM_TYPES.formStep]: (child: LayoutDataItem) =>
		[
			LAYOUT_DATA_ITEM_TYPES.collection,
			LAYOUT_DATA_ITEM_TYPES.container,
			LAYOUT_DATA_ITEM_TYPES.dropZone,
			LAYOUT_DATA_ITEM_TYPES.row,
			LAYOUT_DATA_ITEM_TYPES.fragment,
			LAYOUT_DATA_ITEM_TYPES.form,
		].some((type) => type === child.type),
	[LAYOUT_DATA_ITEM_TYPES.formStepContainer]: (child: LayoutDataItem) =>
		[LAYOUT_DATA_ITEM_TYPES.formStep].some((type) => type === child.type),
	[LAYOUT_DATA_ITEM_TYPES.row]: (child: LayoutDataItem) =>
		[LAYOUT_DATA_ITEM_TYPES.column].some((type) => type === child.type),
	[LAYOUT_DATA_ITEM_TYPES.column]: (child: LayoutDataItem) =>
		[
			LAYOUT_DATA_ITEM_TYPES.collection,
			LAYOUT_DATA_ITEM_TYPES.container,
			LAYOUT_DATA_ITEM_TYPES.dropZone,
			LAYOUT_DATA_ITEM_TYPES.row,
			LAYOUT_DATA_ITEM_TYPES.fragment,
			LAYOUT_DATA_ITEM_TYPES.form,
		].some((type) => type === child.type),
	[LAYOUT_DATA_ITEM_TYPES.fragment]: () => false,
	[LAYOUT_DATA_ITEM_TYPES.fragmentDropZone]: (child: LayoutDataItem) =>
		[
			LAYOUT_DATA_ITEM_TYPES.collection,
			LAYOUT_DATA_ITEM_TYPES.dropZone,
			LAYOUT_DATA_ITEM_TYPES.container,
			LAYOUT_DATA_ITEM_TYPES.row,
			LAYOUT_DATA_ITEM_TYPES.fragment,
			LAYOUT_DATA_ITEM_TYPES.form,
		].some((type) => type === child.type),
};

/**
 * Checks if the given child can be nested inside given parent
 */
export default function checkAllowedChild(
	child: DragAndDropItem,
	parent: DragAndDropItem,
	layoutData: LayoutData,
	fragmentEntryLinks: FragmentEntryLinkMap
) {
	if (isUnmappedCollection(parent) || isUnmappedForm(parent)) {
		return false;
	}

	const isStepper = child.fieldTypes?.includes('stepper');
	const formParent = getFormParent(parent, layoutData);

	if (
		!isStepper &&
		isMultistepForm(formParent) &&
		!hasFormStepParent(parent, layoutData)
	) {
		return false;
	}

	if (child.type === LAYOUT_DATA_ITEM_TYPES.fragment) {
		if (isStepper) {
			if (parent.type !== LAYOUT_DATA_ITEM_TYPES.form) {
				return false;
			}

			const existingStepper = getStepperChild(
				parent,
				layoutData,
				fragmentEntryLinks
			);

			if (existingStepper && existingStepper.itemId !== child.itemId) {
				return false;
			}
		}
		else {
			if (
				child.fragmentEntryType === FRAGMENT_ENTRY_TYPES.input &&
				!formParent
			) {
				return false;
			}

			if (formParent && child.isWidget) {
				return false;
			}
		}
	}

	return LAYOUT_DATA_CHECK_ALLOWED_CHILDREN[parent.type](child, parent);
}

function isUnmappedForm(item: LayoutDataItem) {
	return item.type === LAYOUT_DATA_ITEM_TYPES.form && !formIsMapped(item);
}
