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

import classNames from 'classnames';
import PropTypes from 'prop-types';
import {useLayoutEffect, useMemo} from 'react';

import {ITEM_TYPES} from '../../config/constants/itemTypes';
import {config} from '../../config/index';
import createSelectEditableValue from '../../selectors/selectEditableValue';
import selectPrefixedSegmentsExperienceId from '../../selectors/selectPrefixedSegmentsExperienceId';
import {useSelector} from '../../store/index';
import {useHoveredItemId, useHoveredItemType, useIsActive} from '../Controls';
import {useEditableDecoration} from './EditableDecorationContext';
import {EDITABLE_DECORATION_CLASS_NAMES} from './EditableDecorationMask';
import getEditableUniqueId from './getEditableUniqueId';

export default function FragmentContentDecoration({
	editableElement,
	element,
	fragmentEntryLinkId,
	itemId
}) {
	const hoveredItemId = useHoveredItemId();
	const hoveredItemType = useHoveredItemType();
	const isActive = useIsActive();
	const languageId = useSelector(state => state.languageId);
	const segmentsExperienceId = useSelector(
		selectPrefixedSegmentsExperienceId
	);

	const editableUniqueId = getEditableUniqueId(
		fragmentEntryLinkId,
		editableElement.id
	);

	const editableValue = useSelector(state =>
		createSelectEditableValue(
			state,
			fragmentEntryLinkId,
			editableElement.id
		)
	);

	const {
		registerElement,
		unregisterElement,
		updateClassName
	} = useEditableDecoration();

	const className = classNames({
		[EDITABLE_DECORATION_CLASS_NAMES.active]: isActive(editableUniqueId),

		[EDITABLE_DECORATION_CLASS_NAMES.hovered]: useMemo(() => {
			if (hoveredItemType === ITEM_TYPES.editable) {
				return editableUniqueId === hoveredItemId;
			}
			else if (hoveredItemType === ITEM_TYPES.mappedContent) {
				return (
					`${editableValue.classNameId}-${editableValue.classPK}` ===
					hoveredItemId
				);
			}

			return false;
		}, [editableUniqueId, editableValue, hoveredItemId, hoveredItemType]),

		[EDITABLE_DECORATION_CLASS_NAMES.mapped]: useMemo(
			() =>
				editableValue &&
				((editableValue.classNameId &&
					editableValue.classPK &&
					editableValue.fieldId) ||
					editableValue.mappedField),
			[editableValue]
		),

		[EDITABLE_DECORATION_CLASS_NAMES.highlighted]: useMemo(
			() =>
				[
					itemId,
					...Array.from(
						element.querySelectorAll('lfr-editable')
					).map(editableElement =>
						getEditableUniqueId(
							fragmentEntryLinkId,
							editableElement.id
						)
					)
				].some(_itemId => isActive(_itemId)),
			[element, fragmentEntryLinkId, isActive, itemId]
		),

		[EDITABLE_DECORATION_CLASS_NAMES.translated]: useMemo(
			() =>
				config.defaultLanguageId !== languageId &&
				(editableValue[languageId] ||
					(editableValue[segmentsExperienceId] &&
						editableValue[segmentsExperienceId][languageId])),
			[editableValue, languageId, segmentsExperienceId]
		)
	});

	useLayoutEffect(() => {
		if (className) {
			registerElement(editableUniqueId, editableElement);
			updateClassName(editableUniqueId, className);
		}
		else {
			unregisterElement(editableUniqueId);
		}
	}, [
		className,
		editableElement,
		editableUniqueId,
		registerElement,
		unregisterElement,
		updateClassName
	]);

	useLayoutEffect(
		() => () => {
			unregisterElement(editableUniqueId);
		},
		[editableUniqueId, unregisterElement]
	);

	return null;
}

FragmentContentDecoration.propTypes = {
	editableElement: PropTypes.instanceOf(HTMLElement).isRequired,
	element: PropTypes.instanceOf(HTMLElement).isRequired,
	fragmentEntryLinkId: PropTypes.string.isRequired,
	itemId: PropTypes.string.isRequired
};
