/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
