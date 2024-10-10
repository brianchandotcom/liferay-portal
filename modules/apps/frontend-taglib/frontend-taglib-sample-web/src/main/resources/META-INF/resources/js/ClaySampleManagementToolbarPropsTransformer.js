/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function propsTransformer(props) {
	return {
		...props,
		onActionButtonClick: () => {
			alert('Action button clicked');
		},
		onCheckboxChange: () => {
			alert('Select all checkbox changed');
		},
		onClearSelectionButtonClick: () => {
			alert('Clear selection button clicked');
		},
		onInfoButtonClick: () => {
			alert('Info button clicked');
		},
		onSelectAllButtonClick: () => {
			alert('Select all button clicked');
		},
		onShowMoreButtonClick: () => {
			alert('Show more button clicked');
		},
	};
}
