/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import type {A11yCheckerOptions} from './A11yChecker';
export default function main(
	props: Omit<A11yCheckerOptions, 'callback' | 'targets'>
): void;
