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

const StatusIcon: React.FC<Props> = ({ignored, status}) => {
	if (ignored) {
		return (
			<span className="text-secondary">
				<ClayIcon symbol="simple-circle" />
			</span>
		);
	}

	if (status === 'PASS') {
		return (
			<span className="text-success">
				<ClayIcon symbol="check-circle-full" />
			</span>
		);
	}

	return (
		<span className="text-danger">
			<ClayIcon symbol="exclamation-circle" />
		</span>
	);
};

export default StatusIcon;
