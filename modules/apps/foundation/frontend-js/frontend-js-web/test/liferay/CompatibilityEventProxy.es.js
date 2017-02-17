'use strict';

import EventEmitter from 'metal-events/src/EventEmitter';
import CompatibilityEventProxy from '../../src/main/resources/META-INF/resources/liferay/CompatibilityEventProxy.es';

describe('CompatibilityEventProxy', () => {
	function createMockedTarget(event, emitFacade) {
		let mockedTarget = {
			fire: function() {

			},

			_yuievt: {
				events: {}
			}
		};

		if (event) {
			mockedTarget._yuievt.events[event] = {
				emitFacade: emitFacade
			};
		}

		return mockedTarget;
	}

	let eventNameToEmit = 'eventToEmit';

	let eventObjectToEmit = {
		key: eventNameToEmit
	};

	let eventFacadeObjectToEmit = {
		type: eventNameToEmit
	};

	let	host;

	beforeEach((done) => {
		host = new EventEmitter();
		done();
	});

	afterEach((done) => {
		host.dispose();
		done();
	});

	let namespace = 'namespace';

	it('should not emit any event when no targets have been added', (done) => {
		let component = new CompatibilityEventProxy({
			host: host
		});

		let spy = sinon.spy(component, 'emitCompatibleEvents_');

		host.emit(eventNameToEmit, eventObjectToEmit, eventFacadeObjectToEmit);

		assert.strictEqual(false, spy.called);

		done();
	});

	it('should not crash if target has no method fire', (done) => {
		let mockedTarget = {};

		let component = new CompatibilityEventProxy({
			host: host
		});

		let spy = sinon.spy(component, 'emitCompatibleEvents_');

		component.addTarget(mockedTarget);

		host.emit(eventNameToEmit, eventObjectToEmit, eventFacadeObjectToEmit);

		assert.strictEqual(undefined, spy.exceptions[0]);

		done();
	});

	it('should emit adapted event with event name and event params to given targets when no namespace is specified', (done) => {
		let mockedTarget = createMockedTarget(eventNameToEmit);

		let spy = sinon.spy(mockedTarget, 'fire');

		let component = new CompatibilityEventProxy({
			host: host
		});

		component.addTarget(mockedTarget);

		host.emit(eventNameToEmit, eventObjectToEmit, eventFacadeObjectToEmit);

		assert.strictEqual(true, spy.calledWith(eventNameToEmit, eventObjectToEmit));

		done();
	});

	it('should emit adapted event with event name and event params to given targets when namespace is specified', (done) => {
		let namespacedEventNameToEmit = namespace + ':' + eventNameToEmit;

		let mockedTarget = createMockedTarget(namespacedEventNameToEmit);

		let spy = sinon.spy(mockedTarget, 'fire');

		let component = new CompatibilityEventProxy({
			host: host,
			namespace: namespace
		});

		component.addTarget(mockedTarget);

		host.emit(eventNameToEmit, eventObjectToEmit, eventFacadeObjectToEmit);

		assert.strictEqual(true, spy.calledWith(namespacedEventNameToEmit, eventObjectToEmit));

		done();
	});

	it('should emit adapted event to given targets when target is not listening', (done) => {
		let mockedTarget = createMockedTarget();

		let spy = sinon.spy(mockedTarget, 'fire');

		let component = new CompatibilityEventProxy({
			host: host
		});

		component.addTarget(mockedTarget);

		host.emit(eventNameToEmit, eventObjectToEmit, eventFacadeObjectToEmit);

		assert.strictEqual(true, spy.calledWith(eventNameToEmit, eventObjectToEmit));

		done();
	});

	it('should emit adapted event to given targets when target is listening', (done) => {
		let mockedTarget = createMockedTarget(eventNameToEmit);

		let spy = sinon.spy(mockedTarget, 'fire');

		let component = new CompatibilityEventProxy({
			host: host
		});

		component.addTarget(mockedTarget);

		host.emit(eventNameToEmit, eventObjectToEmit, eventFacadeObjectToEmit);

		assert.strictEqual(true, spy.calledWith(eventNameToEmit, eventObjectToEmit));

		done();
	});

	it('should maintain target original state of emitFacade after emiting events', (done) => {
		let emitFacade = false;

		let mockedTarget = createMockedTarget(eventNameToEmit, emitFacade);

		let spy = sinon.spy(mockedTarget, 'fire');

		let component = new CompatibilityEventProxy({
			host: host
		});

		component.addTarget(mockedTarget);

		host.emit(eventNameToEmit, eventObjectToEmit, eventFacadeObjectToEmit);

		assert.strictEqual(true, spy.calledWith(eventNameToEmit, eventObjectToEmit));
		assert.strictEqual(emitFacade, mockedTarget._yuievt.events[eventNameToEmit].emitFacade);

		done();
	});

	it('should maintain target original state of emitFacade after emiting events when component emitFacade is true', (done) => {
		let emitFacade = false;

		let mockedTarget = createMockedTarget(eventNameToEmit, emitFacade);

		let spy = sinon.spy(mockedTarget, 'fire');

		let component = new CompatibilityEventProxy({
			emitFacade: true,
			host: host
		});

		component.addTarget(mockedTarget);

		host.emit(eventNameToEmit, eventObjectToEmit, eventFacadeObjectToEmit);

		assert.strictEqual(true, spy.calledWith(eventNameToEmit, eventObjectToEmit));
		assert.strictEqual(emitFacade, mockedTarget._yuievt.events[eventNameToEmit].emitFacade);

		done();
	});

	it('should adapt the events according to specified RegExp', (done) => {
		let eventNameToEmit = 'eventChanged';

		let eventObjectToEmit = {
			key: eventNameToEmit
		};

		let eventFacadeObjectToEmit = {
			type: eventNameToEmit
		};

		let adaptedEventNameToEmit = 'eventChange';

		let mockedTarget = createMockedTarget(eventNameToEmit);

		let spy = sinon.spy(mockedTarget, 'fire');

		let component = new CompatibilityEventProxy({
			adaptedEvents: {
				match: /(.*)(Changed)$/,
				replace: '$1Change'
			},
			host: host
		});

		component.addTarget(mockedTarget);

		host.emit(eventNameToEmit, eventObjectToEmit, eventFacadeObjectToEmit);

		assert.strictEqual(true, spy.getCall(0).args[0] === adaptedEventNameToEmit);

		done();
	});

});