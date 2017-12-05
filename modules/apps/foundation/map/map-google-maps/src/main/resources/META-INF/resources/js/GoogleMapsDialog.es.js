import State, {Config} from 'metal-state';

/**
 * GoogleMapsDialog
 */
class GoogleMapsDialog extends State {
	/**
	 * Creates a new map dialog using Google Map's API
	 * @param  {Array} args List of arguments to be passed to State
	 */
	constructor(...args) {
		super(...args);

		this._dialog = google.maps.InfoWindow();
	}

	/**
	 * Opens the dialog with the given map attribute and passes
	 * the given configuration to the dialog object.
	 * @param {Object} cfg
	 */
	open(cfg) {
		this._dialog.setOptions(cfg);

		this._dialog.open(this.map, cfg.marker);
	}
}

/**
 * State definition.
 * @type {!Object}
 * @static
 */
GoogleMapsDialog.STATE = {
	/**
	 * Map used for creating the dialog content
	 * @type {Object}
	 */
	map: Config.object(),
};

export default GoogleMapsDialog;
export {GoogleMapsDialog};