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

import type {Atom, Immutable, Selector} from '@liferay/frontend-js-state-web';

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
): [value: Immutable<T>, setValue: (newValue: T) => void];
