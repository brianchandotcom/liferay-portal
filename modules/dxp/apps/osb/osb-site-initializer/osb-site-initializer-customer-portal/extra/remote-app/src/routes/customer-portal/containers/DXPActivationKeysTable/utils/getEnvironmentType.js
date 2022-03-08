/* eslint-disable no-console */
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

const PRODUCTION = 'Production';
const NONPRODUCTION = 'Non-Production';
const DEVELOPER = 'Developer';
const BACKUP = 'Backup';

export function getEnvironmentType(productName) {
	if (productName.toLowerCase().includes(NONPRODUCTION.toLowerCase())) {
		return NONPRODUCTION;
	}

	if (productName.toLowerCase().includes(PRODUCTION.toLowerCase())) {
		return PRODUCTION;
	}

	if (productName.toLowerCase().includes(DEVELOPER.toLowerCase())) {
		return DEVELOPER;
	}
	if (productName.toLowerCase().includes(BACKUP.toLowerCase())) {
		return BACKUP;
	}
}
