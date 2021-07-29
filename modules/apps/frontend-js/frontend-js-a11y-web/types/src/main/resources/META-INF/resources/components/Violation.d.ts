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

/// <reference types="react" />

import './Violation.scss';
import type {Violations} from '../hooks/useA11y';
declare type Params = {
	name: string;
	ruleId: string;
	target: string;
};
declare type ViolationProps = {
	next?: (payload: Params) => void;
	params?: Pick<Params, 'ruleId'>;
	previous?: () => void;
	violations: Omit<Violations, 'iframes'>;
};
declare function Violation({
	next,
	params,
	previous,
	violations,
}: ViolationProps): JSX.Element | null;
export default Violation;
