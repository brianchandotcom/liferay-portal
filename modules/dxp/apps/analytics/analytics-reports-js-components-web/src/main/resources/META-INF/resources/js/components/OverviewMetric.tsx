/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Text} from '@clayui/core';
import {ColorType} from '@clayui/core/lib/typography/Text';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React from 'react';

import {getPercentage, toThousands} from '../utils/math';

export enum TrendClassification {
	Negative = 'NEGATIVE',
	Neutral = 'NEUTRAL',
	Positive = 'POSITIVE',
}

type Classification = {
	[key in TrendClassification]: ColorType;
};

const classification: Classification = {
	[TrendClassification.Negative]: 'danger',
	[TrendClassification.Neutral]: 'secondary',
	[TrendClassification.Positive]: 'success',
};

interface IOverviewMetricProps {
	name: string;
	onSelectMetric: () => void;
	selected?: boolean;
	trend: {
		percentage: number;
		trendClassification: TrendClassification;
	};
	value: number;
}

const OverviewMetric: React.FC<IOverviewMetricProps> = ({
	name,
	onSelectMetric,
	selected,
	trend,
	value,
}) => {
	return (
		<button
			className={classNames('overview-metric tab-focus text-uppercase', {
				selected,
			})}
			data-testid={`overview__${name.toLocaleLowerCase()}-metric`}
			onClick={onSelectMetric}
		>
			<div className="overview-metric__title">
				<Text size={2}>{name}</Text>
			</div>

			<div className="overview-metric__value">{toThousands(value)}</div>

			<div className="overview-metric__comparison">
				<Text
					color={classification[trend.trendClassification]}
					size={1}
				>
					{trend.percentage !== 0 && (
						<ClayIcon
							className="mr-1"
							symbol={
								trend.percentage > 0
									? 'caret-top'
									: 'caret-bottom'
							}
						/>
					)}

					{`${getPercentage(trend.percentage)}%`}
				</Text>

				<span className="ml-1 overview-metric__percentage-description">
					<Text size={1}>vs Previous Period</Text>
				</span>
			</div>
		</button>
	);
};

export default OverviewMetric;
