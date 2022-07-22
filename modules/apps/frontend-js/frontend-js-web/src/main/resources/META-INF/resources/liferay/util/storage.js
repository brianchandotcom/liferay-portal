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
			this.removeItem(key);

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

	get size() {
		return this.storage.size;
	}

	TYPES = CONSENT_TYPES;
}
