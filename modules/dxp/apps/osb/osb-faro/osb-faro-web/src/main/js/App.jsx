import AlertFeed from 'shared/components/AlertFeed';
import autobind from 'autobind-decorator';
import BundleRouter from './route-middleware/BundleRouter';
import ChannelProvider from 'shared/context/channel';
import client from 'shared/apollo/client';
import ErrorPage from 'shared/pages/ErrorPage';
import Loading from 'shared/components/Loading';
import ModalRenderer from 'shared/components/ModalRenderer';
import pathToRegexp from 'path-to-regexp';
import React, {lazy, Suspense} from 'react';
import RouteNotFound from 'shared/components/RouteNotFound';
import store from 'shared/store';
import Tooltip from 'shared/components/Tooltip';
import UnassignedSegmentsProvider from 'shared/context/unassignedSegments';
import {ApolloProvider} from '@apollo/react-components';
import {ApolloProvider as ApolloProviderHooks} from '@apollo/react-hooks';
import {ClayIconSpriteContext} from '@clayui/icon';
import {ClayLinkContext} from '@clayui/link';
import {close, modalTypes, open} from 'shared/actions/modals';
import {connect, Provider} from 'react-redux';
import {hasChanges} from 'shared/util/react';
import {
	Link,
	matchPath,
	Route,
	BrowserRouter as Router,
	Switch,
	withRouter
} from 'react-router-dom';
import {OAuthUpgradeWarningContext} from 'shared/context/oAuthUpgradeWarning';
import {OnboardingContext} from 'shared/context/onboarding';
import {PROD_MODE, spritemap} from 'shared/util/constants';
import {PropTypes} from 'prop-types';
import {Routes} from 'shared/util/router';
import {saveState} from 'shared/store/local-storage';
import {setBackURL} from 'shared/actions/settings';
import {throttle} from 'lodash';

// Workspaces

const AddWorkspace = lazy(() =>
	import(/* webpackChunkName: "AddWorkspace" */ './shared/pages/AddWorkspace')
);
const SelectWorkspaceAccount = lazy(() =>
	import(
		/* webpackChunkName: "SelectWorkspaceAccount" */ './shared/pages/SelectWorkspaceAccount'
	)
);
const Workspaces = lazy(() =>
	import(/* webpackChunkName: "Workspaces" */ './shared/pages/Workspaces')
);

// WorkspaceLayer
const WorkspaceLayer = lazy(() =>
	import(
		/* webpackChunkName: "WorkspaceLayer" */ './shared/components/WorkspaceLayer'
	)
);

// Other

const OAuthReceive = lazy(() =>
	import(
		/* webpackChunkName: "OAuthReceive" */ './settings/pages/OAuthReceive'
	)
);

const SETTINGS_PATH_REGEX = pathToRegexp(Routes.SETTINGS, null, {end: false});

@withRouter
@connect((store, {location: {pathname}}) => {
	const matchingPath = matchPath(pathname, {
		path: Routes.WORKSPACE_WITH_ID
	});

	const currentUserId = store.getIn(['currentUser', 'data']);
	const currentUser = store.getIn(['users', currentUserId, 'data']);

	if (matchingPath) {
		const {
			params: {groupId}
		} = matchingPath;

		return {
			currentUser,
			project: store.getIn(['projects', groupId, 'data'])
		};
	}

	return {currentUser};
})
class RoutesContainer extends React.Component {
	static propTypes = {
		dispatch: PropTypes.func.isRequired
	};

	componentDidUpdate(prevProps) {
		const {currentUser, project} = this.props;

		const pendoFn =
			pendo && pendo.isReady && pendo.isReady()
				? pendo.identify
				: pendo.initialize;

		if (hasChanges(prevProps, this.props, 'location')) {
			this.onRouteChanged();
		}

		let account;
		let visitor;

		if (hasChanges(prevProps, this.props, 'currentUser', 'project')) {
			if (currentUser) {
				// We use userId instead of id because userId persists across workspaces.
				const {
					emailAddress,
					name: userName,
					roleName,
					userId
				} = currentUser;

				visitor = {
					emailAddress,
					id: userId,
					name: userName,
					roleName
				};

				analytics && analytics.identify(userId, null, {ip: '0'});
			}

			if (project) {
				const {
					corpProjectName,
					corpProjectUuid,
					faroSubscription: faroSubscriptionIMap,
					groupId,
					name: workspaceName,
					ownerEmailAddress: workspaceOwnerEmailAddress,
					serverLocation
				} = project;

				const subscriptionName = faroSubscriptionIMap.get('name');

				account = {
					corpProjectNameId: `${corpProjectName}: ${corpProjectUuid}`,
					id: groupId,
					serverLocation,
					subscriptionName,
					workspaceName,
					workspaceOwnerEmailAddress
				};

				analytics &&
					analytics.group(
						groupId,
						{
							groupId,
							serverLocation,
							subscriptionName,
							workspaceName
						},
						{ip: '0'}
					);
			}

			if (account || visitor) {
				pendoFn &&
					pendoFn({
						account,
						visitor
					});
			}
		}
	}

