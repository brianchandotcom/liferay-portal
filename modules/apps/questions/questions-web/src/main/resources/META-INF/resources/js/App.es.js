/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClientContext} from 'graphql-hooks';
import React from 'react';
import {BrowserRouter, HashRouter, Route, Switch} from 'react-router-dom';

import {AppContextProvider} from './AppContext.es';
import {ErrorBoundary} from './components/ErrorBoundary.es';
import ProtectedRoute from './components/ProtectedRoute.es';
import useLazy from './hooks/useLazy.es';
import NavigationBar from './pages/NavigationBar.es';
import {client} from './utils/client.es';
import {getFullPath} from './utils/utils.es';

export default function App(props) {
	redirectForNotifications(props);

	const Component = useLazy();

	const Router = props.historyRouterBasePath ? BrowserRouter : HashRouter;

	const packageName = props.npmResolvedPackageName;

	const questionsComponent = `${packageName}/js/pages/questions/Questions.es`;

	let path = props.historyRouterBasePath;

	if (path && props.i18nPath) {
		path = props.i18nPath + path;
	}

	if (path && location.pathname.includes(path)) {
		path = location.pathname.slice(
			0,
			location.pathname.indexOf(path) + path.length
		);
	}

	return (
		<ClientContext.Provider value={client}>
			<AppContextProvider {...props}>
				<Router basename={path}>
					<ErrorBoundary>
						<div>
							<NavigationBar />

							<Switch>
								<Route
									component={(props) => (
										<Component
											module={`${packageName}/js/pages/home/Home`}
											props={{...props, isHomePath: true}}
										/>
									)}
									exact
									path="/"
								/>

								<Route
									component={(props) => (
										<Component
											module={`${packageName}/js/pages/home/Home`}
											props={props}
										/>
									)}
									exact
									path="/questions"
								/>

								<Route
									component={(props) => (
										<Component
											module={`${packageName}/js/components/ForumsToQuestion.es`}
											props={props}
										/>
									)}
									exact
									path="/questions/question/:questionId"
								/>

								<Route
									component={(props) => (
										<Component
											module={`${packageName}/js/pages/home/UserActivity.es`}
											props={props}
										/>
									)}
									exact
									path="/questions/activity/:creatorId"
								/>

								<Route
									component={(props) => (
										<Component
											module={`${packageName}/js/pages/home/UserSubscriptions.es`}
											props={props}
										/>
									)}
									exact
									path="/questions/subscriptions/:creatorId"
								/>

								<Route
									component={(props) => (
										<Component
											module={questionsComponent}
											props={props}
										/>
									)}
									exact
									path="/questions/tag/:tag"
								/>

								<Route
									component={(props) => (
										<Component
											module={`${packageName}/js/pages/tags/Tags.es`}
											props={props}
										/>
									)}
									exact
									path="/tags"
								/>

								<Route
									path="/questions/:sectionTitle"
									render={({match: {path}}) => (
										<>
											<Switch>
												<ProtectedRoute
													component={(props) => (
														<Component
															module={`${packageName}/js/pages/answers/EditAnswer.es`}
															props={props}
														/>
													)}
													exact
													path={`${path}/:questionId/answers/:answerId/edit`}
												/>

												<Route
													component={(props) => (
														<Component
															module={
																questionsComponent
															}
															props={props}
														/>
													)}
													exact
													path={`${path}/creator/:creatorId`}
												/>

												<Route
													component={(props) => (
														<Component
															module={
																questionsComponent
															}
															props={props}
														/>
													)}
													exact
													path={`${path}/tag/:tag`}
												/>

												<ProtectedRoute
													component={(props) => (
														<Component
															module={`${packageName}/js/pages/questions/NewQuestion.es`}
															props={props}
														/>
													)}
													exact
													path={`${path}/new`}
												/>

												<Route
													component={(props) => (
														<Component
															module={`${packageName}/js/pages/questions/Question.es`}
															props={props}
														/>
													)}
													exact
													path={`${path}/:questionId`}
												/>

												<ProtectedRoute
													component={(props) => (
														<Component
															module={`${packageName}/js/pages/questions/EditQuestion.es`}
															props={props}
														/>
													)}
													exact
													path={`${path}/:questionId/edit`}
												/>

												<Route
													component={(props) => (
														<Component
															module={
																questionsComponent
															}
															props={props}
														/>
													)}
													exact
													path={`${path}/`}
												/>
											</Switch>
										</>
									)}
								/>
							</Switch>
						</div>
					</ErrorBoundary>
				</Router>
			</AppContextProvider>
		</ClientContext.Provider>
	);

	function redirectForNotifications(props) {
		if (window.location.search && !props.historyRouterBasePath) {
			const urlSearchParams = new URLSearchParams(window.location.search);

			const redirectTo = urlSearchParams.get('redirectTo');
			if (redirectTo) {
				window.history.replaceState(
					{},
					document.title,
					getFullPath() + decodeURIComponent(redirectTo)
				);
			}
		}
	}
}
