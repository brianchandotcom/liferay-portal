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

export function alphabeticalSort(a, b) {
	return a.toLowerCase().localeCompare(b.toLowerCase());
}

export function stringLengthSort(a, b) {
	if (a.length < b.length) {
		return -1;
	}

	if (a.length > b.length) {
		return 1;
	}

	return 0;
}
