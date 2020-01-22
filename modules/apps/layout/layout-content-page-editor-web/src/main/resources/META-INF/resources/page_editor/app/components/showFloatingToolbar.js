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

import React from 'react';

import {EDITABLE_FLOATING_TOOLBAR_BUTTONS} from '../config/constants/editableFloatingToolbarButtons';
import {EDITABLE_TYPES} from '../config/constants/editableTypes';
import FloatingToolbar from './FloatingToolbar';

export function showFloatingToolbar(editable, fragmentEntryLinkId) {
	const itemId = `${fragmentEntryLinkId}-${editable.current.getAttribute(
		'id'
	)}`;

	const editableType = editable.current.getAttribute('type');

	const showLinkButton =
		editableType == EDITABLE_TYPES.text ||
		editableType == EDITABLE_TYPES.image ||
		editableType == EDITABLE_TYPES.link;

	const buttons = [{icon: 'pencil', panelId: 'panel'}];

	if (showLinkButton) {
		buttons.push(EDITABLE_FLOATING_TOOLBAR_BUTTONS.link);
	}

	return (
		<FloatingToolbar
			buttons={buttons}
			item={{
				editableId: editable.current.getAttribute('id'),
				fragmentEntryLinkId,
				itemId
			}}
			itemRef={editable}
		/>
	);
}
