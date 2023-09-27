/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* eslint-disable @liferay/no-use-strict-in-module */

import State from '../src/main/resources/META-INF/resources/State';
import {withEnv} from './helpers';

import type {
	Atom,
	Selector,
} from '../src/main/resources/META-INF/resources/State';

describe('State', () => {
	beforeEach(() => {
		State.__internal__.reset();
	});

	describe('atom()', () => {
		it('returns a read-only object', () => {
			withEnv('development', () => {
				const atom = State.atom('foo', 'value');

				expect(() => {
					'use strict';

					// @ts-ignore: avoid TS2540 here.

					atom.default = 'overwrite';
				}).toThrow(/read only/);
			});

			// But not in production, to avoid the performance hit.

			withEnv('production', () => {
				const atom = State.atom('bar', 'value');

				expect(() => {
					'use strict';

					// @ts-ignore: avoid TS2540 here.

					atom.default = 'overwrite';
				}).not.toThrow();

				expect(atom.default).toBe('overwrite');
			});
		});

		it('complains if an atom with the same key was previously registered', () => {
			withEnv('production', () => {
				State.atom('foo', 'value');

				expect(() => State.atom('foo', 'value')).toThrow(
					/key "foo" already taken/
				);
			});

			// But not in development, to permit the possibility of hot
			// reloading...

			withEnv('development', () => {
				State.atom('bar', 'value');

				expect(() => State.atom('foo', 'value')).not.toThrow();
			});
		});

		it('complains if a selector with the same key was previously registered', () => {
			withEnv('production', () => {
				State.selector('baz', () => 'value');

				expect(() => State.atom('baz', 'value')).toThrow(
					/key "baz" already taken by a selector/
				);
			});

			// And also in development, because having colliding keys for atoms
			// and selectors is a clear mistake.

			withEnv('development', () => {
				State.selector('qux', () => 'value');

				expect(() => State.atom('qux', 'value')).toThrow(
					/key "qux" already taken by a selector/
				);
			});
		});

		it('reports type errors if declared with the wrong type', () => {
			State.atom('bar', 'string') as Atom<string>;

			// @ts-expect-error

			State.atom('bar', 'string') as Atom<number>;
		});
	});

	describe('readAtom()', () => {
		let atom: Atom<string>;

		beforeEach(() => {
			atom = State.atom('foo', 'thing');
		});

		it('returns the default value if the atom is not yet written', () => {
			expect(State.readAtom(atom)).toBe('thing');
		});

		it('returns the updated value if the atom has been written', () => {
			State.writeAtom(atom, 'updated');

			expect(State.readAtom(atom)).toBe('updated');
		});

		it('reports type errors if result is assigned to wrong type', () => {
			State.readAtom(atom) as string;

			// @ts-expect-error

			State.readAtom(atom) as number;
		});

		it('returns read-only objects in development', () => {
			withEnv('development', () => {
				const atom = State.atom('frozen', {contents: 'foo'});

				const value = State.readAtom(atom);

				// @ts-expect-error TS2339: Property 'added' does not exist

				value.added = true;

				// @ts-expect-error TS2339: Property 'added' does not exist

				expect(value.added).toBe(undefined);
			});

			withEnv('production', () => {
				const atom = State.atom('mutable', {contents: 'foo'});

				const value = State.readAtom(atom);

				// @ts-expect-error TS2339: Property 'added' does not exist

				value.added = true;

				// @ts-expect-error TS2339: Property 'added' does not exist

				expect(value.added).toBe(true);
			});
		});
	});

	describe('readSelector()', () => {
		let person: Atom<string>;
		let fruit: Atom<string>;
		let letterCount: Selector<number>;
		let nameAndLength: Selector<string>;
		let everything: Selector<Array<number | string>>;

		beforeEach(() => {
			person = State.atom('person', 'Chema');
			fruit = State.atom('fruit', 'apples');

			letterCount = State.selector('letterCount', (get: Function) => {
				return get(person).length + get(fruit).length;
			});

			nameAndLength = State.selector('nameAndLength', (get: Function) => {
				return `${get(person)}: ${get(letterCount)}`;
			});

			everything = State.selector('everything', (get: Function) => {
				return [
					get(person),
					get(fruit),
					get(letterCount),
					get(nameAndLength),
				];
			});
		});

		it('returns a value derived from 2 atoms', () => {
			expect(State.readSelector(letterCount)).toBe(11);
		});

		it('reports type errors if result is assigned to wrong type', () => {
			State.readSelector(letterCount) as number;

			// @ts-expect-error

			State.readSelector(letterCount) as string;
		});

		it('returns a value derived from an atom and a selector', () => {
			expect(State.readSelector(nameAndLength)).toBe('Chema: 11');
		});

		it('returns a value derived from multiple atoms and selectors', () => {
			expect(State.readSelector(everything)).toEqual([
				'Chema',
				'apples',
				11,
				'Chema: 11',
			]);
		});

		it('memoizes selector values until invalidated', () => {

			// Memoization is fair only because selectors are expected to be
			// idempotent ("pure" in the sense that their value depends only on
			// the value of the inputs that they read using the `get` function).

			let callCount = 0;

			const selector = State.selector('thing', (get) => {
				callCount++;

				return get(person) + '!';
			});

			expect(callCount).toBe(0);

			expect(State.readSelector(selector)).toBe('Chema!');

			expect(callCount).toBe(1);

			expect(State.readSelector(selector)).toBe('Chema!');

			expect(callCount).toBe(1);

			State.writeAtom(person, 'Carlos');

			expect(State.readSelector(selector)).toBe('Carlos!');

			expect(callCount).toBe(2);

			expect(State.readSelector(selector)).toBe('Carlos!');

			expect(callCount).toBe(2);
		});

		it('detects dependency cycles', () => {
			const direct: Selector<string> = State.selector(
				'directCycle',
				(get) => {
					return get(direct) + '';
				}
			);

			expect(() => State.readSelector(direct)).toThrow(
				/cycle detected: "directCycle" -> "directCycle"/
			);

			/* eslint-disable @typescript-eslint/no-use-before-define */

			const intermediate: Selector<string> = State.selector(
				'intermediate',
				(get) => {
					return `1:${get(indirect)}:`;
				}
			);

			const other = State.selector('other', (get) => {
				return `2:${get(intermediate)}:`;
			});

			const indirect = State.selector('indirectCycle', (get) => {
				return `3:${get(other)}:`;
			});

			/* eslint-enable @typescript-eslint/no-use-before-define */

			expect(() => State.readSelector(indirect)).toThrow(
				/cycle detected: "indirectCycle" -> "other" -> "intermediate" -> "indirectCycle"/
			);
		});

		it('returns read-only objects in development', () => {
			withEnv('development', () => {
				const selector = State.selector('frozen', (get) => {
					return {
						person: get(person),
					};
				});

				const value = State.readSelector(selector);

				// @ts-expect-error TS2339: Property 'added' does not exist

				value.added = 'thing';

				// @ts-expect-error TS2339: Property 'added' does not exist

				expect(value.added).toBe(undefined);

				expect(() => {
					'use strict';

					const value = State.readSelector(selector);

					// @ts-expect-error TS2339: Property 'added' does not exist

					value.added = 'thing';
				}).toThrow(/not extensible/);
			});

			withEnv('production', () => {
				const selector = State.selector('mutable', (get) => {
					return {
						person: get(person),
					};
				});

				const value = State.readSelector(selector);

				// @ts-expect-error TS2339: Property 'added' does not exist

				value.added = 'thing';

				// @ts-expect-error TS2339: Property 'added' does not exist

				expect(value.added).toBe('thing');
			});
		});
	});

	describe('selector()', () => {
		it('returns a read-only object', () => {
			withEnv('development', () => {
				const selector = State.selector('foo', () => 'value');

				expect(() => {
					'use strict';

					// @ts-ignore: avoid TS2540 here.

					selector.key = 'overwrite';
				}).toThrow(/read only/);
			});

			// But not in production, to avoid the performance hit.

			withEnv('production', () => {
				const selector = State.selector('bar', () => 'value');

				expect(() => {
					'use strict';

					// @ts-ignore: avoid TS2540 here.

					selector.key = 'overwrite';
				}).not.toThrow();

				expect(selector.key).toBe('overwrite');
			});
		});

		it('complains if an atom with the same key was previously registered', () => {
			withEnv('production', () => {
				State.atom('foo', 'value');

				expect(() => State.selector('foo', () => 'value')).toThrow(
					/key "foo" already taken/
				);
			});

			// And also in development, because having colliding keys for atoms
			// and selectors is a clear mistake.

			withEnv('development', () => {
				State.atom('bar', 'value');

				expect(() => State.selector('bar', () => 'value')).toThrow(
					/key "bar" already taken by an atom/
				);
			});
		});

		it('complains if a selector with the same key was previously registered', () => {
			withEnv('production', () => {
				State.selector('baz', () => 'value');

				expect(() => State.selector('baz', () => 'other')).toThrow(
					/key "baz" already taken/
				);
			});

			// But not in development, to permit the possibility of hot
			// reloading...

			withEnv('development', () => {
				State.selector('qux', () => 'value');

				expect(() =>
					State.selector('qux', () => 'other')
				).not.toThrow();
			});
		});

		it('reports type errors if declared with the wrong type', () => {
			State.selector('bar', () => 'string') as Selector<string>;

			// @ts-expect-error

			State.selector('bar', () => 'string') as Selector<number>;
		});
	});

	describe('subscribe()', () => {
		let atom: Atom<string>;

		beforeEach(() => {
			atom = State.atom('foo', 'thing');
		});

		it('can subscribe to an atom', () => {
			const fn = jest.fn();

			State.subscribe(atom, fn);

			State.writeAtom(atom, 'updated');

			expect(fn).toHaveBeenCalledWith('updated');
		});

		it('can subscribe to a selector', () => {
			const fn = jest.fn();

			const selector = State.selector('bar', (get) => get(atom) + 'bar');

			State.subscribe(selector, fn);

			State.writeAtom(atom, '...');

			expect(fn).toHaveBeenCalledWith('...bar');
		});

		it('can be disposed', () => {
			const atomListener = jest.fn();

			const atomSubscription = State.subscribe(atom, atomListener);

			atomSubscription.dispose();

			State.writeAtom(atom, 'updated');

			expect(atomListener).not.toHaveBeenCalled();

			const selector = State.selector('baz', (get) => get(atom) + 'baz');

			const selectorListener = jest.fn();

			const selectorSubscription = State.subscribe(
				selector,
				selectorListener
			);

			selectorSubscription.dispose();

			State.writeAtom(atom, 'modified');

			expect(selectorListener).not.toHaveBeenCalled();
		});

		it('allows multiple subscriptions to the same atom', () => {
			const fn1 = jest.fn();

			State.subscribe(atom, fn1);

			State.writeAtom(atom, 'update once');

			const fn2 = jest.fn();

			State.subscribe(atom, fn2);

			State.writeAtom(atom, 'update twice');

			const fn3 = jest.fn();

			State.subscribe(atom, fn3);

			State.writeAtom(atom, 'update thrice');

			expect(fn1.mock.calls).toEqual([
				['update once'],
				['update twice'],
				['update thrice'],
			]);

			expect(fn2.mock.calls).toEqual([
				['update twice'],
				['update thrice'],
			]);

			expect(fn3.mock.calls).toEqual([['update thrice']]);
		});

		it('invalidates dependencies', () => {
			const fn = jest.fn();

			let callCount = 0;

			const selector = State.selector('bar', (get) => {
				callCount++;

				return get(atom) + '!';
			});

			expect(callCount).toBe(0);

			State.subscribe(selector, fn);

			// Subscribing evaluates the selector, in order to compute the
			// dependency graph.

			expect(callCount).toBe(1);

			expect(State.readSelector(selector)).toBe('thing!');

			expect(callCount).toBe(1);

			State.writeAtom(atom, 'thong');

			// Subscriber reads the invalidated selector.

			expect(callCount).toBe(2);

			expect(fn).toHaveBeenCalledWith('thong!');

			expect(State.readSelector(selector)).toBe('thong!');

			expect(callCount).toBe(2);
		});
	});

	describe('writeAtom()', () => {
		let atom: Atom<string>;

		beforeEach(() => {
			atom = State.atom('foo', 'thing');
		});

		it('updates the value of the atom', () => {
			State.writeAtom(atom, 'updated');

			expect(State.readAtom(atom)).toBe('updated');
		});

		it('does not notify subscribers if the value has not changed', () => {
			State.writeAtom(atom, 'thing');

			const fn = jest.fn();

			State.subscribe(atom, fn);

			State.writeAtom(atom, 'thing');

			expect(fn).not.toHaveBeenCalled();
		});
	});

	describe('__internal__', () => {
		describe('debug.atoms', () => {
			it('is a list of all known atoms', () => {
				expect(State.__internal__.debug.atoms).toEqual([]);

				State.atom('hey', 1);
				State.atom('you', true);

				expect(State.__internal__.debug.atoms).toEqual([
					expect.objectContaining({
						default: 1,
						key: 'hey',
					}),
					expect.objectContaining({
						default: true,
						key: 'you',
					}),
				]);
			});

			it('is not a shared mutable list', () => {
				State.atom('thing', null);

				const atoms = State.__internal__.debug.atoms;

				expect(atoms.length).toBe(1);

				atoms.pop();

				expect(State.__internal__.debug.atoms).toEqual([
					expect.objectContaining({
						default: null,
						key: 'thing',
					}),
				]);
			});
		});

		describe('debug.selectors', () => {
			it('is a list of all known selectors', () => {
				expect(State.__internal__.debug.selectors).toEqual([]);

				const atom = State.atom('foo', 1);

				State.selector('bar', (get) => get(atom) + 'bar');
				State.selector('baz', (get) => get(atom) + 'baz');

				expect(State.__internal__.debug.selectors).toEqual([
					expect.objectContaining({
						deriveValue: expect.any(Function),
						key: 'bar',
					}),
					expect.objectContaining({
						deriveValue: expect.any(Function),
						key: 'baz',
					}),
				]);
			});

			it('is not a shared mutable list', () => {
				const atom = State.atom('foo', 1);

				State.selector('bar', (get) => get(atom) + 'bar');

				const selectors = State.__internal__.debug.selectors;

				expect(selectors.length).toBe(1);

				selectors.pop();

				expect(State.__internal__.debug.selectors).toEqual([
					expect.objectContaining({
						deriveValue: expect.any(Function),
						key: 'bar',
					}),
				]);
			});
		});
	});

	describe('__unsafe__', () => {
		let atom: Atom<string>;

		beforeEach(() => {
			atom = State.atom('atom', 'hello');

			State.selector('selector', (get) => get(atom).length);

			jest.spyOn(console, 'warn');
		});

		afterEach(() => {
			jest.restoreAllMocks();
		});

		describe('readKey()', () => {
			it('obtains the value of an atom', () => {
				expect(State.__unsafe__.readKey('atom')).toBe('hello');
			});

			it('obtains the value of a selector', () => {
				expect(State.__unsafe__.readKey('selector')).toBe(5);
			});

			it('emits a warning on first use of each key in development', () => {
				withEnv('development', () => {
					expect(console.warn).not.toHaveBeenCalled();

					State.__unsafe__.readKey('atom');

					expect(console.warn).toHaveBeenCalledWith(
						expect.stringContaining(
							'access via string key "atom" is not type-safe'
						)
					);

					State.__unsafe__.readKey('atom');

					expect(console.warn).toHaveBeenCalledTimes(1);

					State.__unsafe__.readKey('selector');

					expect(console.warn).toHaveBeenLastCalledWith(
						expect.stringContaining(
							'access via string key "selector" is not type-safe'
						)
					);

					State.__unsafe__.readKey('selector');

					expect(console.warn).toHaveBeenCalledTimes(2);
				});

				jest.resetAllMocks();

				withEnv('production', () => {
					State.__unsafe__.readKey('atom');
					State.__unsafe__.readKey('selector');

					expect(console.warn).not.toHaveBeenCalled();
				});
			});

			it('complains if no atom or selector matches the key', () => {
				expect(() => State.__unsafe__.readKey('bogus')).toThrow(
					/no atom or selector exists/
				);
			});

			it('is not type-safe', () => {
				State.__unsafe__.readKey('atom') as number;
				State.__unsafe__.readKey('atom') as string;
				State.__unsafe__.readKey('selector') as number;
				State.__unsafe__.readKey('selector') as string;
			});
		});

		describe('subscribeKey()', () => {
			let atomCallback: typeof jest.fn;
			let selectorCallback: typeof jest.fn;

			beforeEach(() => {
				atomCallback = jest.fn();
				selectorCallback = jest.fn();
			});

			it('watches the value of an atom', () => {
				const {dispose} = State.__unsafe__.subscribeKey(
					'atom',
					atomCallback
				);

				State.writeAtom(atom, 'new value');

				expect(atomCallback).toHaveBeenCalledWith('new value');

				dispose();

				State.writeAtom(atom, 'another value');

				expect(atomCallback).toHaveBeenCalledTimes(1);
			});

			it('watches the value of a selector', () => {
				const {dispose} = State.__unsafe__.subscribeKey(
					'selector',
					selectorCallback
				);

				State.writeAtom(atom, 'new value');

				expect(selectorCallback).toHaveBeenCalledWith(9);

				dispose();

				State.writeAtom(atom, 'another value');

				expect(selectorCallback).toHaveBeenCalledTimes(1);
			});

			it('emits a warning on first use of each key', () => {
				withEnv('development', () => {
					expect(console.warn).not.toHaveBeenCalled();

					State.__unsafe__.subscribeKey('atom', () => {});

					expect(console.warn).toHaveBeenCalledWith(
						expect.stringContaining(
							'access via string key "atom" is not type-safe'
						)
					);

					State.__unsafe__.subscribeKey('atom', () => {});

					expect(console.warn).toHaveBeenCalledTimes(1);

					State.__unsafe__.subscribeKey('selector', () => {});

					expect(console.warn).toHaveBeenLastCalledWith(
						expect.stringContaining(
							'access via string key "selector" is not type-safe'
						)
					);

					State.__unsafe__.subscribeKey('selector', () => {});

					expect(console.warn).toHaveBeenCalledTimes(2);
				});

				jest.resetAllMocks();

				withEnv('production', () => {
					State.__unsafe__.readKey('atom');
					State.__unsafe__.readKey('selector');

					expect(console.warn).not.toHaveBeenCalled();
				});
			});

			it('complains if no atom or selector matches the key', () => {
				expect(() =>
					State.__unsafe__.subscribeKey('bogus', () => {})
				).toThrow(/no atom or selector exists/);
			});

			it('is not type-safe', () => {
				State.__unsafe__.subscribeKey('atom', (_value: unknown) => {});

				// @ts-expect-error

				State.__unsafe__.subscribeKey('atom', (_value: string) => {});
			});
		});

		describe('writeKey()', () => {
			it('writes a new value to an atom', () => {
				State.__unsafe__.writeKey('atom', 'new value');

				expect(State.readAtom(atom)).toBe('new value');
			});

			it('refuses to write to a selector', () => {
				expect(() => {
					State.__unsafe__.writeKey('selector', 'right');
				}).toThrow(/expected atom but found selector/);
			});

			it('complains if atom does not exist', () => {
				expect(() => State.__unsafe__.writeKey('new', 'stuff')).toThrow(
					/no atom exists/
				);
			});

			it('emits a warning on first use of each key', () => {
				withEnv('development', () => {
					expect(console.warn).not.toHaveBeenCalled();

					State.__unsafe__.writeKey('atom', 'foo');

					expect(console.warn).toHaveBeenCalledWith(
						expect.stringContaining(
							'access via string key "atom" is not type-safe'
						)
					);

					State.__unsafe__.writeKey('atom', 'bar');

					expect(console.warn).toHaveBeenCalledTimes(1);

					State.atom('other', 'value');

					State.__unsafe__.writeKey('other', 'other');

					expect(console.warn).toHaveBeenLastCalledWith(
						expect.stringContaining(
							'access via string key "other" is not type-safe'
						)
					);

					State.__unsafe__.writeKey('other', 'yet another');

					expect(console.warn).toHaveBeenCalledTimes(2);
				});

				jest.resetAllMocks();

				withEnv('production', () => {
					State.__unsafe__.readKey('atom');
					State.__unsafe__.readKey('selector');

					expect(console.warn).not.toHaveBeenCalled();
				});
			});

			it('is not type-safe', () => {
				State.__unsafe__.writeKey('atom', 'hi');
				State.__unsafe__.writeKey('atom', 9000);
			});
		});
	});
});
