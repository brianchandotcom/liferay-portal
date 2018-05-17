import {
	isDefAndNotNull,
	isString
} from 'metal';

// Constants for URL generation

const CACHE_LEVEL = 'p_p_cacheability';

const HUB = 'p_p_hub';

const HUB_ACTION = '0';

const HUB_PARTIAL_ACTION = '1';

const HUB_RESOURCE = '2';

const PORTLET_MODE = 'p_p_mode';

const PUBLIC_RENDER_PARAM = 'p_r_p_';

const RENDER_PARAM = 'priv_r_p_';

const RESOURCE_ID = 'p_p_resource_id';

const TOKEN_DELIM = '&';

const VALUE_ARRAY_EMPTY = '';

const VALUE_DELIM = '=';

const VALUE_NULL = '';

const WINDOW_STATE = 'p_p_state';

/**
 * Decodes the update strings.
 * The update string is a JSON object containing the entire page state.
 * This decoder returns an object containing the portlet data for portlets whose
 * state has changed as compared to the current page state.
 * @param {Object} pageRenderState The page render state.
 * @param {string} updateString The update string to decode.
 * @return {Object}
 * @review
 */

const decodeUpdateString = function(pageRenderState, updateString) {
	const portlets = {};

	try {
		const newRenderState = JSON.parse(updateString);
		const portlets = pageRenderState.portlets;

		if (newRenderState.portlets && portlets) {
			const keys = Object.keys(portlets);

			for (const key of keys) {
				const newState = newRenderState.portlets[key].state;
				const oldState = portlets[key].state;

				if (!newState || !oldState) {
					throw new Error(`Invalid update string.\nold state=${oldState}\nnew state=${newState}`);
				}

				if (stateChanged(newState, key)) {
					portlets[key] = newRenderState.portlets[key];
				}
			}
		}

	}
	catch (e) {
	}

	return portlets;
};

/**
 * Function to extract data from form and encode
 * it as an 'application/x-www-form-urlencoded' string.
 * @param {string} portletId The portlet ID.
 * @param {HTMLFormElement} form Form to be submitted.
 * @review
 */

const encodeFormAsString = function(portletId, form) {
	const parameters = [];

	for (let i = 0; i < form.elements.length; i++) {
		const element = form.elements[i];
		const name = element.name;
		const tag = element.nodeName.toUpperCase();
		const type = tag === 'INPUT' ? element.type.toUpperCase() : '';
		const value = element.value;

		if (name && !element.disabled && type !== 'FILE') {
			if (tag === 'SELECT' && element.multiple) {
				const options = [...element.options];

				options.forEach(
					opt => {
						if (opt.checked) {
							const value = opt.value;

							const parameter = encodeURIComponent(portletId + name) + '=' + encodeURIComponent(value);

							parameters.push(parameter);
						}
					}
				);
			}
			else if (
				(type !== 'CHECKBOX' && type !== 'RADIO') || element.checked) {
				let param = encodeURIComponent(portletId + name) + '=' + encodeURIComponent(value);
				parameters.push(param);
			}
		}
	}

	return parameters.join('&');
};

/**
 * Helper for encoding a multi valued parameter.
 * @param {string} name The parameter's name.
 * @param {Array.<string>} values The parameter's value.
 * @return {string}
 * @review
 */

const encodeParameter = function(name, values) {
	let str = '';

	if (Array.isArray(values)) {
		if (values.length === 0) {
			str += TOKEN_DELIM + encodeURIComponent(name) + VALUE_DELIM + VALUE_ARRAY_EMPTY;
		}
		else {
			for (let value of values) {
				str += TOKEN_DELIM + encodeURIComponent(name);
				if (value === null) {
					str += VALUE_DELIM + VALUE_NULL;
				}
				else {
					str += VALUE_DELIM + encodeURIComponent(value);
				}
			}
		}
	}
	return str;
};

/**
 * Helper for generating parameter strings for the URL
 * @param {Object} pageRenderState The page render state.
 * @param {string} portletId The portlet ID.
 * @param {string} name The parameter's name.
 * @param {string} type The parameter's type.
 * @param {string} group The parameter's group.
 * @review
 */

const	generateParameterString = function(pageRenderState, portletId, name, type, group) {
	let str = '';

	if (pageRenderState.portlets && pageRenderState.portlets[portletId]) {
		const portletData = pageRenderState.portlets[portletId];

		if (portletData && portletData.state && portletData.state.parameters) {
			const values = portletData.state.parameters[name];

			if (values !== undefined) {

				// If values are present, encode the mutlivalued parameter string

				if (type === PUBLIC_RENDER_PARAM) {
					str += encodeParameter(group, values);
				}
				else if (type === RENDER_PARAM) {
					str += encodeParameter(RENDER_PARAM + name, values);
				}
				else {
					str += encodeParameter(portletId + name, values);
				}
			}
		}
	}

	return str;
};

