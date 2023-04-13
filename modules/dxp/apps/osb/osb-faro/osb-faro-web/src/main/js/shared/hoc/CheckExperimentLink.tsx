import React, {useEffect} from 'react';
import {EXPERIMENT_ROOT_QUERY} from 'experiments/queries/ExperimentQuery';
import {matchPath} from 'react-router-dom';
import {Routes, toRoute} from 'shared/util/router';
import {useLazyQuery} from '@apollo/react-hooks';
import {WrapSafeResults} from 'shared/hoc/util';

type History = {
	replace: (path: string) => void;
};

type Location = {
	pathname: string;
};

interface IWrappedComponentProps {
	groupId: string;
	history: History;
	location: Location;
}

const checkExperimentLink = (
	WrappedComponent: React.ComponentType<IWrappedComponentProps>
) => ({groupId, history, location, ...otherProps}) => {
	const [getExperiment, {data, error, loading}] = useLazyQuery(
		EXPERIMENT_ROOT_QUERY
	);

	useEffect(() => {
		const isExperiment = matchPath<{channelId: string; id: string}>(
			location.pathname,
			{
				exact: true,
				path: Routes.TESTS_OVERVIEW
			}
		);

		if (isExperiment && !isExperiment.params.channelId) {
			getExperiment({
				variables: {experimentId: isExperiment.params.id}
			});
		}
	}, []);

	useEffect(() => {
		if (data && data.experiment) {
			const {
				experiment: {channelId, id}
			} = data;

			history.replace(
				toRoute(Routes.TESTS_OVERVIEW, {
					channelId,
					groupId,
					id
				})
			);
		}
	}, [data]);

	return (
		<WrapSafeResults
			{...{error: error || (data && !data.experiment), loading}}
			page
			pageDisplay
		>
			<WrappedComponent
				{...otherProps}
				groupId={groupId}
				history={history}
				location={location}
			/>
		</WrapSafeResults>
	);
};

export default checkExperimentLink;