	onRouteChanged() {
		const {
			document: {referrer, title},
			location: {href, pathname, search}
		} = window;

		analytics.page(
			{
				path: pathname,
				referrer,
				search,
				title,
				url: href
			},
			{ip: '0'}
		);

		if (!SETTINGS_PATH_REGEX.test(pathname)) {
			this.props.dispatch(
				setBackURL(
					`${window.location.pathname}${window.location.search}`
				)
			);
		}
	}

	render() {
		const {children, location} = this.props;

		return location && location.state && location.state.notFoundError ? (
			<ErrorPage />
		) : (
			children
		);
	}
}

export default class App extends React.Component {
	static defaultProps = {
		setup: true
	};

	static propTypes = {
		setup: PropTypes.bool
	};

	state = {
		onboardingTriggered: false,
		showOAuthUpgradeWarning: null
	};

	constructor(props) {
		super(props);

		store.subscribe(throttle(() => saveState(store.getState()), 1000));
	}

	@autobind
	handleUserConfirmation(message, callback) {
		store.dispatch(
			open(modalTypes.CONFIRMATION_MODAL, {
				cancelMessage: Liferay.Language.get('stay-on-page'),
				message,
				modalVariant: 'modal-warning',
				onClose: () => {
					callback(false);

					store.dispatch(close());
				},
				onSubmit: () => {
					callback(true);
				},
				submitButtonDisplay: 'warning',
				submitMessage: Liferay.Language.get('leave-page'),
				title: Liferay.Language.get('unsaved-changes'),
				titleIcon: 'warning-full'
			})
		);
	}

	render() {
		const {onboardingTriggered, showOAuthUpgradeWarning} = this.state;

		return (
			<ApolloProvider client={client}>
				<ApolloProviderHooks client={client}>
					<Provider store={store}>
						<ClayIconSpriteContext.Provider value={spritemap}>
							<ClayLinkContext.Provider
								value={({children, href, ...otherProps}) => (
									<Link to={href} {...otherProps}>
										{children}
									</Link>
								)}
							>
								<UnassignedSegmentsProvider>
									<OAuthUpgradeWarningContext.Provider
										value={{
											setShowOAuthUpgradeWarning: value =>
												this.setState({
													showOAuthUpgradeWarning: value
												}),
											showOAuthUpgradeWarning
										}}
									>
										<OnboardingContext.Provider
											value={{
												onboardingTriggered,
												setOnboardingTriggered: () =>
													this.setState({
														onboardingTriggered: true
													})
											}}
										>
											<ChannelProvider>
												{/* eslint-disable react/jsx-handler-names */}
												<Router
													getUserConfirmation={
														this
															.handleUserConfirmation
													}
												>
													{/* eslint-enable react/jsx-handler-names */}
													<RoutesContainer>
														<AlertFeed />

														<Tooltip />

														<ModalRenderer />

														<Suspense
															fallback={
																<Loading />
															}
														>
															<Switch>
																<BundleRouter
																	data={
																		Workspaces
																	}
																	exact
																	path={
																		Routes.BASE
																	}
																/>

																<BundleRouter
																	data={
																		Workspaces
																	}
																	exact
																	path={
																		Routes.WORKSPACES
																	}
																/>

																<BundleRouter
																	data={
																		SelectWorkspaceAccount
																	}
																	exact
																	path={
																		Routes.WORKSPACE_ADD
																	}
																/>

																{!PROD_MODE && (
																	<BundleRouter
																		data={
																			AddWorkspace
																		}
																		exact
																		path={
																			Routes.WORKSPACE_ADD_TRIAL
																		}
																	/>
																)}

																<BundleRouter
																	data={
																		AddWorkspace
																	}
																	exact
																	path={
																		Routes.WORKSPACE_ADD_WITH_CORP_PROJECT_UUID
																	}
																/>

																<BundleRouter
																	data={
																		SelectWorkspaceAccount
																	}
																	exact
																	path={
																		Routes.WORKSPACE_SELECT_ACCOUNT
																	}
																/>

																<BundleRouter
																	data={
																		OAuthReceive
																	}
																	exact
																	path={
																		Routes.OAUTH_RECEIVE
																	}
																/>

																<Route
																	component={
																		Loading
																	}
																	path={
																		Routes.LOADING
																	}
																/>

																<WorkspaceLayer />

																<RouteNotFound />
															</Switch>
														</Suspense>
													</RoutesContainer>
												</Router>
											</ChannelProvider>
										</OnboardingContext.Provider>
									</OAuthUpgradeWarningContext.Provider>
								</UnassignedSegmentsProvider>
							</ClayLinkContext.Provider>
						</ClayIconSpriteContext.Provider>
					</Provider>
				</ApolloProviderHooks>
			</ApolloProvider>
		);
	}
}
