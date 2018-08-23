import Promise from 'metal-promise';

import PortletInit from './PortletInit.es';
import {
	validateArguments,
	validatePortletId
} from './portlet_util.es';

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

	return new Promise(
		(resolve, reject) => {
			if (validatePortletId(pageRenderState, portletId)) {
				resolve(new PortletInit(portletId));
			}
			else {
				reject(new Error('Invalid portlet ID'));
			}
		}
	);
};

export {register};
export default register;