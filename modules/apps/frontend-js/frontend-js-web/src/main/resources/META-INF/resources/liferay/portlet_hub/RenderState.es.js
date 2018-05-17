import {isObject, isString} from 'metal';

import PortletConstants from './portlet_constants.es';

class RenderState {

	constructor(state) {
		if (isObject(state)) {
			this.from(state);
		}
		else {
			this.parameters = {};
			this.portletMode = PortletConstants.VIEW;
			this.windowState = PortletConstants.NORMAL;
		}
	}

	/**
	 * Clone returns a copy of this RenderState instance.
	 *
	 * @memberof RenderState
	 * @return {RenderState} A RenderState instance with same properties.
	 * @review
	 */

	clone() {
		return new RenderState(this);
	}

	/**
	 * Returns an instance of RenderState based on a RenderState
	 *
	 * @memberof RenderState
	 * @param {RenderState} renderState
	 * @review
	 */

	from(renderState) {
		this.parameters = {};

		for (let name in renderState.parameters) {
			if (renderState.parameters.hasOwnProperty(name) && Array.isArray(renderState.parameters[name])) {
				this.parameters[name] = renderState.parameters[name].slice(0);
			}
		}

		this.setPortletMode(renderState.portletMode);
		this.setWindowState(renderState.windowState);
	}

	/**
	 * Returns the portletMode for this RenderState.
	 *
	 * @memberof RenderState
	 * @return {String} The portletMode for this render state
	 * @review
	 */

	getPortletMode() {
		return this.portletMode;
	}

	/**
	 * Returns the string parameter value for the given name.
	 * If name designates a multi-valued parameter this function returns
	 * the first value in the values array. If the parameter is undefined
	 * this function returns the optional default parameter <code>defaultValue</code>.
	 *
	 * @memberof RenderState
	 * @param {String} name The name of the parameter to retreive.
	 * @param {?String} defaultValue  The default value of the parameter in case it is undefined.
	 * @return
	 * @review
	 */

	getValue(name, defaultValue) {
		if (!isString(name)) {
			throw new TypeError('Parameter name must be a string');
		}

		let value = this.parameters[name];

		if (Array.isArray(value)) {
			value = value[0];
		}

		if (value === undefined && defaultValue !== undefined) {
			value = defaultValue;
		}

		return value;
	}

	/**
	 * Gets the string array parameter value for the given <code>name</code>.
	 * If the parameter for the given name is undefined, this function
	 * returns the optional default value array <code>def</code>.
	 *
	 * @memberof RenderState
	 * @param {String} name  The name of the parameter to retrieve.
	 * @param {?Array} defaultValue   The default value for the parameter if it is undefined.
	 * @return {Array}
	 * @review
	 */

	getValues(name, defaultValue) {
		if (!isString(name)) {
			throw new TypeError('Parameter name must be a string');
		}

		const value = this.parameters[name];

		return value ? value : defaultValue;
	}

	/**
	 * Returns the windowState for this RenderState.
	 *
	 * @memberof RenderState
	 * @return {String} the window state for this render state
	 * @review
	 */

	getWindowState() {
		return this.windowState;
	}

	/**
	 * Removes the parameter with the given name.
	 *
	 * @memberof RenderState
	 * @param {String} name The name of the parameter to be removed.
	 * @review
	 */

	remove(name) {
		if (!isString(name)) {
			throw new TypeError('Parameter name must be a string');
		}

		if (this.parameters[name] !== undefined) {
			delete this.parameters[name];
		}
	}

	/**
	 * Sets the portletMode to the specified value.
	 *
	 * @memberof RenderState
	 * @param {String} portletMode The portlet mode to be set.
	 * @review
	 * @see {PortletConstants}
	 */

	setPortletMode(portletMode) {
		if (!isString(portletMode)) {
			throw new TypeError('Portlet Mode must be a string');
		}

		const mode = portletMode.toLowerCase();

		if (mode === PortletConstants.EDIT || mode === PortletConstants.HELP || mode === PortletConstants.VIEW) {
			this.portletMode = mode;
		}
	}

	/**
	 * Sets a parameter with a given name and value.
	 * The value may be a string or an array.
	 *
	 * @memberof RenderState
	 * @param {String} name	The name of the parameter.
	 * @param {Array|String} value  The value of the parameter.
	 * @review
	 */

	setValue(name, value) {
		if (!isString(name)) {
			throw new TypeError('Parameter name must be a string');
		}

		if (!isString(value) && value !== null && !Array.isArray(value)) {
			throw new TypeError('Parameter value must be a string, an array or null');
		}

		if (!Array.isArray(value)) {
			value = [value];
		}

		this.parameters[name] = value;
	}

	/**
	 * Sets a parameter with a given name and value.
	 * The value may be a string or an array.
	 *
	 * @memberof RenderState
	 * @param {String} name	The name of the parameter.
	 * @param {Array|String} value  The value of the parameter.
	 * @review
	 */

	setValues(name, value) {
		this.setValue(name, value);
	}

	/**
	 * Sets the windowState to the specified value.
	 *
	 * @memberof RenderState
	 * @param {String} windowSstate The window state to be set.
	 * @return {String}
	 * @review
	 * @see {PortletConstants}
	 */

	setWindowState(windowState) {
		if (!isString(windowState)) {
			throw new TypeError('Window State must be a string');
		}

		const state = windowState.toLowerCase();

		if (state === PortletConstants.MAXIMIZED || state === PortletConstants.MINIMIZED || state === PortletConstants.NORMAL) {
			this.windowState = state;
		}
	}
}

export {RenderState};
export default RenderState;