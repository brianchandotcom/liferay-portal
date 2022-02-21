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

import ClayButton from '@clayui/button';
import ClayForm, {ClayCheckbox, ClayInput} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import {useState} from 'react';

import {Avatar} from '../../components/Avatar';
import Input from '../../components/Input';
import Container from '../../components/Layout/Container';
import {assignee} from '../../util/mock';

const UserManagement: React.FC = () => {
	const [{name, url}] = assignee;
	const [value, setValue] = useState(false);

	return (
		<ClayLayout.Container>
			<Container>
				<ClayForm>
					<ClayLayout.Row justify="start">
						<ClayLayout.Col size={3} sm={12} xl={3}>
							<h5 className="font-weight-normal">
								User Information
							</h5>
						</ClayLayout.Col>

						<ClayLayout.Col size={3} sm={12} xl={7}>
							<ClayForm.Group className="form-group-sm">
								<Input
									label="First Name"
									name="firstname"
									required
								/>

								<Input
									label="Last Name"
									name="lastname"
									required
								/>

								<Input
									label="Email Address"
									name="emailAddress"
									required
								/>

								<Input
									label="Screen Name"
									name="screeName"
									required
								/>
							</ClayForm.Group>
						</ClayLayout.Col>
					</ClayLayout.Row>

					<hr />

					<ClayLayout.Row justify="start">
						<ClayLayout.Col size={3} sm={12} xl={3}>
							<h5 className="font-weight-normal">Avatar</h5>
						</ClayLayout.Col>

						<ClayLayout.Col size={3} sm={12} xl={3}>
							<ClayForm.Group className="form-group-sm">
								<Avatar displayName name={name} url={url} />

								<br />

								<ClayInput
									placeholder="First Name"
									type="text"
								/>
							</ClayForm.Group>
						</ClayLayout.Col>
					</ClayLayout.Row>

					<hr />

					<ClayLayout.Row justify="start">
						<ClayLayout.Col size={3} sm={12} xl={3}>
							<h5 className="font-weight-normal">
								Change Password
							</h5>
						</ClayLayout.Col>

						<ClayLayout.Col size={3} sm={12} xl={3}>
							<ClayForm.Group className="form-group-sm">
								<ClayButton className="bg-neutral-2 borderless neutral text-neutral-7">
									Change Password
								</ClayButton>
							</ClayForm.Group>
						</ClayLayout.Col>
					</ClayLayout.Row>

					<hr />

					<ClayLayout.Row justify="start">
						<ClayLayout.Col size={3} sm={12} xl={3}>
							<h5 className="font-weight-normal">Roles</h5>
						</ClayLayout.Col>

						<ClayLayout.Col size={3} sm={12} xl={9}>
							<div>
								<div className="col-12">
									<ClayCheckbox
										checked={value}
										label="Testray User"
										onChange={() => setValue((val) => !val)}
									/>
								</div>

								<div className="col-12 ml-4 mt-2">
									<p>
										This role is for general Liferay
										employees and enables authenticated
										users to view test results.
									</p>
								</div>
							</div>

							<div>
								<div className="col-12">
									<ClayCheckbox
										checked={value}
										label="Testray Analyst"
										onChange={() => setValue((val) => !val)}
									/>
								</div>

								<div className="col-12 ml-4 mt-2">
									<p>
										Testers carry permissions to analyze
										test results by workflowing results or
										collaborating on tasks
									</p>
								</div>
							</div>

							<div>
								<div className="col-12">
									<ClayCheckbox
										checked={value}
										label="Testray Administrator"
										onChange={() => setValue((val) => !val)}
									/>
								</div>

								<div className="col-12 ml-4 mt-2">
									<p>
										Test Lead should only have access to
										Builds, Test Plans, Test Suites and Test
										Cases. This role will be responsible for
										creating Builds and for creating Test
										Plans/Test Runs. The people assigned to
										these would be our current product
										leads.
									</p>
								</div>
							</div>
						</ClayLayout.Col>
					</ClayLayout.Row>
				</ClayForm>
			</Container>
		</ClayLayout.Container>
	);
};

export default UserManagement;
