/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ApiHelper from '../../../services/ApiHelper';
import {Space} from '../../../types/Space';

async function getSpaces(): Promise<Space[]> {
	const {data, error} = await ApiHelper.get<{items: Space[]}>(
		'/o/headless-asset-library/v1.0/asset-libraries'
	);

	if (data) {
		return data.items;
	}

	throw new Error(error);
}

export default {
	getSpaces,
};
