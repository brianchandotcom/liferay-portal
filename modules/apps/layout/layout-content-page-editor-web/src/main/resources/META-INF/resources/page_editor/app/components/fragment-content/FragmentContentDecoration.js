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
import {useEffect, useLayoutEffect} from 'react';

import {config} from '../../config/index';
import createSelectEditableValue from '../../selectors/selectEditableValue';
import selectPrefixedSegmentsExperienceId from '../../selectors/selectPrefixedSegmentsExperienceId';
import {useSelector} from '../../store/index';
import {useIsActive, useIsHovered} from '../Controls';
import {useEditableDecoration} from './EditableDecorationContext';
import {EDITABLE_DECORATION_CLASS_NAMES} from './EditableDecorationMask';
import getEditableUniqueId from './getEditableUniqueId';

export default function FragmentContentDecoration({
	editableElement,
	element,
	fragmentEntryLinkId,
	itemId
}) {
	const isActive = useIsActive();
	const isHovered = useIsHovered();
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
		[EDITABLE_DECORATION_CLASS_NAMES.hovered]: isHovered(editableUniqueId),

		[EDITABLE_DECORATION_CLASS_NAMES.mapped]:
			editableValue &&
			((editableValue.classNameId &&
				editableValue.classPK &&
				editableValue.fieldId) ||
				editableValue.mappedField),

		[EDITABLE_DECORATION_CLASS_NAMES.highlighted]: [
			itemId,
			...Array.from(
				element.querySelectorAll('lfr-editable')
			).map(editableElement =>
				getEditableUniqueId(fragmentEntryLinkId, editableElement.id)
			)
		].some(_itemId => isActive(_itemId)),

		[EDITABLE_DECORATION_CLASS_NAMES.translated]:
			config.defaultLanguageId !== languageId &&
			(editableValue[languageId] ||
				(editableValue[segmentsExperienceId] &&
					editableValue[segmentsExperienceId][languageId]))
	});

	useLayoutEffect(() => {
		registerElement(editableUniqueId, editableElement);

		return () => {
			unregisterElement(editableUniqueId);
		};
	}, [editableElement, editableUniqueId, registerElement, unregisterElement]);

	useEffect(() => {
		updateClassName(editableUniqueId, className);
	}, [className, editableElement, editableUniqueId, updateClassName]);

	return null;
}

FragmentContentDecoration.propTypes = {
	editableElement: PropTypes.instanceOf(HTMLElement).isRequired,
	element: PropTypes.instanceOf(HTMLElement).isRequired,
	fragmentEntryLinkId: PropTypes.string.isRequired,
	itemId: PropTypes.string.isRequired
};
