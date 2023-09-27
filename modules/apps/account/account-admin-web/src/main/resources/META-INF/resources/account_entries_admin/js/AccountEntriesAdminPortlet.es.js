/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PortletBase} from 'frontend-js-web';

class AccountEntriesAdminPortlet extends PortletBase {

	/**
	 * @inheritDoc
	 */
	created() {
		this._handleTypeSelectChange = this._handleTypeSelectChange.bind(this);
	}

	/**
	 * @inheritDoc
	 */
	attached() {
		const typeSelect = this.one('#type');

		if (typeSelect) {
			this._updateVisibility(typeSelect);

			typeSelect.addEventListener('change', this._handleTypeSelectChange);
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
		const businessAccountOnlySection = this.one('.business-account-only');

		if (businessAccountOnlySection) {
			businessAccountOnlySection.classList.toggle(
				'hide',
				typeSelect.value === 'person'
			);
		}
	}

	/**
	 * @inheritDoc
	 */
	detached() {
		super.detached();

		const typeSelect = this.one('#type');

		if (typeSelect) {
			typeSelect.removeEventListener(
				'change',
				this._handleTypeSelectChange
			);
		}
	}
}

export default AccountEntriesAdminPortlet;
