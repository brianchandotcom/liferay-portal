/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import './Rule.scss';
import React from 'react';
import type {ImpactValue} from 'axe-core';
interface IRule extends React.ButtonHTMLAttributes<HTMLButtonElement> {
	quantity?: number;
	ruleSubtext?: ImpactValue;
	ruleText?: React.ReactNode;
	ruleTitle?: React.ReactNode;
}
declare function Rule({
	quantity,
	ruleSubtext,
	ruleText,
	ruleTitle,
	...otherProps
}: IRule): JSX.Element;
export default Rule;
