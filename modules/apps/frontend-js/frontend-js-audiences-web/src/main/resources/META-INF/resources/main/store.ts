/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {log} from './log';

import type {AudienceId} from './index';

const STORAGE_KEY = 'com.liferay.frontend.js.audiences';
const STORAGE_MAX_AGE = 365 * 24 * 60 * 60;
const STORAGE_SEPARATOR = '\n';

class Store {
	clear(): void {
		log(`Clearing audiences`);

		document.cookie = `${STORAGE_KEY}=; path=/; max-age=0`;
	}

	getAudienceIds(): Set<AudienceId> {
		const value = this._getCookie();

		if (!value) {
			return new Set();
		}

		try {
			return new Set(decodeURIComponent(value).split(STORAGE_SEPARATOR));
		}
		catch (error) {
			log(
				`Invalid audiences cookie found: '${value}'`,
				`(reason: ${error})`
			);

			return new Set();
		}
	}

	setAudienceIds(audienceIds: Set<AudienceId>): void {
		const value = encodeURIComponent(
			[...audienceIds].join(STORAGE_SEPARATOR)
		);

		document.cookie = `${STORAGE_KEY}=${value}; path=/; max-age=${STORAGE_MAX_AGE}`;
	}

	private _getCookie(): string | undefined {
		return document.cookie
			.split('; ')
			.find((item) => item.startsWith(`${STORAGE_KEY}=`))
			?.slice(STORAGE_KEY.length + 1);
	}
}

export const store = new Store();
