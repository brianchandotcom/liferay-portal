/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
