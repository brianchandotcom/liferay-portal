/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * - draftSaved (0) When a request that generates a draft has finished the status will be draftSaved.
 * - error (1) When any timeout or request `error` occurs, the status will be set
 * - savingDraft (2) When a request that generates a draft is pending the status will be `savingDraft`.
 * 	 to error.
 */
export declare const SERVICE_NETWORK_STATUS_TYPES: {
	readonly draftSaved: 0;
	readonly error: 1;
	readonly savingDraft: 2;
};
export declare type ServiceNetworkStatusType = typeof SERVICE_NETWORK_STATUS_TYPES[keyof typeof SERVICE_NETWORK_STATUS_TYPES];
