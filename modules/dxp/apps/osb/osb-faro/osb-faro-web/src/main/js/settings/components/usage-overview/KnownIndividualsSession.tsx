import React from 'react';
import {Colors} from 'shared/util/charts';
import {CurrentUsage} from './CurrentUsage';
import {STATUS_DISPLAY_MAP} from 'shared/util/subscriptions';
import {sub} from 'shared/util/lang';
import {Text} from '@clayui/core';
import {toThousands} from 'shared/util/numbers';
import {UsageMetric} from './UsageMetric';
import {UsageMetricBarChart} from './UsageMetricBarChart';

export const KnownIndividualsSession = ({currentPlan}) => {
	const {count, limit, status} = currentPlan.metrics.get('individuals');
	const usersCount = currentPlan.metrics.get('usersCount') ?? 0;

	return (
		<div className='mt-4 mb-5'>
			<UsageMetric
				description={Liferay.Language.get(
					'active-and-logged-in-users-on-dxp-synced-to-analytics-cloud'
				)}
				title={Liferay.Language.get('known-individuals')}
			>
				<CurrentUsage
					count={count}
					items={{
						itemA: {
							color: Colors[STATUS_DISPLAY_MAP[status]],
							label: Liferay.Language.get('known-individuals'),
							value: count
						}
					}}
					legendText={sub(
						Liferay.Language.get('x-known-individuals-remaining'),
						[(limit - count).toLocaleString()]
					)}
					limit={limit}
				/>

				<div className='mt-4'>
					<div className='mb-1'>
						<Text color='secondary' size={3}>
							{Liferay.Language.get(
								'individuals-breakdown'
							).toUpperCase()}
						</Text>
					</div>

					<UsageMetricBarChart
						items={{
							itemA: {
								color: Colors.mormont,
								label: sub(
									Liferay.Language.get(
										'individuals-synced-to-analytics-cloud-x'
									),
									[toThousands(usersCount)]
								) as string,
								value: usersCount
							},
							itemB: {
								color: Colors.primary,
								label: sub(
									Liferay.Language.get('known-individuals-x'),
									[toThousands(count)]
								) as string,
								value: count
							}
						}}
						total={usersCount + count}
					/>
				</div>
			</UsageMetric>
		</div>
	);
};