/**
 * Helper for generating portlet mode & window state strings for the URL.
 * @param {Object} pageRenderState The page render state.
 * @param {string} portletId The portlet ID.
 * @return {string}
 * @review
 */

const generatePortletModeAndWindowStateString = function(pageRenderState, portletId) {
	let str = '';

	if (pageRenderState.portlets) {
		const portletData = pageRenderState.portlets[portletId];

		if (portletData.state) {
			const state = portletData.state;

			str += TOKEN_DELIM + PORTLET_MODE + VALUE_DELIM + encodeURIComponent(state.portletMode);
			str += TOKEN_DELIM + WINDOW_STATE + VALUE_DELIM + encodeURIComponent(state.windowState);
		}
	}

	return str;
};

/**
 * Gets the updated public parameters for the given portlet ID and new render state.
 * Returns an object whose properties are the group indexes of the
 * updated public parameters. The values are the new public parameter values.
 * @param {Object} pageRenderState The page render state.
 * @param {string} portletId The portlet ID.
 * @param {RenderState} state The new render state.
 * @return {Object} Object containing the updated public render parameters.
 * @review
 */

const getUpdatedPublicRenderParameters = function(pageRenderState, portletId, state) {
	const publicRenderParameters = {};

	if (pageRenderState && pageRenderState.portlets) {

		const portletData = pageRenderState.portlets[portletId];

		if (portletData && portletData.pubParams) {

			const portletPublicParameters = portletData.pubParams;

			const keys = Object.keys(portletPublicParameters);

			for (let key of keys) {
				if (!isParameterInStateEqual(pageRenderState, portletId, state, key)) {
					const group = portletPublicParameters[key];

					publicRenderParameters[group] = state.parameters[name];
				}
			}
		}
	}

	return publicRenderParameters;
};

/**
 * Returns a URL of the specified type.
 * @param {Object} pageRenderState The page render state.
 * @param {string} type The URL type.
 * @param {string} portletId The portlet ID.
 * @param {Object} parameters Additional parameters. May be <code>null</code>.
 * @param {string} cache Cacheability. Must be present if type = "RESOURCE". May be <code>null</code>.
 * @param {string} resourceId Resource ID. May be present if type = "RESOURCE". May be <code>null</code>.
 * @return {Promise} A promise that resolves the generated URL.
 * @review
 */

const getUrl = function(pageRenderState, type, portletId, parameters, cache, resourceId) {
	let cacheability = 'cacheLevelPage';
	let url = '';

	if (pageRenderState && pageRenderState.portlets) {

		// If target portlet not defined for render URL, set it to null

		if (type === 'RENDER' && portletId === undefined) {
			portletId = null;
		}

		const portletData = pageRenderState.portlets[portletId];

		if (portletData) {

			if (type === 'RESOURCE') {
				url = decodeURIComponent(portletData.encodedResourceURL);

				if (cache) {
					cacheability = cache;
				}

				url += TOKEN_DELIM + HUB + VALUE_DELIM + encodeURIComponent(HUB_RESOURCE);
				url += TOKEN_DELIM + CACHE_LEVEL + VALUE_DELIM + encodeURIComponent(cacheability);

				if (resourceId) {
					url += TOKEN_DELIM + RESOURCE_ID + VALUE_DELIM + encodeURIComponent(resourceId);
				}
			}
			else if (type === 'RENDER' && portletId !== null) {
				url = decodeURIComponent(portletData.encodedRenderURL);
			}
			else if (type === 'RENDER') {
				url = decodeURIComponent(pageRenderState.encodedCurrentURL);
			}
			else if (type === 'ACTION') {
				url = decodeURIComponent(portletData.encodedActionURL);
				url += TOKEN_DELIM + HUB + VALUE_DELIM + encodeURIComponent(HUB_ACTION);
			}
			else if (type === 'PARTIAL_ACTION') {
				url = decodeURIComponent(portletData.encodedActionURL);
				url += TOKEN_DELIM + HUB + VALUE_DELIM + encodeURIComponent(HUB_PARTIAL_ACTION);
			}

			// Now add the state to the URL, taking into account cacheability if
			// we're dealing with a resource URL.

			// Put the private & public parameters on the URL if cacheability != FULL

			if (type !== 'RESOURCE' || cacheability !== 'cacheLevelFull') {

				// Add the state for the target portlet, if there is one.
				// (for the render URL, pid can be null, and the state will have
				// been added previously)

				if (portletId) {
					if (portletData.state && portletData.state.parameters) {
						const stateParameters = portletData.state.parameters;

						let str = '';

						const keys = Object.keys(stateParameters);

						for (let key in keys) {
							if (!isPublicParameter(pageRenderState, portletId, key)) {
								str += generateParameterString(pageRenderState, portletId, key, RENDER_PARAM);
							}
						}
						url += str;
					}
				}

				// Add the public render parameters for all portlets

				if (pageRenderState.prpMap) {
					const publicRenderParameters = {};

					let str = '';

					const mapKeys = Object.keys(pageRenderState.prpMap);
					for (let mapKey of mapKeys) {
						const groupKeys = Object.keys(pageRenderState.prpMap[mapKey]);
						for (let groupKey of groupKeys) {
							const groupName = pageRenderState.prpMap[mapKey][groupKey];
							const parts = groupName.split('|');

							// Only need to add parameter once, since it is shared

							if (!publicRenderParameters.hasOwnProperty(mapKey)) {
								publicRenderParameters[mapKey] = generateParameterString(
									pageRenderState,
									parts[0],
									parts[1],
									PUBLIC_RENDER_PARAM,
									mapKey
								);

								str += publicRenderParameters[mapKey];
							}
						}
					}
					url += str;
				}
			}
		}
	}

	if (parameters) {
		let str = '';

		const parameterKeys = Object.keys(parameters);
		for (let parameterKey of parameterKeys) {
			str += encodeParameter(portletId + parameterKey, parameters[parameterKey]);
		}

		url += str;
	}

	return Promise.resolve(url);
};

