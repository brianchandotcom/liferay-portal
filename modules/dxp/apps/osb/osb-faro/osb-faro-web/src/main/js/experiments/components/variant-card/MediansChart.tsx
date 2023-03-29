import BasePage from 'shared/components/base-page';
import getMedianMapper from 'experiments/hocs/mappers/experiment-variant-median-mapper';
import HTMLBarChart from 'shared/components/HTMLBarChart';
import Legend from 'shared/components/Legend';
import NoResultsDisplay from 'shared/components/NoResultsDisplay';
import React, {useContext} from 'react';
import {EXPERIMENT_QUERY} from 'experiments/queries/ExperimentQuery';
import {SafeResults} from 'shared/hoc/util';
import {Sizes} from 'shared/util/constants';
import {useQuery} from '@apollo/react-hooks';

const MediansChart = () => {
	const {
		router: {
			params: {id: experimentId}
		}
	} = useContext(BasePage.Context);

	const result = useQuery(EXPERIMENT_QUERY, {
		variables: {experimentId}
	});

	return (
		<SafeResults {...result}>
			{props => {
				const {empty, legend, mediansData} = getMedianMapper(props);

				return empty ? (
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
				) : (
					<>
						<HTMLBarChart {...mediansData} />

						<Legend data={legend} />
					</>
				);
			}}
		</SafeResults>
	);
};

export default MediansChart;
