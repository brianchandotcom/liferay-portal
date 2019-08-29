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

class AccountsManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	activateAccounts(itemData) {
		this._updateAccounts(itemData.activateAccountsURL);
	}

	deactivateAccounts(itemData) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-deactivate-this')
			)
		) {
			this._updateAccounts(itemData.deactivateAccountsURL);
		}
	}

	deleteAccounts(itemData) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			this._updateAccounts(itemData.deleteAccountsURL);
		}
	}

	_updateAccounts(url) {
		const form = this.one('#fm');

		Liferay.Util.postForm(form, {
			data: {
				accountEntryIds: Liferay.Util.listCheckedExcept(
					form,
					this.ns('allRowIds')
				)
			},
			url
		});
	}
}

export default AccountsManagementToolbarDefaultEventHandler;
