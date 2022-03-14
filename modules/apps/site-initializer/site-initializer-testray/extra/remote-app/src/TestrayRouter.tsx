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

import {ClayModalProvider} from '@clayui/modal';
import {HashRouter, Route, Routes} from 'react-router-dom';

import Layout from './components/Layout/Layout';
import OutletBridge from './pages/OutletBridge';
import Cases from './pages/Project/Cases';
import Case from './pages/Project/Cases/Case';
import CaseOutlet from './pages/Project/Cases/CaseOutlet';
import CaseRequirement from './pages/Project/Cases/CaseRequirement';
import Home from './pages/Project/Home';
import Overview from './pages/Project/Overview';
import ProjectOutlet from './pages/Project/ProjectOutlet';
import Requirements from './pages/Project/Requirements';
import AddRequirements from './pages/Project/Requirements/AddRequirements';
import Requirement from './pages/Project/Requirements/Requirement';
import Routines from './pages/Project/Routines';
import Build from './pages/Project/Routines/Builds/Build';
import BuildOutlet from './pages/Project/Routines/Builds/BuildOutlet';
import CaseResult from './pages/Project/Routines/Builds/Inner/CaseResult';
import CaseResultOutlet from './pages/Project/Routines/Builds/Inner/CaseResult/CaseResultOutlet';
import CaseResultHistory from './pages/Project/Routines/Builds/Inner/CaseResult/History';
import CaseTypes from './pages/Project/Routines/Builds/Inner/CaseTypes';
import Components from './pages/Project/Routines/Builds/Inner/Components';
import Results from './pages/Project/Routines/Builds/Inner/Results';
import Runs from './pages/Project/Routines/Builds/Inner/Run';
import Teams from './pages/Project/Routines/Builds/Inner/Teams';
import Routine from './pages/Project/Routines/Routine';
import RoutineArchived from './pages/Project/Routines/RoutineArchived';
import RoutineOutlet from './pages/Project/Routines/RoutineOutlet';
import Suites from './pages/Project/Suites';
import Suite from './pages/Project/Suites/Suite';
import Testflow from './pages/Testflow';
import Subtasks from './pages/Testflow/Subtask';
import TestflowArchived from './pages/Testflow/TestflowArchived';
import TestflowOutlet from './pages/Testflow/TestflowOutlet';
import TestFlowTasks from './pages/Testflow/TestflowTasks';
import UserManagement from './pages/UserManagement';
import AddUser from './pages/UserManagement/AddUser';
import UserList from './pages/UserManagement/UsersList';

const TestrayRoute = () => (
	<HashRouter>
		<ClayModalProvider>
			<Routes>
				<Route element={<Layout />} path="/">
					<Route element={<Home />} index />

					<Route
						element={<ProjectOutlet />}
						path="project/:projectId"
					>
						<Route element={<Home />} index />

						<Route element={<Overview />} path="overview" />

						<Route element={<OutletBridge />} path="suites">
							<Route element={<Suites />} index />

							<Route element={<Suite />} path=":testraySuiteId" />
						</Route>

						<Route element={<OutletBridge />} path="cases">
							<Route element={<Cases />} index />

							<Route
								element={<CaseOutlet />}
								path=":testrayCaseId"
							>
								<Route element={<Case />} index />

								<Route
									element={<CaseRequirement />}
									path="requirements"
								/>
							</Route>
						</Route>

						<Route path="requirements">
							<Route element={<Requirements />} index />

							<Route
								element={<Requirement />}
								path=":requirementId"
							/>
						</Route>

						<Route element={<OutletBridge />} path="routines">
							<Route element={<Routines />} index />

							<Route
								element={<RoutineOutlet />}
								path=":routineId"
							>
								<Route element={<Routine />} index />

								<Route
									element={<RoutineArchived />}
									path="archived"
								/>

								<Route
									element={
										<BuildOutlet ignorePath="case-result" />
									}
									path="build/:testrayBuildId"
								>
									<Route element={<Build />} index />

									<Route
										element={<CaseResultOutlet />}
										path="case-result/:testrayCaseResultId"
									>
										<Route element={<CaseResult />} index />

										<Route
											element={<CaseResultHistory />}
											path="history"
										/>
									</Route>

									<Route element={<Runs />} path="runs" />

									<Route
										element={<CaseTypes />}
										path="case-types"
									/>

									<Route element={<Teams />} path="teams" />

									<Route
										element={<Components />}
										path="components"
									/>

									<Route
										element={<Results />}
										path="results"
									/>
								</Route>
							</Route>
						</Route>
					</Route>

					<Route element={<OutletBridge />} path="manage">
						<Route element={<UserManagement />} path="user" />

						<Route element={<AddUser />} path="adduser" />

						<Route element={<UserList />} path="userlist" />

						<Route
							element={<AddRequirements />}
							path="requirements"
						/>
					</Route>

					<Route element={<TestflowOutlet />} path="testflow">
						<Route element={<Testflow />} index />

						<Route element={<TestflowArchived />} path="archived" />

						<Route element={<Subtasks />} path="subtasks" />

						<Route
							element={<TestFlowTasks />}
							path=":testrayTaskId"
						/>
					</Route>

					<Route element={<div>Page not found</div>} path="*" />
				</Route>
			</Routes>
		</ClayModalProvider>
	</HashRouter>
);

export default TestrayRoute;