/**
 * Compares two parameters and returns a boolean indicating if they're equal
 * or not.
 * @param {?Array.<string>} parameter1 The first parameter to compare.
 * @param {?Array.<string>} parameter2 The second parameter to compare.
 * @return {boolean}
 * @review
 */

const isParameterEqual = function(parameter1, parameter2) {
	let result = false;

	// The values are either string arrays or undefined.

	if (parameter1 === undefined && parameter2 === undefined) {
		result = true;
	}

	if (parameter1 === undefined || parameter2 === undefined) {
		result = false;
	}

	if (parameter1.length !== parameter2.length) {
		result = false;
	}

	for (let i = parameter1.length - 1; i >= 0; i--) {
		if (parameter1[i] !== parameter2[i]) {
			result = false;
		}
	}

	return result;
};

/**
 * Compares the values of the named parameter in the new render state
 * with the values of that parameter in the current state.
 * @param {Object} pageRenderState The page render state.
 * @param {string} portletId The portlet ID.
 * @param {RenderState} state The new render state.
 * @param {string} name The name of the parameter to check.
 * @return {boolean} True if the new parameter's value is different from the current value.
 * @review
 */

const isParameterInStateEqual = function(pageRenderState, portletId, state, name) {
	let result = false;

	if (pageRenderState && pageRenderState.portlets) {

		const portletData = pageRenderState.portlets[portletId];

		if (state.parameters[name] && portletData.state.parameters[name]) {
			const newParameter = state.parameters[name];
			const oldParameter = portletData.state.parameters[name];

			result = isParameterEqual(newParameter, oldParameter);
		}
	}

	return result;
};

/**
 * Function for checking if a parameter is public.
 * @param {Object} pageRenderState The page render state.
 * @param {string} portletId  The portlet ID.
 * @param {string} name  The name of the parameter to check.
 * @return {boolean}
 * @review
 */

const isPublicParameter = function(pageRenderState, portletId, name) {
	let result = false;

	if (pageRenderState && pageRenderState.portlets) {
		const portletData = pageRenderState.portlets[portletId];

		if (portletData && portletData.pubParams) {
			const keys = Object.keys(portletData.pubParams);
			result = keys.indexOf(name) !== -1;
		}
	}

	return result;
};

/**
 * Returns true if input state differs from the current page state.
 * Throws exception if input state is malformed.
 * @param {Object} pageRenderState The (current) page render state.
 * @param {RenderState} newState The new state to be set.
 * @param {string} portletId The portlet ID.
 * @return {boolean}  True if the two state are different.
 * @review
 */

const stateChanged = function(pageRenderState, newState, portletId) {
	let result = false;

	if (pageRenderState && pageRenderState.portlets) {
		const portletData = pageRenderState.portlets[portletId];

		if (portletData) {
			const oldState = pageRenderState.portlets[portletId].state;

			if (!newState.portletMode || !newState.windowState || !newState.parameters) {
				throw new Error(`Error decoding state: ${newState}`);
			}

			if (newState.porletMode !== oldState.portletMode || newState.windowState !== oldState.windowState) {
				result = true;
			}
			else {

				// Has a parameter changed or been added?

				const newKeys = Object.keys(newState.parameters);
				for (const key of newKeys) {
					const newParameter = newState.parameters[key];
					const oldParameter = oldState.parameters[key];

					if (!isParameterEqual(newParameter, oldParameter)) {
						result = true;
					}
				}

				// Make sure no parameter was deleted

				const oldKeys = Object.keys(oldState.parameters);
				for (const key of oldKeys) {
					if (!newState.parameters[key]) {
						result = true;
					}
				}
			}
		}
	}

	return result;
};

