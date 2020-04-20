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
import {Config} from 'metal-state';

class RedirectNotFoundEntriesManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	ignoreSelectedRedirectNotFoundEntries() {
		const form = this.one('#fm');

		Liferay.Util.postForm(form, {
			data: {
				deleteEntryIds: Liferay.Util.listCheckedExcept(
					form,
					this.ns('allRowIds')
				),
				ignored: true,
			},
			url: this.editRedirectNotFoundEntriesURL,
		});
	}

	unignoreSelectedRedirectNotFoundEntries() {
		const form = this.one('#fm');

		Liferay.Util.postForm(form, {
			data: {
				deleteEntryIds: Liferay.Util.listCheckedExcept(
					form,
					this.ns('allRowIds')
				),
				ignored: false,
			},
			url: this.editRedirectNotFoundEntriesURL,
		});
	}
}

RedirectNotFoundEntriesManagementToolbarDefaultEventHandler.STATE = {
	editRedirectNotFoundEntriesURL: Config.string(),
	spritemap: Config.string(),
};

export default RedirectNotFoundEntriesManagementToolbarDefaultEventHandler;
