/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openToast} from 'frontend-js-web';

import {FragmentLayoutDataItem} from '../../types/layout_data/FragmentLayoutDataItem';
import {LayoutData, LayoutDataItem} from '../../types/layout_data/LayoutData';
import {FragmentEntryLinkMap} from '../actions/addFragmentEntryLinks';
import {WidgetSet} from '../actions/updateWidgets';
import {FRAGMENT_ENTRY_TYPES} from '../config/constants/fragmentEntryTypes';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import checkAllowedChild, {
	MovementItem,
} from './drag_and_drop/checkAllowedChild';
import {formIsMapped} from './formIsMapped';
import {getFormParent} from './getFormParent';
import getItemWidget from './getItemWidget';
import {getStepperChild} from './getStepperChild';
import getWidget from './getWidget';
import {hasCollectionParent} from './hasCollectionParent';
import isStepper from './isStepper';
import toMovementItem from './toMovementItem';

type Props = {
	fragmentEntryLinks: FragmentEntryLinkMap;
	getWidgets: () => WidgetSet[];
	layoutData: LayoutData;
	onInvalid?: () => {};
	sources: Array<MovementItem>;
	targetId: LayoutDataItem['itemId'];
};

export function isMovementValid({
	fragmentEntryLinks,
	getWidgets,
	layoutData,
	onInvalid,
	sources,
	targetId,
}: Props) {
	const target = toMovementItem(targetId, layoutData, fragmentEntryLinks);

	// Return false if target not found (for example if target is an editable)

	if (!target) {
		return false;
	}

	// Iterate sources to check if they are movable to target

	for (const source of sources) {

		// Skip iteration if movement is valid

		if (
			checkAllowedChild(
				source,
				target,
				layoutData,
				fragmentEntryLinks,
				getWidgets
			)
		) {
			continue;
		}

		// If not valid, display error message

		let message = '';

		if (target.type === LAYOUT_DATA_ITEM_TYPES.dropZone) {
			message = Liferay.Language.get(
				'fragments-and-widgets-cannot-be-placed-inside-this-area'
			);
		}
		else if (target.type === LAYOUT_DATA_ITEM_TYPES.collection) {
			message = Liferay.Language.get(
				'fragments-cannot-be-placed-inside-an-unmapped-collection-display-fragment'
			);
		}
		else if (
			target.type === LAYOUT_DATA_ITEM_TYPES.form &&
			!formIsMapped(target)
		) {
			message = Liferay.Language.get(
				'fragments-cannot-be-placed-inside-an-unmapped-form-container'
			);
		}
		else if (source.fragmentEntryType === FRAGMENT_ENTRY_TYPES.input) {
			message = Liferay.Language.get(
				'form-components-can-only-be-placed-inside-a-mapped-form-container'
			);

			if (isStepper(source)) {
				const form = getFormParent(target, layoutData);

				if (sources.length > 1) {
					message = Liferay.Language.get(
						'steppers-cannot-be-moved-along-with-other-elements'
					);
				}

				if (
					form &&
					getStepperChild(form, layoutData, fragmentEntryLinks)
				) {
					message = Liferay.Language.get(
						'forms-can-only-contain-one-stepper'
					);
				}
			}
		}
		else if (source.isWidget && getFormParent(target, layoutData)) {
			message = Liferay.Language.get(
				'widgets-cannot-be-placed-inside-a-form-container'
			);
		}
		else if (target.type === LAYOUT_DATA_ITEM_TYPES.formStepContainer) {
			message = Liferay.Language.get(
				'fragments-cannot-be-placed-inside-a-form-step-container'
			);
		}
		else if (
			isNonInstanceableWidget(source, fragmentEntryLinks, getWidgets) &&
			hasCollectionParent(target, layoutData)
		) {
			message = Liferay.Language.get(
				'noninstanceable-widgets-cannot-be-placed-inside-a-collection-display'
			);
		}
		else if (source.parentId !== target.itemId) {
			message = Liferay.Language.get('an-unexpected-error-occurred');
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

function isNonInstanceableWidget(
	source: MovementItem,
	fragmentEntryLinks: FragmentEntryLinkMap,
	getWidgets: () => WidgetSet[]
) {
	if (!source.isWidget) {
		return false;
	}

	const widgets = getWidgets();

	const widget = source.portletId
		? getWidget(widgets, source.portletId)
		: getItemWidget(
				source as FragmentLayoutDataItem,
				fragmentEntryLinks,
				widgets
			);

	if (!widget?.instanceable) {
		return true;
	}
}
