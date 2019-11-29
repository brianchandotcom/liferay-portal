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

import {isDefAndNotNull, isString} from 'metal';

// Constants for URL generation

const AJAX_ACTION_VALUE = '0';

const CACHE_LEVEL_KEY = 'p_p_cacheability';

const HUB_ACTION_KEY = 'p_p_hub';

const PARTIAL_ACTION_VALUE = '1';

const PORTLET_MODE_KEY = 'p_p_mode';

const PUBLIC_RENDER_PARAM_KEY = 'p_r_p_';

const RENDER_PARAM_KEY = 'priv_r_p_';

const RESOURCE_ID_KEY = 'p_p_resource_id';

const TOKEN_DELIM = '&';

const VALUE_ARRAY_EMPTY = '';

const VALUE_DELIM = '=';

const VALUE_NULL = '';

const WINDOW_STATE_KEY = 'p_p_state';

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
	const portlets =
		pageRenderState && pageRenderState.portlets
			? pageRenderState.portlets
			: {};

	try {
		const newRenderState = JSON.parse(updateString);

		if (newRenderState.portlets) {
			const keys = Object.keys(portlets);

			keys.forEach(key => {
				const newState = newRenderState.portlets[key].state;
				const oldState = portlets[key].state;

				if (!newState || !oldState) {
					throw new Error(
						`Invalid update string.\nold state=${oldState}\nnew state=${newState}`
					);
				}

				if (stateChanged(pageRenderState, newState, key)) {
					portlets[key] = newRenderState.portlets[key];
				}
			});
		}
	}
	catch (e) {
		// Do nothing
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

				options.forEach(opt => {
					if (opt.checked) {
						const value = opt.value;

						const parameter =
							encodeURIComponent(portletId + name) +
							'=' +
							encodeURIComponent(value);

						parameters.push(parameter);
					}
				});
			}
			else if (
				(type !== 'CHECKBOX' && type !== 'RADIO') ||
				element.checked
			) {
				const param =
					encodeURIComponent(portletId + name) +
					'=' +
					encodeURIComponent(value);
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
			str +=
				TOKEN_DELIM +
				encodeURIComponent(name) +
				VALUE_DELIM +
				VALUE_ARRAY_EMPTY;
		}
		else {
			values.forEach(value => {
				str += TOKEN_DELIM + encodeURIComponent(name);

				if (value === null) {
					str += VALUE_DELIM + VALUE_NULL;
				}
				else {
					str += VALUE_DELIM + encodeURIComponent(value);
				}
			});
		}
	}

	return str;
};

/**
 * Generates the required options for an action URL request
 * according to the portletId, action URL and optional form element.
 *
 * @param {string} portletId The id of the portlet.
 * @param {string} url The action url.
 * @param {HTMLFormElement} The form element.
 * @return {Object}
 * @review
 */

