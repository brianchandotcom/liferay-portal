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

export default function propsTransformer(props) {
	return {
		...props,
		onActionButtonClick: () => {
			if (Liferay.__FF__.enableCustomDialogs) {
				Liferay.Util.openAlertModal({message: 'Action button clicked'});
			}
			else {
				alert('Action button clicked');
			}
		},
		onCheckboxChange: () => {
			if (Liferay.__FF__.enableCustomDialogs) {
				Liferay.Util.openAlertModal({
					message: 'Select all checkbox changed',
				});
			}
			else {
				alert('Select all checkbox changed');
			}
		},
		onClearSelectionButtonClick: () => {
			if (Liferay.__FF__.enableCustomDialogs) {
				Liferay.Util.openAlertModal({
					message: 'Clear selection button clicked',
				});
			}
			else {
				alert('Clear selection button clicked');
			}
		},
		onInfoButtonClick: () => {
			if (Liferay.__FF__.enableCustomDialogs) {
				Liferay.Util.openAlertModal({message: 'Info button clicked'});
			}
			else {
				alert('Info button clicked');
			}
		},
		onSelectAllButtonClick: () => {
			if (Liferay.__FF__.enableCustomDialogs) {
				Liferay.Util.openAlertModal({
					message: 'Select all button clicked',
				});
			}
			else {
				alert('Select all button click');
			}
		},
		onShowMoreButtonClick: () => {
			if (Liferay.__FF__.enableCustomDialogs) {
				Liferay.Util.openAlertModal({
					message: 'Show more button clicked',
				});
			}
			else {
				alert('Show more button clicked');
			}
		},
	};
}
