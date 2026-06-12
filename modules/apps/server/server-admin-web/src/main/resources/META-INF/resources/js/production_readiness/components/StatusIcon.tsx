/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import React from 'react';

import {RuleStatus} from '../types';

interface Props {
	ignored: boolean;
	status: RuleStatus;
}

function statusIcon(
	status: RuleStatus,
	ignored: boolean
): {className: string; symbol: string} {
	if (ignored) {
		return {className: 'text-secondary', symbol: 'simple-circle'};
	}

	if (status === 'PASS') {
		return {className: 'text-success', symbol: 'check-circle-full'};
	}

	return {className: 'text-danger', symbol: 'exclamation-circle'};
}

const StatusIcon: React.FC<Props> = ({ignored, status}) => {
	const {className, symbol} = statusIcon(status, ignored);

	return (
		<span aria-hidden="true" className={className}>
			<ClayIcon symbol={symbol} />
		</span>
	);
};

export default StatusIcon;
