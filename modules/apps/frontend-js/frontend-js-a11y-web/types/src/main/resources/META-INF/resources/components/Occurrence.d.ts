/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import './Occurrence.scss';
import type {Violations} from '../hooks/useA11y';
declare type Params = {
	name: string;
	ruleId: string;
	target: string;
};
declare type OccurrenceProps = {
	params?: Params;
	previous?: (state: Omit<Params, 'target'>) => void;
	violations: Omit<Violations, 'iframes'>;
};
declare function Occurrence({
	params,
	previous,
	violations,
}: OccurrenceProps): JSX.Element | null;
export default Occurrence;
