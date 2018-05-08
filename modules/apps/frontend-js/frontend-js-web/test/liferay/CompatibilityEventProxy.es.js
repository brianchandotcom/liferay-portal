'use strict';

import EventEmitter from 'metal-events/src/EventEmitter';

import CompatibilityEventProxy from '../../src/main/resources/META-INF/resources/liferay/CompatibilityEventProxy.es';

describe(
	'CompatibilityEventProxy',
	() => {
		function createMockedTarget(event, emitFacade) {
			const mockedTarget = {
				fire: function() {},

				_yuievt: {
					events: {}
				}
			};

			if (event) {
				mockedTarget._yuievt.events[event] = {
					emitFacade: emitFacade
				};
			}

			return mockedTarget;
		}

		const eventNameToEmit = 'eventToEmit';

		const eventObjectToEmit = {
			key: eventNameToEmit
		};

		const eventFacadeObjectToEmit = {
			type: eventNameToEmit
		};

		let host;

		beforeEach(
			done => {
				host = new EventEmitter();
				done();
			}
		);

		afterEach(
			done => {
				host.dispose();
				done();
			}
		);

		const namespace = 'namespace';

		it(
			'should not emit any event when no targets have been added',
			done => {
				const component = new CompatibilityEventProxy(
					{
						host: host
					}
				);

				const spy = jest.spyOn(component, 'emitCompatibleEvents_');

				host.emit(eventNameToEmit, eventObjectToEmit, eventFacadeObjectToEmit);

				expect(spy).not.toHaveBeenCalled();

				spy.mockReset();
				spy.mockRestore();

				done();
			}
		);

		it(
			'should not crash if target has no method fire',
			done => {
				const mockedTarget = {};

				const component = new CompatibilityEventProxy(
					{
						host: host
					}
				);

				const spy = jest.spyOn(component, 'emitCompatibleEvents_');

				component.addTarget(mockedTarget);

				expect(
					() => {
						host.emit(eventNameToEmit, eventObjectToEmit, eventFacadeObjectToEmit);
					}
				).not.toThrow();

				spy.mockReset();
				spy.mockRestore();

				done();
			}
		);

		it(
			'should emit adapted event with event name and event params to given targets when no namespace is specified',
			done => {
				const mockedTarget = createMockedTarget(eventNameToEmit);

				const spy = jest.spyOn(mockedTarget, 'fire');

				const component = new CompatibilityEventProxy(
					{
						host: host
					}
				);

				component.addTarget(mockedTarget);

				host.emit(eventNameToEmit, eventObjectToEmit, eventFacadeObjectToEmit);

				expect(spy).toHaveBeenCalledWith(eventNameToEmit, eventObjectToEmit);

				spy.mockReset();
				spy.mockRestore();

				done();
			}
		);

		it(
			'should emit adapted event with event name and event params to given targets when namespace is specified',
			done => {
				const namespacedEventNameToEmit = namespace + ':' + eventNameToEmit;

				const mockedTarget = createMockedTarget(namespacedEventNameToEmit);

				const spy = jest.spyOn(mockedTarget, 'fire');

				const component = new CompatibilityEventProxy(
					{
						host: host,
						namespace: namespace
					}
				);

				component.addTarget(mockedTarget);

				host.emit(eventNameToEmit, eventObjectToEmit, eventFacadeObjectToEmit);

				expect(spy).toHaveBeenCalledWith(
					namespacedEventNameToEmit,
					eventObjectToEmit
				);

				done();
			}
		);

		it(
			'should emit adapted event to given targets when target is not listening',
			done => {
				const mockedTarget = createMockedTarget();

				const spy = jest.spyOn(mockedTarget, 'fire');

				const component = new CompatibilityEventProxy(
					{
						host: host
					}
				);

				component.addTarget(mockedTarget);

				host.emit(eventNameToEmit, eventObjectToEmit, eventFacadeObjectToEmit);

				expect(spy).toHaveBeenCalledWith(eventNameToEmit, eventObjectToEmit);

				done();
			}
		);

		it(
			'should emit adapted event to given targets when target is listening',
			done => {
				const mockedTarget = createMockedTarget(eventNameToEmit);

				const spy = jest.spyOn(mockedTarget, 'fire');

				const component = new CompatibilityEventProxy(
					{
						host: host
					}
				);

				component.addTarget(mockedTarget);

				host.emit(eventNameToEmit, eventObjectToEmit, eventFacadeObjectToEmit);

				expect(spy).toHaveBeenCalledWith(eventNameToEmit, eventObjectToEmit);

				done();
			}
		);

		it(
			'should maintain target original state of emitFacade after emiting events',
			done => {
				const emitFacade = false;

				const mockedTarget = createMockedTarget(eventNameToEmit, emitFacade);

				const spy = jest.spyOn(mockedTarget, 'fire');

				const component = new CompatibilityEventProxy(
					{
						host: host
					}
				);

				component.addTarget(mockedTarget);

				host.emit(eventNameToEmit, eventObjectToEmit, eventFacadeObjectToEmit);

				expect(spy).toHaveBeenCalledWith(eventNameToEmit, eventObjectToEmit);
				expect(emitFacade).toEqual(
					mockedTarget._yuievt.events[eventNameToEmit].emitFacade
				);

				done();
			}
		);

		it(
			'should maintain target original state of emitFacade after emiting events when component emitFacade is true',
			done => {
				const emitFacade = false;

				const mockedTarget = createMockedTarget(eventNameToEmit, emitFacade);

				const spy = jest.spyOn(mockedTarget, 'fire');

				const component = new CompatibilityEventProxy(
					{
						emitFacade: true,
						host: host
					}
				);

				component.addTarget(mockedTarget);

				host.emit(eventNameToEmit, eventObjectToEmit, eventFacadeObjectToEmit);

				expect(spy).toHaveBeenCalledWith(eventNameToEmit, eventObjectToEmit);
				expect(emitFacade).toEqual(
					mockedTarget._yuievt.events[eventNameToEmit].emitFacade
				);

				done();
			}
		);

		it(
			'should adapt the events according to specified RegExp',
			done => {
				const eventNameToEmit = 'eventChanged';

				const eventObjectToEmit = {
					key: eventNameToEmit
				};

				const eventFacadeObjectToEmit = {
					type: eventNameToEmit
				};

				const adaptedEventNameToEmit = 'eventChange';

				const mockedTarget = createMockedTarget(eventNameToEmit);

				const spy = jest.spyOn(mockedTarget, 'fire');

				const component = new CompatibilityEventProxy(
					{
						adaptedEvents: {
							match: /(.*)(Changed)$/,
							replace: '$1Change'
						},
						host: host
					}
				);

				component.addTarget(mockedTarget);

				host.emit(eventNameToEmit, eventObjectToEmit, eventFacadeObjectToEmit);

				expect(spy.mock.calls[0][0]).toEqual(adaptedEventNameToEmit);

				done();
			}
		);

		it(
			'should emit events even if the event does not have a key property',
			done => {
				const eventObjectToEmit = {};

				const mockedTarget = createMockedTarget(eventNameToEmit);

				const spy = jest.spyOn(mockedTarget, 'fire');

				const component = new CompatibilityEventProxy(
					{
						host: host
					}
				);

				component.addTarget(mockedTarget);

				host.emit(eventNameToEmit, eventObjectToEmit, eventFacadeObjectToEmit);

				expect(spy).toHaveBeenCalledWith(eventNameToEmit, eventObjectToEmit);

				done();
			}
		);
	}
);