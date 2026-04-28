import Card from 'shared/components/Card';
import classNames from 'classnames';
import ClayIcon from '@clayui/icon';
import Loading from 'shared/components/Loading';
import React, {ReactNode} from 'react';
import {getIcon, getStatsColor} from 'shared/util/metrics';
import {isNil} from 'lodash';
import {Text} from '@clayui/core';
import {toRounded} from 'shared/util/numbers';
import {TrendClassification} from 'segment/types';

interface IMetricCardTrend {
	percentage: number;
	trendClassification: TrendClassification;
}

interface IMetricCardProps {
	bodyClassName?: string;
	className?: string;
	description: string;
	loading?: boolean;
	renderTrendLabel: (percentageNode: ReactNode) => ReactNode;
	title: string;
	trend?: IMetricCardTrend;
	trendClassName?: string;
	value: ReactNode;
}

const MetricCard: React.FC<IMetricCardProps> = ({
	bodyClassName,
	className,
	description,
	loading = false,
	renderTrendLabel,
	title,
	trend,
	trendClassName,
	value
}) => {
	if (loading) {
		return (
			<Card className={classNames(className, 'flex-fill p-3 w-100')}>
				<Card.Body>
					<Loading />
				</Card.Body>
			</Card>
		);
	}

	const percentageColor = getStatsColor(trend?.trendClassification || '');

	return (
		<Card className={classNames(className, 'flex-fill p-3 w-100')}>
			<Card.Title>
				<div className='text-uppercase text-weight-semi-bold'>
					<Text>{title}</Text>
				</div>
			</Card.Title>
			<Card.Body className={bodyClassName} noPadding>
				<div className='mt-1'>
					<Text color='secondary' size={3}>
						{description}
					</Text>
				</div>

				<span className='mt-3 text-lowercase text-weight-semi-bold'>
					<Text size={7}>{value}</Text>
				</span>

				<span className={classNames('text-secondary', trendClassName)}>
					{!isNil(trend?.trendClassification) &&
						trend?.trendClassification !==
							TrendClassification.Neutral && (
							<ClayIcon
								style={{color: percentageColor}}
								symbol={getIcon(trend?.percentage ?? 0) ?? ''}
							/>
						)}
					{renderTrendLabel(
						<span
							className='mr-1'
							key='percentage'
							style={{color: percentageColor}}
						>
							{`${toRounded(
								Math.abs(trend?.percentage ?? 0),
								2
							)}%`}
						</span>
					)}
				</span>
			</Card.Body>
		</Card>
	);
};

export default MetricCard;
