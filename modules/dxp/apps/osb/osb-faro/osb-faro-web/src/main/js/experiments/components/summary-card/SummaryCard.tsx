import BasePage from 'shared/components/base-page';
import getSummaryMapper from 'experiments/hocs/mappers/experiment-summary-mapper';
import Header from '../summary-base-card/Header';
import React, {useContext, useState} from 'react';
import SummaryBaseCard from '../summary-base-card';
import SummaryCardDraft from 'experiments/components/summary-card-draft';
import SummaryCardRun from 'experiments/components/summary-card-run';
import {DocumentNode} from 'graphql';
import {
	EXPERIMENT_DRAFT_QUERY,
	EXPERIMENT_QUERY
} from 'experiments/queries/ExperimentQuery';
import {SafeResults} from 'shared/hoc/util';
import {Status} from '../summary-base-card/types';
import {useAddRefetch} from 'experiments/util/experiments';
import {useQuery} from '@apollo/react-hooks';

interface IWithSummaryCard extends React.HTMLAttributes<HTMLElement> {
	status?: string;
	timeZoneId: string;
}

const withSummaryCard: React.FC<IWithSummaryCard> = ({status, timeZoneId}) => {
	const {
		router: {
			params: {id: experimentId}
		}
	} = useContext(BasePage.Context);

	const [statusSummary, setStatusSummary] = useState<Status>();

	let query: DocumentNode = null;
	let Component: React.FC = null;

	if (status === 'DRAFT') {
		query = EXPERIMENT_DRAFT_QUERY;
		Component = SummaryCardDraft;
	} else {
		query = EXPERIMENT_QUERY;
		Component = SummaryCardRun;
	}

	const {data, refetch, ...result} = useQuery(query, {
		variables: {experimentId}
	});

	useAddRefetch(refetch);

	return (
		<SummaryBaseCard status={statusSummary}>
			<SafeResults
				{...result}
				data={{
					...data,
					experimentId,
					timeZoneId
				}}
			>
				{props => {
					const {
						header,
						status: statusMapper,
						...otherProps
					} = getSummaryMapper(props);

					setStatusSummary(statusMapper);

					return (
						<>
							<SummaryBaseCard.Header
								cardModals={header.cardModals}
								modals={header.modals}
							>
								<Header
									Description={header.Description}
									title={header.title}
								/>
							</SummaryBaseCard.Header>

							<Component {...otherProps} />
						</>
					);
				}}
			</SafeResults>
		</SummaryBaseCard>
	);
};

export default withSummaryCard;
