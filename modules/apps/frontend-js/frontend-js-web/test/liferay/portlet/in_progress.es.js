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
	describe('allows the portlet client test for a blocking operation', () => {
		const ids = global.portlet.getIds();
		const portletA = ids[0];
		const portletB = ids[1];
		const portletC = ids[2];
		const portletD = ids[3];

		const pageState = global.portlet.data.pageRenderState;

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

		beforeEach(() => {
			jest.useFakeTimers();

			return Promise.all([
				register(portletA),
				register(portletB),
				register(portletC),
				register(portletD)
			]).then(values => {
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
			});
		});

		afterEach(() => {
			jest.clearAllTimers();

			listenerA.mockReset();
			hubA.removeEventListener(handleA);

			listenerB.mockReset();
			hubB.removeEventListener(handleB);

			listenerC.mockReset();
			hubC.removeEventListener(handleC);

			listenerD.mockReset();
			hubD.removeEventListener(handleD);
		});

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

		it('is present in the register return object and is a function', () => {
			expect(typeof hubA.isInProgress).toEqual('function');
		});

		it('returns a boolean value', () => {
			const returnValue = hubA.isInProgress();

			expect(typeof returnValue).toEqual('boolean');
		});

		it('returns false if a blocking operation is not in progress', () => {
			const returnValue = hubA.isInProgress();

			expect(returnValue).toBe(false);
		});

		it('returns false through a different hub if a blocking operation is not in progress', () => {
			const returnValue = hubD.isInProgress();

			expect(returnValue).toBe(false);
		});

		it('returns true when a partial action has been started but setPageState has not been called', () => {
			const parameters = {};

			return hubB
				.startPartialAction(parameters)
				.then(partialActionInitObject => {
					expect(hubB.isInProgress()).toBeTruthy();

					partialActionInitObject.setPageState(JSON.stringify({}));

					jest.runAllTimers();

					expect(listenerB).toHaveBeenCalled();
				});
		});

		it('returns true through a different hub when a partial action has been started but setPageState has not been called', () => {
			const parameters = {};

			return hubB
				.startPartialAction(parameters)
				.then(partialActionInitObject => {
					expect(hubB.isInProgress()).toBeTruthy();
					expect(hubD.isInProgress()).toBeTruthy();

					partialActionInitObject.setPageState(JSON.stringify({}));

					jest.runAllTimers();

					expect(listenerB).toHaveBeenCalled();
				});
		});

		it('returns true when setPageState has been called but the updates have not been dispatched', () => {
			const parameters = {};

			hubB.startPartialAction(parameters).then(
				partialActionInitObject => {
					partialActionInitObject.setPageState(JSON.stringify({}));

					expect(hubB.isInProgress()).toBeTruthy();

					jest.runAllTimers();

					expect(listenerB).toHaveBeenCalled();
				}
			);
		});

		it('returns false after setPageState updates have been dispatched', () => {
			const parameters = {};

			return hubB
				.startPartialAction(parameters)
				.then(partialActionInitObject => {
					partialActionInitObject.setPageState(JSON.stringify({}));
					expect(hubD.isInProgress()).toBeTruthy();

					jest.runAllTimers();

					expect(listenerD).toHaveBeenCalled();
					expect(hubD.isInProgress()).toBeFalsy();
				});
		});

		it('returns false through a different hub after setPageState updates have been dispatched', () => {
			const parameters = {ap1: ['actionVal']};

			hubB.startPartialAction(parameters).then(
				partialActionInitObject => {
					partialActionInitObject.setPageState(JSON.stringify({}));

					expect(hubB.isInProgress()).toBeTruthy();

					jest.runAllTimers();

					expect(listenerC).toHaveBeenCalled();
					expect(hubB.isInProgress()).toBeFalsy();
				}
			);
		});

		it('returns true when action has been called but the updates have not been dispatched', () => {
			global.fetchMock([portletA, portletB, portletC, portletD]);

			const element = document.createElement('form');
			const parameters = {};

			const testFn = () => hubB.action(parameters, element);

			expect(testFn).not.toThrow();
			expect(hubD.isInProgress()).toBeTruthy();
		});

		it('returns true through a different hub when action has been called but the updates have not been dispatched', () => {
			global.fetchMock([portletA, portletB, portletC, portletD]);

			const element = document.createElement('form');
			const parameters = {};

			const testFn = () => hubB.action(parameters, element);

			expect(testFn).not.toThrow();
			expect(hubD.isInProgress()).toBeTruthy();

			jest.runAllTimers();

			expect(listenerB).toHaveBeenCalled();
		});

		it('returns false after action updates have been dispatched', () => {
			global.fetchMock([portletA, portletB, portletC, portletD]);

			const element = document.createElement('form');
			const parameters = {};

			const testFn = () => hubB.action(parameters, element);

			expect(testFn).not.toThrow();

			jest.runAllTimers();

			expect(listenerB).toHaveBeenCalled();
			expect(hubC.isInProgress()).toBeFalsy();
		});

		it('returns true when setRenderState has been called but the updates have not been dispatched', () => {
			const state = pageState.portlets.PortletC.state;

			state.parameters.param1 = ['paramValue1'];

			const testFn = () => hubC.setRenderState(state);

			expect(testFn).not.toThrow();
			expect(hubD.isInProgress()).toBeTruthy();

			jest.runAllTimers();

			expect(listenerC).toHaveBeenCalled();
		});

		it('returns true through a different hub when setRenderState has been called but the updates have not been dispatched', () => {
			const state = pageState.portlets.PortletC.state;

			state.parameters.param1 = ['paramValue1'];

			const testFn = () => hubC.setRenderState(state);

			expect(testFn).not.toThrow();
			expect(hubD.isInProgress()).toBeTruthy();

			jest.runAllTimers();

			expect(listenerC).toHaveBeenCalled();
		});

		it('returns false after setRenderState updates have been dispatched', () => {
			const state = pageState.portlets.PortletC.state;

			state.parameters.param1 = ['paramValue1'];

			const testFn = () => hubC.setRenderState(state);

			expect(testFn).not.toThrow();

			jest.runAllTimers();

			expect(listenerC).toHaveBeenCalled();
			expect(hubA.isInProgress()).toBeFalsy();
			expect(hubB.isInProgress()).toBeFalsy();
			expect(hubC.isInProgress()).toBeFalsy();
			expect(hubD.isInProgress()).toBeFalsy();
		});
	});
});
