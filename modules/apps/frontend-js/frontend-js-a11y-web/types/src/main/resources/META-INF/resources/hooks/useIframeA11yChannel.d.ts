/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import type {Recv} from '../SDK';
import type {Violations} from './useA11y';
declare type FilteredViolations = Omit<Violations, 'iframes'>;
export default function useIframeA11yChannel<T, K>(
	iframes: Record<string, Array<string>>,
	violations: FilteredViolations,
	onMessage: Recv<T, K>
): void;
export {};
