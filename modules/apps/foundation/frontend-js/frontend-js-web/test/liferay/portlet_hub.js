'use strict';

import PortletInit from '../../src/main/resources/META-INF/resources/liferay/portlet_hub/PortletInit';
import register from '../../src/main/resources/META-INF/resources/liferay/portlet_hub/register';

describe('Portlet Hub', () => {
	afterEach(() => {
		PortletInit._clientEventListeners = [];
	});

	describe('Client Events', () => {
		it('it should throw error if registered is called without portletId', () => {
			expect.assertions(1);

			return register().catch(err => {
				expect(err.message).toEqual('Invalid portlet ID');
			});
		});

		it('it should resolve to instance of PortletInit', () => {
			return register('portletA').then(hub => {
				expect(hub).toBeInstanceOf(PortletInit);
			});
		});
	});

	describe('Client Events', () => {
		it('it should add client event listener', () => {
			const stub = jest.fn();

			return register('portletA').then(hub => {
				hub.addEventListener('clientEvent', stub);

				expect(stub.mock.calls.length).toBe(0);

				hub.dispatchClientEvent('clientEvent');

				expect(stub.mock.calls.length).toBe(1);
			});
		});

		it('it should return number of listeners', () => {
			const stub = jest.fn();

			return register('portletA').then(hub => {
				hub.addEventListener('clientEvent', stub);
				hub.addEventListener('clientEvent', stub);
				hub.addEventListener('clientEvent2', stub);

				expect(stub.mock.calls.length).toBe(0);

				expect(hub.dispatchClientEvent('clientEvent')).toBe(2);
			});
		});

		it('it should throw error if addEventListener is called with invalid args', () => {
			return register('portletA').then(hub => {
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

		it('it should not call listener if it is removed', () => {
			const stub = jest.fn();

			return register('portletA').then(hub => {
				const handle = hub.addEventListener('clientEvent', stub);

				hub.removeEventListener(handle);

				const total = hub.dispatchClientEvent('clientEvent');

				expect(stub.mock.calls.length).toBe(0);
				expect(total).toBe(0);
			});
		});

		it('it should throw error if dispatchClientEvent is called with invalid args', () => {
			const stub = jest.fn();

			return register('portletA').then(hub => {
				expect(() => {
					hub.dispatchClientEvent(1, 2, 3);
				}).toThrow('Too many arguments passed to dispatchClientEvent');

				expect(() => {
					hub.dispatchClientEvent(1);
				}).toThrow('Event type must be a string');
			});
		});

		it('it should throw error when attempting to dispatch event with protected name', () => {
			return register('portletA').then(hub => {
				expect(() => {
					hub.dispatchClientEvent('portlet.clientEvent')
				}).toThrow('The event type is invalid: portlet.clientEvent');
			});
		});

		it('it should throw error if removeEventListener is called with invalid args', () => {
			return register('portletA').then(hub => {
				expect(() => {
					hub.removeEventListener(1, 2);
				}).toThrow('Too many arguments passed to removeEventListener');

				expect(() => {
					hub.removeEventListener();
				}).toThrow('The event handle provided is undefined');

				expect(() => {
					hub.removeEventListener({});
				}).toThrow(`The event listener handle doesn't match any listeners.`);
			});
		});

		it('it should call listener with matching wildcard event types', () => {
			const stub = jest.fn();

			return register('portletA').then(hub => {
				const handle = hub.addEventListener('Event$', stub);

				const total = hub.dispatchClientEvent('clientEvent');

				expect(stub.mock.calls.length).toBe(1);
				expect(total).toBe(1);
			});
		});
	});
});
