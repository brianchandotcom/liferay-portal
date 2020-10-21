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
import dom from 'metal-dom';
import {EventHandler} from 'metal-events';

class AccountEntriesAdminPortlet extends PortletBase {

	/**
	 * @inheritDoc
	 */
	created() {
		this.eventHandler_ = new EventHandler();
	}

	/**
	 * @inheritDoc
	 */
	attached() {
		this.businessAccountOnlySection = this.one('.business-account-only');

		const typeSelect = this.one('#type');

		if (typeSelect) {
			this.updateVisibility_(typeSelect);

			this.eventHandler_.add(
				dom.on(typeSelect, 'change', (e) => {
					this.updateVisibility_(e.currentTarget);
				})
			);
		}
	}

	/**
	 * Hides or shows the business-account-only fields in the edit form.
	 *
	 * @param {HTMLSelectElement} typeSelect
	 * @private
	 */
	updateVisibility_(typeSelect) {
		this.businessAccountOnlySection?.classList.toggle(
			'hide',
			typeSelect.value === 'person'
		);
	}

	/**
	 * @inheritDoc
	 */
	detached() {
		super.detached();
		this.eventHandler_.removeAllListeners();
	}
}

export default AccountEntriesAdminPortlet;
