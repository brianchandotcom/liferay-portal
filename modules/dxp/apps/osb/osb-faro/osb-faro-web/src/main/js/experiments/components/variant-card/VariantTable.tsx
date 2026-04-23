import columns from './variant-columns';
import React from 'react';
import Table, {Column} from 'shared/components/table';
import {createOrderIOMap} from 'shared/util/pagination';
import {
	getBestVariant,
	getMetricUnit,
	mergedVariants
} from 'experiments/util/experiments';
import {IExperiment} from '../summary-card/types';
import {MetricName} from 'experiments/util/types';
import {useStatefulPagination} from 'shared/hooks/useStatefulPagination';

export const VariantTable = ({
	experiment
}: {
	experiment: IExperiment & {type?: string};
}) => {
	const {onOrderIOMapChange, orderIOMap} = useStatefulPagination(undefined, {
		initialOrderIOMap: createOrderIOMap('dxpVariantName')
	});

	const {
		dxpVariants,
		goal,
		metrics,
		publishedDXPVariantId,
		status,
		type,
		winnerDXPVariantId
	} = experiment;

	const variantMetrics = metrics?.variantMetrics ?? [];
	const variants = mergedVariants(dxpVariants, variantMetrics);
	const metric = goal?.metric as MetricName | undefined;
	const metricUnit = getMetricUnit(metric as MetricName);
	const bestVariant = getBestVariant({
		dxpVariants: experiment.dxpVariants,
		goal: goal as {metric: MetricName} | undefined,
		metrics: {variantMetrics}
	});

	return (
		<div className='analytics-variant-card-table'>
			<Table
				columns={
					columns({
						bestVariant,
						metric,
						metricUnit,
						publishedDXPVariantId,
						status,
						type,
						winnerDXPVariantId
					}) as Column[]
				}
				headingNowrap={false}
				internalSort
				items={variants}
				onOrderIOMapChange={onOrderIOMapChange}
				orderIOMap={orderIOMap}
				rowIdentifier='dxpVariantId'
			/>
		</div>
	);
};
