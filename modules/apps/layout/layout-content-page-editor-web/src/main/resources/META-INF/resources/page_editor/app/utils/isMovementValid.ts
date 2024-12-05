/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openToast, sub} from 'frontend-js-web';

import {LayoutData, LayoutDataItem} from '../../types/layout_data/LayoutData';
import {FragmentEntryLinkMap} from '../actions/addFragmentEntryLinks';
import {WidgetSet} from '../actions/updateWidgets';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import selectLayoutDataItemLabel from '../selectors/selectLayoutDataItemLabel';
import checkAllowedChild, {
	MovementItem,
} from './drag_and_drop/checkAllowedChild';
import toMovementItem from './toMovementItem';

type Props = {
	fragmentEntryLinks: FragmentEntryLinkMap;
	getWidgets: () => WidgetSet[];
	layoutData: LayoutData;
	onInvalid?: () => {};
	sources: Array<MovementItem>;
	targetId: LayoutDataItem['itemId'];
	type?: 'drop' | 'paste';
};

const VALID_PASTE_TYPES: Array<LayoutDataItem['type']> = [
	LAYOUT_DATA_ITEM_TYPES.column,
	LAYOUT_DATA_ITEM_TYPES.container,
	LAYOUT_DATA_ITEM_TYPES.collection,
	LAYOUT_DATA_ITEM_TYPES.collectionItem,
	LAYOUT_DATA_ITEM_TYPES.dropZone,
	LAYOUT_DATA_ITEM_TYPES.form,
	LAYOUT_DATA_ITEM_TYPES.formStep,
	LAYOUT_DATA_ITEM_TYPES.root,
];

export function getPasteTargetId(
	targetId: LayoutDataItem['itemId'],
	layoutData: LayoutData
): string {
	const target = layoutData.items[targetId];
	const items = layoutData.items;

	// Return first step id for multistep form

	if (
		target.type === LAYOUT_DATA_ITEM_TYPES.form &&
		target.config.formType === 'multistep'
	) {
		for (const childId of target.children) {
			const child = items[childId];

			if (child.type === LAYOUT_DATA_ITEM_TYPES.formStepContainer) {
				return items[child.children[0]].itemId;
			}
		}
	}

	// Return collectionItem id if collection is mapped

	if (
		target.type === LAYOUT_DATA_ITEM_TYPES.collection &&
		target.config.collection
	) {
		return target.children[0];
	}

	// Return available parent id

	if (VALID_PASTE_TYPES.some((type) => type === target.type)) {
		return target.itemId;
	}

	const parent = items[target.parentId];

	// If not found go deeper and check for available parent id

	return getPasteTargetId(parent.itemId, layoutData);
}

export function isMovementValid({
	fragmentEntryLinks,
	getWidgets,
	layoutData,
	onInvalid,
	sources,
	targetId,
	type = 'paste',
}: Props) {
	const target = toMovementItem(
		type === 'paste' ? getPasteTargetId(targetId, layoutData) : targetId,
		layoutData,
		fragmentEntryLinks
	);

	// Return false if target not found (for example if target is an editable)

	if (!target) {
		return false;
	}

	// Iterate sources to check if they are movable to target

	for (const source of sources) {

		// Check if movement is valid

		const {reason, valid} = checkAllowedChild(
			source,
			target,
			layoutData,
			fragmentEntryLinks,
			getWidgets,
			sources.length > 1
		);

		// Skip iteration if it is

		if (valid) {
			continue;
		}

		// If not valid, display error message

		let message = '';

		if (reason === 'input-outside-form') {
			message = Liferay.Language.get(
				'form-components-can-only-be-placed-inside-a-mapped-form-container'
			);
		}
		else if (reason === 'existing-stepper') {
			message = Liferay.Language.get(
				'forms-can-only-contain-one-stepper'
			);
		}
		else if (reason === 'noninstanceable-widget-inside-collection') {
			message = Liferay.Language.get(
				'noninstanceable-widgets-cannot-be-placed-inside-a-collection-display'
			);
		}
		else if (reason === 'stepper-multiple-action') {
			message = Liferay.Language.get(
				'steppers-cannot-be-moved-along-with-other-elements'
			);
		}
		else if (reason === 'stepper-outside-form') {
			message = Liferay.Language.get(
				'steppers-can-only-be-placed-inside-a-form-container'
			);
		}
		else if (reason === 'targeting-step-container') {
			message = Liferay.Language.get(
				'fragments-cannot-be-placed-inside-a-form-step-container'
			);
		}
		else if (reason === 'unmapped-collection') {
			message = Liferay.Language.get(
				'fragments-cannot-be-placed-inside-an-unmapped-collection-display-fragment'
			);
		}
		else if (reason === 'unmapped-form') {
			message = Liferay.Language.get(
				'fragments-cannot-be-placed-inside-an-unmapped-form-container'
			);
		}
		else if (reason === 'widget-inside-form') {
			message = Liferay.Language.get(
				'widgets-cannot-be-placed-inside-a-form-container'
			);
		}
		else {
			const sourceLabel = selectLayoutDataItemLabel(
				{
					fragmentEntryLinks,
					layoutData,
				},
				source
			);

			const targetLabel = selectLayoutDataItemLabel(
				{
					fragmentEntryLinks,
					layoutData,
				},
				target
			);

			message = sub(
				Liferay.Language.get('a-x-cannot-be-dropped-inside-a-x'),
				[sourceLabel, targetLabel]
			);
		}

		if (sources.length > 1) {
			message = `${Liferay.Language.get('no-element-was-moved')} ${message}`;
		}

		if (message) {
			openToast({
				message,
				type: 'danger',
			});
		}

		if (onInvalid) {
			onInvalid();
		}

		// Stop loop as there's at least an invalid move

		return false;
	}

	return true;
}
