/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';

import {CHECK_VALUE, NATURAL_VALUE, UNCHECKED_VALUE} from './constants';

const COLORS = {
	[CHECK_VALUE]: {className: 'checked', symbol: 'check'},
	[NATURAL_VALUE]: {className: 'neutral', symbol: 'check'},
	[UNCHECKED_VALUE]: {className: 'unchecked', symbol: 'hr'},
};

export function RuleIcon({label, status}) {
	const ruleConfig = COLORS[status] || {};

	return (
		<span
			className={classNames('create-account__rule', ruleConfig.className)}
		>
			<ClayIcon
				className={classNames(
					'create-account__rule__icon mr-1 rounded',
					ruleConfig.className
				)}
				symbol={ruleConfig.symbol}
			/>

			{label}
		</span>
	);
}
