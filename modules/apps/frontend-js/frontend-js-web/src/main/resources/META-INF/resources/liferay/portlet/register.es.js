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

import PortletInit from './PortletInit.es';
import {validateArguments, validatePortletId} from './portlet_util.es';

/**
 * Registers a portlet client with the portlet hub.
 * @param {string} portletId The unique portlet identifier
 * @return {Promise} A Promise object. Returns an {@link PortletInit} object
 * containing functions for use by the portlet client on successful resolution.
 * Returns an Error object containing a descriptive message on failure.
 * @review
 */

const register = function(portletId) {
	validateArguments(arguments, 1, 1, ['string']);

	const pageRenderState = global.portlet.data.pageRenderState;

	return new Promise((resolve, reject) => {
		if (validatePortletId(pageRenderState, portletId)) {
			resolve(new PortletInit(portletId));
		}
		else {
			reject(new Error('Invalid portlet ID'));
		}
	});
};

export {register};
export default register;
