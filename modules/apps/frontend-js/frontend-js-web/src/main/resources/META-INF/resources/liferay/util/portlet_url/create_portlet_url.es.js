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

import getPortletNamespace from '../get_portlet_namespace.es';

const SCHEME_REGEXP = /^[a-z][a-z0-9+.-]*:/i;

function isAbsolute_(urlString) {
	return SCHEME_REGEXP.test(urlString);
}

/**
 * Returns a portlet URL in form of a URL Object
 * @param {!string} basePortletURL The base portlet URL to be modified in this utility
 * @param {object} parameters Search parameters to be added or changed in the base URL
 * @return {URL} Portlet URL Object
 * @review
 */
export default function createPortletURL(basePortletURL, parameters = {}) {
	if (typeof basePortletURL !== 'string') {
		throw new TypeError('basePortletURL parameter must be a string');
	}

	if (!parameters || typeof parameters !== 'object') {
		throw new TypeError('parameters argument must be an object');
	}

	const reservedParameters = new Set([
		'doAsGroupId',
		'doAsUserId',
		'doAsUserLanguageId',
		'p_auth',
		'p_auth_secret',
		'p_f_id',
		'p_j_a_id',
		'p_l_id',
		'p_l_reset',
		'p_p_auth',
		'p_p_cacheability',
		'p_p_i_id',
		'p_p_id',
		'p_p_isolated',
		'p_p_lifecycle',
		'p_p_mode',
		'p_p_resource_id',
		'p_p_state',
		'p_p_state_rcv',
		'p_p_static',
		'p_p_url_type',
		'p_p_width',
		'p_t_lifecycle',
		'p_v_l_s_g_id',
		'refererGroupId',
		'refererPlid',
		'saveLastPath',
		'scroll'
	]);

	if (
		basePortletURL.indexOf(Liferay.ThemeDisplay.getPortalURL()) !== 0 &&
		!isAbsolute_(basePortletURL)
	) {
		if (basePortletURL.indexOf('/') !== 0) {
			basePortletURL = `${Liferay.ThemeDisplay.getPortalURL()}/${basePortletURL}`;
		}
		else {
			basePortletURL =
				Liferay.ThemeDisplay.getPortalURL() + basePortletURL;
		}
	}

	const portletURL = new URL(basePortletURL);

	const urlSearchParams = new URLSearchParams(portletURL.search);

	const portletID = parameters.p_p_id || urlSearchParams.get('p_p_id');

	if (Object.entries(parameters).length && !portletID) {
		throw new TypeError(
			'Portlet ID must not be null if parameters are provided'
		);
	}

	let namespace = '';

	if (Object.entries(parameters).length) {
		namespace = getPortletNamespace(portletID);
	}

	Object.keys(parameters).forEach(key => {
		let param;

		if (reservedParameters.has(key)) {
			param = key;
		}
		else {
			param = `${namespace}${key}`;
		}

		urlSearchParams.set(param, parameters[key]);
	});

	portletURL.search = urlSearchParams.toString();

	return portletURL;
}
