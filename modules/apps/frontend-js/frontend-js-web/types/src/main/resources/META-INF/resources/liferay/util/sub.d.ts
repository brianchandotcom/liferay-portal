/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import type {ReactNode} from 'react';
export default function sub<Params extends Array<string | number | ReactNode>>(
	langKey: string,
	...params: Params
): Params extends ReactNode[] ? ReactNode[] : string;
