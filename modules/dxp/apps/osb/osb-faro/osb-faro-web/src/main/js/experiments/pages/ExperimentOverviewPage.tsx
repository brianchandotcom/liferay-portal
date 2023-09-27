import * as breadcrumbs from 'shared/util/breadcrumbs';
import BasePage from 'shared/components/base-page';
import DeleteExperimentModal from 'experiments/components/modals/DeleteExperimentModal';
import ErrorPage from 'shared/pages/ErrorPage';
import React, {useState} from 'react';
import SessionCard from 'experiments/components/SessionCard';
import SummaryCard from 'experiments/components/summary-card/SummaryCard';
import TextTruncate from 'shared/components/TextTruncate';
import VariantCard from 'experiments/components/variant-card/index';
import {connect, ConnectedProps} from 'react-redux';
import {EXPERIMENT_ROOT_QUERY} from 'experiments/queries/ExperimentQuery';
import {getActions, useAddRefetch} from 'experiments/util/experiments';
import {RootState} from 'shared/store';
import {Router} from 'shared/types';
import {Routes, toRoute} from 'shared/util/router';
import {SafeResults} from 'shared/hoc/util';
import {StateProvider} from 'experiments/state';
import {useChannelContext} from 'shared/context/channel';
import {useModal} from '@clayui/modal';
import {useQuery} from '@apollo/react-hooks';

const NAV_ITEMS = [
	{
		exact: true,
		label: Liferay.Language.get('report'),
		route: Routes.TESTS_OVERVIEW
	}
];

const connector = connect(
	(
		store: RootState,
		{
			router: {
				params: {groupId}
			}
		}: {router: Router}
	) => ({
		timeZoneId: store.getIn([
			'projects',
			groupId,
			'data',
			'timeZone',
			'timeZoneId'
		])
	})
);

type PropsFromRedux = ConnectedProps<typeof connector>;

interface IExperimentOverviewPage
	extends React.HTMLAttributes<HTMLElement>,
		PropsFromRedux {
	router: Router;
}

interface IExperimentActionsProps extends React.HTMLAttributes<HTMLElement> {
	experiment: {
		id: string;
		pageURL: string;
		publishable: boolean;
		status: string;
	};
}

const ExperimentActions: React.FC<IExperimentActionsProps> = ({
	experiment: {id, pageURL, publishable, status}
}) => {
	const [visibleDeleteModal, setVisibleDeleteModal] = useState(false);
	const {observer, onClose} = useModal({
		onClose: () => setVisibleDeleteModal(false)
	});

	return (
		<>
			<BasePage.Header.Actions
				actions={getActions(status, {
					id,
					onDelete: () => setVisibleDeleteModal(true),
					pageURL,
					publishable
				})}
			/>

			{visibleDeleteModal && (
				<DeleteExperimentModal
					experimentId={id}
					observer={observer}
					onClose={onClose}
				/>
			)}
		</>
	);
};

const ExperimentOverviewPage: React.FC<IExperimentOverviewPage> = ({
	router,
	timeZoneId
}) => {
	const {
		params: {channelId, groupId, id: experimentId}
	} = router;

	const {refetch, ...result} = useQuery(EXPERIMENT_ROOT_QUERY, {
		fetchPolicy: 'network-only',
		variables: {experimentId}
	});

	const {selectedChannel} = useChannelContext();

	useAddRefetch(refetch);

	return (
		<SafeResults {...result}>
			{({experiment}) =>
				experiment ? (
					<BasePage documentTitle={Liferay.Language.get('tests')}>
						<BasePage.Header
							breadcrumbs={[
								breadcrumbs.getHome({
									channelId,
									groupId,
									label:
										selectedChannel && selectedChannel.name
								}),
								breadcrumbs.getTests({channelId, groupId}),
								breadcrumbs.getEntityName({
									label: experiment.name
								})
							]}
							groupId={groupId}
						>
							<BasePage.Header.TitleSection
								subtitle={
									<TextTruncate title={experiment.pageURL}>
										<a
											href={experiment.pageURL}
											target='_blank'
										>
											{experiment.pageURL}
										</a>
									</TextTruncate>
								}
								title={experiment.name}
							/>

							<ExperimentActions experiment={experiment} />

							<BasePage.Header.NavBar
								items={NAV_ITEMS}
								routeParams={{
									channelId,
									groupId,
									id: experimentId,
									title: experiment.name,
									touchpoint: experiment.pageURL
								}}
							/>
						</BasePage.Header>

						<BasePage.Context.Provider
							value={{
								filters: {},
								router
							}}
						>
							<BasePage.Body>
								<div className='row'>
									<div className='col-sm-12'>
										<SummaryCard
											status={experiment.status}
											timeZoneId={timeZoneId}
										/>
									</div>
								</div>

								{experiment.status !== 'DRAFT' && (
									<>
										<div className='row'>
											<div className='col-sm-12'>
												<VariantCard
													label={Liferay.Language.get(
														'variant-report'
													)}
												/>
											</div>
										</div>

										<div className='row'>
											<div className='col-sm-12'>
												<SessionCard
													label={Liferay.Language.get(
														'test-sessions'
													)}
												/>
											</div>
										</div>
									</>
								)}
							</BasePage.Body>
						</BasePage.Context.Provider>
					</BasePage>
				) : (
					<ErrorPage
						href={toRoute(Routes.TESTS, {channelId, groupId})}
						linkLabel={Liferay.Language.get('go-to-tests')}
					/>
				)
			}
		</SafeResults>
	);
};

const ExperimentWithState = props => (
	<StateProvider>
		<ExperimentOverviewPage {...props} />
	</StateProvider>
);

export default connector(ExperimentWithState);
