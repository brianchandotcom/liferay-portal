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

const PRODUCTION_VERSION = 7.1;
const PRODUCTION_ENVIRONMENT = 'production';

export const DOWNLOADABLE_LICENSE_KEYS = {
	above71DXPVersion: (firstSelectedKey, selectedKey) =>
		+selectedKey?.productVersion >= PRODUCTION_VERSION &&
		+firstSelectedKey?.productVersion >= PRODUCTION_VERSION,
	below71DXPVersion: (firstSelectedKey, selectedKey) =>
		firstSelectedKey?.licenseEntryType === PRODUCTION_ENVIRONMENT &&
		firstSelectedKey?.sizing === selectedKey?.sizing &&
		firstSelectedKey?.startDate === selectedKey?.startDate &&
		firstSelectedKey?.expirationDate === selectedKey?.expirationDate &&
		firstSelectedKey?.productVersion === selectedKey?.productVersion,
};
