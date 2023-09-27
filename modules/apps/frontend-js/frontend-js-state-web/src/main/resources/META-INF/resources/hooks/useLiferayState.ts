/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useState} from 'react';

import State from '../State';

import type {Atom, Selector} from '../State';
import type {Immutable} from '../types';

/**
 * Hook-based abstraction over `State.read()`, `State.write()`, and
 * `State.subscribe()` that allows you to conveniently read/update/watch atoms
 * or selectors from within a React component in a way that is similar to
 * React's own `useState()` hook.
 *
 * Given an atom or selector, returns a tuple containing the current value and a
 * function for updating it.
 *
 * (Note, however, that actually trying to update a selector will throw
 * an error because selectors are read-only.)
 */
export default function useLiferayState<T>(
	atomOrSelector: Atom<T> | Selector<T>
): [value: Immutable<T>, setValue: (newValue: T) => void] {
	const [currentValue, setValue] = useState(() => {
		return State.read(atomOrSelector);
	});

	useEffect(() => {
		const {dispose} = State.subscribe(atomOrSelector, setValue);

		return dispose;
	}, [atomOrSelector]);

	return [
		currentValue,
		useCallback((newValue) => State.write(atomOrSelector, newValue), [
			atomOrSelector,
		]),
	];
}