const generateActionUrl = function(portletId, url, form) {
	const request = {
		credentials: 'same-origin',
		method: 'POST',
		url
	};

	if (form) {
		const enctype = form.enctype;

		if (enctype === 'multipart/form-data') {
			const formData = new FormData(form);

			request.body = formData;
		}
		else {
			const formAsString = encodeFormAsString(portletId, form);
			const method = form.method ? form.method.toUpperCase() : 'GET';

			if (method === 'GET') {
				if (url.indexOf('?') >= 0) {
					url += `&${formAsString}`;
				}
				else {
					url += `?${formAsString}`;
				}

				request.url = url;
			}
			else {
				request.body = formAsString;
				request.headers = {
					'Content-Type': 'application/x-www-form-urlencoded'
				};
			}
		}
	}

	return request;
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

const generateParameterString = function(
	pageRenderState,
	portletId,
	name,
	type,
	group
) {
	let str = '';

	if (pageRenderState.portlets && pageRenderState.portlets[portletId]) {
		const portletData = pageRenderState.portlets[portletId];

		if (portletData && portletData.state && portletData.state.parameters) {
			const values = portletData.state.parameters[name];

			if (values !== undefined) {
				// If values are present, encode the mutlivalued parameter string

				if (type === PUBLIC_RENDER_PARAM_KEY) {
					str += encodeParameter(group, values);
				}
				else if (type === RENDER_PARAM_KEY) {
					str += encodeParameter(
						portletId + RENDER_PARAM_KEY + name,
						values
					);
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

const generatePortletModeAndWindowStateString = function(
	pageRenderState,
	portletId
) {
	let str = '';

	if (pageRenderState.portlets) {
		const portletData = pageRenderState.portlets[portletId];

		if (portletData.state) {
			const state = portletData.state;

			str +=
				TOKEN_DELIM +
				PORTLET_MODE_KEY +
				VALUE_DELIM +
				encodeURIComponent(state.portletMode);
			str +=
				TOKEN_DELIM +
				WINDOW_STATE_KEY +
				VALUE_DELIM +
				encodeURIComponent(state.windowState);
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

const getUpdatedPublicRenderParameters = function(
	pageRenderState,
	portletId,
	state
) {
	const publicRenderParameters = {};

	if (pageRenderState && pageRenderState.portlets) {
		const portletData = pageRenderState.portlets[portletId];

		if (portletData && portletData.pubParms) {
			const portletPublicParameters = portletData.pubParms;

			const keys = Object.keys(portletPublicParameters);

			keys.forEach(key => {
				if (
					!isParameterInStateEqual(
						pageRenderState,
						portletId,
						state,
						key
					)
				) {
					const group = portletPublicParameters[key];

					publicRenderParameters[group] = state.parameters[key];
				}
			});
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

const getUrl = function(
	pageRenderState,
	type,
	portletId,
	parameters,
	cache,
	resourceId
) {
	let cacheability = 'cacheLevelPage';
	let str = '';
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

				url +=
					TOKEN_DELIM +
					CACHE_LEVEL_KEY +
					VALUE_DELIM +
					encodeURIComponent(cacheability);

				if (resourceId) {
					url +=
						TOKEN_DELIM +
						RESOURCE_ID_KEY +
						VALUE_DELIM +
						encodeURIComponent(resourceId);
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
				url +=
					TOKEN_DELIM +
					HUB_ACTION_KEY +
					VALUE_DELIM +
					encodeURIComponent(AJAX_ACTION_VALUE);
			}
			else if (type === 'PARTIAL_ACTION') {
				url = decodeURIComponent(portletData.encodedActionURL);
				url +=
					TOKEN_DELIM +
					HUB_ACTION_KEY +
					VALUE_DELIM +
					encodeURIComponent(PARTIAL_ACTION_VALUE);
			}

			// Now add the state to the URL, taking into account cacheability if
			// we're dealing with a resource URL.

			// Put the private & public parameters on the URL if cacheability != FULL

			if (type !== 'RESOURCE' || cacheability !== 'cacheLevelFull') {
				// Add the state for the target portlet, if there is one.
				// (for the render URL, pid can be null, and the state will have
				// been added previously)

				if (portletId) {
					url += generatePortletModeAndWindowStateString(
						pageRenderState,
						portletId
					);
				}

				// Add the state for the target portlet, if there is one.
				// (for the render URL, pid can be null, and the state will have
				// been added previously)

				if (portletId) {
					str = '';
					if (portletData.state && portletData.state.parameters) {
						const stateParameters = portletData.state.parameters;

						const keys = Object.keys(stateParameters);

						keys.forEach(key => {
							if (
								!isPublicParameter(
									pageRenderState,
									portletId,
									key
								)
							) {
								str += generateParameterString(
									pageRenderState,
									portletId,
									key,
									RENDER_PARAM_KEY
								);
							}
						});

						url += str;
					}
				}

				// Add the public render parameters for all portlets

				if (pageRenderState.prpMap) {
					str = '';

					const publicRenderParameters = {};

					const mapKeys = Object.keys(pageRenderState.prpMap);

					mapKeys.forEach(mapKey => {
						const groupKeys = Object.keys(
							pageRenderState.prpMap[mapKey]
						);

						groupKeys.forEach(groupKey => {
							const groupName =
								pageRenderState.prpMap[mapKey][groupKey];

							const parts = groupName.split('|');

							// Only need to add parameter once, since it is shared

							if (
								!Object.hasOwnProperty.call(
									publicRenderParameters,
									mapKey
								)
							) {
								publicRenderParameters[
									mapKey
								] = generateParameterString(
									pageRenderState,
									parts[0],
									parts[1],
									PUBLIC_RENDER_PARAM_KEY,
									mapKey
								);

								str += publicRenderParameters[mapKey];
							}
						});
					});

					url += str;
				}
			}
		}
	}

	// Encode resource or action parameters

	if (parameters) {
		str = '';
		const parameterKeys = Object.keys(parameters);

		parameterKeys.forEach(parameterKey => {
			str += encodeParameter(
				portletId + parameterKey,
				parameters[parameterKey]
			);
		});

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

const isParameterInStateEqual = function(
	pageRenderState,
	portletId,
	state,
	name
) {
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

		if (portletData && portletData.pubParms) {
			const keys = Object.keys(portletData.pubParms);

			result = keys.includes(name);
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

			if (
				!newState.portletMode ||
				!newState.windowState ||
				!newState.parameters
			) {
				throw new Error(`Error decoding state: ${newState}`);
			}

			if (
				newState.porletMode !== oldState.portletMode ||
				newState.windowState !== oldState.windowState
			) {
				result = true;
			}
			else {
				// Has a parameter changed or been added?

				const newKeys = Object.keys(newState.parameters);

				newKeys.forEach(key => {
					const newParameter = newState.parameters[key];
					const oldParameter = oldState.parameters[key];

					if (!isParameterEqual(newParameter, oldParameter)) {
						result = true;
					}
				});

				// Make sure no parameter was deleted

				const oldKeys = Object.keys(oldState.parameters);

				oldKeys.forEach(key => {
					if (!newState.parameters[key]) {
						result = true;
					}
				});
			}
		}
	}

	return result;
};

/**
 * Used by the portlet hub methods to check the number and types of the
 * arguments.
 *
 * @param {string[]} args The argument list to be checked.
 * @param {number} min The minimum number of arguments.
 * @param {number} max The maximum number of arguments. If this value is
 * undefined, the function can take any number of arguments greater
 * than max.
 * @param {string[]} types An array containing the expected parameter types in the
 * order of occurrance in the argument array.
 * @throws {TypeError} Thrown if the parameters are in some manner incorrect.
 * @review
 */

const validateArguments = function(args = [], min = 0, max = 1, types = []) {
	if (args.length < min) {
		throw new TypeError(
			`Too few arguments provided: Number of arguments: ${args.length}`
		);
	}
	else if (args.length > max) {
		throw new TypeError(
			`Too many arguments provided: ${[].join.call(args, ', ')}`
		);
	}
	else if (Array.isArray(types)) {
		let i = Math.min(args.length, types.length) - 1;

		for (i; i >= 0; i--) {
			if (typeof args[i] !== types[i]) {
				throw new TypeError(
					`Parameter ${i} is of type ${typeof args[
						i
					]} rather than the expected type ${types[i]}`
				);
			}

			if (args[i] === null || args[i] === undefined) {
				throw new TypeError(`Argument is ${typeof args[i]}`);
			}
		}
	}
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
		throw new TypeError(
			`Invalid form method ${method}. Allowed methods are GET & POST`
		);
	}

	const enctype = form.enctype;

	if (
		enctype &&
		enctype !== 'application/x-www-form-urlencoded' &&
		enctype !== 'multipart/form-data'
	) {
		throw new TypeError(
			`Invalid form enctype ${enctype}. Allowed: 'application/x-www-form-urlencoded' & 'multipart/form-data'`
		);
	}

	if (enctype && enctype === 'multipart/form-data' && method !== 'POST') {
		throw new TypeError(
			'Invalid method with multipart/form-data. Must be POST'
		);
	}

	if (!enctype || enctype === 'application/x-www-form-urlencoded') {
		const l = form.elements.length;

		for (let i = 0; i < l; i++) {
			if (
				form.elements[i].nodeName.toUpperCase() === 'INPUT' &&
				form.elements[i].type.toUpperCase() === 'FILE'
			) {
				throw new TypeError(
					"Must use enctype = 'multipart/form-data' with input type FILE."
				);
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

	keys.forEach(key => {
		if (!Array.isArray(parameters[key])) {
			throw new TypeError(`${key} parameter is not an array`);
		}

		if (!parameters[key].length) {
			throw new TypeError(`${key} parameter is an empty array`);
		}
	});
};

/**
 * Validates the specificed portletId against the list
 * of current portlet in the pageRenderState.
 *
 * @param {string} portletId The ID of the portlet to be registered.
 * @param {Object} pageRenderState The current pageRenderState.
 * @return {boolean} A flag indicating if the specified portlet id is valid.
 * @review
 */

const validatePortletId = function(pageRenderState = {}, portletId = '') {
	return (
		pageRenderState.portlets &&
		Object.keys(pageRenderState.portlets).includes(portletId)
	);
};

/**
 * Verifies that the input parameters are in valid format, that the portlet
 * mode and window state values are allowed for the portlet.
 * @param {RenderState} state The render state object to check.
 * @param {Object} portletData The porltet render state.
 * @throws {TypeError} Thrown if any component of the state is incorrect.
 * @review
 */

const validateState = function(state = {}, portletData = {}) {
	validateParameters(state.parameters);

	const portletMode = state.portletMode;

	if (!isString(portletMode)) {
		throw new TypeError(
			`Invalid parameters. portletMode is ${typeof portletMode}`
		);
	}
	else {
		const allowedPortletModes = portletData.allowedPM;

		if (!allowedPortletModes.includes(portletMode.toLowerCase())) {
			throw new TypeError(
				`Invalid portletMode=${portletMode} is not in ${allowedPortletModes}`
			);
		}
	}

	const windowState = state.windowState;

	if (!isString(windowState)) {
		throw new TypeError(
			`Invalid parameters. windowState is ${typeof windowState}`
		);
	}
	else {
		const allowedWindowStates = portletData.allowedWS;

		if (!allowedWindowStates.includes(windowState.toLowerCase())) {
			throw new TypeError(
				`Invalid windowState=${windowState} is not in ${allowedWindowStates}`
			);
		}
	}
};

export {
	decodeUpdateString,
	encodeFormAsString,
	generateActionUrl,
	generatePortletModeAndWindowStateString,
	getUpdatedPublicRenderParameters,
	getUrl,
	validateArguments,
	validateForm,
	validateParameters,
	validatePortletId,
	validateState
};
