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

export const ACTIVATION_KEYS_LICENSE_FILTER_TYPES = {
	activated: ({expirationDate, startDate}) => {
		const today = new Date();

		return new Date(startDate) < today && new Date(expirationDate) > today;
	},
	all: () => Boolean,
	expired: ({expirationDate}) => new Date(expirationDate) < new Date(),
	notActivated: ({startDate}) => new Date(startDate) > new Date(),
};
