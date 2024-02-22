import React from 'react';
import {getPercentage} from 'shared/util/util';
import {sub} from 'shared/util/lang';
import {Text} from '@clayui/core';
import {toRounded} from 'shared/util/numbers';
import {UsageMetricBarChart} from './UsageMetricBarChart';

export const CurrentUsage = ({count, items, legendText, limit}) => (
	<>
		<div className='d-flex justify-content-between mb-1'>
			<Text color='secondary' size={3}>
				{Liferay.Language.get('current-usage').toUpperCase()}
			</Text>

			<Text color='secondary' size={3}>
				{`${sub(Liferay.Language.get('x-of-x'), [
					count.toLocaleString(),
					limit.toLocaleString()
				])} - ${sub(Liferay.Language.get('x-used'), [
					`${toRounded(getPercentage(count, limit))}%`
				])}`}
			</Text>
		</div>

		<UsageMetricBarChart items={items} showLegend={false} total={limit} />

		<Text color='secondary' size={3}>
			{legendText}
		</Text>
	</>
);
