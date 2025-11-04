import * as API from 'shared/api';
import * as breadcrumbs from 'shared/util/breadcrumbs';
import BasePage from 'shared/components/base-page';
import BundleRouter from 'route-middleware/BundleRouter';
import DownloadPDFReport from 'shared/components/download-report/DownloadPDFReport';
import EmbeddedAlertList from 'shared/components/EmbeddedAlertList';
import ErrorPage from 'shared/pages/ErrorPage';
import getCN from 'classnames';
import Label from 'shared/components/Label';
import Loading from 'shared/components/Loading';
import React, {lazy, Suspense, useContext} from 'react';
import RouteNotFound from 'shared/components/RouteNotFound';
import {AlertTypes} from 'shared/components/Alert';
import {ChannelContext} from 'shared/context/channel';
import {CSVType} from 'shared/components/download-report/utils';
import {DownloadStaticCSVReport} from 'shared/components/download-report/DownloadStaticCSVReport';
import {getMatchedRoute, Routes, SEGMENTS, toRoute} from 'shared/util/router';
import {Segment} from 'shared/util/records';
import {SegmentStates} from 'shared/util/constants';
import {Switch, useParams} from 'react-router-dom';
import {useRequest} from 'shared/hooks/useRequest';

const Overview = lazy(() =>
	import(/* webpackChunkName: "SegmentOverview" */ './Overview')
);
const Membership = lazy(() =>
	import(/* webpackChunkName: "SegmentMembership" */ './Membership')
);
const Interests = lazy(() =>
	import(/* webpackChunkName: "SegmentInterests" */ './Interests')
);
const InterestDetails = lazy(() =>
	import(/* webpackChunkName: "SegmentInterestDetails" */ './InterestDetails')
);
const Distribution = lazy(() =>
	import(/* webpackChunkName: "SegmentDistribution" */ './Distribution')
);

const NAV_ITEMS = [
	{
		exact: true,
		label: Liferay.Language.get('overview'),
		route: Routes.CONTACTS_SEGMENT
	},
	{
		exact: true,
		label: Liferay.Language.get('membership'),
		route: Routes.CONTACTS_SEGMENT_MEMBERSHIP
	},
	{
		exact: false,
		label: Liferay.Language.get('interests'),
		route: Routes.CONTACTS_SEGMENT_INTERESTS
	},
	{
		exact: true,
		label: Liferay.Language.get('distribution'),
		route: Routes.CONTACTS_SEGMENT_DISTRIBUTION
	}
];

