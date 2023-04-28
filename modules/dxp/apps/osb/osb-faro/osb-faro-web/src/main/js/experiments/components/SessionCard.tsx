import BasePage from 'shared/components/base-page';
import Card from 'shared/components/Card';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import getCN from 'classnames';
import getSessionMapper from 'experiments/hocs/mappers/experiment-session-mapper';
import getSessionVariantsMapper from 'experiments/hocs/mappers/experiment-session-variants-mapper';
import LineChart from 'experiments/components/LineChart';
import NoResultsDisplay from 'shared/components/NoResultsDisplay';
import React, {useContext} from 'react';
import {DocumentNode} from 'graphql';
import {
	EXPERIMENT_SESSION_HISTOGRAM_QUERY,
	EXPERIMENT_SESSION_VARIANTS_HISTOGRAM_QUERY
} from 'experiments/queries/ExperimentQuery';
import {SafeResults} from 'shared/hoc/util';
import {Sizes} from 'shared/util/constants';
import {useFakeLoading} from 'shared/hooks';
import {useQuery} from '@apollo/react-hooks';
import {useStateValue} from 'experiments/state';

const CLASSNAME = 'analytics-session-card';
const PER_VARIANT = 'per-variant';
const TOTAL = 'total';

const getSessionView = (sessionView: String) => {
	let query: DocumentNode = null;
	let mapper: Function = null;

	if (sessionView === TOTAL) {
		query = EXPERIMENT_SESSION_HISTOGRAM_QUERY;
		mapper = getSessionMapper;
	} else if (sessionView === PER_VARIANT) {
		query = EXPERIMENT_SESSION_VARIANTS_HISTOGRAM_QUERY;
		mapper = getSessionVariantsMapper;
	}

	return {
		mapper,
		query
	};
};

interface ISessionCardProps extends React.HTMLAttributes<HTMLElement> {
	label?: string;
}

const SessionCard: React.FC<ISessionCardProps> = ({label}) => {
	const [{sessionViewTriggered}, dispatch]: any = useStateValue();

	const {
		router: {
			params: {id: experimentId}
		}
	} = useContext(BasePage.Context);

	const {mapper, query} = getSessionView(sessionViewTriggered);

	const {data, loading, ...result} = useQuery(query, {
		variables: {experimentId}
	});

	const fakeLoading = useFakeLoading(data);

	return (
		<Card className={CLASSNAME} minHeight={405}>
			<Card.Header className='align-items-center d-flex justify-content-between'>
				<Card.Title>{label}</Card.Title>

				<ClayButton.Group>
					<ClayButton
						className={getCN('button-root', {
							active: sessionViewTriggered === TOTAL
						})}
						displayType='secondary'
						onClick={() =>
							dispatch({
								newAction: TOTAL,
								type: 'changeSessionView'
							})
						}
						small
					>
						<ClayIcon
							className='icon-root mr-2'
							symbol='session-single-chart'
						/>

						{Liferay.Language.get('total')}
					</ClayButton>

					<ClayButton
						className={getCN('button-root', {
							active: sessionViewTriggered === PER_VARIANT
						})}
						displayType='secondary'
						onClick={() =>
							dispatch({
								newAction: PER_VARIANT,
								type: 'changeSessionView'
							})
						}
						small
					>
						<ClayIcon
							className='icon-root mr-2'
							symbol='session-multiple-chart'
						/>

						{Liferay.Language.get('per-variant')}
					</ClayButton>
				</ClayButton.Group>
			</Card.Header>

			<Card.Body>
				{
					<SafeResults
						data={data}
						loading={loading || fakeLoading}
						{...result}
					>
						{props => {
							const {empty, ...chartProps} = mapper(props);

							if (empty) {
								return (
									<NoResultsDisplay
										description={Liferay.Language.get(
											'metrics-will-show-once-there-are-visitors-to-your-variants'
										)}
										icon={{
											border: false,
											size: Sizes.XLarge,
											symbol: 'ac-chart'
										}}
										title={Liferay.Language.get(
											'we-are-currently-collecting-data'
										)}
									/>
								);
							}

							return <LineChart {...chartProps} />;
						}}
					</SafeResults>
				}
			</Card.Body>
		</Card>
	);
};

export default SessionCard;
