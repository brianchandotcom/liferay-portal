import CardTabs from 'shared/components/CardTabs';
import MetricStateRenderer from './MetricStateRenderer';
import React from 'react';
import {buildTabs, getMetricCardTabsData} from './util';
import {ICommonMetricProps, useActions, useData} from './MetricBaseCard';
import {useMetricQuery} from './hooks';

interface IMetricTabsRendererProps extends ICommonMetricProps {}

const MetricTabsRenderer: React.FC<IMetricTabsRendererProps> = ({
	filters,
	interval,
	rangeSelectors
}) => {
	const {queries, variables} = useData();

	const {data, error, loading} = useMetricQuery({
		filters,
		interval,
		Query: queries.TabsQuery,
		rangeSelectors,
		variables
	});

	return (
		<MetricStateRenderer error={error} loading={loading} spacer>
			<MetricTabs data={data} />
		</MetricStateRenderer>
	);
};

interface IMetricTabsProps extends Partial<IMetricTabsRendererProps> {
	data: any;
}

const MetricTabs: React.FC<IMetricTabsProps> = ({data}) => {
	const {activeItemIndex, metrics, queries} = useData();
	const {changeActiveItemIndex} = useActions();
	const items = getMetricCardTabsData(data[queries.name], metrics);

	return (
		<CardTabs
			activeTabId={activeItemIndex}
			className='analytics-metrics-tabs'
			tabs={buildTabs({
				activeItemIndex,
				items,
				onActiveItemIndexChange: changeActiveItemIndex
			})}
		/>
	);
};

export default MetricTabsRenderer;
