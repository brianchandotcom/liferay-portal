/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */
import {
	Bar,
	BarChart,
	CartesianGrid,
	Legend,
	Tooltip,
	XAxis,
	YAxis,
} from 'recharts';
import i18n from '../../../../../../../../../../../../../common/I18n';
import {Skeleton} from '../../../../../../../../../../../../../common/components';

const UsageChart = ({data, loading}) => {
	if (loading || !data) {
		return (
			<Skeleton align="center" className="mb-5" height={20} width={100} />
		);
	}

	return (
		<div>
			<div className="d-flex justify-content-center">
				<BarChart
					cursor="pointer"
					data={data.annualSubscriptions}
					height={300}
					margin={{
						bottom: 0,
						left: 0,
						right: 60,
						top: 0,
					}}
					width={700}
				>
					<CartesianGrid strokeDasharray="3 3" />

					<XAxis dataKey="year" />

					<YAxis
						allowDataOverflow={true}
						allowDecimals={false}
						domain={['dataMin', 'dataMax']}
						type="number"
					/>

					<Tooltip
						contentStyle={{borderRadius: '8px'}}
						itemStyle={{color: '#282934'}}
					/>

					<Legend
						align="left"
						formatter={(value) => (
							<span
								className="text-color-class"
								style={{
									color: '#282934',
									marginRight: '40px',
								}}
							>
								{value}
							</span>
						)}
						iconType="square"
						wrapperStyle={{
							paddingRight: '100px',
						}}
					/>

					<Bar
						dataKey="maxConcurrentQuantity"
						fill="#BBD2FF"
						name={i18n.translate('subscriptions-purchased')}
						stackId="maxConcurrentQuantity"
					/>

					<Bar
						dataKey="maxConcurrentConsumption"
						fill="#E7EFFF"
						name={i18n.translate('keys-provisioned')}
						stackId="maxConcurrentConsumption"
					/>
				</BarChart>
			</div>

			<div className="d-flex flex-row-reverse m-2 mr-3">
				<h6>
					{i18n.translate('keys-provisioned-total') +
						': ' +
						data.currentConsumption}
				</h6>
			</div>
		</div>
	);
};

export default UsageChart;
