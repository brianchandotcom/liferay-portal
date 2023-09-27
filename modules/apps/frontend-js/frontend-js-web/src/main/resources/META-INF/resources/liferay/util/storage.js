/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CONSENT_TYPES, checkConsent} from './consent';

export default class Storage {
	constructor(storage) {
		this.storage = storage;
	}

	clear() {
		return this.storage.clear();
	}

	getItem(key, type) {
		if (!checkConsent(type)) {
			return null;
		}

		return this.storage.getItem(key);
	}

	key(index, type) {
		if (!checkConsent(type)) {
			return null;
		}

		return this.storage.key(index);
	}

	removeItem(key) {
		return this.storage.removeItem(key);
	}

	setItem(key, value, type) {
		if (!checkConsent(type)) {
			return false;
		}

		this.storage.setItem(key, value);

		return true;
	}

	get length() {
		return this.storage.length;
	}

	TYPES = CONSENT_TYPES;
}
