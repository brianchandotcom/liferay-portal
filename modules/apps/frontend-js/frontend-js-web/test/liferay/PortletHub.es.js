import PortletInit from '../../src/main/resources/META-INF/resources/liferay/portlet_hub/PortletInit.es';
import RenderState from '../../src/main/resources/META-INF/resources/liferay/portlet_hub/RenderState.es';
import register from '../../src/main/resources/META-INF/resources/liferay/portlet_hub/register.es';
import {portlet} from './mock_portlet_data.es';

function fetchMock(data) {
	global.fetch = jest.fn().mockImplementation(
		() => {
			return Promise.resolve(
				{
					json: jest.fn().mockImplementation(() => Promise.resolve(data)),
					text: jest.fn().mockImplementation(() => Promise.resolve(JSON.stringify(data)))
				}
			);
		}
	);
}

global.portlet = {
	getInitData: portlet.test.getInitData
};

describe(
	'Portlet Hub',
	() => {
		beforeEach(
			() => {
			}
		);

		describe(
			'register',
			() => {
				it(
					'should throw error if called without portletId',
					() => {
						expect.assertions(1);

						return register().catch(
							err => {
								expect(err.message).toEqual('Invalid portlet ID');
							}
						);
					}
				);

				it(
					'should return an instance of PortletInit',
					() => {
						return register('portletA').then(
							hub => {
								expect(hub).toBeInstanceOf(PortletInit);
							}
						);
					}
				);
			}
		);

		describe(
			'client events',
			() => {

				let portletHub;

				beforeEach(
					() => {
						portletHub = null;
					}
				);

				afterEach(
					() => {

					}
				);

				it(
					'should add client event listener',
					() => {
						const stub = jest.fn();

						return register('portletA').then(
							hub => {
								portletHub = hub;

								const handle = hub.addEventListener('clientEvent', stub);

								expect(stub.mock.calls.length).toBe(0);

								hub.dispatchClientEvent('clientEvent');

								expect(stub.mock.calls.length).toBe(1);

								hub.removeEventListener(handle);
							}
						);
					}
				);

				it(
					'should return number of listeners',
					() => {
						const stub = jest.fn();

						return register('portletA').then(
							hub => {
								portletHub = hub;

								expect(stub.mock.calls.length).toBe(0);

								const handle = hub.addEventListener('clientEvent', stub);
								const total = hub.dispatchClientEvent('clientEvent');

								expect(total).toBe(1);
								hub.removeEventListener(handle);
							}
						);
					}
				);

				it(
					'should throw error if addEventListener is called with invalid args',
					() => {
						return register('portletA').then(
							hub => {

								expect(
									() => {
										hub.addEventListener(1, 2, 3);
									}
								).toThrow('Too many arguments passed to addEventListener');

								expect(
									() => {
										hub.addEventListener(1);
									}
								).toThrow('Invalid arguments passed to addEventListener');

								expect(
									() => {
										hub.addEventListener('string', 1);
									}
								).toThrow('Invalid arguments passed to addEventListener');
							}
						);
					}
				);

				it(
					'should not call listener if it is removed',
					() => {
						const stub = jest.fn();

						return register('portletA').then(
							hub => {
								const handle = hub.addEventListener('toBeRemoved', stub);

								hub.removeEventListener(handle);

								const total = hub.dispatchClientEvent('toBeRemoved');

								expect(stub.mock.calls.length).toBe(0);
								expect(total).toBe(0);
							}
						);
					}
				);

				it(
					'should throw error if dispatchClientEvent is called with invalid args',
					() => {
						const stub = jest.fn();

						return register('portletA').then(
							hub => {
								expect(
									() => {
										hub.dispatchClientEvent(1, 2, 3);
									}
								).toThrow('Too many arguments passed to dispatchClientEvent');

								expect(
									() => {
										hub.dispatchClientEvent(1);
									}
								).toThrow('Event type must be a string');
							}
						);
					}
				);

				it(
					'should throw error when attempting to dispatch event with protected name',
					() => {
						return register('portletA').then(
							hub => {
								expect(
									() => {
										hub.dispatchClientEvent('portlet.clientEvent');
									}
								).toThrow('The event type is invalid: portlet.clientEvent');
							}
						);
					}
				);

				it(
					'should throw error if removeEventListener is called with invalid args',
					() => {
						return register('portletA').then(
							hub => {
								expect(
									() => {
										hub.removeEventListener(1, 2);
									}
								).toThrow('Too many arguments passed to removeEventListener');

								expect(
									() => {
										hub.removeEventListener();
									}
								).toThrow('The event handle provided is undefined');

								expect(
									() => {
										hub.removeEventListener({});
									}
								).toThrow('The event listener handle doesn\'t match any listeners.');
							}
						);
					}
				);

				it(
					'should call listener with matching wildcard event types',
					() => {
						const stub = jest.fn();

						return register('portletA').then(
							hub => {
								portletHub = hub;

								const handle = hub.addEventListener('awesome\\w+Event$', stub);

								const total = hub.dispatchClientEvent('awesomeClientEvent');

								expect(stub.mock.calls.length).toBe(1);
								expect(total).toBe(1);
							}
						);
					}
				);
			}
		);

		describe(
			'the newState function',
			() => {
				it(
					'should return a new RenderState object',
					() => {
						expect.assertions(3);

						return register('portletB')
							.then(
								hub => {
									const renderState = hub.newState(
										{
											parameters: {
												a: [1, 2, 3],
												b: [4]
											},
											portletMode: 'view',
											windowState: 'normal'
										}
									);

									const valuesA = renderState.getValues('a');

									expect(valuesA).toEqual(
										expect.arrayContaining([1, 2, 3])
									);

									const valueB = renderState.getValue('b');

									expect(valueB).toEqual(4);

									expect(renderState.portletMode).toEqual('view');
								}
							);
					}
				);
			}
		);

		describe(
			'the newParameters function',
			() => {
				it(
					'should return new parameters according to the data passed',
					() => {
						expect.assertions(4);

						return register('portletC').then(
							hub => {
								const parameters1 = {
									a: [1, 2, 3],
									b: null,
									c: 'foo',
									d: ['four', 'five', 'six']
								};

								const parameters2 = hub.newParameters(parameters1);

								expect(parameters2.a).toEqual(
									expect.arrayContaining([1, 2, 3])
								);

								expect(parameters2.b).not.toBeDefined();
								expect(parameters2.c).not.toBeDefined();

								expect(parameters2.d).toEqual(
									expect.arrayContaining(['four', 'five', 'six'])
								);
							}
						);
					}
				);
			}
		);

		describe(
			'the action function',
			() => {
				const ids = portlet.test.getIds();
				const onStateChange = jest.fn();
				const pageState = portlet.test.getInitData();
				const portletA = ids[0];
				const portletB = ids[1];
				const portletC = ids[2];
				const portletD = ids[3];

				let hubA;
				let hubB;
				let listenerA;

				beforeEach(
					() => {
						fetchMock(
							[
								portletA
							]
						);

						return Promise.all(
							[
								register(portletA),
								register(portletB)
							]
						).then(
							values => {
								hubA = values[0];

								listenerA = hubA.addEventListener(
									'portlet.onStateChange',
									onStateChange
								);

								hubB = values[1];
							}
						);
					}
				);

				afterEach(
					() => {
						global.fetch.mockRestore();

						hubA.removeEventListener(listenerA);
						hubA = null;
						hubB = null;
					}
				);

				it(
					'is present in the register return object and is a function',
					() => {
						expect(hubA.action).toBeInstanceOf(Function);
					}
				);

				it(
					'throws a TypeError if too many (>2) arguments are provided',
					() => {
						const element = document.createElement('form');
						const parameters = {};

						return hubA.action(parameters, element, 'param3').catch(
							err => {
								expect(err.name).toEqual('TypeError');
								expect(err.message).toEqual('Invalid argument type. Argument 3 is of type [object String]');
							}
						);
					}
				);

				it(
					'throws a TypeError if a single argument is null',
					() => {
						return hubA.action(null).catch(
							err => {
								expect(err.name).toEqual('TypeError');
								expect(err.message).toEqual('Invalid argument type. Argument 1 is of type [object Null]');
							}
						);
					}
				);

				it(
					'throws a TypeError if the element argument is null',
					() => {
						const parameters = {};

						hubA.action(parameters, null).catch(
							err => {
								expect(err.name).toEqual('TypeError');
								expect(err.message).toEqual('Invalid argument type. Argument 2 is of type [object Null]');
							}
						);
					}
				);

				it(
					'throws a TypeError if action parameters is null',
					() => {
						const element = document.createElement('form');

						hubA.action(null, element).catch(
							err => {
								expect(err.name).toEqual('TypeError');
								expect(err.message).toEqual('Invalid argument type. Argument 1 is of type [object Null]');
							}
						);
					}
				);

				it(
					'throws a TypeError if action parameters is invalid',
					() => {
						const element = document.createElement('form');
						const parameters = {
							a: 'value'
						};

						hubA.action(parameters, element).catch(
							err => {
								expect(err.name).toEqual('TypeError');
								expect(err.message).toEqual('a parameter is not an array');
							}
						);
					}
				);

				it(
					'throws a TypeError if the element argument is invalid',
					() => {
						const element = document.createElement('form');
						const parameters = {};

						hubA.action(parameters, 'Invalid').catch(
							err => {
								expect(err.name).toEqual('TypeError');
								expect(err.message).toEqual('Invalid argument type. Argument 2 is of type [object String]');
							}
						);
					}
				);

				it(
					'throws a TypeError if there are 2 element arguments',
					() => {
						const element = document.createElement('form');
						const parameters = {};

						return hubA.action(element, element).catch(
							err => {
								expect(err.name).toEqual('TypeError');
								expect(err.message).toEqual('Too many [object HTMLFormElement] arguments: [object HTMLFormElement], [object HTMLFormElement]');
							}
						);
					}
				);

				it(
					'throws a TypeError if there are 2 action parameters arguments',
					() => {
						const parameters = {};

						return hubA.action(parameters, parameters).catch(
							err => {
								expect(err.name).toEqual('TypeError');
								expect(err.message).toEqual('Too many parameters arguments');
							}
						);
					}
				);

				it(
					'does not throw if both arguments are valid',
					done => {
						const element = document.createElement('form');
						const parameters = {
							param1: ['paramValue1']
						};

						return hubA.action(parameters, element).then(
							updatedPortletIds => {
								setTimeout(
									() => {
										expect(onStateChange).toHaveBeenCalled();
										done();
									}
								);
							}
						);
					}
				);

				it(
					'throws an AccessDeniedException if action is called twice',
					() => {
						const element = document.createElement('form');
						const parameters = {};

						return Promise.all(
							[
								hubA.action(parameters, element),
								hubA.action(parameters, element)
							]
						).catch(
							err => {
								expect(err.name).toEqual('AccessDeniedException');
								expect(err.message).toEqual('Operation is already in progress');
							}
						);
					}
				);

				it(
					'throws an NotInitializedException if no onStateChange listener is registered.',
					() => {
						fetchMock([portletA]);

						const element = document.createElement('form');
						const parameters = {
							param1: ['paramValue1']
						};

						return hubB.action(parameters, element).catch(
							err => {
								global.fetch.mockRestore();

								expect(err.name).toEqual('NotInitializedException');
								expect(err.message).toEqual('No onStateChange listener registered for portlet: PortletB');
							}
						);
					}
				);

				it(
					'causes the onStateChange listener to be called and state is as expected',
					done => {
						const element = document.createElement('form');
						const parameters = {};

						return hubA.action(parameters, element).then(
							updatedPortletIds => {
								setTimeout(
									() => {
										expect(onStateChange).toHaveBeenCalled();
										done();
									}
								);
							}
						);
					}
				);
			}
		);

		describe(
			'actions affect multiple portlets',
			() => {
				const onStateChangeA = jest.fn();
				const onStateChangeB = jest.fn();
				const onStateChangeC = jest.fn();
				const onStateChangeD = jest.fn();

				const ids = portlet.test.getIds();
				const pageState = portlet.test.getInitData();
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
				let listenerZ;

				beforeEach(
					() => {
						return Promise.all(
							[
								register(portletA),
								register(portletB),
								register(portletC),
								register(portletD)
							]
						).then(
							values => {
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
							}
						);
					}
				);

				afterEach(
					() => {
						hubA.removeEventListener(listenerA);
						hubB.removeEventListener(listenerB);
						hubC.removeEventListener(listenerC);
						hubD.removeEventListener(listenerD);
					}
				);

				it(
					'throws an AccessDeniedException if called before previous action completes',
					done => {
						fetchMock([portletA]);

						const element = document.createElement('form');
						const parameters = {};

						onStateChangeA.mockClear();
						onStateChangeB.mockClear();

						const fnA = () => hubA.action(parameters, element);

						const fnB = () => hubB.action(parameters, element).catch(err => err);

						return Promise.all([fnA(), fnB()]).then(
							values => {
								const err = values[1];

								expect(err.message).toEqual('Operation is already in progress');
								expect(err.name).toEqual('AccessDeniedException');
								expect(onStateChangeB).not.toHaveBeenCalled();

								done();
							}
						);
					}
				);

				it(
					'allows actions that update the state of 2 portlets. other portlets are not updated',
					() => {
						fetchMock([portletB, portletC]);

						const element = document.createElement('form');
						const parameters = {};

						return hubB.action(parameters, element).then(
							updatedPortletIds => {
								global.fetch.mockRestore();

								expect(onStateChangeA).not.toHaveBeenCalled();
								expect(onStateChangeD).not.toHaveBeenCalled();
							}
						);
					}
				);
			}
		);

		describe(
			'provides the ability to add and remove event listeners',
			() => {
				const ids = portlet.test.getIds();
				const pageState = portlet.test.getInitData().portlets;
				const portletA = ids[0];
				const portletB = ids[3];
				const portletC = ids[1];

				let hubA;
				let hubB;

				beforeEach(
					() => {
						return Promise.all(
							[
								register(portletA),
								register(portletB)
							]
						).then(
							values => {
								hubA = values[0];
								hubB = values[1];
							}
						);
					}
				);

				afterEach(
					() => {

					}
				);

				it(
					'is present in the register return object and is a function',
					() => {
						expect(typeof hubA.addEventListener).toEqual('function');
					}
				);

				it(
					'throws a TypeError if no argument is provided',
					() => {
						const testFn = () => {
							hubA.addEventListener();
						};

						expect(testFn).toThrow(TypeError);
					}
				);

				it(
					'throws a TypeError if 1 argument is provided',
					() => {
						const testFn = () => {
							hubA.addEventListener('someEvent');
						};

						expect(testFn).toThrow(TypeError);
					}
				);

				it(
					'throws a TypeError if too many (>2) arguments are provided',
					() => {
						const testFn = () => {
							hubA.addEventListener('param1', 'param2', 'param3');
						};

						expect(testFn).toThrow(TypeError);
					}
				);

				it(
					'throws a TypeError if the type argument is not a string',
					() => {
						const testFn = () => {
							hubA.addEventListener(
								89,
								function(type, data) {}
							);
						};

						expect(testFn).toThrow(TypeError);
					}
				);

				it(
					'throws a TypeError if the function argument is not a function',
					() => {
						const testFn = () => {
							hubA.addEventListener('someEvent', 89);
						};

						expect(testFn).toThrow(TypeError);
					}
				);

				it(
					'throws a TypeError if the type is null',
					() => {
						const testFn = () => {
							hubA.addEventListener(
								null,
								function(type, data) {}
							);
						};

						expect(testFn).toThrow(TypeError);
					}
				);

				it(
					'throws a TypeError if the function is null',
					() => {
						const testFn = () => {
							hubA.addEventListener('someEvent', null);
						};

						expect(testFn).toThrow(TypeError);
					}
				);

				it(
					'throws a TypeError if the type begins with "portlet." but is neither "portlet.onStateChange" or "portlet.onError"',
					() => {
						const testFn = () => {
							hubA.addEventListener(
								'portlet.invalidType',
								function(type, data) {}
							);
						};

						expect(testFn).toThrow(TypeError);
					}
				);

				it(
					'does not throw an exception if both parameters are valid',
					() => {
						const testFn = () => {
							return hubA.addEventListener(
								'someEvent',
								function(type, data) {}
							);
						};

						expect(testFn).not.toThrow();

						const handle = testFn();

						hubA.removeEventListener(handle);
					}
				);

				it(
					'returns a handle to the event handler (an object) when the parameters are valid',
					() => {
						const handle = hubA.addEventListener(
							'someEvent',
							function(type, data) {}
						);

						expect(handle).not.toBeUndefined();

						hubA.removeEventListener(handle);
					}
				);

				it(
					'allows a listener for event type "portlet.onStateChange" to be added',
					() => {
						const testFn = () => {
							return hubA.addEventListener(
								'portlet.onStateChange',
								function(type, data) {}
							);
						};

						// expect(testFn).not.toThrow();

						const handle = testFn();

						expect(handle).not.toBeUndefined();

						hubA.removeEventListener(handle);
					}
				);

				it(
					'allows a listener for event type "portlet.onError" to be added',
					() => {
						const testFn = () => {
							return hubA.addEventListener(
								'portlet.onError',
								function(type, data) {}
							);
						};

						// expect(testFn).not.toThrow();

						const handle = testFn();

						expect(handle).not.toBeUndefined();

						hubA.removeEventListener(handle);
					}
				);
			}
		);

		describe(
			'the removeEventListener function',
			() => {

				const ids = portlet.test.getIds();
				const pageState = portlet.test.getInitData().portlets;
				const portletA = ids[0];

				let hubA;

				beforeEach(
					() => {
						return register(portletA).then(
							hub => {
								hubA = hub;
							}
						);
					}
				);

				it(
					'is present in the register return object and is a function',
					() => {
						expect(hubA.removeEventListener).toBeDefined();
						expect(typeof hubA.removeEventListener).toEqual('function');
					}
				);

				it(
					'throws a TypeError if too many (>1) arguments are provided',
					() => {
						const testFn = () => {
							hubA.removeEventListener('param1', 'param2', 'param3');
						};

						expect(testFn).toThrow(TypeError);
					}
				);

				it(
					'throws a TypeError if the handle is null',
					() => {
						const testFn = () => {
							hubA.removeEventListener(null);
						};

						expect(testFn).toThrow(TypeError);
					}
				);

				it(
					'throws a TypeError if the handle is undefined',
					() => {
						const testFn = () => {
							hubA.removeEventListener(undefined);
						};

						expect(testFn).toThrow(TypeError);
					}
				);

				it(
					'throws a TypeError if the handle has an invalid value',
					() => {
						const testFn = () => {
							hubA.removeEventListener('This is an invalid handle.');
						};

						expect(testFn).toThrow(TypeError);
					}
				);

				it(
					'allows a previously added user event listener to be removed',
					() => {
						const listener = jest.fn();

						const handle = hubA.addEventListener('event', listener);

						const testFn = () => {
							hubA.removeEventListener(handle);
						};

						expect(testFn).not.toThrow();
					}
				);

				it(
					'allows a previously added portlet.onStateChange event listener to be removed',
					() => {
						const listener = jest.fn();

						const handle = hubA.addEventListener('portlet.onStateChange', listener);

						const testFn = () => {
							hubA.removeEventListener(handle);
						};

						expect(testFn).not.toThrow();
					}
				);

				it(
					'allows a previously added portlet.onError event listener to be removed',
					() => {
						const listener = jest.fn();

						const handle = hubA.addEventListener('portlet.onError', listener);

						const testFn = () => {
							hubA.removeEventListener(handle);
						};

						expect(testFn).not.toThrow();
					}
				);

				it(
					'throws a TypeError if the user event handler is removed twice',
					() => {
						const listener = jest.fn();

						const handle = hubA.addEventListener('event', listener);

						hubA.removeEventListener(handle);

						const testFn = () => {
							hubA.removeEventListener(handle);
						};

						expect(testFn).toThrow(TypeError);
					}
				);

				it(
					'throws a TypeError if the onStateChange event handler is removed twice',
					() => {
						const listener = jest.fn();

						const handle = hubA.addEventListener('portlet.onStateChange', listener);

						hubA.removeEventListener(handle);

						const testFn = () => {
							hubA.removeEventListener(handle);
						};

						expect(testFn).toThrow(TypeError);
					}
				);

				it(
					'throws a TypeError if the onError event handler is removed twice',
					() => {
						const listener = jest.fn();

						const handle = hubA.addEventListener('portlet.onError', listener);

						hubA.removeEventListener(handle);

						const testFn = () => {
							hubA.removeEventListener(handle);
						};

						expect(testFn).toThrow(TypeError);
					}
				);
			}
		);

		describe(
			'onStateChange without render data',
			() => {
				const eventType = 'portlet.onStateChange';
				const ids = portlet.test.getIds();
				const pageState = portlet.test.getInitData().portlets;
				const portletA = ids[0];

				let hubA;
				let returnedRenderData;
				let returnedRenderState;
				let returnType;

				const onStateChange = jest.fn(
					(eventType, renderState, renderData) => {
						returnType = eventType;
						returnedRenderState = renderState;
						returnedRenderData = renderData;
					}
				);

				beforeEach(
					() => {
						return register(portletA).then(
							hub => {
								hubA = hub;
								onStateChange.mockClear();
								returnType = returnedRenderState = returnedRenderData = undefined;
							}
						);
					}
				);

				it(
					'does not call the portlet.onStateChange listener during the addEventListener call',
					() => {
						const handle = hubA.addEventListener(eventType, onStateChange);

						expect(onStateChange).not.toHaveBeenCalled();

						hubA.removeEventListener(handle);
					}
				);

				it(
					'is passed a type parameter with value "portlet.onStateChange"',
					done => {
						const handle = hubA.addEventListener(eventType, onStateChange);
						const renderState = new RenderState(pageState[portletA].state);

						setTimeout(
							() => {
								expect(onStateChange).toHaveBeenCalled();
								expect(onStateChange.mock.calls[0][0]).toEqual(eventType);

								hubA.removeEventListener(handle);

								done();
							},
							200
						);
					}
				);

				it(
					'is not passed a RenderData object',
					done => {
						const handle = hubA.addEventListener(eventType, onStateChange);

						setTimeout(
							() => {

								expect(onStateChange).toHaveBeenCalled();

								expect(onStateChange.mock.calls[0]).toHaveLength(2);

								expect(returnedRenderData).not.toBeDefined();

								hubA.removeEventListener(handle);

								done();
							}
							,
							200
						);
					}
				);

				it(
					'is passed a RenderState parameter that has 3 properties',
					done => {
						const handle = hubA.addEventListener(eventType, onStateChange);

						const originalState = pageState[portletA].state;

						const originalKeys = Object.keys(originalState);

						setTimeout(
							() => {
								expect(onStateChange).toHaveBeenCalled();

								const renderState = onStateChange.mock.calls[0][1];

								const keys = Object.keys(renderState);

								expect(keys).toHaveLength(3);
								expect(returnedRenderState).toEqual(renderState);

								hubA.removeEventListener(handle);

								done();
							}
						);
					}
				);

				it(
					'is passed a RenderState object with a "parameters" property',
					done => {
						const handle = hubA.addEventListener(eventType, onStateChange);

						setTimeout(
							() => {
								expect(onStateChange).toHaveBeenCalled();

								const renderState = onStateChange.mock.calls[0][1];

								expect(renderState.parameters).not.toBeUndefined();
								expect(returnedRenderState.parameters).not.toBeUndefined();
								expect(returnedRenderState.parameters).toEqual(renderState.parameters);

								hubA.removeEventListener(handle);

								done();
							}
						);
					}
				);

				it(
					'is passed a RenderState object with a "portletMode" property',
					done => {
						const handle = hubA.addEventListener(eventType, onStateChange);

						setTimeout(
							() => {
								expect(onStateChange).toHaveBeenCalled();

								const renderState = onStateChange.mock.calls[0][1];

								expect(returnedRenderState).not.toBeUndefined();
								expect(returnedRenderState.portletMode).not.toBeUndefined();
								expect(returnedRenderState.portletMode).toEqual(renderState.portletMode);

								hubA.removeEventListener(handle);

								done();
							}
						);
					}
				);

				it(
					'is passed a RenderState object with a "windowState" property',
					done => {
						const handle = hubA.addEventListener(eventType, onStateChange);

						setTimeout(
							() => {
								expect(onStateChange).toHaveBeenCalled();

								const renderState = onStateChange.mock.calls[0][1];

								expect(renderState.windowState).not.toBeUndefined();
								expect(returnedRenderState).not.toBeUndefined();
								expect(returnedRenderState.windowState).toEqual(renderState.windowState);

								hubA.removeEventListener(handle);

								done();
							}
						);
					}
				);

				it(
					'its RenderState "parameters" property is an object',
					done => {
						expect.assertions(3);

						const handle = hubA.addEventListener(eventType, onStateChange);

						setTimeout(
							() => {
								expect(onStateChange).toHaveBeenCalled();

								const renderState = onStateChange.mock.calls[0][1];

								expect(renderState.parameters).not.toBeUndefined();
								expect(typeof renderState.parameters).toEqual('object');

								hubA.removeEventListener(handle);

								done();
							}
						);
					}
				);

				it(
					'its RenderState has 3 parameters',
					done => {
						expect.assertions(2);

						const handle = hubA.addEventListener(eventType, onStateChange);

						const parameters = pageState[portletA].state.parameters;

						const paramCount = Object.keys(parameters).length;

						setTimeout(
							() => {
								expect(onStateChange).toHaveBeenCalled();

								const returnParameters = returnedRenderState.parameters;

								const returnParamCount = Object.keys(returnParameters).length;

								expect(returnParamCount).toEqual(paramCount);

								hubA.removeEventListener(handle);

								done();
							}
						);
					}
				);

				it(
					'its RenderState "windowState" property is a string',
					done => {
						expect.assertions(6);

						const handle = hubA.addEventListener(eventType, onStateChange);

						setTimeout(
							() => {
								expect(onStateChange).toHaveBeenCalled();

								const renderState = onStateChange.mock.calls[0][1];

								expect(returnedRenderState.windowState).not.toBeUndefined();
								expect(typeof returnedRenderState.windowState).toEqual('string');
								expect(renderState.windowState).not.toBeUndefined();
								expect(typeof renderState.windowState).toEqual('string');
								expect(renderState.windowState).toEqual(returnedRenderState.windowState);

								hubA.removeEventListener(handle);

								done();
							}
						);
					}
				);

				it(
					'its RenderState "portletMode" property is a string',
					done => {
						expect.assertions(6);

						const handle = hubA.addEventListener(eventType, onStateChange);

						setTimeout(
							() => {
								expect(onStateChange).toHaveBeenCalled();

								const renderState = onStateChange.mock.calls[0][1];

								expect(returnedRenderState.portletMode).not.toBeUndefined();
								expect(typeof returnedRenderState.portletMode).toEqual('string');
								expect(renderState.portletMode).not.toBeUndefined();
								expect(typeof renderState.portletMode).toEqual('string');
								expect(renderState.portletMode).toEqual(returnedRenderState.portletMode);

								hubA.removeEventListener(handle);

								done();
							}
						);
					}
				);

				it(
					`its RenderState has windowState=${pageState[portletA].state.windowState}`,
					done => {
						expect.assertions(4);

						const handle = hubA.addEventListener(eventType, onStateChange);

						setTimeout(
							() => {
								expect(onStateChange).toHaveBeenCalled();

								const renderState = onStateChange.mock.calls[0][1];

								expect(returnedRenderState.windowState).toEqual(renderState.windowState);
								expect(returnedRenderState.windowState).toEqual(pageState[portletA].state.windowState);
								expect(renderState.windowState).toEqual(pageState[portletA].state.windowState);

								hubA.removeEventListener(handle);

								done();
							}
						);
					}
				);

				it(
					`its RenderState has portletMode=${pageState[portletA].state.portletMode}`,
					done => {
						expect.assertions(4);

						const handle = hubA.addEventListener(eventType, onStateChange);

						setTimeout(
							() => {
								expect(onStateChange).toHaveBeenCalled();

								const renderState = onStateChange.mock.calls[0][1];

								expect(returnedRenderState.portletMode).toEqual(renderState.portletMode);
								expect(returnedRenderState.portletMode).toEqual(pageState[portletA].state.portletMode);
								expect(renderState.portletMode).toEqual(pageState[portletA].state.portletMode);

								hubA.removeEventListener(handle);

								done();
							}
						);
					}
				);

				it(
					'its RenderState parameter is not identical to the test state object"',
					done => {
						expect.assertions(4);

						const handle = hubA.addEventListener(eventType, onStateChange);

						setTimeout(
							() => {
								expect(onStateChange).toHaveBeenCalled();

								const renderState = onStateChange.mock.calls[0][1];

								expect(renderState).not.toBe(pageState[portletA].state);
								expect(returnedRenderState).not.toBe(pageState[portletA].state);
								expect(returnedRenderState).toEqual(renderState);

								hubA.removeEventListener(handle);

								done();
							}
						);
					}
				);

				it(
					'its RenderState parameter equals the test state object"',
					done => {
						expect.assertions(4);

						const handle = hubA.addEventListener(eventType, onStateChange);
						const testState = hubA.newState(pageState[portletA].state);

						setTimeout(
							() => {
								expect(onStateChange).toHaveBeenCalled();

								const renderState = onStateChange.mock.calls[0][1];

								expect(returnedRenderState).toEqual(testState);
								expect(renderState).toEqual(testState);
								expect(renderState).toEqual(returnedRenderState);

								hubA.removeEventListener(handle);

								done();
							}
						);
					}
				);
			}
		);

		describe(
			'onStateChange with render data',
			() => {
				const eventType = 'portlet.onStateChange';
				const ids = portlet.test.getIds();
				const pageState = portlet.test.getInitData().portlets;
				const portletA = ids[0];
				const portletB = ids[3];

				let complete = false;
				let handle = null;
				let hubA;
				let hubB;
				let returnedRenderData;
				let returnedRenderState;
				let returnType;

				const onStateChange = jest.fn(
					(eventType, renderState, renderData) => {
						complete = true;
						returnType = eventType;
						returnedRenderState = renderState;
						returnedRenderData = renderData;
					}
				);

				beforeEach(
					() => {
						return Promise.all(
							[
								register(portletA),
								register(portletB)
							]
						).then(
							values => {
								hubA = values[0];
								hubB = values[1];
								onStateChange.mockClear();
								complete = false;
								returnType = returnedRenderState = returnedRenderData = undefined;

							}
						);
					}
				);

				afterEach(
					() => {
						if (handle !== null) {
							hubB.removeEventListener(handle);
							handle = null;
						}
					}
				);

				it(
					'does not call the portlet.onStateChange listener during the addEventListener call',
					() => {
						handle = hubB.addEventListener(eventType, onStateChange);

						expect(complete).toBeFalsy();
						expect(onStateChange).not.toHaveBeenCalled();
					}
				);

				it(
					'is called asynchronously after an onStateChange handler is registered',
					done => {
						handle = hubB.addEventListener(eventType, onStateChange);

						setTimeout(
							() => {
								expect(onStateChange).toHaveBeenCalled();
								expect(complete).toBeTruthy();

								done();
							},
							100
						);
					}
				);

				it(
					'is passed a type parameter with value "portlet.onStateChange"',
					done => {
						handle = hubB.addEventListener(eventType, onStateChange);

						setTimeout(
							() => {
								expect(onStateChange).toHaveBeenCalled();
								expect(complete).toBeTruthy();
								expect(returnType).toEqual(eventType);

								done();
							}
						);
					}
				);

				it(
					'is passed a RenderState parameter that is an object',
					done => {
						handle = hubB.addEventListener(eventType, onStateChange);

						setTimeout(
							() => {
								expect(onStateChange).toHaveBeenCalled();

								const renderState = onStateChange.mock.calls[0][1];

								expect(typeof renderState).toEqual('object');
								expect(renderState).toEqual(returnedRenderState);
								expect(typeof renderState).toEqual(typeof returnedRenderState);

								done();
							}
						);
					}
				);

				it(
					'is passed a RenderState parameter that has 3 properties',
					done => {
						handle = hubB.addEventListener(eventType, onStateChange);

						setTimeout(
							() => {
								expect(onStateChange).toHaveBeenCalled();

								const renderState = onStateChange.mock.calls[0][1];

								const count = Object.keys(returnedRenderState).length;

								expect(count).toEqual(3);
								expect(Object.keys(renderState).length).toEqual(count);

								expect(renderState).toEqual(returnedRenderState);

								done();
							}
						);
					}
				);

				it(
					'is passed a RenderState object with a "parameters" property',
					done => {
						handle = hubB.addEventListener(eventType, onStateChange);

						setTimeout(
							() => {
								expect(onStateChange).toHaveBeenCalled();

								const renderState = onStateChange.mock.calls[0][1];

								expect(renderState.parameters).not.toBeUndefined();
								expect(returnedRenderState.parameters).not.toBeUndefined();
								expect(renderState.parameters).toEqual(renderState.parameters);

								done();
							}
						);
					}
				);

				it(
					'is passed a RenderState object with a "portletMode" property',
					done => {
						handle = hubB.addEventListener(eventType, onStateChange);

						setTimeout(
							() => {
								expect(onStateChange).toHaveBeenCalled();

								const renderState = onStateChange.mock.calls[0][1];

								expect(renderState.portletMode).not.toBeUndefined();
								expect(returnedRenderState.portletMode).not.toBeUndefined();
								expect(renderState.portletMode).toEqual(renderState.portletMode);

								done();
							}
						);
					}
				);

				it(
					'is passed a RenderState object with a "windowState" property',
					done => {
						handle = hubB.addEventListener(eventType, onStateChange);

						setTimeout(
							() => {
								expect(onStateChange).toHaveBeenCalled();

								const renderState = onStateChange.mock.calls[0][1];

								expect(renderState.windowState).not.toBeUndefined();
								expect(returnedRenderState.windowState).not.toBeUndefined();
								expect(renderState.windowState).toEqual(renderState.windowState);

								done();
							}
						);
					}
				);

				it(
					'its RenderState parameter is not identical to the test state object"',
					done => {
						handle = hubB.addEventListener(eventType, onStateChange);

						setTimeout(
							() => {
								expect(onStateChange).toHaveBeenCalled();

								const renderState = onStateChange.mock.calls[0][1];

								expect(renderState).not.toBe(pageState[portletB].state);
								expect(returnedRenderState).not.toBe(pageState[portletB].state);

								done();
							}
						);
					}
				);

				it(
					'its RenderState parameter equals the test state object"',
					done => {
						handle = hubB.addEventListener(eventType, onStateChange);

						const testState = hubA.newState(pageState[portletB].state);

						setTimeout(
							() => {
								expect(onStateChange).toHaveBeenCalled();

								const renderState = onStateChange.mock.calls[0][1];

								expect(renderState).toEqual(testState);
								expect(returnedRenderState).toEqual(testState);

								done();
							}
						);
					}
				);

				// portletB (which is actually PortletD in the ) is set up to have render data

				it(
					'is passed a RenderData object',
					done => {
						handle = hubB.addEventListener(eventType, onStateChange);

						setTimeout(
							() => {
								expect(onStateChange).toHaveBeenCalled();

								const renderData = onStateChange.mock.calls[0][2];

								expect(renderData).not.toBeUndefined();
								expect(typeof returnedRenderData).toEqual('object');

								done();
							}
						);
					}
				);

				it(
					'is passed a RenderData parameter that has 2 properties',
					done => {
						handle = hubB.addEventListener(eventType, onStateChange);

						setTimeout(
							() => {
								expect(onStateChange).toHaveBeenCalled();

								const renderData = onStateChange.mock.calls[0][2];

								expect(renderData).toEqual(returnedRenderData);

								const count = Object.keys(returnedRenderData).length;

								expect(count).toEqual(2);

								done();
							}
						);
					}
				);

				it(
					'is passed a RenderData object with a "content" property',
					done => {
						handle = hubB.addEventListener(eventType, onStateChange);

						setTimeout(
							() => {
								expect(onStateChange).toHaveBeenCalled();

								const renderData = onStateChange.mock.calls[0][2];

								expect(renderData).toEqual(returnedRenderData);
								expect(renderData.content).not.toBeUndefined();

								done();
							}
						);
					}
				);

				it(
					'is passed a RenderData object with a "mimeType" property',
					done => {
						handle = hubB.addEventListener(eventType, onStateChange);

						setTimeout(
							() => {
								expect(onStateChange).toHaveBeenCalled();

								const renderData = onStateChange.mock.calls[0][2];

								expect(renderData).toEqual(returnedRenderData);
								expect(renderData.mimeType).not.toBeUndefined();

								done();
							}
						);
					}
				);

				it(
					'is passed a RenderData object with a "content" property of type string',
					done => {
						handle = hubB.addEventListener(eventType, onStateChange);

						setTimeout(
							() => {
								expect(onStateChange).toHaveBeenCalled();

								const renderData = onStateChange.mock.calls[0][2];

								expect(renderData).toEqual(returnedRenderData);
								expect(renderData.content).not.toBeUndefined();
								expect(typeof renderData.content).toEqual('string');

								done();
							}
						);
					}
				);

				it(
					'is passed a RenderData object with a "mimeType" property of type string',
					done => {
						handle = hubB.addEventListener(eventType, onStateChange);

						setTimeout(
							() => {
								expect(onStateChange).toHaveBeenCalled();

								const renderData = onStateChange.mock.calls[0][2];

								expect(renderData).toEqual(returnedRenderData);
								expect(renderData.mimeType).not.toBeUndefined();
								expect(typeof renderData.mimeType).toEqual('string');

								done();
							}
						);
					}
				);

				it(
					'its RenderData parameter equals the test render data object"',
					done => {
						handle = hubB.addEventListener(eventType, onStateChange);

						setTimeout(
							() => {
								expect(onStateChange).toHaveBeenCalled();

								const renderData = onStateChange.mock.calls[0][2];

								expect(renderData).toEqual(pageState[portletB].renderData);
								expect(returnedRenderData).toEqual(pageState[portletB].renderData);

								done();
							}
						);
					}
				);
			}
		);

		describe(
			'allows the portlet client to dispatch events',
			() => {
				const ids = portlet.test.getIds();
				const portletA = ids[0];
				const portletB = ids[1];
				const portletC = ids[2];
				const portletD = ids[3];

				const pageState = portlet.test.getInitData();

				let hubA;
				let hubB;
				let hubC;
				let hubD;

				beforeEach(
					() => {
						return Promise.all(
							[
								register(portletA),
								register(portletB),
								register(portletC),
								register(portletD)
							]
						).then(
							values => {
								hubA = values[0];
								hubB = values[1];
								hubC = values[2];
								hubD = values[2];
							}
						);
					}
				);

				describe(
					'the portlet hub is initialized for the tests',
					() => {

						it(
							'initializes a portlet hub instance for portlet A',
							() => {
								expect(hubA).not.toBeUndefined();
							}
						);

						it(
							'initializes a portlet hub instance for portlet B',
							() => {
								expect(hubB).not.toBeUndefined();
							}
						);

						it(
							'initializes a portlet hub instance for portlet C',
							() => {
								expect(hubC).not.toBeUndefined();
							}
						);

						it(
							'initializes a portlet hub instance for portlet D',
							() => {
								expect(hubD).not.toBeUndefined();
							}
						);
					}
				);

				describe(
					'the portlet hub dispatchClientEvent function',
					() => {

						it(
							'is present in the register return object and is a function',
							() => {
								expect(typeof hubA.dispatchClientEvent).toEqual('function');
							}
						);

						it(
							'throws a TypeError if no argument is provided',
							() => {
								const testFn = () => {
									hubA.dispatchClientEvent();
								};

								expect(testFn).toThrow(TypeError);
							}
						);

						it(
							'throws a TypeError if too many (>2) arguments are provided',
							() => {
								const testFn = () => {
									hubA.dispatchClientEvent('param1', 'param2', 'param3');
								};

								expect(testFn).toThrow(TypeError);
							}
						);

						it(
							'throws a TypeError if the type argument is not a string',
							() => {
								const testFn = () => {
									hubA.dispatchClientEvent(89, 'payload');
								};

								expect(testFn).toThrow(TypeError);
							}
						);

						it(
							'throws a TypeError if the type is null',
							() => {
								const testFn = () => {
									hubA.dispatchClientEvent(null, 'payload');
								};

								expect(testFn).toThrow(TypeError);
							}
						);

						it(
							'does not throw an Exception if the payload is null',
							() => {
								const testFn = () => {
									hubA.dispatchClientEvent('event', null);
								};

								expect(testFn).not.toThrow();
							}
						);

						it(
							'throws a TypeError if the type begins with "portlet."',
							() => {
								const testFn = () => {
									hubA.dispatchClientEvent('portlet.invalidType', 'payload');
								};

								expect(testFn).toThrow(TypeError);
							}
						);

						it(
							'throws a TypeError if the type matches a system event type',
							() => {
								const testFn = () => {
									hubA.dispatchClientEvent('portlet.onStateChange', 'payload');
								};

								expect(testFn).toThrow(TypeError);
							}
						);

						it(
							'does not throw an exception if both parameters are valid',
							() => {
								const testFn = () => {
									hubA.dispatchClientEvent('event', 'payload');
								};

								expect(testFn).not.toThrow();
							}
						);

						it(
							'returns count of 0 when no listener for event is registered',
							() => {
								const count = hubA.dispatchClientEvent('event', 'payload');

								expect(count).toEqual(0);
							}
						);

						it(
							'listener is called & count=1 when 1 listener for event is registered',
							() => {
								const listener = jest.fn();
								const payload = 'payload';
								const type = 'event';

								const handle = hubA.addEventListener(type, listener);

								const count = hubA.dispatchClientEvent(type, payload);

								expect(count).toEqual(1);
								expect(listener).toHaveBeenCalled();
								expect(listener).toHaveBeenCalledWith(type, payload);

								hubA.removeEventListener(handle);
							}
						);

						it(
							'causes listener to be called with expected type & string payload when event is dispatched',
							() => {
								const listener = jest.fn();
								const payload = 'payload';
								const type = 'event';

								const handle = hubA.addEventListener(type, listener);

								const count = hubA.dispatchClientEvent(type, payload);

								expect(count).toEqual(1);
								expect(listener).toBeCalledWith(type, payload);

								hubA.removeEventListener(handle);
							}
						);

						it(
							'when type does not match, no event is fired',
							() => {
								const listener = jest.fn();
								const payload = 'payload';
								const type = 'event';

								const handle = hubB.addEventListener('differentEvent', listener);

								const count = hubA.dispatchClientEvent(type, payload);

								expect(count).toEqual(0);
								expect(listener).not.toHaveBeenCalled();
							}
						);

						it(
							'payload=null is transported correctly',
							() => {
								const listener = jest.fn();
								const payload = null;
								const type = 'event';

								const handle = hubA.addEventListener(type, listener);

								const count = hubA.dispatchClientEvent(type, payload);

								expect(count).toEqual(1);
								expect(listener).toHaveBeenCalledWith(type, payload);

								hubA.removeEventListener(handle);
							}
						);

						it(
							'payload of type object is transported correctly',
							() => {
								const listener = jest.fn();

								const payload = {
									addr: 'Stgt',
									name: 'Scott'
								};

								const type = 'event';

								const handle = hubA.addEventListener(type, listener);

								const count = hubA.dispatchClientEvent(type, payload);

								expect(count).toEqual(1);

								expect(listener).toHaveBeenCalledWith(type, payload);

								hubA.removeEventListener(handle);
							}
						);

						it(
							'listener of different portlet is correctly called when event is dispatched',
							() => {
								const listener = jest.fn();
								const payload = 'payload';
								const type = 'event';

								const handle = hubB.addEventListener(type, listener);

								const count = hubA.dispatchClientEvent(type, payload);

								expect(count).toEqual(1);
								expect(listener).toHaveBeenCalledWith(type, payload);

								hubB.removeEventListener(handle);
							}
						);

						it(
							'matches event types by regex',
							() => {
								const listener = jest.fn();
								const payload = 'payload';
								const type = 'liferay.event';

								const handle = hubB.addEventListener('liferay\..*', listener);

								const count = hubA.dispatchClientEvent(type, payload);

								expect(count).toEqual(1);
								expect(listener).toHaveBeenCalledWith(type, payload);
							}
						);

						it(
							'when regex does not match, no event is fired',
							() => {
								const listener = jest.fn();
								const payload = 'payload';
								const type = 'event';

								const handle = hubB.addEventListener('liferay\..*', listener);

								const count = hubA.dispatchClientEvent(type, payload);

								expect(count).toEqual(0);
								expect(listener).not.toHaveBeenCalled();
							}
						);

						it(
							'2 listeners of different portlet are correctly called when event is dispatched',
							() => {
								const listenerB = jest.fn();
								const listenerD = jest.fn();
								const payload = 'payload';
								const type = 'event';

								const handleB = hubB.addEventListener(type, listenerB);

								const handleD = hubD.addEventListener(type, listenerD);

								const count = hubA.dispatchClientEvent(type, payload);

								expect(count).toEqual(2);

								expect(listenerB).toHaveBeenCalledWith(type, payload);
								expect(listenerD).toHaveBeenCalledWith(type, payload);

								hubB.removeEventListener(handleB);
								hubD.removeEventListener(handleD);
							}
						);
					}
				);
			}
		);

		describe(
			'the portlet hub createResourceUrl function',
			() => {
				const ids = portlet.test.getIds();
				const portletA = ids[0];
				const portletB = ids[1];

				const onStateChange = jest.fn();
				const pageState = portlet.test.getInitData();
				let hubA;
				let hubB;
				let listenerA;

				beforeEach(
					() => {
						return Promise.all(
							[
								register(portletA),
								register(portletB)
							]
						).then(
							values => {
								hubA = values[0];

								listenerA = hubA.addEventListener('portlet.onStateChange', onStateChange);

								hubB = values[1];
							}
						);
					}
				);

				afterEach(
					() => {
						hubA.removeEventListener(listenerA);
						hubA = null;
						hubB = null;
					}
				);

				it(
					'is present in the register return object and is a function',
					() => {
						expect(typeof hubA.createResourceUrl).toEqual('function');
					}
				);

				it(
					'throws a TypeError if too many (>3) arguments are provided',
					() => {
						const testFn = () => {
							hubA.createResourceUrl(null, 'param1', 'param2', 'param3');
						};

						expect(testFn).toThrow('Too many arguments. 3 arguments are allowed.');
					}
				);

				it(
					'throws a TypeError if resource parameters is invalid',
					() => {
						const parameters = {
							param1: 'paramValue1'
						};

						const testFn = () => {
							hubA.createResourceUrl(parameters, 'cacheLevelPortlet');
						};

						expect(testFn).toThrow(TypeError);
					}
				);

				it(
					'throws a TypeError if the cacheability argument is invalid',
					() => {
						const parameters = {
							param1: ['paramValue1']
						};

						const testFn = () => {
							hubA.createResourceUrl(parameters, 'Invalid');
						};

						expect(testFn).toThrow(TypeError);
					}
				);

				it(
					'throws a TypeError if there are 2 cacheability arguments',
					() => {
						const parameters = {
							param1: ['paramValue1']
						};

						const testFn = () => {
							hubA.createResourceUrl('cacheLevelPage', 'cacheLevelFull');
						};

						expect(testFn).toThrow(TypeError);
					}
				);

				it(
					'throws a TypeError if there are 2 res parameters arguments',
					() => {
						const parameters = {
							param1: ['paramValue1']
						};

						const testFn = () => {
							return hubA.createResourceUrl(parameters, parameters);
						};

						expect(testFn).toThrow(TypeError);
					}
				);

				it(
					'does not throw if both arguments are valid',
					done => {
						const parameters = {
							param1: ['paramValue1']
						};

						return hubA.createResourceUrl(parameters, 'cacheLevelPage')
							.then(
								updatedIds => {
									done();
								}
							);

					}
				);

				it(
					'returns a string if both arguments are valid',
					() => {
						const parameters = {
							param1: ['paramValue1']
						};

						return hubA.createResourceUrl(parameters, 'cacheLevelFull').then(
							url => {
								expect(typeof url).toEqual('string');
							}
						);
					}
				);

				it(
					'Throws an exception if cacheability is specified first',
					() => {
						const parameters = {
							param1: ['paramValue1']
						};

						const testFn = () => {
							return hubA.createResourceUrl('cacheLevelPage', parameters);
						};

						expect(testFn).toThrow(TypeError);
					}
				);

				it(
					'returns a string if only cacheability present',
					() => {
						const parameters = {
							param1: ['paramValue1']
						};

						return hubA.createResourceUrl(null, 'cacheLevelPortlet').then(
							url => {
								expect(typeof url).toEqual('string');
							}
						);
					}
				);

				it(
					'returns a string if only resource parameters present',
					() => {
						const parameters = {
							param1: ['paramValue1'],
							param2: ['paramValue2']
						};

						return hubA.createResourceUrl(parameters).then(
							url => {
								expect(typeof url).toEqual('string');
							}
						);
					}
				);

				it(
					'returns a string if no parameters present',
					() => {
						const parameters = {
							param1: ['paramValue1'],
							param2: ['paramValue2']
						};

						return hubA.createResourceUrl().then(
							url => {
								expect(typeof url).toEqual('string');
							}
						);
					}
				);

				it(
					'returns a URL indicating the initiating portlet A',
					() => {
						const parameters = {
							param1: ['paramValue1'],
							param2: ['paramValue2']
						};

						return hubA.createResourceUrl(parameters, 'cacheLevelPage').then(
							url => {
								expect(typeof url).toEqual('string');
							}
						);
					}
				);

				it(
					'returns a resource URL',
					() => {
						const cache = 'cacheLevelPage';
						const parameters = {
							param1: ['paramValue1'],
							param2: ['paramValue2']
						};

						return hubB.createResourceUrl(parameters, cache).then(
							url => {
								expect(portlet.test.resource.isResourceUrl(url)).toBeTruthy();
							}
						);
					}
				);

				it(
					'returns a URL with cacheability set to "cacheLevelPage"',
					() => {
						const cache = 'cacheLevelPage';
						const parameters = {
							param1: ['paramValue1'],
							param2: ['paramValue2']
						};

						let str;
						let url;

						return hubB.createResourceUrl(parameters, cache).then(
							url => {
								const str = portlet.test.resource.getCacheability(url);

								expect(str).toEqual(cache);
							}
						);
					}
				);
			}
		);

		describe(
			'allows the portlet client test for a blocking operation',
			() => {
				const ids = portlet.test.getIds();
				const portletA = ids[0];
				const portletB = ids[1];
				const portletC = ids[2];
				const portletD = ids[3];

				const pageState = portlet.test.getInitData();

				const listenerA = jest.fn();
				const listenerB = jest.fn();
				const listenerC = jest.fn();
				const listenerD = jest.fn();

				let handleA;
				let handleB;
				let handleC;
				let handleD;
				let hubA;
				let hubB;
				let hubC;
				let hubD;

				beforeEach(
					() => {
						return Promise.all(
							[
								register(portletA),
								register(portletB),
								register(portletC),
								register(portletD)
							]
						).then(
							values => {
								hubA = values[0];

								handleA = hubA.addEventListener(
									'portlet.onStateChange',
									listenerA
								);

								hubB = values[1];

								handleB = hubB.addEventListener(
									'portlet.onStateChange',
									listenerB
								);

								hubC = values[2];

								handleC = hubC.addEventListener(
									'portlet.onStateChange',
									listenerC
								);

								hubD = values[3];

								handleD = hubD.addEventListener(
									'portlet.onStateChange',
									listenerD
								);
							}
						);
					}
				);

				afterEach(
					() => {
						listenerA.mockReset();
						hubA.removeEventListener(handleA);

						listenerB.mockReset();
						hubB.removeEventListener(handleB);

						listenerC.mockReset();
						hubC.removeEventListener(handleC);

						listenerD.mockReset();
						hubD.removeEventListener(handleD);
					}
				);

				it(
					'initializes a portlet hub instance for portlet A',
					() => {
						expect(hubA).not.toBeUndefined();
					}
				);

				it(
					'initializes a portlet hub instance for portlet B',
					() => {
						expect(hubB).not.toBeUndefined();
					}
				);

				it(
					'initializes a portlet hub instance for portlet C',
					() => {
						expect(hubC).not.toBeUndefined();
					}
				);

				it(
					'initializes a portlet hub instance for portlet D',
					() => {
						expect(hubD).not.toBeUndefined();
					}
				);

				it(
					'is present in the register return object and is a function',
					() => {
						expect(typeof hubA.isInProgress).toEqual('function');
					}
				);

				it(
					'returns a boolean value',
					() => {
						const returnValue = hubA.isInProgress();

						expect(typeof returnValue).toEqual('boolean');
					}
				);

				it(
					'returns false if a blocking operation is not in progress',
					() => {
						const returnValue = hubA.isInProgress();

						expect(returnValue).toBe(false);
					}
				);

				it(
					'returns false through a different hub if a blocking operation is not in progress',
					() => {
						const returnValue = hubD.isInProgress();

						expect(returnValue).toBe(false);
					}
				);

				it(
					'returns true when a partial action has been started but setPageState has not been called',
					done => {
						const parameters = {};

						return hubB.startPartialAction(parameters).then(
							partialActionInitObject => {
								expect(hubB.isInProgress()).toBeTruthy();

								partialActionInitObject.setPageState(
									JSON.stringify({})
								);

								setTimeout(
									() => {
										expect(listenerB).toHaveBeenCalled();

										done();
									}
								);
							}
						);
					}
				);

				it(
					'returns true through a different hub when a partial action has been started but setPageState has not been called',
					done => {
						const parameters = {};

						return hubB.startPartialAction(parameters).then(
							partialActionInitObject => {
								expect(hubB.isInProgress()).toBeTruthy();
								expect(hubD.isInProgress()).toBeTruthy();

								partialActionInitObject.setPageState(JSON.stringify({}));

								setTimeout(
									() => {
										expect(listenerB).toHaveBeenCalled();

										done();
									}
								);
							}
						);
					}
				);

				it(
					'returns true when setPageState has been called but the updates have not been dispatched',
					done => {
						const parameters = {};

						hubB.startPartialAction(parameters).then(
							partialActionInitObject => {
								partialActionInitObject.setPageState(JSON.stringify({}));

								expect(hubB.isInProgress()).toBeTruthy();

								setTimeout(
									() => {
										expect(listenerB).toHaveBeenCalled();

										done();
									}
								);
							}
						);
					}
				);

				it(
					'returns false after setPageState updates have been dispatched',
					done => {
						const parameters = {};

						return hubB.startPartialAction(parameters).then(
							partialActionInitObject => {
								partialActionInitObject.setPageState(JSON.stringify({}));
								expect(hubD.isInProgress()).toBeTruthy();

								setTimeout(
									() => {
										expect(listenerD).toHaveBeenCalled();
										expect(hubD.isInProgress()).toBeFalsy();

										done();
									}
								);
							}
						);
					}
				);

				it(
					'returns false through a different hub after setPageState updates have been dispatched',
					done => {
						const parameters = {ap1: ['actionVal']};

						hubB.startPartialAction(parameters).then(
							partialActionInitObject => {
								partialActionInitObject.setPageState(JSON.stringify({}));

								expect(hubB.isInProgress()).toBeTruthy();

								setTimeout(
									() => {
										expect(listenerC).toHaveBeenCalled();
										expect(hubB.isInProgress()).toBeFalsy();

										done();
									}
								);
							}
						);
					}
				);

				it(
					'returns true when action has been called but the updates have not been dispatched',
					done => {
						fetchMock(
							[
								portletA,
								portletB,
								portletC,
								portletD
							]
						);

						const element = document.createElement('form');
						const parameters = {};

						const testFn = () => hubB.action(parameters, element);

						expect(testFn).not.toThrow();
						expect(hubD.isInProgress()).toBeTruthy();

						global.fetch.mockRestore();

						setTimeout(
							() => {
								done();
							}
						);
					}
				);

				it(
					'returns true through a different hub when action has been called but the updates have not been dispatched',
					done => {
						fetchMock(
							[
								portletA,
								portletB,
								portletC,
								portletD
							]
						);

						const element = document.createElement('form');
						const parameters = {};

						const testFn = () => hubB.action(parameters, element);

						expect(testFn).not.toThrow();
						expect(hubD.isInProgress()).toBeTruthy();

						global.fetch.mockRestore();

						setTimeout(
							() => {
								expect(listenerB).toHaveBeenCalled();

								done();
							}
						);
					}
				);

				it(
					'returns false after action updates have been dispatched',
					done => {
						fetchMock(
							[
								portletA,
								portletB,
								portletC,
								portletD
							]
						);

						const element = document.createElement('form');
						const parameters = {};

						const testFn = () => hubB.action(parameters, element);

						expect(testFn).not.toThrow();

						global.fetch.mockRestore();

						setTimeout(
							() => {
								expect(listenerB).toHaveBeenCalled();
								expect(hubC.isInProgress()).toBeFalsy();

								done();
							}
						);
					}
				);

				it(
					'returns true when setRenderState has been called but the updates have not been dispatched',
					done => {
						const parameters = {};
						const state = pageState.portlets.PortletC.state;

						state.parameters.param1 = ['paramValue1'];

						const testFn = () => hubC.setRenderState(state);

						expect(testFn).not.toThrow();
						expect(hubD.isInProgress()).toBeTruthy();

						setTimeout(
							() => {
								expect(listenerC).toHaveBeenCalled();

								done();
							}
						);
					}
				);

				it(
					'returns true through a different hub when setRenderState has been called but the updates have not been dispatched',
					done => {
						const parameters = {};
						const state = pageState.portlets.PortletC.state;

						state.parameters.param1 = ['paramValue1'];

						const testFn = () => hubC.setRenderState(state);

						expect(testFn).not.toThrow();
						expect(hubD.isInProgress()).toBeTruthy();

						setTimeout(
							() => {
								expect(listenerC).toHaveBeenCalled();

								done();
							}
						);
					}
				);

				it(
					'returns false after setRenderState updates have been dispatched',
					done => {
						const parameters = {};
						const state = pageState.portlets.PortletC.state;

						state.parameters.param1 = ['paramValue1'];

						const testFn = () => hubC.setRenderState(state);

						expect(testFn).not.toThrow();

						setTimeout(
							() => {
								expect(listenerC).toHaveBeenCalled();

								expect(hubA.isInProgress()).toBeFalsy();
								expect(hubB.isInProgress()).toBeFalsy();
								expect(hubC.isInProgress()).toBeFalsy();
								expect(hubD.isInProgress()).toBeFalsy();

								done();
							}
						);
					}
				);
			}
		);
	}
);