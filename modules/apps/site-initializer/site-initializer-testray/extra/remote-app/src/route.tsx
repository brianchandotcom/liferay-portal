/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {HashRouter, Route, Routes} from 'react-router-dom';

import Layout from './components/Layout/Layout';
import Manage from './pages/Manage';
import Cases from './pages/Project/Cases';
import Case from './pages/Project/Cases/Case';
import Home from './pages/Project/Home';
import Overview from './pages/Project/Overview';
import ProjectOutlet from './pages/Project/ProjectOutlet';
import Requirements from './pages/Project/Requirements';
import Requirement from './pages/Project/Requirements/Requirement';
import Builds from './pages/Project/Routines/Build';
import Build from './pages/Project/Routines/Build/Build';
import CaseTypes from './pages/Project/Routines/Build/Inner/CaseTypes';
import Components from './pages/Project/Routines/Build/Inner/Components';
import Results from './pages/Project/Routines/Build/Inner/Results';
import Runs from './pages/Project/Routines/Build/Inner/Run';
import Teams from './pages/Project/Routines/Build/Inner/Teams';
import Routines from './pages/Project/Routines/Routines';
import Suites from './pages/Project/Suites';
import Testflow from './pages/Testflow';
import {TestFlowTasks} from './pages/Testflow/TestflowTasks';

const TestrayRoute = () => (
	<HashRouter>
		<Routes>
			<Route element={<Layout />} path="/">
				<Route element={<Home />} index />

				<Route element={<ProjectOutlet />} path="project/:projectId">
					<Route element={<Home />} index />

					<Route element={<Overview />} path="overview" />

					<Route element={<Suites />} path="suites" />

					<Route path="cases">
						<Route element={<Cases />} index />

						<Route element={<Case />} path=":testrayCaseId" />
					</Route>

					<Route path="requirements">
						<Route element={<Requirements />} index />

						<Route
							element={<Requirement />}
							path=":requirementId"
						/>
					</Route>

					<Route path="routines">
						<Route element={<Routines />} index />

						<Route path="build">
							<Route element={<Builds />} index />

							<Route element={<Build />} path=":testrayBuildId">
								<Route element={<Runs />} path="run" />

								<Route
									element={<CaseTypes />}
									path="case_types"
								/>

								<Route element={<Teams />} path="teams" />

								<Route
									element={<Components />}
									path="components"
								/>

								<Route element={<Results />} path="results" />
							</Route>
						</Route>
					</Route>
				</Route>

				<Route element={<Manage />} path="manage" />

				<Route path="testflow">
					<Route element={<Testflow />} index />

					<Route element={<TestFlowTasks />} path=":testflowId" />
				</Route>

				<Route element={<div>Page not found</div>} path="*" />
			</Route>
		</Routes>
	</HashRouter>
);

export default TestrayRoute;
