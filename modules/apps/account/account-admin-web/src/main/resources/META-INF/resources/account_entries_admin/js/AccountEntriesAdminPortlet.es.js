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

import {PortletBase} from 'frontend-js-web';

class AccountEntriesAdminPortlet extends PortletBase {

	/**
	 * @inheritDoc
	 */
	attached() {
		this.businessAccountOnlySection = this.one('.business-account-only');

		const typeSelect = this.one('#type');

		if (typeSelect) {
			this._updateVisibility(typeSelect);

			typeSelect.addEventListener('change', _handleTypeSelectChange);
		}
	}

	_handleTypeSelectChange(event) {
		this._updateVisibility(event.currentTarget);
	}

	/**
	 * Hides or shows the business-account-only fields in the edit form.
	 *
	 * @param {HTMLSelectElement} typeSelect
	 * @private
	 */
	_updateVisibility(typeSelect) {
		this.businessAccountOnlySection.classList.toggle(
			'hide',
			typeSelect.value === 'person'
		);
	}

	/**
	 * @inheritDoc
	 */
	detached() {
		super.detached();

		const typeSelect = this.one('#type');

		if (typeSelect) {
			typeSelect.removeEventListener('change', _handleTypeSelectChange);
		}
	}
}

export default AccountEntriesAdminPortlet;
