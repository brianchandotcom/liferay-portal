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

declare global {
	var Liferay: {
		Language: {
			get(value: string): string;
		};
		Util: {
			sub(...value: string[]): string;
		};
	};
}
import type {A11yCheckerOptions} from './A11yChecker';
declare const _default: (
	props?: Omit<A11yCheckerOptions, 'callback' | 'targets'>
) => void;
export default _default;
