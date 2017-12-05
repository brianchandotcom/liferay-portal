import MarkerBase from 'map-common/js/MarkerBase.es';

/**
 * GoogleMapsMarker
 */
class GoogleMapsMarker extends MarkerBase {
	/**
	 * If a marked has been created, sets the marker location to the given one
	 * @param {Object} location Location to set the native marker in
	 */
	setPosition(location) {
		if (this._nativeMarker) {
			this._nativeMarker.setPosition(location);
		}
	}

	/**
	 * @inheritDoc
	 */
	_getNativeMarker(location, map) {
		if (!this._nativeMarker) {
			this._nativeMarker = new google.maps.Marker(
				{
					draggable: true,
					map: map,
					position: location,
				}
			);

			google.maps.event.addListener(
				this._nativeMarker,
				'click',
				this._getNativeEventFunction('click')
			);

			google.maps.event.addListener(
				this._nativeMarker,
				'dblclick',
				this._getNativeEventFunction('dblclick')
			);

			google.maps.event.addListener(
				this._nativeMarker,
				'drag',
				this._getNativeEventFunction('drag')
			);

			google.maps.event.addListener(
				this._nativeMarker,
				'dragend',
				this._getNativeEventFunction('dragend')
			);

			google.maps.event.addListener(
				this._nativeMarker,
				'dragstart',
				this._getNativeEventFunction('dragstart')
			);

			google.maps.event.addListener(
				this._nativeMarker,
				'mousedown',
				this._getNativeEventFunction('mousedown')
			);

			google.maps.event.addListener(
				this._nativeMarker,
				'mouseout',
				this._getNativeEventFunction('mouseout')
			);

			google.maps.event.addListener(
				this._nativeMarker,
				'mouseover',
				this._getNativeEventFunction('mouseover')
			);
		}

		return this._nativeMarker;
	}

	/**
	 * @inheritDoc
	 */
	_getNormalizedEventData(nativeEvent) {
		return {
			location: {
				lat: nativeEvent.latLng.lat(),
				lng: nativeEvent.latLng.lng(),
			},
		};
	}
}

export default GoogleMapsMarker;
export {GoogleMapsMarker};