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

import PropTypes from 'prop-types';
import React, {useMemo} from 'react';

import {EDITABLE_FLOATING_TOOLBAR_BUTTONS} from '../../config/constants/editableFloatingToolbarButtons';
import {EDITABLE_FLOATING_TOOLBAR_CLASSNAMES} from '../../config/constants/editableFloatingToolbarClassNames';
import {EDITABLE_TYPES} from '../../config/constants/editableTypes';
import selectEditableValue from '../../selectors/selectEditableValue';
import {useSelector} from '../../store/index';
import {useIsActive} from '../Controls';
import FloatingToolbar from '../floating-toolbar/FloatingToolbar';
import getActiveEditableElement from './getActiveEditableElement';
import getEditableUniqueId from './getEditableUniqueId';

export default function FragmentContentFloatingToolbar({
	element,
	fragmentEntryLinkId,
	onButtonClick
}) {
	const isActive = useIsActive();

	const editableElement = useMemo(
		() => getActiveEditableElement(element, fragmentEntryLinkId, isActive),
		[element, fragmentEntryLinkId, isActive]
	);

	const editableId = useMemo(
		() => (editableElement ? editableElement.id : null),
		[editableElement]
	);

	const editableType = useMemo(
		() => (editableElement ? editableElement.getAttribute('type') : null),
		[editableElement]
	);

	const editableValue = useSelector(state =>
		editableId
			? selectEditableValue(state, fragmentEntryLinkId, editableId)
			: {}
	);

	const floatingToolbarButtons = useMemo(() => {
		if (!editableId) {
			return [];
		}
		const {
			classNameId,
			classPK,
			config,
			fieldId,
			mappedField
		} = editableValue;

		const showLinkButton =
			editableType == EDITABLE_TYPES.text ||
			editableType == EDITABLE_TYPES.image ||
			editableType == EDITABLE_TYPES.link;

		const buttons = [];

		if (showLinkButton) {
			EDITABLE_FLOATING_TOOLBAR_BUTTONS.link.className =
				config.href ||
				(config.classNameId && config.classPK && config.fieldId) ||
				config.mappedField
					? EDITABLE_FLOATING_TOOLBAR_CLASSNAMES.linked
					: '';
			buttons.push(EDITABLE_FLOATING_TOOLBAR_BUTTONS.link);
		}

		if (
			editableType === EDITABLE_TYPES.image &&
			!editableValue.mappedField &&
			!editableValue.fieldId
		) {
			buttons.push(EDITABLE_FLOATING_TOOLBAR_BUTTONS.imageProperties);
		}
		else {
			EDITABLE_FLOATING_TOOLBAR_BUTTONS.edit.className =
				(classNameId && classPK && fieldId) || mappedField
					? EDITABLE_FLOATING_TOOLBAR_CLASSNAMES.disabled
					: '';
			buttons.push(EDITABLE_FLOATING_TOOLBAR_BUTTONS.edit);
		}

		EDITABLE_FLOATING_TOOLBAR_BUTTONS.map.className =
			(classNameId && classPK && fieldId) || mappedField
				? EDITABLE_FLOATING_TOOLBAR_CLASSNAMES.mapped
				: '';
		buttons.push(EDITABLE_FLOATING_TOOLBAR_BUTTONS.map);

		return buttons;
	}, [editableId, editableType, editableValue]);

	return (
		editableId && (
			<FloatingToolbar
				buttons={floatingToolbarButtons}
				item={{
					editableId,
					editableType,
					fragmentEntryLinkId,
					itemId: getEditableUniqueId(fragmentEntryLinkId, editableId)
				}}
				itemRef={{current: editableElement}}
				onButtonClick={buttonId => onButtonClick(buttonId, editableId)}
			/>
		)
	);
}

FragmentContentFloatingToolbar.propTypes = {
	element: PropTypes.instanceOf(HTMLElement),
	fragmentEntryLinkId: PropTypes.string.isRequired,
	onButtonClick: PropTypes.func.isRequired
};
