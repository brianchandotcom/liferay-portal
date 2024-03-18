import * as breadcrumbs from 'shared/util/breadcrumbs';
import BasePage from 'shared/components/base-page';
import ClayLink from '@clayui/link';
import DeleteExperimentModal from 'experiments/components/modals/DeleteExperimentModal';
import React, {useState} from 'react';
import StatesRenderer from 'shared/components/states-renderer/StatesRenderer';
import TestTrafficCard from 'experiments/components/test-traffic/TestTrafficCard';
import TextTruncate from 'shared/components/TextTruncate';
import {
	EXPERIMENT_DRAFT_QUERY,
	EXPERIMENT_QUERY,
	EXPERIMENT_STATUS_QUERY
} from 'experiments/queries/ExperimentQuery';
import {getActions} from 'experiments/util/experiments';
import {Routes} from 'shared/util/router';
import {SessionsCard} from 'experiments/components/SessionsCard';
import {Status} from 'experiments/components/summary-card/types';
import {SummaryCard} from 'experiments/components/summary-card/SummaryCard';
import {useChannelContext} from 'shared/context/channel';
import {useModal} from '@clayui/modal';
import {useParams} from 'react-router-dom';
import {useQuery} from '@apollo/react-hooks';
import {VariantCard} from 'experiments/components/variant-card/VariantCard';

const ExperimentActions = ({experiment}) => {
	const {id, pageURL, publishable, status} = experiment;
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

const ExperimentOverviewPage = () => {
	const {id} = useParams();

	const {data, error, loading} = useQuery(EXPERIMENT_STATUS_QUERY, {
		fetchPolicy: 'no-cache',
		variables: {experimentId: id}
	});

	return (
		<StatesRenderer error={!!error} loading={loading}>
			<StatesRenderer.Loading />

			<StatesRenderer.Error apolloError={error} />

			{!!data && (
				<StatesRenderer.Success>
					<ExperimentOverviewContent
						status={data.experiment.status}
					/>
				</StatesRenderer.Success>
			)}
		</StatesRenderer>
	);
};

const ExperimentOverviewContent = ({status}) => {
	const {channelId, groupId, id} = useParams();
	const {selectedChannel} = useChannelContext();

	let Query = EXPERIMENT_QUERY;

	if (status.toLowerCase() === Status.Draft) {
		Query = EXPERIMENT_DRAFT_QUERY;
	}

	const {data, error, loading} = useQuery(Query, {
		fetchPolicy: 'network-only',
		variables: {experimentId: id}
	});

	return (
		<StatesRenderer error={!!error} loading={loading}>
			<StatesRenderer.Loading />

			<StatesRenderer.Error apolloError={error} />

			{!!data && (
				<StatesRenderer.Success>
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
									label: data.experiment.name
								})
							]}
							groupId={groupId}
						>
							<BasePage.Header.TitleSection
								subtitle={
									<TextTruncate
										title={data.experiment.pageURL}
									>
										<ClayLink
											href={data.experiment.pageURL}
											target='_blank'
										>
											{data.experiment.pageURL}
										</ClayLink>
									</TextTruncate>
								}
								title={data.experiment.name}
							/>

							<ExperimentActions experiment={data.experiment} />

							<BasePage.Header.NavBar
								items={[
									{
										exact: true,
										label: Liferay.Language.get('report'),
										route: Routes.TESTS_OVERVIEW
									}
								]}
								routeParams={{
									channelId,
									groupId,
									id,
									title: data.experiment.name,
									touchpoint: data.experiment.pageURL
								}}
							/>
						</BasePage.Header>

						<BasePage.Body>
							<div className='row'>
								<div className='col-sm-12'>
									<SummaryCard experiment={data.experiment} />
								</div>
							</div>

							{data.experiment.status !== 'DRAFT' && (
								<>
									{data.experiment.type === 'MAB' && (
										<div className='row'>
											<div className='col-sm-12'>
												<TestTrafficCard
													experiment={data.experiment}
												/>
											</div>
										</div>
									)}

									<div className='row'>
										<div className='col-sm-12'>
											<VariantCard
												experiment={data.experiment}
											/>
										</div>
									</div>

									{data.experiment.type === 'AB' && (
										<div className='row'>
											<div className='col-sm-12'>
												<SessionsCard
													experiment={data.experiment}
												/>
											</div>
										</div>
									)}
								</>
							)}
						</BasePage.Body>
					</BasePage>
				</StatesRenderer.Success>
			)}
		</StatesRenderer>
	);
};

export default ExperimentOverviewPage;
