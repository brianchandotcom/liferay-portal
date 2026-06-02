/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {RetentionType} from './index';
import {log} from './log';

const STORAGE_KEY = 'com.liferay.frontend.js.audiences.web';
const STORAGE_SEPARATOR = '|';

class Store {
	private pageAudienceIds: Set<string> = new Set();

	clear(retentionType?: RetentionType): void {
		log(`Clearing audiences for scope: ${retentionType ?? 'ALL'}`);

		switch (retentionType) {
			case 'BROWSER':
				localStorage.removeItem(STORAGE_KEY);
				break;

			case 'PAGE':
				this.pageAudienceIds = new Set();
				break;

			case 'TAB':
				sessionStorage.removeItem(STORAGE_KEY);
				break;

			default:
				localStorage.removeItem(STORAGE_KEY);
				this.pageAudienceIds = new Set();
				sessionStorage.removeItem(STORAGE_KEY);
				break;
		}
	}

	getAudienceIds(): Set<string> {
		const set: Set<string> = new Set();

		for (const audienceId of this.getBrowserAudienceIds()) {
			set.add(audienceId);
		}

		for (const audienceId of this.getPageAudienceIds()) {
			set.add(audienceId);
		}

		for (const audienceId of this.getTabAudienceIds()) {
			set.add(audienceId);
		}

		return set;
	}

	getBrowserAudienceIds(): Set<string> {
		const storageItem = localStorage.getItem(STORAGE_KEY);

		if (!storageItem) {
			return new Set();
		}

		return new Set(storageItem.split(STORAGE_SEPARATOR));
	}

	getPageAudienceIds(): Set<string> {
		return this.pageAudienceIds;
	}

	getTabAudienceIds(): Set<string> {
		const storageItem = sessionStorage.getItem(STORAGE_KEY);

		if (!storageItem) {
			return new Set();
		}

		return new Set(storageItem.split(STORAGE_SEPARATOR));
	}

	setBrowserAudienceIds(audienceIds: Set<string>) {
		localStorage.setItem(
			STORAGE_KEY,
			[...audienceIds].join(STORAGE_SEPARATOR)
		);
	}

	setPageAudienceIds(audienceIds: Set<string>) {
		this.pageAudienceIds = audienceIds;
	}

	setTabAudienceIds(audienceIds: Set<string>) {
		sessionStorage.setItem(
			STORAGE_KEY,
			[...audienceIds].join(STORAGE_SEPARATOR)
		);
	}
}

export const store = new Store();
