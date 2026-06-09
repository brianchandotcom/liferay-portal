/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Ambient declarations for the global `window.Liferay.State` singleton
 * members that the FDS connection client relies on but that are not yet
 * part of the portal's shared `Liferay.State` typings. These augment
 * (merge with) the existing `Liferay.State` namespace rather than
 * replacing it, adding only the atom/selector types and the `__unsafe__`
 * lookup used here.
 *
 * The runtime must go through the global singleton (not an imported copy
 * of `@liferay/frontend-js-state-web`) so that this client shares the
 * same atom registry that the Frontend Data Set widget populates.
 */

declare namespace Liferay {
	namespace State {
		interface Atom<T> {
			readonly __type?: T;
			readonly key: string;
		}

		interface Selector<T> {
			readonly __type?: T;
			readonly key: string;
		}

		type Getter = <T>(atomOrSelector: Atom<T> | Selector<T>) => T;

		namespace __unsafe__ {
			function getAtomOrSelectorKey(
				key: string
			): Atom<unknown> | Selector<unknown> | null;
		}
	}
}
