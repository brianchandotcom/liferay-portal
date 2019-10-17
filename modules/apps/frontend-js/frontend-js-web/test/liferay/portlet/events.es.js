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
	it('adds client event listener', () => {
		const stub = jest.fn();

		return register('PortletA').then(hub => {
			const handle = hub.addEventListener('clientEvent', stub);

			expect(handle).not.toBeUndefined();
			hub.removeEventListener(handle);
		});
	});

	it('returns number of listeners', () => {
		const stub = jest.fn();

		return register('PortletA').then(hub => {
			expect(stub.mock.calls.length).toBe(0);

			const handle = hub.addEventListener('clientEvent', stub);

			const total = hub.dispatchClientEvent('clientEvent', {
				name: 'PortletA'
			});

			expect(total).toBe(1);
			hub.removeEventListener(handle);
		});
	});

	it('throws error if addEventListener is called with invalid args', () => {
		return register('PortletA').then(hub => {
			expect(() => {
				hub.addEventListener(1, 2, 3);
			}).toThrow('Too many arguments passed to addEventListener');

			expect(() => {
				hub.addEventListener(1);
			}).toThrow('Invalid arguments passed to addEventListener');

			expect(() => {
				hub.addEventListener('string', 1);
			}).toThrow('Invalid arguments passed to addEventListener');
		});
	});

	it('does not call listener if it is removed', () => {
		const stub = jest.fn();

		return register('PortletA').then(hub => {
			const handle = hub.addEventListener('toBeRemoved', stub);

			hub.removeEventListener(handle);

			const total = hub.dispatchClientEvent('toBeRemoved', {
				name: 'PortletA'
			});

			expect(stub.mock.calls.length).toBe(0);
			expect(total).toBe(0);
		});
	});

	it('throws error if dispatchClientEvent is called with invalid args', () => {
		return register('PortletA').then(hub => {
			expect(() => {
				hub.dispatchClientEvent(1, 2, 3);
			}).toThrowErrorMatchingSnapshot();

			expect(() => {
				hub.dispatchClientEvent(1);
			}).toThrowErrorMatchingSnapshot();
		});
	});

	it('throws error when attempting to dispatch event with protected name', () => {
		return register('PortletA').then(hub => {
			expect(() => {
				hub.dispatchClientEvent('portlet.clientEvent');
			}).toThrowErrorMatchingSnapshot();
		});
	});

	it('throws error if removeEventListener is called with invalid args', () => {
		return register('PortletA').then(hub => {
			expect(() => {
				hub.removeEventListener(1, 2);
			}).toThrow('Too many arguments passed to removeEventListener');

			expect(() => {
				hub.removeEventListener();
			}).toThrow('The event handle provided is undefined');

			expect(() => {
				hub.removeEventListener({});
			}).toThrow(
				"The event listener handle doesn't match any listeners."
			);
		});
	});

	it('calls listener with matching wildcard event types', () => {
		const stub = jest.fn();

		return register('PortletA').then(hub => {
			const handle = hub.addEventListener('awesome\\w+Event$', stub);

			const total = hub.dispatchClientEvent('awesomeClientEvent', {
				name: 'PortletA'
			});

			expect(stub.mock.calls.length).toBe(1);
			expect(total).toBe(1);

			hub.removeEventListener(handle);
		});
	});

	describe('addEventListener', () => {
		const ids = global.portlet.getIds();
		const portletA = ids[0];
		const portletB = ids[3];

		let hubA;

		beforeEach(() => {
			return Promise.all([register(portletA), register(portletB)]).then(
				values => {
					hubA = values[0];
				}
			);
		});

		it('is present in the register return object and is a function', () => {
			expect(typeof hubA.addEventListener).toEqual('function');
		});

		it('throws a TypeError if no argument is provided', () => {
			const testFn = () => {
				hubA.addEventListener();
			};

			expect(testFn).toThrow(TypeError);
		});

		it('throws a TypeError if 1 argument is provided', () => {
			const testFn = () => {
				hubA.addEventListener('someEvent');
			};

			expect(testFn).toThrow(TypeError);
		});

		it('throws a TypeError if too many (>2) arguments are provided', () => {
			const testFn = () => {
				hubA.addEventListener('param1', 'param2', 'param3');
			};

			expect(testFn).toThrow(TypeError);
		});

		it('throws a TypeError if the type argument is not a string', () => {
			const testFn = () => {
				hubA.addEventListener(89, () => {});
			};

			expect(testFn).toThrow(TypeError);
		});

		it('throws a TypeError if the function argument is not a function', () => {
			const testFn = () => {
				hubA.addEventListener('someEvent', 89);
			};

			expect(testFn).toThrow(TypeError);
		});

		it('throws a TypeError if the type is null', () => {
			const testFn = () => {
				hubA.addEventListener(null, () => {});
			};

			expect(testFn).toThrow(TypeError);
		});

		it('throws a TypeError if the function is null', () => {
			const testFn = () => {
				hubA.addEventListener('someEvent', null);
			};

			expect(testFn).toThrow(TypeError);
		});

		it('throws a TypeError if the type begins with "portlet." but is neither "portlet.onStateChange" or "portlet.onError"', () => {
			const testFn = () => {
				hubA.addEventListener('portlet.invalidType', () => {});
			};

			expect(testFn).toThrow(TypeError);
		});

		it('does not throw an exception if both parameters are valid', () => {
			const testFn = () => {
				return hubA.addEventListener('someEvent', () => {});
			};

			expect(testFn).not.toThrow();

			const handle = testFn();

			hubA.removeEventListener(handle);
		});

		it('returns a handle to the event handler (an object) when the parameters are valid', () => {
			const handle = hubA.addEventListener('someEvent', () => {});

			expect(handle).not.toBeUndefined();

			hubA.removeEventListener(handle);
		});

		it('allows a listener for event type "portlet.onStateChange" to be added', () => {
			const testFn = () => {
				return hubA.addEventListener('portlet.onStateChange', () => {});
			};

			// expect(testFn).not.toThrow();

			const handle = testFn();

			expect(handle).not.toBeUndefined();

			hubA.removeEventListener(handle);
		});

		it('allows a listener for event type "portlet.onError" to be added', () => {
			const testFn = () => {
				return hubA.addEventListener('portlet.onError', () => {});
			};

			// expect(testFn).not.toThrow();

			const handle = testFn();

			expect(handle).not.toBeUndefined();

			hubA.removeEventListener(handle);
		});
	});

	describe('removeEventListener', () => {
		const ids = global.portlet.getIds();
		const portletA = ids[0];

		let hubA;

		beforeEach(() => {
			return register(portletA).then(hub => {
				hubA = hub;
			});
		});

		it('is present in the register return object and is a function', () => {
			expect(hubA.removeEventListener).toBeDefined();
			expect(typeof hubA.removeEventListener).toEqual('function');
		});

		it('throws a TypeError if too many (>1) arguments are provided', () => {
			const testFn = () => {
				hubA.removeEventListener('param1', 'param2', 'param3');
			};

			expect(testFn).toThrow(TypeError);
		});

		it('throws a TypeError if the handle is null', () => {
			const testFn = () => {
				hubA.removeEventListener(null);
			};

			expect(testFn).toThrow(TypeError);
		});

		it('throws a TypeError if the handle is undefined', () => {
			const testFn = () => {
				hubA.removeEventListener(undefined);
			};

			expect(testFn).toThrow(TypeError);
		});

		it('throws a TypeError if the handle has an invalid value', () => {
			const testFn = () => {
				hubA.removeEventListener('This is an invalid handle.');
			};

			expect(testFn).toThrow(TypeError);
		});

		it('allows a previously added user event listener to be removed', () => {
			const listener = jest.fn();

			const handle = hubA.addEventListener('event', listener);

			const testFn = () => {
				hubA.removeEventListener(handle);
			};

			expect(testFn).not.toThrow();
		});

		it('allows a previously added portlet.onStateChange event listener to be removed', () => {
			const listener = jest.fn();

			const handle = hubA.addEventListener(
				'portlet.onStateChange',
				listener
			);

			const testFn = () => {
				hubA.removeEventListener(handle);
			};

			expect(testFn).not.toThrow();
		});

		it('allows a previously added portlet.onError event listener to be removed', () => {
			const listener = jest.fn();

			const handle = hubA.addEventListener('portlet.onError', listener);

			const testFn = () => {
				hubA.removeEventListener(handle);
			};

			expect(testFn).not.toThrow();
		});

		it('throws a TypeError if the user event handler is removed twice', () => {
			const listener = jest.fn();

			const handle = hubA.addEventListener('event', listener);

			hubA.removeEventListener(handle);

			const testFn = () => {
				hubA.removeEventListener(handle);
			};

			expect(testFn).toThrow(TypeError);
		});

		it('throws a TypeError if the onStateChange event handler is removed twice', () => {
			const listener = jest.fn();

			const handle = hubA.addEventListener(
				'portlet.onStateChange',
				listener
			);

			hubA.removeEventListener(handle);

			const testFn = () => {
				hubA.removeEventListener(handle);
			};

			expect(testFn).toThrow(TypeError);
		});

		it('throws a TypeError if the onError event handler is removed twice', () => {
			const listener = jest.fn();

			const handle = hubA.addEventListener('portlet.onError', listener);

			hubA.removeEventListener(handle);

			const testFn = () => {
				hubA.removeEventListener(handle);
			};

			expect(testFn).toThrow(TypeError);
		});
	});

	describe('onStateChange without render data', () => {
		const eventType = 'portlet.onStateChange';
		const ids = global.portlet.getIds();
		const pageState = global.portlet.data.pageRenderState.portlets;
		const portletA = ids[0];

		let hubA;
		let returnedRenderData;
		let returnedRenderState;

		const onStateChange = jest.fn((eventType, renderState, renderData) => {
			returnedRenderState = renderState;
			returnedRenderData = renderData;
		});

		beforeEach(() => {
			return register(portletA).then(hub => {
				hubA = hub;
				onStateChange.mockClear();
				returnedRenderState = returnedRenderData = undefined;
			});
		});

		it('does not call the portlet.onStateChange listener during the addEventListener call', () => {
			const handle = hubA.addEventListener(eventType, onStateChange);

			expect(onStateChange).not.toHaveBeenCalled();

			hubA.removeEventListener(handle);
		});

		it('is passed a type parameter with value "portlet.onStateChange"', done => {
			const handle = hubA.addEventListener(eventType, onStateChange);

			setTimeout(() => {
				expect(onStateChange).toHaveBeenCalled();
				expect(onStateChange.mock.calls[0][0]).toEqual(eventType);

				hubA.removeEventListener(handle);

				done();
			}, 200);
		});

		it('is not passed a RenderData object', done => {
			const handle = hubA.addEventListener(eventType, onStateChange);

			setTimeout(() => {
				expect(onStateChange).toHaveBeenCalled();

				expect(onStateChange.mock.calls[0]).toHaveLength(2);

				expect(returnedRenderData).not.toBeDefined();

				hubA.removeEventListener(handle);

				done();
			}, 200);
		});

		it('is passed a RenderState parameter that has 3 properties', done => {
			const handle = hubA.addEventListener(eventType, onStateChange);

			setTimeout(() => {
				expect(onStateChange).toHaveBeenCalled();

				const renderState = onStateChange.mock.calls[0][1];

				const keys = Object.keys(renderState);

				expect(keys).toHaveLength(3);
				expect(returnedRenderState).toEqual(renderState);

				hubA.removeEventListener(handle);

				done();
			});
		});

		it('is passed a RenderState object with a "parameters" property', done => {
			const handle = hubA.addEventListener(eventType, onStateChange);

			setTimeout(() => {
				expect(onStateChange).toHaveBeenCalled();

				const renderState = onStateChange.mock.calls[0][1];

				expect(renderState.parameters).not.toBeUndefined();
				expect(returnedRenderState.parameters).not.toBeUndefined();
				expect(returnedRenderState.parameters).toEqual(
					renderState.parameters
				);

				hubA.removeEventListener(handle);

				done();
			});
		});

		it('is passed a RenderState object with a "portletMode" property', done => {
			const handle = hubA.addEventListener(eventType, onStateChange);

			setTimeout(() => {
				expect(onStateChange).toHaveBeenCalled();

				const renderState = onStateChange.mock.calls[0][1];

				expect(returnedRenderState).not.toBeUndefined();
				expect(returnedRenderState.portletMode).not.toBeUndefined();
				expect(returnedRenderState.portletMode).toEqual(
					renderState.portletMode
				);

				hubA.removeEventListener(handle);

				done();
			});
		});

		it('is passed a RenderState object with a "windowState" property', done => {
			const handle = hubA.addEventListener(eventType, onStateChange);

			setTimeout(() => {
				expect(onStateChange).toHaveBeenCalled();

				const renderState = onStateChange.mock.calls[0][1];

				expect(renderState.windowState).not.toBeUndefined();
				expect(returnedRenderState).not.toBeUndefined();
				expect(returnedRenderState.windowState).toEqual(
					renderState.windowState
				);

				hubA.removeEventListener(handle);

				done();
			});
		});

		it('its RenderState "parameters" property is an object', done => {
			expect.assertions(3);

			const handle = hubA.addEventListener(eventType, onStateChange);

			setTimeout(() => {
				expect(onStateChange).toHaveBeenCalled();

				const renderState = onStateChange.mock.calls[0][1];

				expect(renderState.parameters).not.toBeUndefined();
				expect(typeof renderState.parameters).toEqual('object');

				hubA.removeEventListener(handle);

				done();
			});
		});

		it('its RenderState has 3 parameters', done => {
			expect.assertions(2);

			const handle = hubA.addEventListener(eventType, onStateChange);

			const parameters = pageState[portletA].state.parameters;

			const paramCount = Object.keys(parameters).length;

			setTimeout(() => {
				expect(onStateChange).toHaveBeenCalled();

				const returnParameters = returnedRenderState.parameters;

				const returnParamCount = Object.keys(returnParameters).length;

				expect(returnParamCount).toEqual(paramCount);

				hubA.removeEventListener(handle);

				done();
			});
		});

		it('its RenderState "windowState" property is a string', done => {
			expect.assertions(6);

			const handle = hubA.addEventListener(eventType, onStateChange);

			setTimeout(() => {
				expect(onStateChange).toHaveBeenCalled();

				const renderState = onStateChange.mock.calls[0][1];

				expect(returnedRenderState.windowState).not.toBeUndefined();
				expect(typeof returnedRenderState.windowState).toEqual(
					'string'
				);
				expect(renderState.windowState).not.toBeUndefined();
				expect(typeof renderState.windowState).toEqual('string');
				expect(renderState.windowState).toEqual(
					returnedRenderState.windowState
				);

				hubA.removeEventListener(handle);

				done();
			});
		});

		it('its RenderState "portletMode" property is a string', done => {
			expect.assertions(6);

			const handle = hubA.addEventListener(eventType, onStateChange);

			setTimeout(() => {
				expect(onStateChange).toHaveBeenCalled();

				const renderState = onStateChange.mock.calls[0][1];

				expect(returnedRenderState.portletMode).not.toBeUndefined();
				expect(typeof returnedRenderState.portletMode).toEqual(
					'string'
				);
				expect(renderState.portletMode).not.toBeUndefined();
				expect(typeof renderState.portletMode).toEqual('string');
				expect(renderState.portletMode).toEqual(
					returnedRenderState.portletMode
				);

				hubA.removeEventListener(handle);

				done();
			});
		});

		it(`its RenderState has windowState=${pageState[portletA].state.windowState}`, done => {
			expect.assertions(4);

			const handle = hubA.addEventListener(eventType, onStateChange);

			setTimeout(() => {
				expect(onStateChange).toHaveBeenCalled();

				const renderState = onStateChange.mock.calls[0][1];

				expect(returnedRenderState.windowState).toEqual(
					renderState.windowState
				);
				expect(returnedRenderState.windowState).toEqual(
					pageState[portletA].state.windowState
				);
				expect(renderState.windowState).toEqual(
					pageState[portletA].state.windowState
				);

				hubA.removeEventListener(handle);

				done();
			});
		});

		it(`its RenderState has portletMode=${pageState[portletA].state.portletMode}`, done => {
			expect.assertions(4);

			const handle = hubA.addEventListener(eventType, onStateChange);

			setTimeout(() => {
				expect(onStateChange).toHaveBeenCalled();

				const renderState = onStateChange.mock.calls[0][1];

				expect(returnedRenderState.portletMode).toEqual(
					renderState.portletMode
				);
				expect(returnedRenderState.portletMode).toEqual(
					pageState[portletA].state.portletMode
				);
				expect(renderState.portletMode).toEqual(
					pageState[portletA].state.portletMode
				);

				hubA.removeEventListener(handle);

				done();
			});
		});

		it('its RenderState parameter is not identical to the test state object"', done => {
			expect.assertions(4);

			const handle = hubA.addEventListener(eventType, onStateChange);

			setTimeout(() => {
				expect(onStateChange).toHaveBeenCalled();

				const renderState = onStateChange.mock.calls[0][1];

				expect(renderState).not.toBe(pageState[portletA].state);
				expect(returnedRenderState).not.toBe(pageState[portletA].state);
				expect(returnedRenderState).toEqual(renderState);

				hubA.removeEventListener(handle);

				done();
			});
		});

		it('its RenderState parameter equals the test state object"', done => {
			expect.assertions(4);

			const handle = hubA.addEventListener(eventType, onStateChange);
			const testState = hubA.newState(pageState[portletA].state);

			setTimeout(() => {
				expect(onStateChange).toHaveBeenCalled();

				const renderState = onStateChange.mock.calls[0][1];

				expect(returnedRenderState).toEqual(testState);
				expect(renderState).toEqual(testState);
				expect(renderState).toEqual(returnedRenderState);

				hubA.removeEventListener(handle);

				done();
			});
		});
	});

	describe('onStateChange with render data', () => {
		const eventType = 'portlet.onStateChange';
		const ids = global.portlet.getIds();
		const pageState = global.portlet.data.pageRenderState.portlets;
		const portletA = ids[0];
		const portletB = ids[3];

		let complete = false;
		let handle = null;
		let hubA;
		let hubB;
		let returnedRenderData;
		let returnedRenderState;
		let returnType;

		const onStateChange = jest.fn((eventType, renderState, renderData) => {
			complete = true;
			returnType = eventType;
			returnedRenderState = renderState;
			returnedRenderData = renderData;
		});

		beforeEach(() => {
			return Promise.all([register(portletA), register(portletB)]).then(
				values => {
					hubA = values[0];
					hubB = values[1];
					onStateChange.mockClear();
					complete = false;
					returnType = returnedRenderState = returnedRenderData = undefined;
				}
			);
		});

		afterEach(() => {
			if (handle !== null) {
				hubB.removeEventListener(handle);
				handle = null;
			}
		});

		it('does not call the portlet.onStateChange listener during the addEventListener call', () => {
			handle = hubB.addEventListener(eventType, onStateChange);

			expect(complete).toBeFalsy();
			expect(onStateChange).not.toHaveBeenCalled();
		});

		it('is called asynchronously after an onStateChange handler is registered', done => {
			handle = hubB.addEventListener(eventType, onStateChange);

			setTimeout(() => {
				expect(onStateChange).toHaveBeenCalled();
				expect(complete).toBeTruthy();

				done();
			}, 100);
		});

		it('is passed a type parameter with value "portlet.onStateChange"', done => {
			handle = hubB.addEventListener(eventType, onStateChange);

			setTimeout(() => {
				expect(onStateChange).toHaveBeenCalled();
				expect(complete).toBeTruthy();
				expect(returnType).toEqual(eventType);

				done();
			});
		});

		it('is passed a RenderState parameter that is an object', done => {
			handle = hubB.addEventListener(eventType, onStateChange);

			setTimeout(() => {
				expect(onStateChange).toHaveBeenCalled();

				const renderState = onStateChange.mock.calls[0][1];

				expect(typeof renderState).toEqual('object');
				expect(renderState).toEqual(returnedRenderState);
				expect(typeof renderState).toEqual(typeof returnedRenderState);

				done();
			});
		});

		it('is passed a RenderState parameter that has 3 properties', done => {
			handle = hubB.addEventListener(eventType, onStateChange);

			setTimeout(() => {
				expect(onStateChange).toHaveBeenCalled();

				const renderState = onStateChange.mock.calls[0][1];

				const count = Object.keys(returnedRenderState).length;

				expect(count).toEqual(3);
				expect(Object.keys(renderState).length).toEqual(count);

				expect(renderState).toEqual(returnedRenderState);

				done();
			});
		});

		it('is passed a RenderState object with a "parameters" property', done => {
			handle = hubB.addEventListener(eventType, onStateChange);

			setTimeout(() => {
				expect(onStateChange).toHaveBeenCalled();

				const renderState = onStateChange.mock.calls[0][1];

				expect(renderState.parameters).not.toBeUndefined();
				expect(returnedRenderState.parameters).not.toBeUndefined();
				expect(renderState.parameters).toEqual(renderState.parameters);

				done();
			});
		});

		it('is passed a RenderState object with a "portletMode" property', done => {
			handle = hubB.addEventListener(eventType, onStateChange);

			setTimeout(() => {
				expect(onStateChange).toHaveBeenCalled();

				const renderState = onStateChange.mock.calls[0][1];

				expect(renderState.portletMode).not.toBeUndefined();
				expect(returnedRenderState.portletMode).not.toBeUndefined();
				expect(renderState.portletMode).toEqual(
					renderState.portletMode
				);

				done();
			});
		});

		it('is passed a RenderState object with a "windowState" property', done => {
			handle = hubB.addEventListener(eventType, onStateChange);

			setTimeout(() => {
				expect(onStateChange).toHaveBeenCalled();

				const renderState = onStateChange.mock.calls[0][1];

				expect(renderState.windowState).not.toBeUndefined();
				expect(returnedRenderState.windowState).not.toBeUndefined();
				expect(renderState.windowState).toEqual(
					renderState.windowState
				);

				done();
			});
		});

		it('its RenderState parameter is not identical to the test state object"', done => {
			handle = hubB.addEventListener(eventType, onStateChange);

			setTimeout(() => {
				expect(onStateChange).toHaveBeenCalled();

				const renderState = onStateChange.mock.calls[0][1];

				expect(renderState).not.toBe(pageState[portletB].state);
				expect(returnedRenderState).not.toBe(pageState[portletB].state);

				done();
			});
		});

		it('its RenderState parameter equals the test state object"', done => {
			handle = hubB.addEventListener(eventType, onStateChange);

			const testState = hubA.newState(pageState[portletB].state);

			setTimeout(() => {
				expect(onStateChange).toHaveBeenCalled();

				const renderState = onStateChange.mock.calls[0][1];

				expect(renderState).toEqual(testState);
				expect(returnedRenderState).toEqual(testState);

				done();
			});
		});

		// portletB (which is actually PortletD in the ) is set up to have render data

		it('is passed a RenderData object', done => {
			handle = hubB.addEventListener(eventType, onStateChange);

			setTimeout(() => {
				expect(onStateChange).toHaveBeenCalled();

				const renderData = onStateChange.mock.calls[0][2];

				expect(renderData).not.toBeUndefined();
				expect(typeof returnedRenderData).toEqual('object');

				done();
			});
		});

		it('is passed a RenderData parameter that has 2 properties', done => {
			handle = hubB.addEventListener(eventType, onStateChange);

			setTimeout(() => {
				expect(onStateChange).toHaveBeenCalled();

				const renderData = onStateChange.mock.calls[0][2];

				expect(renderData).toEqual(returnedRenderData);

				const count = Object.keys(returnedRenderData).length;

				expect(count).toEqual(2);

				done();
			});
		});

		it('is passed a RenderData object with a "content" property', done => {
			handle = hubB.addEventListener(eventType, onStateChange);

			setTimeout(() => {
				expect(onStateChange).toHaveBeenCalled();

				const renderData = onStateChange.mock.calls[0][2];

				expect(renderData).toEqual(returnedRenderData);
				expect(renderData.content).not.toBeUndefined();

				done();
			});
		});

		it('is passed a RenderData object with a "mimeType" property', done => {
			handle = hubB.addEventListener(eventType, onStateChange);

			setTimeout(() => {
				expect(onStateChange).toHaveBeenCalled();

				const renderData = onStateChange.mock.calls[0][2];

				expect(renderData).toEqual(returnedRenderData);
				expect(renderData.mimeType).not.toBeUndefined();

				done();
			});
		});

		it('is passed a RenderData object with a "content" property of type string', done => {
			handle = hubB.addEventListener(eventType, onStateChange);

			setTimeout(() => {
				expect(onStateChange).toHaveBeenCalled();

				const renderData = onStateChange.mock.calls[0][2];

				expect(renderData).toEqual(returnedRenderData);
				expect(renderData.content).not.toBeUndefined();
				expect(typeof renderData.content).toEqual('string');

				done();
			});
		});

		it('is passed a RenderData object with a "mimeType" property of type string', done => {
			handle = hubB.addEventListener(eventType, onStateChange);

			setTimeout(() => {
				expect(onStateChange).toHaveBeenCalled();

				const renderData = onStateChange.mock.calls[0][2];

				expect(renderData).toEqual(returnedRenderData);
				expect(renderData.mimeType).not.toBeUndefined();
				expect(typeof renderData.mimeType).toEqual('string');

				done();
			});
		});

		it('its RenderData parameter equals the test render data object"', done => {
			handle = hubB.addEventListener(eventType, onStateChange);

			setTimeout(() => {
				expect(onStateChange).toHaveBeenCalled();

				const renderData = onStateChange.mock.calls[0][2];

				expect(renderData).toEqual(pageState[portletB].renderData);
				expect(returnedRenderData).toEqual(
					pageState[portletB].renderData
				);

				done();
			});
		});
	});

	describe('Allows the portlet client to dispatch events', () => {
		const ids = global.portlet.getIds();
		const portletA = ids[0];
		const portletB = ids[1];
		const portletC = ids[2];
		const portletD = ids[3];

		let hubA;
		let hubB;
		let hubC;
		let hubD;

		beforeEach(() => {
			return Promise.all([
				register(portletA),
				register(portletB),
				register(portletC),
				register(portletD)
			]).then(values => {
				hubA = values[0];
				hubB = values[1];
				hubC = values[2];
				hubD = values[2];
			});
		});

		describe('The portlet hub is initialized for the tests', () => {
			it('initializes a portlet hub instance for portlet A', () => {
				expect(hubA).not.toBeUndefined();
			});

			it('initializes a portlet hub instance for portlet B', () => {
				expect(hubB).not.toBeUndefined();
			});

			it('initializes a portlet hub instance for portlet C', () => {
				expect(hubC).not.toBeUndefined();
			});

			it('initializes a portlet hub instance for portlet D', () => {
				expect(hubD).not.toBeUndefined();
			});
		});

		describe('The portlet hub dispatchClientEvent function', () => {
			it('is present in the register return object and is a function', () => {
				expect(typeof hubA.dispatchClientEvent).toEqual('function');
			});

			it('throws a TypeError if no argument is provided', () => {
				const testFn = () => {
					hubA.dispatchClientEvent();
				};

				expect(testFn).toThrow(TypeError);
			});

			it('throws a TypeError if too many (>2) arguments are provided', () => {
				const testFn = () => {
					hubA.dispatchClientEvent('param1', 'param2', 'param3');
				};

				expect(testFn).toThrow(TypeError);
			});

			it('throws a TypeError if the type argument is not a string', () => {
				const testFn = () => {
					hubA.dispatchClientEvent(89, 'payload');
				};

				expect(testFn).toThrow(TypeError);
			});

			it('throws a TypeError if the type is null', () => {
				const testFn = () => {
					hubA.dispatchClientEvent(null, 'payload');
				};

				expect(testFn).toThrow(TypeError);
			});

			it('does not throw an Exception if the payload is null', () => {
				const testFn = () => {
					hubA.dispatchClientEvent('event', null);
				};

				expect(testFn).not.toThrow();
			});

			it('throws a TypeError if the type begins with "portlet."', () => {
				const testFn = () => {
					hubA.dispatchClientEvent('portlet.invalidType', 'payload');
				};

				expect(testFn).toThrow(TypeError);
			});

			it('throws a TypeError if the type matches a system event type', () => {
				const testFn = () => {
					hubA.dispatchClientEvent(
						'portlet.onStateChange',
						'payload'
					);
				};

				expect(testFn).toThrow(TypeError);
			});

			it('does not throw an exception if both parameters are valid', () => {
				const testFn = () => {
					hubA.dispatchClientEvent('event', 'payload');
				};

				expect(testFn).not.toThrow();
			});

			it('returns count of 0 when no listener for event is registered', () => {
				const count = hubA.dispatchClientEvent('event', 'payload');

				expect(count).toEqual(0);
			});

			it('listener is called & count=1 when 1 listener for event is registered', () => {
				const listener = jest.fn();
				const payload = 'payload';
				const type = 'event';

				const handle = hubA.addEventListener(type, listener);

				const count = hubA.dispatchClientEvent(type, payload);

				expect(count).toEqual(1);
				expect(listener).toHaveBeenCalled();
				expect(listener).toHaveBeenCalledWith(type, payload);

				hubA.removeEventListener(handle);
			});

			it('causes listener to be called with expected type & string payload when event is dispatched', () => {
				const listener = jest.fn();
				const payload = 'payload';
				const type = 'event';

				const handle = hubA.addEventListener(type, listener);

				const count = hubA.dispatchClientEvent(type, payload);

				expect(count).toEqual(1);
				expect(listener).toBeCalledWith(type, payload);

				hubA.removeEventListener(handle);
			});

			it('when type does not match, no event is fired', () => {
				const listener = jest.fn();
				const payload = 'payload';
				const type = 'event';

				hubB.addEventListener('differentEvent', listener);

				const count = hubA.dispatchClientEvent(type, payload);

				expect(count).toEqual(0);
				expect(listener).not.toHaveBeenCalled();
			});

			it('payload=null is transported correctly', () => {
				const listener = jest.fn();
				const payload = null;
				const type = 'event';

				const handle = hubA.addEventListener(type, listener);

				const count = hubA.dispatchClientEvent(type, payload);

				expect(count).toEqual(1);
				expect(listener).toHaveBeenCalledWith(type, payload);

				hubA.removeEventListener(handle);
			});

			it('payload of type object is transported correctly', () => {
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
			});

			it('listener of different portlet is correctly called when event is dispatched', () => {
				const listener = jest.fn();
				const payload = 'payload';
				const type = 'event';

				const handle = hubB.addEventListener(type, listener);

				const count = hubA.dispatchClientEvent(type, payload);

				expect(count).toEqual(1);
				expect(listener).toHaveBeenCalledWith(type, payload);

				hubB.removeEventListener(handle);
			});

			it('matches event types by regex', () => {
				const listener = jest.fn();
				const payload = 'payload';
				const type = 'liferay.event';

				hubB.addEventListener('liferay..*', listener);

				const count = hubA.dispatchClientEvent(type, payload);

				expect(count).toEqual(1);
				expect(listener).toHaveBeenCalledWith(type, payload);
			});

			it('when regex does not match, no event is fired', () => {
				const listener = jest.fn();
				const payload = 'payload';
				const type = 'event';

				hubB.addEventListener('liferay..*', listener);

				const count = hubA.dispatchClientEvent(type, payload);

				expect(count).toEqual(0);
				expect(listener).not.toHaveBeenCalled();
			});

			it('2 listeners of different portlet are correctly called when event is dispatched', () => {
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
			});
		});
	});
});
