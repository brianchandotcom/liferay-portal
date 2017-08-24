/* eslint-disable require-jsdoc */
/* global sinon, assert */

'use strict';

import MarkerBase from '../../src/main/resources/META-INF/resources/js/MarkerBase.es';

describe('MarkerBase', () => {
	let markerChild;

	// Class extending the GeoJSONBase class
	// We need to implement some methods with dummy content
	class MarkerChild extends MarkerBase {
		_getNativeMarker(location, map) {
			return {name: 'NativeMarker', location, map};
		}

		_getNormalizedEventData(nativeEvent) {
			return {name: 'NormalizedEventData', nativeEvent};
		}
	}

	beforeEach(() => {
		markerChild = new MarkerChild();
		sinon.spy(markerChild, 'emit');
		sinon.spy(markerChild, '_handleNativeEvent');
		sinon.spy(markerChild, '_getNativeMarker');
		sinon.spy(markerChild, '_getNormalizedEventData');
	});

	describe('_handleNativeEvent', () => {
		it('should call _getNormalizedEventData with the given event', () => {
			markerChild._handleNativeEvent({name: 'NativeEvent'}, 'NonNativeEvent');
			assert(markerChild._getNormalizedEventData.calledOnce);
			assert.deepEqual(markerChild._getNormalizedEventData.firstCall.args, [
				{name: 'NativeEvent'},
			]);
		});

		it('should emit an event with the given event type', () => {
			markerChild._handleNativeEvent({name: 'NativeEvent'}, 'NonNativeEvent');
			assert(markerChild.emit.calledOnce);
			assert.equal(markerChild.emit.firstCall.args[0], 'NonNativeEvent');
		});

		it('should add the normalized event data', () => {
			markerChild._handleNativeEvent({name: 'NativeEvent'}, 'NonNativeEvent');
			assert(markerChild.emit.calledOnce);
			assert.deepEqual(markerChild.emit.firstCall.args[1], {
				name: 'NormalizedEventData',
				nativeEvent: {name: 'NativeEvent'},
			});
		});
	});

	describe('_getNativeEventFunction', () => {
		it('returns a function', () => {
			const fn = markerChild._getNativeEventFunction('nativeEvent');
			assert(typeof fn === 'function');
		});

		it('returns a function that calls _handleNativeEvent when executed', () => {
			const fn = markerChild._getNativeEventFunction('nativeEvent');
			fn();
			assert(markerChild._handleNativeEvent.calledOnce);
		});

				// eslint-disable-next-line max-len
		it('returns a function that calls _handleNativeEvent with the given event object when executed', () => {
			const fn = markerChild._getNativeEventFunction('nativeEvent');
			fn({name: 'AwesomeNativeEvent'});
			assert(markerChild._handleNativeEvent.calledOnce);
			assert.deepEqual(
				markerChild._handleNativeEvent.firstCall.args[0],
				{name: 'AwesomeNativeEvent'}
			);
		});

		// eslint-disable-next-line max-len
		it('returns a function that calls _handleNativeEvent with the given event type when executed', () => {
			const fn = markerChild._getNativeEventFunction('nativeEvent');
			fn();
			assert(markerChild._handleNativeEvent.calledOnce);
			assert.equal(markerChild._handleNativeEvent.firstCall.args[1], 'nativeEvent');
		});
	});

	describe('_getNativeMarker', () => {
		it('should throw a not implemented error', () => {
			assert.throw(() => new MarkerBase()._getNativeMarker());
		});
	});

	describe('_getNormalizedEventData', () => {
		it('should throw a not implemented error', () => {
			assert.throw(() => new MarkerBase()._getNormalizedEventData());
		});
	});
});