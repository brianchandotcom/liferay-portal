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

export const KEY_CODES = {
	ARROW_DOWN: 'ArrowDown',
	ARROW_UP: 'ArrowUp',
	ENTER: 'Enter',
	H: 'h',
	P: 'p',
	S: 's',
	SPACE: ' ',
	TAB: 'Tab'
};

/**
 * List of deltas in a shape compatible with ClayPaginationWithBar.
 */
export const DELTAS = [
	{
		label: 5
	},
	{
		label: 10
	},
	{
		label: 20
	},
	{
		label: 40
	},
	{
		label: 50
	}
];

/**
 * Delta that will be initially selected.
 */
export const DEFAULT_DELTA = DELTAS[4];
