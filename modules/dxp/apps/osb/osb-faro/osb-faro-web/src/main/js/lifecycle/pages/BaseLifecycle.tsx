import * as API from 'shared/api';
import * as breadcrumbs from 'shared/util/breadcrumbs';
import AccountsDataSet from 'shared/components/AccountsDataSet';
import BasePage from 'shared/components/base-page';
import GlobalFilters from '../components/GlobalFilters';
import LifecycleChart from 'lifecycle/components/LifecycleChart';
import OverviewSection from '../components/OverviewSection';
import React, {useContext} from 'react';
import {ChannelContext} from 'shared/context/channel';
import {
	LifecycleContextProvider,
	useLifecycle
} from '../context/LifecycleContext';
import {SectionHeader} from 'shared/components/SectionHeader';
import {useParams} from 'react-router-dom';
import {useRequest} from 'shared/hooks/useRequest';

const LifecycleOverview = () => {
	const {filters} = useLifecycle();

	const {groupId} = useParams();

	const {data: overviewData, loading: overviewLoading} = useRequest({
		dataSourceFn: API.lifecycle.fetchOverviewMetrics,
		variables: {
			country: filters.countryFilter,
			groupId: groupId!,
			industry: filters.industryFilter,
			lifecycleId: API.lifecycle.DEFAULT_LIFECYCLE_ID
		}
	});

	return <OverviewSection loading={overviewLoading} metrics={overviewData} />;
};

const LifecycleStagesSection = () => {
	const {filters} = useLifecycle();

	const {groupId} = useParams();

	const {
		data: stagesData,
		error: stagesError,
		loading: stagesLoading
	} = useRequest({
		dataSourceFn: API.lifecycle.fetchLifecycleStages as (params: {
			[key: string]: any;
		}) => Promise<any>,
		variables: {
			country: filters.countryFilter,
			groupId,
			industry: filters.industryFilter,
			lifecycleId: API.lifecycle.DEFAULT_LIFECYCLE_ID
		}
	});

	return (
		<LifecycleChart
			error={!!stagesError}
			loading={stagesLoading}
			stages={stagesData}
		/>
	);
};

const LifecycleAccounts = () => {
	const {filters} = useLifecycle();

	const {channelId, groupId, lifecycleId} = useParams();

	return (
		<>
			<SectionHeader
				icon='box-container'
				title={Liferay.Language.get('accounts')}
			/>

			<AccountsDataSet
				apiURL={`/o/faro/contacts/${groupId!}/account-lifecycle/${lifecycleId!}/accounts`}
				channelId={channelId!}
				countryFilter={filters.countryFilter}
				groupId={groupId!}
				industryFilter={filters.industryFilter}
				lifecycleStageFilter={filters.lifecycleStageFilter}
			/>
		</>
	);
};

const BaseLifecycle = () => {
	const {selectedChannel} = useContext(ChannelContext);

	const {channelId, groupId} = useParams();

	return (
		<LifecycleContextProvider>
			<BasePage documentTitle={Liferay.Language.get('lifecycles')}>
				<BasePage.Header
					breadcrumbs={[
						breadcrumbs.getHome({
							channelId: channelId!,
							groupId: groupId!,
							label: selectedChannel?.name
						})
					]}
					groupId={groupId!}
				>
					<BasePage.Row>
						<BasePage.Header.TitleSection
							className='mb-3'
							title={Liferay.Language.get('lifecycles')}
						/>
					</BasePage.Row>
				</BasePage.Header>
				<BasePage.SubHeader>
					<div className='d-flex justify-content-between w-100'>
						<GlobalFilters />
					</div>
				</BasePage.SubHeader>
				<BasePage.Body>
					<LifecycleOverview />

					<LifecycleStagesSection />

					<LifecycleAccounts />
				</BasePage.Body>
			</BasePage>
		</LifecycleContextProvider>
	);
};

export default BaseLifecycle;
