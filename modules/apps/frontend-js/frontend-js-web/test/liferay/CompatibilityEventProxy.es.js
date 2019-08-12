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

import EventEmitter from 'metal-events/lib/EventEmitter';

import CompatibilityEventProxy from '../../src/main/resources/META-INF/resources/liferay/CompatibilityEventProxy.es';

describe('CompatibilityEventProxy', () => {
	function createMockedTarget(event, emitFacade) {
		const mockedTarget = {
			_yuievt: {
				events: {}
			},

			fire() {}
		};

		if (event) {
			mockedTarget._yuievt.events[event] = {
				emitFacade
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

	beforeEach(done => {
		host = new EventEmitter();
		done();
	});

	afterEach(done => {
		host.dispose();
		done();
	});

	const namespace = 'namespace';

	it('does not emit any event when no targets have been added', done => {
		const component = new CompatibilityEventProxy({
			host
		});

		const spy = jest.spyOn(component, 'emitCompatibleEvents_');

		host.emit(eventNameToEmit, eventObjectToEmit, eventFacadeObjectToEmit);

		expect(spy).not.toHaveBeenCalled();

		spy.mockReset();
		spy.mockRestore();

		done();
	});

	it('does not crash if target has no method fire', done => {
		const mockedTarget = {};

		const component = new CompatibilityEventProxy({
			host
		});

		const spy = jest.spyOn(component, 'emitCompatibleEvents_');

		component.addTarget(mockedTarget);

		expect(() => {
			host.emit(
				eventNameToEmit,
				eventObjectToEmit,
				eventFacadeObjectToEmit
			);
		}).not.toThrow();

		spy.mockReset();
		spy.mockRestore();

		done();
	});

	it('emits adapted event with event name and event params to given targets when no namespace is specified', done => {
		const mockedTarget = createMockedTarget(eventNameToEmit);

		const spy = jest.spyOn(mockedTarget, 'fire');

		const component = new CompatibilityEventProxy({
			host
		});

		component.addTarget(mockedTarget);

		host.emit(eventNameToEmit, eventObjectToEmit, eventFacadeObjectToEmit);

		expect(spy).toHaveBeenCalledWith(eventNameToEmit, eventObjectToEmit);

		spy.mockReset();
		spy.mockRestore();

		done();
	});

	it('emits adapted event with event name and event params to given targets when namespace is specified', done => {
		const namespacedEventNameToEmit = namespace + ':' + eventNameToEmit;

		const mockedTarget = createMockedTarget(namespacedEventNameToEmit);

		const spy = jest.spyOn(mockedTarget, 'fire');

		const component = new CompatibilityEventProxy({
			host,
			namespace
		});

		component.addTarget(mockedTarget);

		host.emit(eventNameToEmit, eventObjectToEmit, eventFacadeObjectToEmit);

		expect(spy).toHaveBeenCalledWith(
			namespacedEventNameToEmit,
			eventObjectToEmit
		);

		done();
	});

	it('emits adapted event to given targets when target is not listening', done => {
		const mockedTarget = createMockedTarget();

		const spy = jest.spyOn(mockedTarget, 'fire');

		const component = new CompatibilityEventProxy({
			host
		});

		component.addTarget(mockedTarget);

		host.emit(eventNameToEmit, eventObjectToEmit, eventFacadeObjectToEmit);

		expect(spy).toHaveBeenCalledWith(eventNameToEmit, eventObjectToEmit);

		done();
	});

	it('emits adapted event to given targets when target is listening', done => {
		const mockedTarget = createMockedTarget(eventNameToEmit);

		const spy = jest.spyOn(mockedTarget, 'fire');

		const component = new CompatibilityEventProxy({
			host
		});

		component.addTarget(mockedTarget);

		host.emit(eventNameToEmit, eventObjectToEmit, eventFacadeObjectToEmit);

		expect(spy).toHaveBeenCalledWith(eventNameToEmit, eventObjectToEmit);

		done();
	});

	it('maintains target original state of emitFacade after emiting events', done => {
		const emitFacade = false;

		const mockedTarget = createMockedTarget(eventNameToEmit, emitFacade);

		const spy = jest.spyOn(mockedTarget, 'fire');

		const component = new CompatibilityEventProxy({
			host
		});

		component.addTarget(mockedTarget);

		host.emit(eventNameToEmit, eventObjectToEmit, eventFacadeObjectToEmit);

		expect(spy).toHaveBeenCalledWith(eventNameToEmit, eventObjectToEmit);
		expect(emitFacade).toEqual(
			mockedTarget._yuievt.events[eventNameToEmit].emitFacade
		);

		done();
	});

	it('maintains target original state of emitFacade after emiting events when component emitFacade is true', done => {
		const emitFacade = false;

		const mockedTarget = createMockedTarget(eventNameToEmit, emitFacade);

		const spy = jest.spyOn(mockedTarget, 'fire');

		const component = new CompatibilityEventProxy({
			emitFacade: true,
			host
		});

		component.addTarget(mockedTarget);

		host.emit(eventNameToEmit, eventObjectToEmit, eventFacadeObjectToEmit);

		expect(spy).toHaveBeenCalledWith(eventNameToEmit, eventObjectToEmit);
		expect(emitFacade).toEqual(
			mockedTarget._yuievt.events[eventNameToEmit].emitFacade
		);

		done();
	});

	it('adapts the events according to specified RegExp', done => {
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

		const component = new CompatibilityEventProxy({
			adaptedEvents: {
				match: /(.*)(Changed)$/,
				replace: '$1Change'
			},
			host
		});

		component.addTarget(mockedTarget);

		host.emit(eventNameToEmit, eventObjectToEmit, eventFacadeObjectToEmit);

		expect(spy.mock.calls[0][0]).toEqual(adaptedEventNameToEmit);

		done();
	});

	it('emits events even if the event does not have a key property', done => {
		const eventObjectToEmit = {};

		const mockedTarget = createMockedTarget(eventNameToEmit);

		const spy = jest.spyOn(mockedTarget, 'fire');

		const component = new CompatibilityEventProxy({
			host
		});

		component.addTarget(mockedTarget);

		host.emit(eventNameToEmit, eventObjectToEmit, eventFacadeObjectToEmit);

		expect(spy).toHaveBeenCalledWith(eventNameToEmit, eventObjectToEmit);

		done();
	});
});
