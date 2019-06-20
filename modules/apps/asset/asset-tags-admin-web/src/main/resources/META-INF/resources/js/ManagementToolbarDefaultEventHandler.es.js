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

import {DefaultEventHandler} from 'frontend-js-web';

class ManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	deleteTags() {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(this.one('#fm'));
		}
	}

	mergeTags(itemData) {
		const mergeURL = itemData.mergeTagsURL;

		location.href = mergeURL.replace(
			escape('[$MERGE_TAGS_IDS$]'),
			Liferay.Util.listCheckedExcept(
				this.one('#fm'),
				this.ns('allRowIds')
			)
		);
	}
}

export default ManagementToolbarDefaultEventHandler;
