/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {navigate} from 'frontend-js-web';

export type Data = {
	redirect: string;
};

export default function createProjectAction(data: Data) {
	const url = new URL(data.redirect);

	navigate(url.pathname + url.search);
}
