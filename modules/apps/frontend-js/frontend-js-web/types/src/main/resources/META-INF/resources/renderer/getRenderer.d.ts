/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {TRenderer} from './types';
declare function getRenderer({
	type,
	url,
}: {
	type: 'clientExtension' | 'internal';
	url: string;
}): Promise<TRenderer>;
export default getRenderer;