/**
 * Validates an HTMLFormElement
 * @param {?HTMLFormElement} form The form element to be validated.
 * @throws {TypeError} Thrown if the form is not an HTMLFormElement.
 * @throws {TypeError} Thrown if the form's method attribute is not valid.
 * @throws {TypeError} Thrown if the form's enctype attribute is not valid.
 * @throws {TypeError} Thrown if the form's enctype attribute is not valid.
 * @review
 */

const validateForm = function(form) {
	if (!(form instanceof HTMLFormElement)) {
		throw new TypeError('Element must be an HTMLFormElement');
	}

	const method = form.method ? form.method.toUpperCase() : undefined;

	if (method && method !== 'GET' && method !== 'POST') {
		throw new TypeError(`Invalid form method ${method}. Allowed methods are GET & POST`);
	}

	const enctype = form.enctype;

	if (enctype	&& enctype !== 'application\/x-www-form-urlencoded' && enctype !== 'multipart\/form-data') {
		throw new TypeError(`Invalid form enctype ${enctype}. Allowed: 'application\/x-www-form-urlencoded' & 'multipart\/form-data'`);
	}

	if (enctype && enctype === 'multipart\/form-data' && method !== 'POST') {
		throw new TypeError('Invalid method with multipart/form-data. Must be POST');
	}

	if (!enctype || enctype === 'application\/x-www-form-urlencoded') {
		const l = form.elements.length;

		for (let i = 0; i < l; i++) {
			if (form.elements[i].nodeName.toUpperCase() === 'INPUT' && form.elements[i].type.toUpperCase() === 'FILE') {
				throw new TypeError('Must use enctype = \'multipart/form-data\' with input type FILE.');
			}
		}
	}
};

/**
 * Verifies that the input parameters are in valid format.
 *
 * Parameters must be an object containing parameter names. It may also
 * contain no property names which represents the case of having no
 * parameters.
 *
 * If properties are present, each property must refer to an array of string
 * values. The array length must be at least 1, because each parameter must
 * have a value. However, a value of 'null' may appear in any array entry.
 *
 * To represent a <code>null</code> value, the property value must equal [null].
 *
 * @param {Object} parameters The parameters to check.
 * @throws {TypeError} Thrown if the parameters are incorrect.
 * @review
 */

const validateParameters = function(parameters) {
	if (!isDefAndNotNull(parameters)) {
		throw new TypeError(`The parameter object is: ${typeof parameters}`);
	}

	const keys = Object.keys(parameters);
	for (let key of keys) {
		if (!Array.isArray(parameters[key])) {
			throw new TypeError(`${key} parameter is not an array`);
		}

		if (!parameters[key].length) {
			throw new TypeError(`${key} parameter is an empty array`);
		}
	}
};

/**
 * Verifies that the input parameters are in valid format, that the portlet
 * mode and window state values are allowed for the portlet.
 * @param {RenderState} state The render state object to check.
 * @param {Object} portletData The porltet render state.
 * @throws {TypeError} Thrown if any component of the state is incorrect.
 * @review
 */

const validateState = function(state, portletData) {
	validateParameters(state.parameters);

	const portletMode = state.portletMode;

	if (!isString(portletMode)) {
		throw new TypeError(`Invalid parameters. portletMode is ${typeof portletMode}`);
	}
	else {
		const allowedPortletModes = portletData.allowedPM;

		if (!allowedPortletModes.includes(portletMode.toLowerCase())) {
			throw new TypeError(`Invalid portletMode=${portletMode} is not in ${allowedPortletModes}`);
		}
	}

	const windowState = state.windowState;

	if (!isString(windowState)) {
		throw new TypeError(`Invalid parameters. windowState is ${typeof windowState}`);
	}
	else {
		const allowedWindowStates = portletData.allowedWS;

		if (!allowedWindowStates.includes(windowState.toLowerCase())) {
			throw new TypeError(`Invalid windowState=${windowState} is not in ${allowedWindowStates}`);
		}
	}
};

export {
	decodeUpdateString,
	encodeFormAsString,
	generatePortletModeAndWindowStateString,
	getUpdatedPublicRenderParameters,
	getUrl,
	validateForm,
	validateParameters,
	validateState
};