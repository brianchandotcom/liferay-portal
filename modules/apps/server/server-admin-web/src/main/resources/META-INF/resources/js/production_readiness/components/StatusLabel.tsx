/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLabel from '@clayui/label';
import React from 'react';

import {RuleResult, RuleStatus} from '../types';

interface Props {
	result: RuleResult;
}

function statusDisplayType(
	status: RuleStatus,
	ignored: boolean
): 'success' | 'danger' | 'secondary' {
	if (ignored) {
		return 'secondary';
	}

	return status === 'PASS' ? 'success' : 'danger';
}

function statusLabel(status: RuleStatus, ignored: boolean): string {
	if (ignored) {
		return Liferay.Language.get('ignored');
	}

	return status === 'PASS'
		? Liferay.Language.get('passed')
		: Liferay.Language.get('failed');
}

const StatusLabel: React.FC<Props> = ({result}) => {
	return (
		<ClayLabel
			displayType={statusDisplayType(result.status, result.ignored)}
		>
			{statusLabel(result.status, result.ignored)}
		</ClayLabel>
	);
};

export default StatusLabel;
