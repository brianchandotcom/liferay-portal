/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClientContext} from 'graphql-hooks';
import React from 'react';
import {BrowserRouter, HashRouter, Route, Routes} from 'react-router';

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

	function SectionRoutes(props) {
		return (
			<Routes>
				<Route
					element={
						<ProtectedRoute>
							<EditAnswer {...props} />
						</ProtectedRoute>
					}
					path=":questionId/answers/:answerId/edit"
				/>

				<Route
					element={<Questions {...props} />}
					path="creator/:creatorId"
				/>

				<Route element={<Questions {...props} />} path="tag/:tag" />

				<Route
					element={
						<ProtectedRoute>
							<NewQuestion {...props} />
						</ProtectedRoute>
					}
					path="new"
				/>

				<Route element={<Question {...props} />} path=":questionId" />

				<Route
					element={
						<ProtectedRoute>
							<EditQuestion {...props} />
						</ProtectedRoute>
					}
					path=":questionId/edit"
				/>

				<Route element={<Questions {...props} />} path="" />
			</Routes>
		);
	}

	return (
		<ClientContext.Provider value={client}>
			<AppContextProvider {...props}>
				<Router basename={path}>
					<ErrorBoundary>
						<div>
							<NavigationBar />

							<Routes>
								<Route
									element={
										<Home {...props} isHomePath={true} />
									}
									path="/"
								/>

								<Route
									element={<Home {...props} />}
									path="/questions"
								/>

								<Route
									element={<ForumsToQuestion {...props} />}
									path="/questions/question/:questionId"
								/>

								<Route
									element={<UserActivity {...props} />}
									path="/questions/activity/:creatorId"
								/>

								<Route
									element={<UserSubscriptions {...props} />}
									path="/questions/subscriptions/:creatorId"
								/>

								<Route
									element={<Questions {...props} />}
									path="/questions/tag/:tag"
								/>

								<Route
									element={<Tags {...props} />}
									path="/tags"
								/>

								<Route
									element={<SectionRoutes {...props} />}
									path="/questions/:sectionTitle/*"
								/>
							</Routes>
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
