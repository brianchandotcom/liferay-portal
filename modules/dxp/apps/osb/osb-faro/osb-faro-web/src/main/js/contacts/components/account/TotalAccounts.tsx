import * as API from 'shared/api';
import MetricCard from 'shared/components/MetricCard';
import React from 'react';
import {
	AccountMetricType,
	IAccountMetric,
	Metric
} from '../../pages/account/utils/types';
import {sub} from 'shared/util/lang';
import {toThousands} from 'shared/util/numbers';
import {useRequest} from 'shared/hooks/useRequest';

const renderAccountValue = (metric?: Metric) =>
	sub(
		metric?.value === 1
			? Liferay.Language.get('x-account')
			: Liferay.Language.get('x-accounts'),
		[toThousands(metric?.value ?? 0)]
	);

const TotalAccounts = ({groupId}: {groupId: string}) => {
	const {data, loading} = useRequest({
		dataSourceFn: API.accounts.fetchMetrics,
		variables: {
			groupId
		}
	});

	const metrics = data as IAccountMetric[] | undefined;

	const getMetric = (metricType: AccountMetricType) =>
		metrics?.find(metric => metric.metricType === metricType);

	const renderTrendLabel = (percentageNode: React.ReactNode) =>
		sub(
			Liferay.Language.get('x-vs-previous-90-days'),
			[percentageNode],
			false
		);

	const cardContainerClassName = 'col-12 col-lg-4 d-flex';

	return (
		<div className='row g-4'>
			<div className={cardContainerClassName}>
				<MetricCard
					description={Liferay.Language.get(
						'displays-all-accounts-included-in-this-property'
					)}
					loading={loading}
					renderTrendLabel={renderTrendLabel}
					title={Liferay.Language.get('total-accounts')}
					trend={getMetric(AccountMetricType.Total)?.trend}
					value={renderAccountValue(
						getMetric(AccountMetricType.Total)
					)}
				/>
			</div>
			<div className={cardContainerClassName}>
				<MetricCard
					description={Liferay.Language.get(
						'displays-all-new-accounts-included-in-this-property'
					)}
					loading={loading}
					renderTrendLabel={renderTrendLabel}
					title={Liferay.Language.get('new-accounts')}
					trend={getMetric(AccountMetricType.New)?.trend}
					value={renderAccountValue(getMetric(AccountMetricType.New))}
				/>
			</div>
			<div className={cardContainerClassName}>
				<MetricCard
					description={Liferay.Language.get(
						'displays-all-active-accounts-included-in-this-property'
					)}
					loading={loading}
					renderTrendLabel={renderTrendLabel}
					title={Liferay.Language.get('active-accounts')}
					trend={getMetric(AccountMetricType.Active)?.trend}
					value={renderAccountValue(
						getMetric(AccountMetricType.Active)
					)}
				/>
			</div>
		</div>
	);
};

export default TotalAccounts;
