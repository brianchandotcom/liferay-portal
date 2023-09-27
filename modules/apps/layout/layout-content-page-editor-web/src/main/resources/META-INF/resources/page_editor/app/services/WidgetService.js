/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {config} from '../config/index';
import serviceFetch from './serviceFetch';

export default {

	/**
	 * Adds a Widget to the current layout
	 * @param {object} options
	 * @param {function} options.onNetworkStatus
	 * @param {string} options.portletId Portlet id of the Widget
	 * @param {string} options.parentItemId id of the parent where the portlet is going to be added
	 * @param {string} options.position position where the portlet is going to be added
	 * @param {string} options.portletId Portlet id of the Widget
	 * @param {string} options.segmentsExperienceId Current segmentsExperienceId
	 * @return {Promise<object>}
	 */
	addPortlet({
		onNetworkStatus,
		parentItemId,
		portletId,
		portletItemId,
		position,
		segmentsExperienceId,
	}) {
		return serviceFetch(
			config.addPortletURL,
			{
				body: {
					parentItemId,
					portletId,
					portletItemId,
					position,
					segmentsExperienceId,
				},
			},
			onNetworkStatus,
			{requestGenerateDraft: true}
		);
	},

	getWidgets() {
		return serviceFetch(config.getPortletsURL, {}, () => {});
	},

	/**
	 * Mark a widget as highlighted if it wasn't, and unmark it if it was
	 * @param {object} options
	 * @param {string} options.portletId
	 * @param {boolean} options.highlighted
	 * @param {function} options.onNetworkStatus
	 */
	toggleWidgetHighlighted({highlighted, onNetworkStatus, portletId}) {
		return serviceFetch(
			config.updatePortletsHighlightedConfigurationURL,
			{
				body: {
					highlighted,
					portletId,
				},
			},
			onNetworkStatus,
			{requestGenerateDraft: true}
		);
	},
};
