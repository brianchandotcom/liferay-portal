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

import register from '../../../src/main/resources/META-INF/resources/liferay/portlet/register.es';

describe('PortletHub', () => {
	beforeEach(() => {
		jest.useFakeTimers();
	});

	afterEach(() => {
		jest.clearAllTimers();
	});

	describe('action', () => {
		const ids = global.portlet.getIds();
		const onStateChange = jest.fn();
		const portletA = ids[0];
		const portletB = ids[1];

		let hubA;
		let hubB;
		let listenerA;

		beforeEach(() => {
			global.fetch.mockResponse(JSON.stringify([portletA]));

			return Promise.all([register(portletA), register(portletB)]).then(
				values => {
					hubA = values[0];

					listenerA = hubA.addEventListener(
						'portlet.onStateChange',
						onStateChange
					);

					hubB = values[1];
				}
			);
		});

		afterEach(() => {
			if (hubA !== null) {
				hubA.removeEventListener(listenerA);
			}
			hubA = null;
			hubB = null;
		});

		it('is present in the register return object and is a function', () => {
			expect(hubA.action).toBeInstanceOf(Function);
		});

		it('throws a TypeError if too many (>2) arguments are provided', () => {
			const element = document.createElement('form');
			const parameters = {};

			const testFn = () => {
				hubA.action(parameters, element, 'param3');
			};

			expect(testFn).toThrowError(TypeError);
		});

		it('throws a TypeError if a single argument is null', () => {
			const testFn = () => {
				hubA.action(null);
			};

			expect(testFn).toThrow(TypeError);
		});

		it('throws a TypeError if the element argument is null', () => {
			const testFn = () => {
				hubA.action({}, null);
			};

			expect(testFn).toThrowError(TypeError);
		});

		it('throws a TypeError if action parameters is null', () => {
			const testFn = () => {
				hubA.action(null, document.createElement('form'));
			};

			expect(testFn).toThrow(TypeError);
		});

		it('throws a TypeError if action parameters is invalid', () => {
			const parameters = {
				a: 'value'
			};

			const testFn = () => {
				hubA.action(parameters, document.createElement('form'));
			};

			expect(testFn).toThrow(TypeError);
		});

		it('throws a TypeError if the element argument is invalid', () => {
			const testFn = () => {
				hubA.action({}, 'Invalid');
			};

			expect(testFn).toThrow(TypeError);
		});

		it('throws a TypeError if there are 2 element arguments', () => {
			const element = document.createElement('form');
			const testFn = () => {
				hubA.action(element, element);
			};

			expect(testFn).toThrow(TypeError);
		});

		it('throws a TypeError if there are 2 action parameters arguments', () => {
			const parameters = {};
			const testFn = () => {
				hubA.action(parameters, parameters);
			};

			expect(testFn).toThrow(TypeError);
		});

		it('does not throw if both arguments are valid', () => {
			const element = document.createElement('form');
			const parameters = {
				param1: ['paramValue1']
			};

			return hubA.action(parameters, element).then(() => {
				jest.runAllTimers();
				expect(onStateChange).toHaveBeenCalled();
			});
		});

		it('throws an AccessDeniedException if action is called twice', () => {
			const element = document.createElement('form');
			const parameters = {};

			hubA.action(parameters, element);

			const testFn = () => hubA.action(parameters, element);

			expect(testFn).toThrow();
		});

		it('throws an NotInitializedException if no onStateChange listener is registered.', () => {
			const element = document.createElement('form');
			const parameters = {
				param1: ['paramValue1']
			};

			const testFn = () => {
				hubB.action(parameters, element);
			};

			expect(testFn).toThrow();
		});

		it('causes the onStateChange listener to be called and state is as expected', () => {
			const element = document.createElement('form');
			const parameters = {};

			return hubA.action(parameters, element).then(() => {
				jest.runAllTimers();
				expect(onStateChange).toHaveBeenCalled();
			});
		});
	});

	describe('actions affect multiple portlets', () => {
		const onStateChangeA = jest.fn();
		const onStateChangeB = jest.fn();
		const onStateChangeC = jest.fn();
		const onStateChangeD = jest.fn();

		const ids = global.portlet.getIds();
		const portletA = ids[0];
		const portletB = ids[1];
		const portletC = ids[2];
		const portletD = ids[3];

		let hubA;
		let hubB;
		let hubC;
		let hubD;
		let listenerA;
		let listenerB;
		let listenerC;
		let listenerD;

		beforeEach(() => {
			return Promise.all([
				register(portletA),
				register(portletB),
				register(portletC),
				register(portletD)
			]).then(values => {
				hubA = values[0];

				listenerA = hubA.addEventListener(
					'portlet.onStateChange',
					onStateChangeA
				);

				onStateChangeA.mockClear();

				hubB = values[1];

				listenerB = hubB.addEventListener(
					'portlet.onStateChange',
					onStateChangeB
				);

				onStateChangeB.mockClear();

				hubC = values[2];

				listenerC = hubC.addEventListener(
					'portlet.onStateChange',
					onStateChangeC
				);

				onStateChangeC.mockClear();

				hubD = values[3];

				listenerD = hubD.addEventListener(
					'portlet.onStateChange',
					onStateChangeD
				);

				onStateChangeD.mockClear();
			});
		});

		afterEach(() => {
			hubA.removeEventListener(listenerA);
			hubB.removeEventListener(listenerB);
			hubC.removeEventListener(listenerC);
			hubD.removeEventListener(listenerD);
		});

		it('throws an AccessDeniedException if called before previous action completes', () => {
			global.fetch.mockResponse(JSON.stringify([portletA]));

			const element = document.createElement('form');
			const parameters = {};

			onStateChangeA.mockClear();
			onStateChangeB.mockClear();

			hubA.action(parameters, element);
			const testFn = () => hubB.action(parameters, element);

			expect(testFn).toThrow();
			expect(onStateChangeB).not.toHaveBeenCalled();
		});

		it('allows actions that update the state of 2 portlets. other portlets are not updated', () => {
			global.fetch.mockResponse(JSON.stringify([portletB, portletC]));

			const element = document.createElement('form');
			const parameters = {};

			return hubB.action(parameters, element).then(() => {
				expect(onStateChangeA).not.toHaveBeenCalled();
				expect(onStateChangeD).not.toHaveBeenCalled();
			});
		});
	});
});
