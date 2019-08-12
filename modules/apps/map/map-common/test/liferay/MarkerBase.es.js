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

import MarkerBase from '../../src/main/resources/META-INF/resources/js/MarkerBase.es';

describe('MarkerBase', () => {
	let markerChild;

	class MarkerChild extends MarkerBase {
		_getNativeMarker(location, map) {
			return {location, map, name: 'NativeMarker'};
		}

		_getNormalizedEventData(nativeEvent) {
			return {name: 'NormalizedEventData', nativeEvent};
		}
	}

	beforeEach(() => {
		markerChild = new MarkerChild();

		jest.spyOn(markerChild, '_getNativeMarker');
		jest.spyOn(markerChild, '_getNormalizedEventData');
		jest.spyOn(markerChild, '_handleNativeEvent');
		jest.spyOn(markerChild, 'emit');
	});

	describe('_handleNativeEvent', () => {
		it('calls _getNormalizedEventData with the given event', () => {
			markerChild._handleNativeEvent(
				{name: 'NativeEvent'},
				'NonNativeEvent'
			);

			expect(markerChild._getNormalizedEventData).toHaveBeenCalledTimes(
				1
			);
			expect(markerChild._getNormalizedEventData.mock.calls[0]).toEqual([
				{name: 'NativeEvent'}
			]);
		});

		it('emits an event with the given event type', () => {
			markerChild._handleNativeEvent(
				{name: 'NativeEvent'},
				'NonNativeEvent'
			);

			expect(markerChild.emit).toHaveBeenCalledTimes(1);
			expect(markerChild.emit.mock.calls[0][0]).toBe('NonNativeEvent');
		});

		it('adds the normalized event data', () => {
			markerChild._handleNativeEvent(
				{name: 'NativeEvent'},
				'NonNativeEvent'
			);

			expect(markerChild.emit).toHaveBeenCalledTimes(1);
			expect(markerChild.emit.mock.calls[0][1]).toEqual({
				name: 'NormalizedEventData',
				nativeEvent: {name: 'NativeEvent'}
			});
		});
	});

	describe('_getNativeEventFunction', () => {
		it('returns a function', () => {
			const fn = markerChild._getNativeEventFunction('nativeEvent');

			expect(typeof fn === 'function');
		});

		it('returns a function that calls _handleNativeEvent when executed', () => {
			const fn = markerChild._getNativeEventFunction('nativeEvent');

			fn();

			expect(markerChild._handleNativeEvent).toHaveBeenCalledTimes(1);
		});

		it('returns a function that calls _handleNativeEvent with the given event object when executed', () => {
			const fn = markerChild._getNativeEventFunction('nativeEvent');

			fn({name: 'AwesomeNativeEvent'});

			expect(markerChild._handleNativeEvent).toHaveBeenCalledTimes(1);
			expect(markerChild._handleNativeEvent.mock.calls[0][0]).toEqual({
				name: 'AwesomeNativeEvent'
			});
		});

		it('returns a function that calls _handleNativeEvent with the given event type when executed', () => {
			const fn = markerChild._getNativeEventFunction('nativeEvent');

			fn();

			expect(markerChild._handleNativeEvent).toHaveBeenCalledTimes(1);
			expect(markerChild._handleNativeEvent.mock.calls[0][1]).toBe(
				'nativeEvent'
			);
		});
	});

	describe('_getNativeMarker', () => {
		it('throws a not implemented error', () => {
			expect(() => {
				new MarkerBase()._getNativeMarker();
			}).toThrow();
		});
	});

	describe('_getNormalizedEventData', () => {
		it('throws a not implemented error', () => {
			expect(() => {
				new MarkerBase()._getNormalizedEventData();
			}).toThrow();
		});
	});
});
