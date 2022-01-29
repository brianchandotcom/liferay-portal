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
 * Uses the `actions` object from the REST response to determine if the user has
 * permission to perform the action.
 *
 * `actionPermissions` has a structure like this:
 * {
 *   "delete": {"method": "DELETE", "href": "http://localhost:8080/o/search-experiences-rest/v1.0/sxp-blueprints/1"},
 *   "get": {"method": "GET", "href": "http://localhost:8080/o/search-experiences-rest/v1.0/sxp-blueprints/1"},
 *   "update": {"method": "UPDATE", "href": "http://localhost:8080/o/search-experiences-rest/v1.0/sxp-blueprints/1"}
 * }
 *
 * @see SXPBlueprintResourceImpl.java for action key implementation
 *
 * @param {string} actionId
 * @param {object} actionPermissions
 * @returns
 */
export function checkPermission(actionId, actionPermissions = {}) {
	return Object.keys(actionPermissions).includes(actionId);
}
