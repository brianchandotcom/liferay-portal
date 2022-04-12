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

import {openAlertModal} from 'frontend-js-web';

function openAlert(message) {
	if (Liferay.__FF__.customDialogsEnabled) {
		openAlertModal({message});
	}
	else {
		alert(message);
	}
}

export default function propsTransformer(props) {
	return {
		...props,
		onActionButtonClick: () => {
			openAlert('Action button clicked');
		},
		onCheckboxChange: () => {
			openAlert('Select all checkbox changed');
		},
		onClearSelectionButtonClick: () => {
			openAlert('Clear selection button clicked');
		},
		onInfoButtonClick: () => {
			openAlert('Info button clicked');
		},
		onSelectAllButtonClick: () => {
			openAlert('Select all button clicked');
		},
		onShowMoreButtonClick: () => {
			openAlert('Show more button clicked');
		},
	};
}
