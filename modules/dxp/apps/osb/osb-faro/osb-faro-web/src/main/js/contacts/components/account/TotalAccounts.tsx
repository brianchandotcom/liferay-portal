import * as API from 'shared/api';
import Card from 'shared/components/Card';
import classNames from 'classnames';
import ClayIcon from '@clayui/icon';
import Loading from 'shared/components/Loading';
import React from 'react';
import {getIcon, getStatsColor} from 'shared/util/metrics';
import {isNil} from 'lodash/fp';
import {Metric} from '../../pages/account/utils/types';
import {sub} from 'shared/util/lang';
import {Text} from '@clayui/core';
import {toRounded} from 'shared/util/numbers';
import {TrendClassification} from 'segment/types';
import {useRequest} from 'shared/hooks/useRequest';

interface IAccountCardProps {
	className?: string;
	description: string;
	loading?: boolean;
	metrics: Metric;
	title: string;
}

const AccountCard: React.FC<IAccountCardProps> = ({
	className,
	description,
	loading = false,
	metrics,
	title
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

	return (
		<Card className={classNames(className, 'flex-fill p-3 w-100')}>
			<Card.Title>
				<div className='text-uppercase text-weight-semi-bold'>
					<Text>{title}</Text>
				</div>
			</Card.Title>
			<Card.Body noPadding>
				<div className='mt-1'>
					<Text color='secondary' size={3}>
						{description}
					</Text>
				</div>

				<span className='mt-3 text-lowercase text-weight-semi-bold'>
					<Text size={7}>
						{sub(
							metrics?.value === 1
								? Liferay.Language.get('x-account')
								: Liferay.Language.get('x-accounts'),
							[metrics?.value ?? 0]
						)}
					</Text>
				</span>

				<span className='text-secondary'>
					{!isNil(metrics?.trend?.trendClassification) &&
						metrics?.trend?.trendClassification !==
							TrendClassification.Neutral && (
							<ClayIcon
								style={{
									color: getStatsColor(
										metrics?.trend?.trendClassification
									)
								}}
								symbol={getIcon(metrics?.trend?.percentage)}
							/>
						)}
					{sub(
						Liferay.Language.get('x-vs-previous-90-days'),
						[
							<span
								className='mr-1'
								key='percentage'
								style={{
									color:
										getStatsColor(
											metrics?.trend?.trendClassification
										) || TrendClassification.Neutral
								}}
							>
								{`${toRounded(
									Math.abs(metrics?.trend?.percentage ?? 0),
									2
								)}%`}
							</span>
						],
						false
					)}
				</span>
			</Card.Body>
		</Card>
	);
};

const TotalAccounts = ({groupId}) => {
	const {data, loading} = useRequest({
		dataSourceFn: API.accounts.fetchMetrics,
		variables: {
			groupId
		}
	});

	const {activeCount, newCount, totalCount} = data || {};

	return (
		<div className='d-flex w-100'>
			<AccountCard
				className='mr-4'
				description={Liferay.Language.get(
					'displays-all-accounts-included-in-this-property'
				)}
				loading={loading}
				metrics={totalCount || {}}
				title={Liferay.Language.get('total-accounts')}
			/>

			<AccountCard
				className='mr-4'
				description={Liferay.Language.get(
					'displays-all-new-accounts-included-in-this-property'
				)}
				loading={loading}
				metrics={newCount || {}}
				title={Liferay.Language.get('new-accounts')}
			/>

			<AccountCard
				description={Liferay.Language.get(
					'displays-all-active-accounts-included-in-this-property'
				)}
				loading={loading}
				metrics={activeCount || {}}
				title={Liferay.Language.get('active-accounts')}
			/>
		</div>
	);
};

export default TotalAccounts;
