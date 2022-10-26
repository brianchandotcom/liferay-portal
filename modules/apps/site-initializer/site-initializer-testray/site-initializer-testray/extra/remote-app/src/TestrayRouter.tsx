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
import CompareRunsOutlet from './pages/CompareRuns/CompareRunsOutlet';
import CompareRunsRedirect from './pages/CompareRuns/CompareRunsRedirect';
import CompareRunsComponents from './pages/CompareRuns/Components';
import CompareRunsDetails from './pages/CompareRuns/Details';
import CompareRunsTeams from './pages/CompareRuns/Teams';
import Users from './pages/Manage/User';
import ChangeUserPassword from './pages/Manage/User/ChangeUserPassword';
import UserForm from './pages/Manage/User/UserForm';
import UserOutlet from './pages/Manage/User/UserOutlet';
import OutletBridge from './pages/OutletBridge';
import Projects from './pages/Project';
import Cases from './pages/Project/Cases';
import Case from './pages/Project/Cases/Case';
import CaseForm from './pages/Project/Cases/CaseForm';
import CaseOutlet from './pages/Project/Cases/CaseOutlet';
import CaseRequirement from './pages/Project/Cases/CaseRequirement';
import Export from './pages/Project/Cases/Export';
import ExportPage from './pages/Project/Cases/ExportPage';
import Overview from './pages/Project/Overview';
import ProjectForm from './pages/Project/ProjectForm';
import ProjectOutlet from './pages/Project/ProjectOutlet';
import Requirements from './pages/Project/Requirements';
import Requirement from './pages/Project/Requirements/Requirement';
import RequirementsForm from './pages/Project/Requirements/RequirementsForm';
import RequirementsOutlet from './pages/Project/Requirements/RequirementsOutlet';
import Routines from './pages/Project/Routines';
import Build from './pages/Project/Routines/Builds/Build';
import BuildForm from './pages/Project/Routines/Builds/BuildForm';
import BuildOutlet from './pages/Project/Routines/Builds/BuildOutlet';
import CaseResult from './pages/Project/Routines/Builds/Inner/CaseResult';
import CaseResultEditTest from './pages/Project/Routines/Builds/Inner/CaseResult/CaseResultEditTest';
import CaseResultOutlet from './pages/Project/Routines/Builds/Inner/CaseResult/CaseResultOutlet';
import CaseResultHistory from './pages/Project/Routines/Builds/Inner/CaseResult/History';
import CaseTypes from './pages/Project/Routines/Builds/Inner/CaseTypes';
import Components from './pages/Project/Routines/Builds/Inner/Components';
import Runs from './pages/Project/Routines/Builds/Inner/Run/Run';
import Teams from './pages/Project/Routines/Builds/Inner/Teams';
import BuildTemplates from './pages/Project/Routines/Builds/Templates';
import Routine from './pages/Project/Routines/Routine';
import RoutineArchived from './pages/Project/Routines/RoutineArchived';
import RoutineForm from './pages/Project/Routines/RoutineForm';
import RoutineOutlet from './pages/Project/Routines/RoutineOutlet';
import Suites from './pages/Project/Suites';
import Suite from './pages/Project/Suites/Suite';
import SuiteForm from './pages/Project/Suites/SuiteForm';
import SuiteOutlet from './pages/Project/Suites/SuiteOutlet';
import Testflow from './pages/Testflow';
import Subtasks from './pages/Testflow/Subtask';
import TestflowArchived from './pages/Testflow/TestflowArchived';
import TestflowForm from './pages/Testflow/TestflowForm';
import TestflowOutlet from './pages/Testflow/TestflowOutlet';
import TestFlowTasks from './pages/Testflow/TestflowTasks';

