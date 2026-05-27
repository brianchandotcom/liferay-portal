/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayProgressBar from '@clayui/progress-bar';
import classNames from 'classnames';
import React from 'react';

import {
	getMonthlyConsumptionPercent,
	getMonthlyThreshold,
} from '../utils/consumption';
import MetricCard from './MetricCard';

import './MonthlyConsumptionCard.scss';

export default function MonthlyConsumptionCard({
	allowance,
	consumed,
}: {
	allowance: number;
	consumed: number;
}) {
	const level = getMonthlyThreshold(allowance, consumed);
	const percent = getMonthlyConsumptionPercent(allowance, consumed);

	return (
		<MetricCard
			icon={<ClayIcon symbol="database" />}
			title={Liferay.Language.get('monthly-lrt-consumed')}
			value={
				<>
					{consumed.toLocaleString()} LRT
					<span className="ai-hub-monthly-consumption-card-allowance">
						({Liferay.Language.get('of')}
						&nbsp;
						{allowance.toLocaleString()})
					</span>
				</>
			}
		>
			<ClayProgressBar
				className={classNames(
					'ai-hub-monthly-consumption-card-progress',
					{
						'ai-hub-monthly-consumption-card-progress-exceeded':
							level === 'exceeded',
					}
				)}
				value={percent}
				warn={level === 'warning'}
			>
				{`${percent}%`}
			</ClayProgressBar>
		</MetricCard>
	);
}
