/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

/**
 * List of deltas in a shape compatible with ClayPaginationWithBar.
 */
export const DELTAS = [
	{
		label: 5,
	},
	{
		label: 10,
	},
	{
		label: 20,
	},
	{
		label: 40,
	},
	{
		label: 50,
	},
];

/**
 * Delta that will be initially selected.
 */
export const DEFAULT_DELTA = DELTAS[2]; // 20

/**
 * Key codes used for hotkeys and focus management.
 */
export const KEY_CODES = {
	ARROW_DOWN: 'ArrowDown',
	ARROW_UP: 'ArrowUp',
	ENTER: 'Enter',
	H: 'h',
	P: 'p',
	S: 's',
	SPACE: ' ',
	TAB: 'Tab',
};

/**
 * Fetch options to pass to ClayDataProvider.
 * Match fetch options from frontend-js-web's fetch.es.js.
 * @see https://github.com/liferay/liferay-portal/blob/master/modules/apps/frontend-js/frontend-js-web/src/main/resources/META-INF/resources/liferay/util/fetch.es.js
 */
export const FETCH_OPTIONS = {
	credentials: 'include',
	...(typeof Liferay !== 'undefined' && {
		headers: new Headers({'x-csrf-token': Liferay.authToken}),
	}),
};

/**
 * Element class that portal uses to target tooltips.
 * @see https://github.com/liferay/liferay-portal/blob/b7681ff418ef34e735c4e31aff6fd06bbfceae67/portal-web/docroot/html/common/themes/bottom_js.jspf#L123
 */
export const PORTAL_TOOLTIP_TRIGGER_CLASS = 'lfr-portal-tooltip';
