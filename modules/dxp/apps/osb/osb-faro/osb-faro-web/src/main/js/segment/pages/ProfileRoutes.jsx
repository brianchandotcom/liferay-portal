import * as API from 'shared/api';
import * as breadcrumbs from 'shared/util/breadcrumbs';
import BasePage from 'shared/components/base-page';
import BundleRouter from 'route-middleware/BundleRouter';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import EmbeddedAlertList from 'shared/components/EmbeddedAlertList';
import ErrorPage from 'shared/pages/ErrorPage';
import getCN from 'classnames';
import Label from 'shared/components/Label';
import Loading from 'shared/components/Loading';
import React, {lazy, Suspense, useContext, useEffect, useState} from 'react';
import RouteNotFound from 'shared/components/RouteNotFound';
import {AlertTypes} from 'shared/components/Alert';
import {ChannelContext} from 'shared/context/channel';
import {formatUTCDate} from 'shared/util/date';
import {Routes, SEGMENTS, toRoute} from 'shared/util/router';
import {Segment} from 'shared/util/records';
import {SegmentStates, SegmentTypes} from 'shared/util/constants';
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

const SEGMENTS_LANGUAGE_MAP = {
	[SegmentTypes.Batch]: Liferay.Language.get('batch-segment'),
	[SegmentTypes.RealTime]: Liferay.Language.get('real-time-segment')
};

export const SegmentProfileRoutes = () => {
	const {selectedChannel} = useContext(ChannelContext);

	const {channelId, groupId, id} = useParams();

	const {data, error, loading, refetch} = useRequest({
		dataSourceFn: API.individualSegment.fetch,
		variables: {
			groupId,
			includeReferencedObjects: true,
			segmentId: id
		}
	});

	const segment = new Segment(data);

	const [segmentDetails, setSegmentDetails] = useState({
		dateModified: segment.dateModified,
		name: segment.name,
		segmentType: segment.segmentType
	});

	useEffect(() => {
		if (data && !loading) {
			setSegmentDetails({
				dateModified: segment.dateModified,
				name: segment.name,
				segmentType: segment.segmentType
			});
		}
	}, [data, loading]);

	const title = segmentDetails.name || Liferay.Language.get('unknown');

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

	const checkDisabled = () => segment.state === SegmentStates.Disabled;

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

	return (
		<BasePage
			className={getCN(
				'segment-profile-root',
				'segment-overview-root',
				'overview-root'
			)}
			documentTitle={`${segmentDetails.name} - ${Liferay.Language.get(
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
					breadcrumbs.getEntityName({label: segmentDetails.name})
				]}
				groupId={groupId}
			>
				<BasePage.Row>
					<BasePage.Header.TitleSection
						className='mb-3'
						subtitle={`Last update: ${formatUTCDate(
							segmentDetails.dateModified,
							'MMM DD, YYYY hh:mm a'
						)
							.replace('am', 'a.m.')
							.replace('pm', 'p.m.')}`}
						title={title}
					>
						<Label display='secondary' size='lg' uppercase>
							{SEGMENTS_LANGUAGE_MAP[segmentDetails.segmentType]}
						</Label>
					</BasePage.Header.TitleSection>
				</BasePage.Row>
			</BasePage.Header>

			<BasePage.SubHeader>
				<div className='d-flex justify-content-end w-100'>
					<ClayButton
						borderless
						button
						className='button-root'
						disabled={loading}
						displayType='secondary'
						key={Liferay.Language.get('refresh-data')}
						onClick={refetch}
					>
						{loading ? (
							<Loading align='false' className='mr-2 mt-n1' />
						) : (
							<ClayIcon className='mr-2' symbol='reload' />
						)}
						{Liferay.Language.get('refresh-data')}
					</ClayButton>
					<ClayLink
						borderless
						button
						className='button-root'
						displayType='secondary'
						href={toRoute(Routes.CONTACTS_SEGMENT_EDIT, {
							channelId,
							groupId,
							id
						})}
					>
						<ClayIcon className='mr-2' symbol='pencil' />
						{Liferay.Language.get('edit-segment')}
					</ClayLink>
				</div>
			</BasePage.SubHeader>

			<EmbeddedAlertList alerts={getAlerts()} />

			<BasePage.Body disabled={checkDisabled()}>
				<Suspense fallback={<Loading />}>
					<Switch>
						<BundleRouter
							componentProps={{segment}}
							data={Membership}
							exact
							path={Routes.CONTACTS_SEGMENT_MEMBERSHIP}
						/>

						<BundleRouter
							componentProps={{segment}}
							data={InterestDetails}
							exact
							path={Routes.CONTACTS_SEGMENT_INTEREST_DETAILS}
						/>

						<BundleRouter
							componentProps={{segment}}
							data={Interests}
							destructured={false}
							exact
							path={Routes.CONTACTS_SEGMENT_INTERESTS}
						/>

						<BundleRouter
							componentProps={{segment}}
							data={Distribution}
							exact
							path={Routes.CONTACTS_SEGMENT_DISTRIBUTION}
						/>

						<BundleRouter
							componentProps={{segment}}
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
