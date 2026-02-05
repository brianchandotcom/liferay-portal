/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Label from '@clayui/label';
import React from 'react';

type NameDisplayType =
	| 'danger'
	| 'info'
	| 'secondary'
	| 'success'
	| 'unstyled'
	| 'warning';

const mapKeyToNameDisplayType: {[key: string]: NameDisplayType} = {
	blocked: 'danger',
	done: 'success',
	inProgress: 'info',
	notStarted: 'secondary',
	overdue: 'warning',
};

type State = {
	key: string;
	name: string;
};

interface StateLabelProps {
	dueDate?: string;
	state?: State;
}

function StateLabel({dueDate, state}: StateLabelProps) {
	if (!state || !state.name) {
		return null;
	}

	const isOverdue =
		dueDate && state.key !== 'done' && Date.parse(dueDate) < Date.now();

	return (
		<>
			<Label displayType={mapKeyToNameDisplayType[state.key]}>
				{state.name}
			</Label>

			{isOverdue && (
				<Label displayType="warning">
					{Liferay.Language.get('overdue')}
				</Label>
			)}
		</>
	);
}

export default StateLabel;
