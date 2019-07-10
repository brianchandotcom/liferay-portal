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

'use strict';

import {
	component,
	componentReady,
	destroyComponent,
	destroyComponents,
	destroyUnfulfilledPromises
} from '../../src/main/resources/META-INF/resources/liferay/component.es';

describe('Liferay', () => {
	afterEach(() => {
		destroyComponents();
	});

	beforeEach(() => {
		Liferay = {
			fire: () => 0
		};
	});

	describe('Liferay.component', () => {
		it('stores function inputs and invoke them lazily on component retrieval', () => {
			const myButton = {myButton: 'myButton'};
			const spy = jest.fn(() => myButton);

			component('myButton', spy);

			expect(spy).not.toHaveBeenCalled();

			expect(component('myButton')).toEqual(myButton);
		});

		it('warns through console when a component is registered twice', () => {
			let msg = '';

			// eslint-disable-next-line no-console
			console.warn = function() {
				for (let i = 0; i < arguments.length; i++) {
					msg += arguments[i].toString();
					msg += ' ';
				}
			};

			component('myButton', 1);
			component('myButton', 2);

			expect(msg).toEqual(
				'Component with id "myButton" is being registered twice. This can lead to unexpected behaviour in the "Liferay.component" and "Liferay.componentReady" APIs, as well as in the "*:registered" events. '
			);
		});
	});

	describe('Liferay.componentReady', () => {
		it('returns a single component if called before it is registered', () => {
			const myButton = {myButton: 'myButton'};

			const promise = componentReady('myButton').then(component => {
				expect(component).toBe(myButton);
			});

			component('myButton', myButton);

			return promise;
		});

		it('returns a single component if called after it is registered', () => {
			const myButton = {myButton: 'myButton'};

			component('myButton', myButton);

			return componentReady('myButton').then(component => {
				expect(component).toBe(myButton);
			});
		});

		it('returns an array of components if called before they are registered', () => {
			const myButton1 = {myButton1: 'myButton1'};
			const myButton2 = {myButton2: 'myButton2'};

			const promise = componentReady('myButton1', 'myButton2').then(
				([component1, component2]) => {
					expect(component1).toBe(myButton1);
					expect(component2).toBe(myButton2);
				}
			);

			component('myButton1', myButton1);
			component('myButton2', myButton2);

			return promise;
		});

		it('returns an array of components if called after they are registered', () => {
			const myButton1 = {myButton1: 'myButton1'};
			const myButton2 = {myButton2: 'myButton2'};

			component('myButton1', myButton1);
			component('myButton2', myButton2);

			return componentReady('myButton1', 'myButton2').then(
				([component1, component2]) => {
					expect(component1).toBe(myButton1);
					expect(component2).toBe(myButton2);
				}
			);
		});
	});

	describe('Liferay.destroyComponent', () => {
		it('destroys a registered component', () => {
			const componentId = 'myComponent';

			component(componentId, {});

			destroyComponent(componentId);

			expect(component(componentId)).toBeUndefined();
		});

		it('ignores non registered components', () => {
			component('componentId', {});

			expect(() => {
				destroyComponent('otherComponentId');
			}).not.toThrow();
		});

		it("invokes a component's lifecyle destroy method if present when destroying it", () => {
			const componentId = 'myComponent';
			const destroyFn = jest.fn();

			component(componentId, {
				destroy: destroyFn
			});

			destroyComponent(componentId);

			expect(destroyFn).toHaveBeenCalled();
		});

		it("invokes a component's lifecyle dispose method if present when destroying it", () => {
			const componentId = 'myComponent';
			const disposeFn = jest.fn();

			component(componentId, {
				dispose: disposeFn
			});

			destroyComponent(componentId);

			expect(disposeFn).toHaveBeenCalled();
		});
	});

	describe('Liferay.destroyComponents', () => {
		it('destroys all registered components if no filter function is provided', () => {
			component('component1', 1);
			component('component2', 2);
			component('component3', 3);

			destroyComponents();

			expect(component('component1')).toBeUndefined();
			expect(component('component2')).toBeUndefined();
			expect(component('component3')).toBeUndefined();
		});

		it('invokes the provided filter function for every component with the registered component and destroy config as params', () => {
			const filterFn = jest.fn();

			const componentConfig = {destroy: true};

			component('component1', 1);
			component('component2', 2, componentConfig);
			component('component3', 3);

			destroyComponents(filterFn);

			expect(filterFn).toHaveBeenCalledTimes(3);

			expect(filterFn.mock.calls[0]).toEqual([1, {}]);
			expect(filterFn.mock.calls[1]).toEqual([2, componentConfig]);
			expect(filterFn.mock.calls[2]).toEqual([3, {}]);
		});

		it('only destroys the components matched by the provided filter function', () => {
			const filterFn = jest.fn(
				(component, componentConfig) => componentConfig.destroy
			);

			const componentConfig = {destroy: true};

			component('component1', 1);
			component('component2', 2, componentConfig);
			component('component3', 3);

			destroyComponents(filterFn);

			expect(component('component1')).toBe(1);
			expect(component('component2')).toBeUndefined();
			expect(component('component3')).toBe(3);
		});
	});

	describe('Liferay.destroyUnfulfilledPromises', () => {
		it('cleans up all pending invocations of componentReady', () => {
			const spy = jest.fn();

			componentReady('component').then(spy);

			destroyUnfulfilledPromises();

			const promise = componentReady('component').then(() => {
				expect(spy).not.toHaveBeenCalled();
			});

			component('component', 1);

			return promise;
		});
	});
});
