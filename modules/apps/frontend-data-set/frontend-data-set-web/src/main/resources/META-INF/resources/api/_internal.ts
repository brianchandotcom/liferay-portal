/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Package-private helpers shared by the opinionated subscription
 * entry points (`subscribeSearch`, `subscribeFilters`). These are not
 * part of the public surface: consumers should reach for a blessed
 * slice helper rather than acquiring an atom or registering a selector
 * directly. New slices grow by adding a new opinionated helper.
 */

import type {FDSState} from '@liferay/js-api/data-set';

type Atom<T> = Liferay.State.Atom<T>;
type Selector<T> = Liferay.State.Selector<T>;

const DEFAULT_TIMEOUT = 5000;
const DEFAULT_INTERVAL = 100;

export function getFDSAtom(
	id: string,
	options?: {interval?: number; timeout?: number}
): Promise<Atom<FDSState>> {
	const timeout = options?.timeout ?? DEFAULT_TIMEOUT;
	const interval = options?.interval ?? DEFAULT_INTERVAL;

	const key = `${id}_fdsState`;

	return new Promise((resolve, reject) => {
		const existing = Liferay?.State?.__unsafe__?.getAtomOrSelectorKey(key);

		if (existing) {
			return resolve(existing as Atom<FDSState>);
		}

		const startTime = Date.now();

		const poll = setInterval(() => {
			const atom = Liferay?.State?.__unsafe__?.getAtomOrSelectorKey(key);

			if (atom) {
				clearInterval(poll);

				return resolve(atom as Atom<FDSState>);
			}

			if (Date.now() - startTime >= timeout) {
				clearInterval(poll);

				reject(
					new Error(
						`FDS atom "${key}" was not found within ${timeout}ms`
					)
				);
			}
		}, interval);
	});
}

export function getOrCreateSelector<T>(
	key: string,
	deriveValue: (get: Liferay.State.Getter) => T
): Selector<T> {
	const existing = Liferay.State.__unsafe__.getAtomOrSelectorKey(key);

	return (
		(existing as Selector<T> | null) ??
		Liferay.State.selector<T>(key, deriveValue)
	);
}
