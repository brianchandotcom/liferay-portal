/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Label from '@clayui/label';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {sub} from 'frontend-js-web';
import React from 'react';

import {
	formatExpirationDate,
	formatExpirationDateLong,
	isExpiringSoon,
} from '../utils/expirationStatus';

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

interface StatusLabelProps {
	expirationDate?: string | null;
	label: string;
}

const StatusLabel = ({expirationDate, label}: StatusLabelProps) => {
	if (!isExpiringSoon(label, expirationDate ?? undefined)) {
		return (
			<Label displayType={mapLabelToLabelDisplayType[label]}>
				{Liferay.Language.get(label)}
			</Label>
		);
	}

	const formattedDate = formatExpirationDate(expirationDate ?? undefined);
	const expiringSoonText = Liferay.Language.get('expiring-soon');
	const ariaLabel = sub(
		Liferay.Language.get('expiring-soon.expires-on-x'),
		formatExpirationDateLong(expirationDate ?? undefined) ?? ''
	);

	return (
		<span className="align-items-center c-gap-2 d-flex flex-wrap">
			<Label displayType={mapLabelToLabelDisplayType[label]}>
				{Liferay.Language.get(label)}
			</Label>

			<ClayTooltipProvider>
				<span
					aria-label={ariaLabel}
					tabIndex={0}
					title={formattedDate ?? ''}
				>
					<Label displayType="warning">{expiringSoonText}</Label>
				</span>
			</ClayTooltipProvider>
		</span>
	);
};

export default StatusLabel;
