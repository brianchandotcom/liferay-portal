import columns from './variant-columns';
import getVariantTableMapper from 'experiments/hocs/mappers/experiment-variant-table-mapper';
import React from 'react';
import Table from 'shared/components/table';
import {CLASSNAME} from './constants';
import {createOrderIOMap} from 'shared/util/pagination';
import {EXPERIMENT_QUERY} from 'experiments/queries/ExperimentQuery';
import {SafeResults} from 'shared/hoc/util';
import {Status} from 'experiments/util/types';
import {useParams} from 'react-router-dom';
import {useQuery} from '@apollo/react-hooks';
import {useStatefulPagination} from 'shared/hooks';

type Variant = {
	changes: number;
	confidenceLevel: number;
	control: boolean;
	dxpVariantId: string;
	dxpVariantName: string;
	improvementChance: number;
	improvementLift: number;
	metricRangeEnd: number;
	metricRangeStart: number;
	probabilityToWin: number;
	trafficSplit: number;
	uniqueVisitors: number;
};

export interface VariantCardIProps extends React.HTMLAttributes<HTMLElement> {
	bestVariant: Variant;
	data: Array<Variant>;
	metric: string;
	metricUnit: string;
	publishedDXPVariantId: string;
	status: Status;
	winnerDXPVariantId: string;
}

const VariantTableCard = () => {
	const {id: experimentId} = useParams();

	const {onOrderIOMapChange, orderIOMap} = useStatefulPagination(null, {
		initialOrderIOMap: createOrderIOMap('dxpVariantName')
	});

	const result = useQuery(EXPERIMENT_QUERY, {
		variables: {experimentId}
	});

	return (
		<SafeResults {...result}>
			{props => {
				const {
					bestVariant,
					data,
					metric,
					metricUnit,
					publishedDXPVariantId,
					status,
					winnerDXPVariantId
				}: VariantCardIProps = getVariantTableMapper(props);

				return (
					<div className={`${CLASSNAME}-table`}>
						<Table
							columns={columns({
								bestVariant,
								metric,
								metricUnit,
								publishedDXPVariantId,
								status,
								winnerDXPVariantId
							})}
							headingNowrap={false}
							internalSort
							items={data}
							nowrap={false}
							onOrderIOMapChange={onOrderIOMapChange}
							orderIOMap={orderIOMap}
							rowIdentifier='dxpVariantId'
						/>
					</div>
				);
			}}
		</SafeResults>
	);
};

export default VariantTableCard;