const TestrayRoute = () => (
	<HashRouter>
		<ClayModalProvider>
			<Routes>
				<Route element={<Export />} path="/export/:id" />

				<Route element={<Layout />} path="/">
					<Route element={<Projects />} index />

					<Route element={<OutletBridge />} path="project">
						<Route element={<ProjectForm />} path="create" />

						<Route element={<ProjectOutlet />} path=":projectId">
							<Route element={<Projects />} index />

							<Route element={<ProjectForm />} path="update" />

							<Route element={<Overview />} path="overview" />

							<Route element={<OutletBridge />} path="suites">
								<Route element={<Suites />} index />

								<Route element={<SuiteForm />} path="create" />

								<Route
									element={<SuiteOutlet />}
									path=":suiteId"
								>
									<Route element={<Suite />} index />

									<Route
										element={<SuiteForm />}
										path="update"
									/>
								</Route>
							</Route>

							<Route element={<OutletBridge />} path="cases">
								<Route element={<Cases />} index />

								<Route element={<CaseForm />} path="create" />

								<Route element={<CaseOutlet />} path=":caseId">
									<Route element={<Case />} index />

									<Route
										element={<CaseForm />}
										path="update"
									/>

									<Route
										element={<CaseRequirement />}
										path="requirements"
									/>
								</Route>

								<Route element={<ExportPage />} path="export" />
							</Route>

							<Route path="requirements">
								<Route element={<Requirements />} index />

								<Route
									element={<RequirementsForm />}
									path="create"
								/>

								<Route
									element={<RequirementsOutlet />}
									path=":requirementId"
								>
									<Route element={<Requirement />} index />

									<Route
										element={<RequirementsForm />}
										path="update"
									/>
								</Route>
							</Route>

							<Route element={<OutletBridge />} path="routines">
								<Route element={<Routines />} index />

								<Route
									element={<RoutineForm />}
									path="create"
								/>

								<Route
									element={<RoutineOutlet />}
									path=":routineId"
								>
									<Route
										element={<BuildTemplates />}
										path="templates"
									/>

									<Route element={<Routine />} index />

									<Route
										element={<RoutineForm />}
										path="update"
									/>

									<Route
										element={<BuildForm />}
										path="create"
									/>

									<Route
										element={<BuildForm />}
										path="create/:buildTemplateId"
									/>

									<Route
										element={<BuildForm />}
										path="create/template/:buildTemplate"
									/>

									<Route
										element={<RoutineArchived />}
										path="archived"
									/>

									<Route
										element={
											<BuildOutlet
												ignorePaths={[
													'case-result',
													'update',
												]}
											/>
										}
										path="build/:buildId"
									>
										<Route element={<Build />} index />

										<Route
											element={<BuildForm />}
											path="update"
										/>

										<Route
											element={<CaseResultOutlet />}
											path="case-result/:caseResultId"
										>
											<Route
												element={<CaseResult />}
												index
											/>

											<Route
												element={<CaseResultHistory />}
												path="history"
											/>

											<Route
												element={<CaseResultEditTest />}
												path="edit/:status"
											/>
										</Route>

										<Route element={<Runs />} path="runs" />

										<Route
											element={<CaseTypes />}
											path="case-types"
										/>

										<Route
											element={<Teams />}
											path="teams"
										/>

										<Route
											element={<Components />}
											path="components"
										/>
									</Route>
								</Route>
							</Route>
						</Route>
					</Route>

					<Route element={<OutletBridge />} path="manage">
						<Route element={<OutletBridge />} path="user">
							<Route element={<Users />} index />

							<Route element={<UserForm />} path="create" />

							<Route element={<UserOutlet />} path=":userId">
								<Route element={<OutletBridge />} path="update">
									<Route element={<UserForm />} index />

									<Route
										element={<ChangeUserPassword />}
										path="password"
									/>
								</Route>
							</Route>
						</Route>

						<Route element={<UserOutlet />} path="user/me">
							<Route element={<UserForm />} index />

							<Route
								element={<ChangeUserPassword />}
								path="password"
							/>
						</Route>
					</Route>

					<Route element={<TestflowOutlet />} path="testflow">
						<Route element={<Testflow />} index />

						<Route element={<TestflowArchived />} path="archived" />

						<Route
							element={<TestflowForm />}
							path=":buildId/create"
						/>

						<Route element={<OutletBridge />} path=":taskId">
							<Route element={<TestFlowTasks />} index />

							<Route
								element={<Subtasks />}
								path="subtasks/:subtaskId"
							/>
						</Route>
					</Route>

					<Route element={<CompareRunsOutlet />} path="compare-runs">
						<Route element={<CompareRunsRedirect />} index />

						<Route
							element={<CompareRunsComponents />}
							path="components"
						/>

						<Route
							element={<CompareRunsDetails />}
							path="details"
						/>

						<Route element={<CompareRunsTeams />} path="teams" />
					</Route>

					<Route element={<div>Page not found</div>} path="*" />
				</Route>
			</Routes>
		</ClayModalProvider>
	</HashRouter>
);

export default TestrayRoute;
