/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Label from '@clayui/label';
import React from 'react';

declare type LabelDisplayType =
	| 'secondary'
	| 'info'
	| 'warning'
	| 'danger'
	| 'success'
	| 'unstyled';

const mapLabelToLabelDisplayType: {[key: string]: LabelDisplayType} = {
	'approved': 'success',
	'denied': 'danger',
	'draft': 'secondary',
	'expired': 'danger',
	'in-trash': 'info',
	'inactive': 'secondary',
	'incomplete': 'warning',
	'pending': 'info',
	'scheduled': 'info',
};

interface StatusRendererProps {
	value: {
		label: string;
		label_i18n: string;
	};
}

const StatusRenderer = ({value}: StatusRendererProps) => (
	<Label displayType={mapLabelToLabelDisplayType[value.label]}>
		{value.label_i18n}
	</Label>
);

export default StatusRenderer;
