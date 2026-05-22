/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Label from '@clayui/label';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {sub} from 'frontend-js-web';
import React from 'react';

import {
	ASSET_STATUS,
	ASSET_STATUS_TO_DISPLAY_TYPE,
	AssetStatus,
} from '../utils/constants';
import {
	formatExpirationDate,
	formatExpirationDateLong,
	isExpiringSoon,
} from '../utils/expirationStatus';

interface StatusLabelProps {
	expirationDate?: string;
	label: AssetStatus;
}

const StatusLabel = ({expirationDate, label}: StatusLabelProps) => {
	if (label === ASSET_STATUS.EXPIRED && expirationDate) {
		const formattedDate = formatExpirationDate(expirationDate);
		const formattedDateLong = formatExpirationDateLong(expirationDate);

		if (formattedDate && formattedDateLong) {
			const ariaLabel = sub(
				Liferay.Language.get('expired-on-x'),
				formattedDateLong
			);

			return (
				<ClayTooltipProvider>
					<span
						aria-label={ariaLabel}
						tabIndex={0}
						title={formattedDate}
					>
						<Label
							displayType={ASSET_STATUS_TO_DISPLAY_TYPE[label]}
							inverse
						>
							{Liferay.Language.get(label)}
						</Label>
					</span>
				</ClayTooltipProvider>
			);
		}
	}

	if (label !== ASSET_STATUS.APPROVED || !isExpiringSoon(expirationDate)) {
		return (
			<Label displayType={ASSET_STATUS_TO_DISPLAY_TYPE[label]} inverse>
				{Liferay.Language.get(label)}
			</Label>
		);
	}

	const formattedDate = formatExpirationDate(expirationDate);
	const expiringSoonText = Liferay.Language.get('expiring-soon');
	const ariaLabel = sub(
		Liferay.Language.get('expiring-soon-expires-on-x'),
		formatExpirationDateLong(expirationDate) ?? ''
	);

	return (
		<span className="align-items-center c-gap-2 d-flex flex-wrap">
			<Label displayType={ASSET_STATUS_TO_DISPLAY_TYPE[label]} inverse>
				{Liferay.Language.get(label)}
			</Label>

			<ClayTooltipProvider>
				<span
					aria-label={ariaLabel}
					tabIndex={0}
					title={formattedDate ?? ''}
				>
					<Label displayType="warning" inverse>
						{expiringSoonText}
					</Label>
				</span>
			</ClayTooltipProvider>
		</span>
	);
};

export default StatusLabel;
