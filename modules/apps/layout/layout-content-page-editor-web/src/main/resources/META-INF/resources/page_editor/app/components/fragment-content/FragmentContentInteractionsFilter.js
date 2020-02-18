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

import {closest} from 'metal-dom';
import PropTypes from 'prop-types';
import React, {useMemo} from 'react';

import {ITEM_TYPES} from '../../config/constants/itemTypes';
import {useHoverItem, useIsActive, useSelectItem} from '../Controls';
import {useSetEditableProcessorUniqueId} from './EditableProcessorContext';
import {getEditableElement} from './getEditableElement';
import getEditableElementId from './getEditableElementId';
import getEditableUniqueId from './getEditableUniqueId';

export default function FragmentContentInteractionsFilter({
	children,
	editableElements,
	fragmentEntryLinkId,
	itemId
}) {
	const hoverItem = useHoverItem();
	const isActive = useIsActive();
	const selectItem = useSelectItem();
	const setEditableProcessorUniqueId = useSetEditableProcessorUniqueId();

	const siblingIds = useMemo(
		() => [
			itemId,
			...editableElements.map(editableElement =>
				getEditableUniqueId(
					fragmentEntryLinkId,
					getEditableElementId(editableElement)
				)
			)
		],
		[fragmentEntryLinkId, itemId, editableElements]
	);

	const hoverEditable = event => {
		const editableElement = getEditableElement(event.target);

		if (editableElement) {
			event.stopPropagation();

			hoverItem(
				getEditableUniqueId(
					fragmentEntryLinkId,
					getEditableElementId(editableElement)
				),
				{itemType: ITEM_TYPES.editable}
			);
		}
	};

	const preventLinkClick = event => {
		const closestElement = closest(event.target, '[href]');

		if (
			closestElement &&
			!closestElement.dataset.lfrPageEditorHrefEnabled
		) {
			event.preventDefault();
		}
	};

	const selectEditable = event => {
		const editableElement = getEditableElement(event.target);

		if (editableElement) {
			event.stopPropagation();

			const editableUniqueId = getEditableUniqueId(
				fragmentEntryLinkId,
				getEditableElementId(editableElement)
			);

			if (isActive(editableUniqueId)) {
				event.stopPropagation();
			}
			else {
				selectItem(editableUniqueId, {
					itemType: ITEM_TYPES.editable,
					multiSelect: event.shiftKey
				});
			}
		}
	};

	const enableProcessor = event => {
		const editableElement = getEditableElement(event.target);

		if (editableElement) {
			const editableUniqueId = getEditableUniqueId(
				fragmentEntryLinkId,
				getEditableElementId(editableElement)
			);

			if (isActive(editableUniqueId)) {
				setEditableProcessorUniqueId(editableUniqueId);
			}
		}
	};

	const props = {
		onClickCapture: preventLinkClick
	};

	if (siblingIds.some(isActive)) {
		props.onClickCapture = event => {
			preventLinkClick(event);
			selectEditable(event);
		};

		props.onDoubleClickCapture = enableProcessor;
		props.onMouseOverCapture = hoverEditable;
	}

	return <div {...props}>{children}</div>;
}

FragmentContentInteractionsFilter.propTypes = {
	element: PropTypes.instanceOf(HTMLElement),
	fragmentEntryLinkId: PropTypes.string.isRequired,
	itemId: PropTypes.string.isRequired
};
