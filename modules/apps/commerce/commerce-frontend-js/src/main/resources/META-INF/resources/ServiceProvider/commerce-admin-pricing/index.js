/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {composeAPI} from '../composeAPI';
import * as v2 from './v2.0/index';

const BASE_ENDPOINT = '/o/headless-commerce-admin-pricing/';

const APIs = {
	v2,
};

export default function main(version) {
	return composeAPI(version, APIs, BASE_ENDPOINT);
}
