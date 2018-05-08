import State, {Config} from 'metal-state';

/**
 * OpenStreetMapDialog
 * @review
 */
class OpenStreetMapDialog extends State {
	/**
	 * Creates a new map dialog using OpenStreetMap's API
	 * @param  {Array} args List of arguments to be passed to State
	 * @review
	 */
	constructor(...args) {
		super(...args);

		this._dialog = L.popup(
			{
				className: 'leaflet-popup',
				minWidth: 400,
			}
		);
	}

	/**
	 * Opens the dialog with the given map attribute and passes
	 * the given configuration to the dialog object.
	 * @param {Object} cfg
	 * @review
	 */
	open(cfg) {
		this._dialog.setContent(cfg.content);
		this._dialog.setLatLng(cfg.position);

		this._dialog.options.offset = cfg.marker.options.icon.options.popupAnchor || [0, 0];

		this._dialog.openOn(this.map);
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
OpenStreetMapDialog.STATE = {
	/**
	 * Map used for creating the dialog content
	 * @review
	 * @type {Object}
	 */
	map: Config.object(),
};

export default OpenStreetMapDialog;
export {OpenStreetMapDialog};