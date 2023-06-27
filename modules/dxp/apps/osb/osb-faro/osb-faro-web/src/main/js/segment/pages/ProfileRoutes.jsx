import * as breadcrumbs from 'shared/util/breadcrumbs';
import BasePage from 'shared/components/base-page';
import BundleRouter from 'route-middleware/BundleRouter';
import EmbeddedAlertList from 'shared/components/EmbeddedAlertList';
import getCN from 'classnames';
import Label from 'shared/components/Label';
import Loading from 'shared/components/Loading';
import React, {lazy, Suspense} from 'react';
import RouteNotFound from 'shared/components/RouteNotFound';
import {AlertTypes} from 'shared/components/Alert';
import {ChannelContext} from 'shared/context/channel';
import {compose} from 'shared/hoc';
import {getMatchedRoute, Routes, SEGMENTS, toRoute} from 'shared/util/router';
import {PropTypes} from 'prop-types';
import {Segment} from 'shared/util/records';
import {SegmentStates, SegmentTypes} from 'shared/util/constants';
import {Switch, withRouter} from 'react-router-dom';
import {withSegment} from 'shared/hoc/WithSegment';

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

export class SegmentProfileRoutes extends React.Component {
	static contextType = ChannelContext;

	static propTypes = {
		groupId: PropTypes.string.isRequired,
		id: PropTypes.string.isRequired,
		segment: PropTypes.instanceOf(Segment).isRequired
	};

	checkDisabled() {
		return this.props.segment.get('state') === SegmentStates.Disabled;
	}

	getAlerts() {
		const {segment} = this.props;

		if (segment.get('state') === SegmentStates.InProgress) {
			return [
				{
					alertType: AlertTypes.Info,
					message: Liferay.Language.get(
						'segment-data-is-processing-please-check-back-later'
					),
					stripe: true
				}
			];
		} else if (this.checkDisabled()) {
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
	}

	getClassNameForRoute(matchedRoute) {
		const {className} = this.props;

		switch (matchedRoute) {
			case Routes.CONTACTS_SEGMENT_DISTRIBUTION:
				return getCN('segment-distribution-root', className);
			case Routes.CONTACTS_SEGMENT_INTERESTS:
				return getCN('contacts-interests-root', className);
			case Routes.CONTACTS_SEGMENT:
				return getCN(
					'segment-overview-root',
					'overview-root',
					className
				);
			case Routes.CONTACTS_SEGMENT_MEMBERSHIP:
			default:
				return className;
		}
	}

	getPageTitleLabel() {
		const {
			segment: {segmentType}
		} = this.props;

		return segmentType === SegmentTypes.Dynamic
			? Liferay.Language.get('dynamic-segment')
			: Liferay.Language.get('static-segment');
	}

	render() {
		const {channelId, groupId, id, segment} = this.props;

		const matchedRoute = getMatchedRoute(NAV_ITEMS);

		const componentProps = {segment};

		const {selectedChannel} = this.context;

		return (
			<BasePage
				className={getCN(
					'segment-profile-root',
					this.getClassNameForRoute(matchedRoute)
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
						<BasePage.Header.TitleSection
							title={
								segment.name || Liferay.Language.get('unknown')
							}
						>
							<Label display='secondary' size='lg' uppercase>
								{this.getPageTitleLabel()}
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
										label: Liferay.Language.get(
											'edit-segment'
										)
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

				<EmbeddedAlertList alerts={this.getAlerts()} />

				<BasePage.Body
					disabled={this.checkDisabled()}
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
	}
}

export default compose(withRouter, withSegment(true))(SegmentProfileRoutes);
