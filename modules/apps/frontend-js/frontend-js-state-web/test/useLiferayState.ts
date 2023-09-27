/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {act, renderHook} from '@testing-library/react-hooks';

import State from '../src/main/resources/META-INF/resources/State';
import useLiferayState from '../src/main/resources/META-INF/resources/hooks/useLiferayState';

import type {
	Atom,
	Selector,
} from '../src/main/resources/META-INF/resources/State';

describe('State', () => {
	let atom: Atom<string>;
	let selector: Selector<number>;

	beforeEach(() => {
		State.__internal__.reset();

		atom = State.atom('hello', 'world');

		selector = State.selector('helloLength', (get) => get(atom).length);
	});

	it('provides an initial default value', () => {
		const {result} = renderHook(() => useLiferayState(atom));

		expect(result.current[0]).toBe('world');
	});

	it('provides a setter for updating an atom', () => {
		const {result} = renderHook(() => useLiferayState(atom));

		act(() => result.current[1]('WORLD'));

		expect(result.current[0]).toBe('WORLD');
	});

	it('maintains setter identity over time', () => {

		// ie. to make it harmless to pass into `useEffect` etc.

		const {result} = renderHook(() => useLiferayState(atom));

		const setter = result.current[1];

		act(() => State.writeAtom(atom, 'something else'));

		expect(result.current[1]).toBe(setter);
	});

	it('can read a selector', () => {
		const {result} = renderHook(() => useLiferayState(selector));

		expect(result.current[0]).toBe(5);
	});

	it('updates a selector whose dependencies have updated', () => {
		const {result} = renderHook(() => useLiferayState(selector));

		act(() => State.writeAtom(atom, 'longer string'));

		expect(result.current[0]).toBe(13);
	});

	it('complains about attempted writes to selectors', () => {
		const {result} = renderHook(() => useLiferayState(selector));

		// Selectors are read-only derived data. Only atoms can be written to.

		expect(() => result.current[1](9000)).toThrow(
			/expected atom but received selector/
		);
	});

	it('reports type errors if used with the wrong type', () => {
		renderHook(() => {
			const [_a, _b]: [
				string,
				(newValue: string) => void
			] = useLiferayState(atom);

			const [_c, _d]: [
				number,
				(newValue: number) => void
			] = useLiferayState(selector);

			// @ts-expect-error: Wrong type for value (using atom).

			const [_e, _f]: [
				number,
				(newValue: string) => void
			] = useLiferayState(atom);

			// @ts-expect-error: Wrong type for value (using selector).

			const [_g, _h]: [
				string,
				(newValue: number) => void
			] = useLiferayState(selector);

			// @ts-expect-error: Wrong type for setter parameter (using atom).

			const [_i, _j]: [
				string,
				(newValue: number) => void
			] = useLiferayState(atom);

			// @ts-expect-error: Wrong type for setter parameter (using selector).

			const [_k, _l]: [
				number,
				(newValue: string) => void
			] = useLiferayState(selector);

			const complexAtom = State.atom('nested', [
				{age: 10, name: 'bruce'},
			]);

			const [_m, _n]: [
				Readonly<Array<Readonly<{age: number; name: string}>>>,
				(
					newValue: Readonly<
						Array<Readonly<{age: number; name: string}>>
					>
				) => void
			] = useLiferayState(complexAtom);

			// @ts-expect-error: Wrong type for value and setter.

			const [_o, _p]: [
				Readonly<Array<Readonly<{name: null}>>>,
				(newValue: Readonly<Array<Readonly<{name: null}>>>) => void
			] = useLiferayState(complexAtom);
		});
	});
});
