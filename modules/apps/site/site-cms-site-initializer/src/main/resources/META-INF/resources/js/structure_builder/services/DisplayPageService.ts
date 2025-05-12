/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {config} from '../config';
import ApiHelper from './ApiHelper';

async function resetDisplayPage() {
	return await ApiHelper.post(config.resetStructureDisplayPageURL);
}

export default {
	resetDisplayPage,
};
