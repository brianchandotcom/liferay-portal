import Card from 'shared/components/Card';
import classNames from 'classnames';
import ClayIcon from '@clayui/icon';
import React from 'react';
import {getIcon, getStatsColor} from 'shared/util/metrics';
import {sub} from 'shared/util/lang';
import {Text} from '@clayui/core';
import {toRounded} from 'shared/util/numbers';

export type Metric = {
	trend: Trend;
	value: number;
};

export type Trend = {
	percentage: number;
	trendClassification: TrendClassification;
};

export enum TrendClassification {
	Negative = 'NEGATIVE',
	Neutral = 'NEUTRAL',
	Positive = 'POSITIVE'
}

const mockData: Metric[] = [
	{
		trend: {
			percentage: 50,
			trendClassification: TrendClassification.Positive
		},
		value: 15
	},
	{
		trend: {
			percentage: -30,
			trendClassification: TrendClassification.Negative
		},
		value: 10
	},
	{
		trend: {
			percentage: 0,
			trendClassification: TrendClassification.Neutral
		},
		value: 1
	}
];

const AccountCard = ({className, description, metrics, title}) => {
	const {
		trend: {
			percentage = 0,
			trendClassification = TrendClassification.Neutral
		},
		value = 0
	} = metrics;

	return (
		<Card className={classNames(className, 'flex-fill p-3 w-100')}>
			<Card.Title>
				<div className='text-uppercase text-weight-semi-bold'>
					<Text>{title}</Text>
				</div>
			</Card.Title>
			<Card.Body noPadding>
				<span className='mt-1'>
					<Text color='secondary' size={3}>
						{description}
					</Text>
				</span>

				<span className='mt-3 text-lowercase text-weight-semi-bold'>
					<Text size={7}>
						{sub(
							value === 1
								? Liferay.Language.get('x-account')
								: Liferay.Language.get('x-accounts'),
							[value]
						)}
					</Text>
				</span>

				<span className='text-secondary'>
					{trendClassification !== TrendClassification.Neutral && (
						<ClayIcon
							style={{
								color: getStatsColor(trendClassification)
							}}
							symbol={getIcon(percentage)}
						/>
					)}
					{sub(
						Liferay.Language.get('x-vs-previous-90-days'),
						[
							<>
								<span
									className='mr-1'
									key='percentage'
									style={{
										color:
											getStatsColor(
												trendClassification
											) || TrendClassification.Neutral
									}}
								>
									{`${toRounded(
										Math.abs(percentage) ?? 0,
										2
									)}%`}
								</span>
							</>
						],
						false
					)}
				</span>
			</Card.Body>
		</Card>
	);
};

const TotalAccounts = () => (
	<div className='d-flex w-100'>
		<AccountCard
			className='mr-4'
			description={Liferay.Language.get(
				'displays-all-accounts-included-in-this-property'
			)}
			metrics={mockData[0]}
			title={Liferay.Language.get('total-accounts')}
		/>
		<AccountCard
			className='mr-4'
			description={Liferay.Language.get(
				'displays-all-new-accounts-included-in-this-property'
			)}
			metrics={mockData[1]}
			title={Liferay.Language.get('new-accounts')}
		/>
		<AccountCard
			className=''
			description={Liferay.Language.get(
				'displays-all-active-accounts-included-in-this-property'
			)}
			metrics={mockData[2]}
			title={Liferay.Language.get('active-accounts')}
		/>
	</div>
);

export default TotalAccounts;
