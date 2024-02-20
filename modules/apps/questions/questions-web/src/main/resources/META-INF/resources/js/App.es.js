/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClientContext} from 'graphql-hooks';
import React from 'react';
import {BrowserRouter, HashRouter, Route, Switch} from 'react-router-dom';

import {AppContextProvider} from './AppContext.es';
import {ErrorBoundary} from './components/ErrorBoundary.es';
import ForumsToQuestion from './components/ForumsToQuestion.es';
import ProtectedRoute from './components/ProtectedRoute.es';
import NavigationBar from './pages/NavigationBar.es';
import EditAnswer from './pages/answers/EditAnswer.es';
import Home from './pages/home/Home';
import UserActivity from './pages/home/UserActivity.es';
import UserSubscriptions from './pages/home/UserSubscriptions.es';
import EditQuestion from './pages/questions/EditQuestion.es';
import NewQuestion from './pages/questions/NewQuestion.es';
import Question from './pages/questions/Question.es';
import Questions from './pages/questions/Questions.es';
import Tags from './pages/tags/Tags.es';
import {client} from './utils/client.es';
import {getFullPath} from './utils/utils.es';

export default function App(props) {
	redirectForNotifications(props);

	const Router = props.historyRouterBasePath ? BrowserRouter : HashRouter;

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
										<Home {...props} isHomePath={true} />
									)}
									exact
									path="/"
								/>

								<Route
									component={(props) => <Home {...props} />}
									exact
									path="/questions"
								/>

								<Route
									component={(props) => (
										<ForumsToQuestion {...props} />
									)}
									exact
									path="/questions/question/:questionId"
								/>

								<Route
									component={(props) => (
										<UserActivity {...props} />
									)}
									exact
									path="/questions/activity/:creatorId"
								/>

								<Route
									component={(props) => (
										<UserSubscriptions {...props} />
									)}
									exact
									path="/questions/subscriptions/:creatorId"
								/>

								<Route
									component={(props) => (
										<Questions {...props} />
									)}
									exact
									path="/questions/tag/:tag"
								/>

								<Route
									component={(props) => <Tags {...props} />}
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
														<EditAnswer
															{...props}
														/>
													)}
													exact
													path={`${path}/:questionId/answers/:answerId/edit`}
												/>

												<Route
													component={(props) => (
														<Questions {...props} />
													)}
													exact
													path={`${path}/creator/:creatorId`}
												/>

												<Route
													component={(props) => (
														<Questions {...props} />
													)}
													exact
													path={`${path}/tag/:tag`}
												/>

												<ProtectedRoute
													component={(props) => (
														<NewQuestion
															{...props}
														/>
													)}
													exact
													path={`${path}/new`}
												/>

												<Route
													component={(props) => (
														<Question {...props} />
													)}
													exact
													path={`${path}/:questionId`}
												/>

												<ProtectedRoute
													component={(props) => (
														<EditQuestion
															{...props}
														/>
													)}
													exact
													path={`${path}/:questionId/edit`}
												/>

												<Route
													component={(props) => (
														<Questions {...props} />
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
