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
import {useEffect} from 'react';

import {ITEM_TYPES} from '../../config/constants/itemTypes';
import {useHoverItem, useIsActive, useSelectItem} from '../Controls';
import {useSetEditableProcessorUniqueId} from './EditableProcessorContext';
import getEditableUniqueId from './getEditableUniqueId';

export default function FragmentContentClickFilter({
	element,
	fragmentEntryLinkId,
	itemId
}) {
	const hoverItem = useHoverItem();
	const isActive = useIsActive();
	const selectItem = useSelectItem();
	const setEditableProcessorUniqueId = useSetEditableProcessorUniqueId();

	useEffect(() => {
		if (!element) {
			return;
		}

		const clearHover = () => {
			hoverItem(null);
		};

		const hoverEditable = event => {
			const editableElement = closest(event.target, 'lfr-editable');

			if (editableElement) {
				hoverItem(
					getEditableUniqueId(
						fragmentEntryLinkId,
						editableElement.id
					),
					{itemType: ITEM_TYPES.editable}
				);
			}
			else {
				hoverItem(itemId);
			}
		};

		const preventLinkClick = event => {
			const closestElement = closest(event.target, '[href]');

			if (
				closestElement &&
				!('data-lfr-page-editor-href-enabled' in element.dataset)
			) {
				event.preventDefault();
			}
		};

		const selectEditable = event => {
			const editableElement = closest(event.target, 'lfr-editable');

			const parentOrChildIsActive = [
				itemId,
				...Array.from(
					element.querySelectorAll('lfr-editable')
				).map(editableElement =>
					getEditableUniqueId(fragmentEntryLinkId, editableElement.id)
				)
			].some(isActive);

			if (editableElement && parentOrChildIsActive) {
				event.stopPropagation();

				const editableUniqueId = getEditableUniqueId(
					fragmentEntryLinkId,
					editableElement.id
				);

				if (isActive(editableUniqueId)) {
					setEditableProcessorUniqueId(editableUniqueId);
				}
				else {
					selectItem(editableUniqueId, {
						itemType: ITEM_TYPES.editable,
						multiSelect: event.shiftKey
					});
				}
			}
		};

		element.addEventListener('click', preventLinkClick, true);
		element.addEventListener('click', selectEditable, true);
		element.addEventListener('mouseleave', clearHover, true);
		element.addEventListener('mouseover', hoverEditable, true);

		return () => {
			element.removeEventListener('click', preventLinkClick, true);
			element.removeEventListener('click', selectEditable, true);
			element.removeEventListener('mouseleave', clearHover, true);
			element.removeEventListener('mouseover', hoverEditable, true);
		};
	});

	return null;
}

FragmentContentClickFilter.propTypes = {
	element: PropTypes.instanceOf(HTMLElement),
	fragmentEntryLinkId: PropTypes.string.isRequired,
	itemId: PropTypes.string.isRequired
};
