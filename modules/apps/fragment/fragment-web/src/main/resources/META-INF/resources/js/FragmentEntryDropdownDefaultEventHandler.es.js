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

import {DefaultEventHandler, openSelectionModal} from 'frontend-js-web';

class FragmentEntryDropdownDefaultEventHandler extends DefaultEventHandler {
	copyContributedEntryToFragmentCollection(itemData) {
		openSelectionModal({
			id: this.ns('selectFragmentCollection'),
			onSelect: (selectedItem) => {
				if (selectedItem) {
					this.one('#contributedEntryKeys').value =
						itemData.contributedEntryKey;
					this.one('#fragmentCollectionId').value = selectedItem.id;

					submitForm(
						this.one('#fragmentEntryFm'),
						itemData.copyContributedEntryURL
					);
				}
			},
			selectEventName: this.ns('selectFragmentCollection'),
			title: Liferay.Language.get('select-collection'),
			url: itemData.selectFragmentCollectionURL,
		});
	}
}

FragmentEntryDropdownDefaultEventHandler.STATE = {};

export default FragmentEntryDropdownDefaultEventHandler;
