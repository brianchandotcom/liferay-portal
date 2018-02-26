'use strict';

import Promise from 'metal-promise';

import PortletInit from './PortletInit.es';

/**
 * Registers a portlet client with the portlet hub.
 *
 * @param {string} portletId The unique portlet identifier
 * @returns {Promise} A Promise object. Returns an {@link PortletInit} object
 * containing functions for use by the portlet client on successful resolution.
 * Returns an Error object containing a descriptive message on failure.
 * @review
 */

function register(portletId) {
	return new Promise(
		(resolve, reject) => {
			if (!portletId) {
				reject(new Error('Invalid portlet ID'));
			}
			else {
				resolve(new PortletInit(portletId));
			}
		}
	);
}

export {register};
export default register;