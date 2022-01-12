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
