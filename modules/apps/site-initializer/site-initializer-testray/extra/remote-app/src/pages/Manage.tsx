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

import {Avatar} from '../components/Avatar';
import Input from '../components/Input';
import Container from '../components/Layout/Container';
import i18n from '../i18n';
import {assignee} from '../util/mock';

const UserManagement = () => {
	const [{name, url}] = assignee;
	const [value, setValue] = useState(false);

	return (
		<ClayLayout.Container>
			<Container>
				<ClayForm>
					<ClayLayout.Row justify="start">
						<ClayLayout.Col size={3} sm={12} xl={3}>
							<h5 className="font-weight-normal">
								{i18n.translate('user-information')}
							</h5>
						</ClayLayout.Col>

						<ClayLayout.Col size={3} sm={12} xl={7}>
							<ClayForm.Group className="form-group-sm">
								<Input
									label={i18n.translate('first-name')}
									name="firstname"
									required
								/>

								<Input
									label={i18n.translate('last-name')}
									name="lastname"
									required
								/>

								<Input
									label={i18n.translate('email-address')}
									name="emailAddress"
									required
								/>

								<Input
									label={i18n.translate('screen-name')}
									name="screeName"
									required
								/>
							</ClayForm.Group>
						</ClayLayout.Col>
					</ClayLayout.Row>

					<hr />

					<ClayLayout.Row justify="start">
						<ClayLayout.Col size={3} sm={12} xl={3}>
							<h5 className="font-weight-normal">
								{i18n.translate('avatar')}
							</h5>
						</ClayLayout.Col>

						<ClayLayout.Col size={3} sm={12} xl={3}>
							<ClayForm.Group className="form-group-sm">
								<Avatar displayName name={name} url={url} />

								<br />

								<ClayInput
									placeholder={i18n.translate('first-name')}
									type="text"
								/>
							</ClayForm.Group>
						</ClayLayout.Col>
					</ClayLayout.Row>

					<hr />

					<ClayLayout.Row justify="start">
						<ClayLayout.Col size={3} sm={12} xl={3}>
							<h5 className="font-weight-normal">
								{i18n.translate('change-password')}
							</h5>
						</ClayLayout.Col>

						<ClayLayout.Col size={3} sm={12} xl={3}>
							<ClayForm.Group className="form-group-sm">
								<ClayButton className="bg-neutral-2 borderless neutral text-neutral-7">
									{i18n.translate('change-password')}
								</ClayButton>
							</ClayForm.Group>
						</ClayLayout.Col>
					</ClayLayout.Row>

					<hr />

					<ClayLayout.Row justify="start">
						<ClayLayout.Col size={3} sm={12} xl={3}>
							<h5 className="font-weight-normal">
								{i18n.translate('roles')}
							</h5>
						</ClayLayout.Col>

						<ClayLayout.Col size={3} sm={12} xl={9}>
							<div>
								<div className="col-12">
									<ClayCheckbox
										checked={value}
										label={i18n.translate('testray-user')}
										onChange={() =>
											setValue((value) => !value)
										}
									/>
								</div>

								<div className="col-12 ml-4 mt-2">
									<p>
										{i18n.translate(
											'this-role-is-for-general-liferay-employees-and-enables-authenticated-users-to-view-test-results'
										)}
									</p>
								</div>
							</div>

							<div>
								<div className="col-12">
									<ClayCheckbox
										checked={value}
										label="Testray Analyst"
										onChange={() =>
											setValue((value) => !value)
										}
									/>
								</div>

								<div className="col-12 ml-4 mt-2">
									<p>
										{i18n.translate(
											'Testers carry permissions to analyze test results by workflowing results or collaborating on tasks'
										)}
									</p>
								</div>
							</div>

							<div>
								<div className="col-12">
									<ClayCheckbox
										checked={value}
										label={i18n.translate(
											'testray-administrator'
										)}
										onChange={() =>
											setValue((value) => !value)
										}
									/>
								</div>

								<div className="col-12 ml-4 mt-2">
									<p>
										{i18n.translate(
											'test-lead-should-only-have-access-to-builds-test-plans-test-suites-and-test-cases-.-this-role-will-be-responsible-for-creating-builds-and-for-creating-test-plans-test-runs-the-people-assigned-to-these-would-be-our-current-product-leads'
										)}
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
