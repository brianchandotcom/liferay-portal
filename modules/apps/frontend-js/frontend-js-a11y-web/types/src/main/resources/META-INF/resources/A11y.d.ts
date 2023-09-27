/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import type {A11yCheckerOptions} from './A11yChecker';
export declare function A11y(
	props: Omit<A11yCheckerOptions, 'callback'>
): JSX.Element | null;
