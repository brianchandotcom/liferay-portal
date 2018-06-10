'use strict';

import fs from 'fs';

describe(
	'LiferayComponent',
	() => {
		afterEach(
			() => {
				Liferay.destroyComponents();
			}
		);

		beforeEach(
			() => {
				const window = {
					Liferay: {
						fire: () => 0,
						namespace: 0
					}
				};

				const AUI = Object.assign(
					() => (
						{
							mix: () => 0,
							namespace: () => 0
						}
					),
					{
						$: Object.assign(
							() => (
								{
									on: () => 0
								}
							),
							{
								ajaxPrefilter: () => 0,
								ajaxSetup: () => 0
							}
						),
						_: {
							assign: () => 0,
							forEach: () => 0,
							isFunction: () => false
						}
					}
				);

				const themeDisplay = {
					getPathContext: () => 0
				};

				const script = fs.readFileSync(
					'./src/main/resources/META-INF/resources/liferay/liferay.js'
				);

				eval(script.toString());
			}
		);

		describe(
			'Liferay.component',
			() => {
				it(
					'should warn through console when a component is registered twice',
					() => {
						let msg = '';

						console.warn = function() {
							for (let i = 0; i < arguments.length; i++) {
								msg += arguments[i].toString();
								msg += ' ';
							}
						};

						Liferay.component('myButton', 1);
						Liferay.component('myButton', 2);

						expect(msg).toEqual('Component with id "myButton" is being registered twice. This can lead to unexpected behaviour in the "Liferay.component" and "Liferay.componentReady" APIs, as well as in the "*:registered" events. ');
					}
				);
			}
		);

		describe(
			'Liferay.componentReady',
			() => {
				it(
					'should return a single component if called before it is registered',
					() => {
						const myButton = {myButton: 'myButton'};

						const promise = Liferay.componentReady('myButton').then(
							component => {
								expect(component).toBe(myButton);
							}
						);

						Liferay.component('myButton', myButton);

						return promise;
					}
				);

				it(
					'should return a single component if called after it is registered',
					() => {
						const myButton = {myButton: 'myButton'};

						Liferay.component('myButton', myButton);

						return Liferay.componentReady('myButton').then(
							component => {
								expect(component).toBe(myButton);
							}
						);
					}
				);

				it(
					'should return an array of components if called before they are registered',
					() => {
						const myButton1 = {myButton1: 'myButton1'};
						const myButton2 = {myButton2: 'myButton2'};

						const promise = Liferay.componentReady('myButton1', 'myButton2').then(
							([component1, component2]) => {
								expect(component1).toBe(myButton1);
								expect(component2).toBe(myButton2);
							}
						);

						Liferay.component('myButton1', myButton1);
						Liferay.component('myButton2', myButton2);

						return promise;
					}
				);

				it(
					'should return an array of components if called after they are registered',
					() => {
						const myButton1 = {myButton1: 'myButton1'};
						const myButton2 = {myButton2: 'myButton2'};

						Liferay.component('myButton1', myButton1);
						Liferay.component('myButton2', myButton2);

						return Liferay.componentReady('myButton1', 'myButton2').then(
							([component1, component2]) => {
								expect(component1).toBe(myButton1);
								expect(component2).toBe(myButton2);
							}
						);
					}
				);
			}
		);

		describe(
			'Liferay.destroyComponent',
			() => {
				it(
					'should destroy a registered component',
					() => {
						const componentId = 'myComponent';

						Liferay.component(componentId, {});

						Liferay.destroyComponent(componentId);

						expect(Liferay.component(componentId)).toBeUndefined();
					}
				);

				it(
					'should ignore non registered components',
					() => {
						Liferay.component('componentId', {});

						expect(
							() => {
								Liferay.destroyComponent('otherComponentId');
							}
						).not.toThrow();
					}
				);

				it(
					'should invoke a component\'s lifecyle destroy method if present when destroying it',
					() => {
						const componentId = 'myComponent';
						const destroyFn = jest.fn();

						Liferay.component(
							componentId,
							{
								destroy: destroyFn
							}
						);

						Liferay.destroyComponent(componentId);

						expect(destroyFn).toHaveBeenCalled();
					}
				);

				it(
					'should invoke a component\'s lifecyle dispose method if present when destroying it',
					() => {
						const componentId = 'myComponent';
						const disposeFn = jest.fn();

						Liferay.component(
							componentId,
							{
								dispose: disposeFn
							}
						);

						Liferay.destroyComponent(componentId);

						expect(disposeFn).toHaveBeenCalled();
					}
				);
			}
		);

		describe(
			'Liferay.destroyComponents',
			() => {
				it(
					'should destroy all registered components if no filter function is provided',
					() => {
						Liferay.component('component1', 1);
						Liferay.component('component2', 2);
						Liferay.component('component3', 3);

						Liferay.destroyComponents();

						expect(Liferay.component('component1')).toBeUndefined();
						expect(Liferay.component('component2')).toBeUndefined();
						expect(Liferay.component('component3')).toBeUndefined();
					}
				);

				it(
					'should invoke the provided filter function for every component with the registered component and destroy config as params',
					() => {
						const filterFn = jest.fn();

						const destroyConfig = {destroy: true};

						Liferay.component('component1', 1);
						Liferay.component('component2', 2, destroyConfig);
						Liferay.component('component3', 3);

						Liferay.destroyComponents(filterFn);

						expect(filterFn).toHaveBeenCalledTimes(3);

						expect(filterFn.mock.calls[0]).toEqual([1, {}]);
						expect(filterFn.mock.calls[1]).toEqual([2, destroyConfig]);
						expect(filterFn.mock.calls[2]).toEqual([3, {}]);
					}
				);

				it(
					'should only destoy the components matched by the provided filter function',
					() => {
						const filterFn = jest.fn(
							(component, destroyConfig) => destroyConfig.destroy
						);

						const destroyConfig = {destroy: true};

						Liferay.component('component1', 1);
						Liferay.component('component2', 2, destroyConfig);
						Liferay.component('component3', 3);

						Liferay.destroyComponents(filterFn);

						expect(Liferay.component('component1')).toBe(1);
						expect(Liferay.component('component2')).toBeUndefined();
						expect(Liferay.component('component3')).toBe(3);
					}
				);
			}
		);
	}
);