export const SegmentProfileRoutes = () => {
	const contextType = useContext(ChannelContext);

	const checkDisabled = () => segment.state === SegmentStates.Disabled;

	const {channelId, groupId, id} = useParams();
	const {data, error, loading} = useRequest({
		dataSourceFn: API.individualSegment.fetch,
		variables: {
			groupId,
			includeReferencedObjects: true,
			segmentId: id
		}
	});

	const segment = new Segment(data);

	if (loading) {
		return <Loading />;
	}

	if (error) {
		return (
			<ErrorPage
				href={toRoute(Routes.CONTACTS_LIST_ENTITY, {
					channelId,
					groupId,
					type: SEGMENTS
				})}
				linkLabel={Liferay.Language.get('go-to-segments')}
				message={Liferay.Language.get(
					'the-segment-you-are-looking-for-does-not-exist'
				)}
				subtitle={Liferay.Language.get('segment-not-found')}
			/>
		);
	}

	const getAlerts = () => {
		if (segment.state === SegmentStates.InProgress) {
			return [
				{
					alertType: AlertTypes.Info,
					message: Liferay.Language.get(
						'segment-data-is-processing-please-check-back-later'
					),
					stripe: true
				}
			];
		} else if (checkDisabled()) {
			return [
				{
					alertType: AlertTypes.Danger,
					message: Liferay.Language.get(
						'this-segment-is-disabled-because-some-criteria-has-been-affected-by-removal-of-a-data-source.-to-continue-using-this-segment-please-update-the-criteria'
					),
					stripe: true
				}
			];
		}
	};

	const getClassNameForRoute = matchedRoute => {
		switch (matchedRoute) {
			case Routes.CONTACTS_SEGMENT_DISTRIBUTION:
				return getCN('segment-distribution-root');
			case Routes.CONTACTS_SEGMENT_INTERESTS:
				return getCN('contacts-interests-root');
			case Routes.CONTACTS_SEGMENT:
				return getCN('segment-overview-root', 'overview-root');
			case Routes.CONTACTS_SEGMENT_MEMBERSHIP:
			default:
				return;
		}
	};

	const matchedRoute = getMatchedRoute(NAV_ITEMS);

	const {selectedChannel} = contextType;

	const componentProps = {segment};

	const title = segment.name || Liferay.Language.get('unknown');

	return (
		<BasePage
			className={getCN(
				'segment-profile-root',
				getClassNameForRoute(matchedRoute)
			)}
			documentTitle={`${segment.name} - ${Liferay.Language.get(
				'segment'
			)}`}
		>
			<BasePage.Header
				breadcrumbs={[
					breadcrumbs.getHome({
						channelId,
						groupId,
						label: selectedChannel && selectedChannel.name
					}),
					breadcrumbs.getSegments({channelId, groupId}),
					breadcrumbs.getEntityName({label: segment.name})
				]}
				groupId={groupId}
			>
				<BasePage.Row>
					<BasePage.Header.TitleSection title={title}>
						<Label display='secondary' size='lg' uppercase>
							{Liferay.Language.get('segment')}
						</Label>
					</BasePage.Header.TitleSection>

					<BasePage.Header.Section>
						<BasePage.Header.PageActions
							actions={[
								{
									button: true,
									displayType: 'secondary',
									href: toRoute(
										Routes.CONTACTS_SEGMENT_EDIT,
										{
											channelId,
											groupId,
											id,
											type: SEGMENTS
										}
									),
									label: Liferay.Language.get('edit-segment')
								}
							]}
						/>
					</BasePage.Header.Section>
				</BasePage.Row>

				<BasePage.Header.NavBar
					items={NAV_ITEMS}
					routeParams={{channelId, groupId, id}}
				/>
			</BasePage.Header>

			{getMatchedRoute(NAV_ITEMS) === Routes.CONTACTS_SEGMENT && (
				<BasePage.SubHeader>
					<div className='d-flex justify-content-end w-100'>
						<DownloadPDFReport
							disabled={false}
							showDateRange={false}
							subtitle={selectedChannel?.name}
							title={title}
						/>
					</div>
				</BasePage.SubHeader>
			)}

			{getMatchedRoute(NAV_ITEMS) ===
				Routes.CONTACTS_SEGMENT_MEMBERSHIP && (
				<BasePage.SubHeader>
					<div className='d-flex justify-content-end w-100'>
						<DownloadStaticCSVReport
							disabled={checkDisabled()}
							segmentId={segment.id}
							type={CSVType.Membership}
							typeLang={Liferay.Language.get(
								'segment-membership'
							)}
						/>
					</div>
				</BasePage.SubHeader>
			)}

			<EmbeddedAlertList alerts={getAlerts()} />

			<BasePage.Body
				disabled={checkDisabled()}
				pageContainer={
					matchedRoute !== Routes.CONTACTS_SEGMENT_DISTRIBUTION
				}
			>
				<Suspense fallback={<Loading />}>
					<Switch>
						<BundleRouter
							componentProps={componentProps}
							data={Membership}
							exact
							path={Routes.CONTACTS_SEGMENT_MEMBERSHIP}
						/>

						<BundleRouter
							componentProps={componentProps}
							data={InterestDetails}
							exact
							path={Routes.CONTACTS_SEGMENT_INTEREST_DETAILS}
						/>

						<BundleRouter
							componentProps={componentProps}
							data={Interests}
							destructured={false}
							exact
							path={Routes.CONTACTS_SEGMENT_INTERESTS}
						/>

						<BundleRouter
							componentProps={componentProps}
							data={Distribution}
							exact
							path={Routes.CONTACTS_SEGMENT_DISTRIBUTION}
						/>

						<BundleRouter
							componentProps={componentProps}
							data={Overview}
							exact
							path={Routes.CONTACTS_SEGMENT}
						/>

						<RouteNotFound />
					</Switch>
				</Suspense>
			</BasePage.Body>
		</BasePage>
	);
};

export default SegmentProfileRoutes;
