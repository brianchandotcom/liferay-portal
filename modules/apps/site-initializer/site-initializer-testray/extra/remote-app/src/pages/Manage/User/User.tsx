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
import ClayForm, {ClayCheckbox} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import {useContext, useEffect, useState} from 'react';
import {useForm} from 'react-hook-form';
import {useNavigate} from 'react-router-dom';

import Avatar from '../../../components/Avatar';
import Form from '../../../components/Form';
import Container from '../../../components/Layout/Container';
import {AccountContext} from '../../../context/AccountContext';
import {useHeader} from '../../../hooks';
import useFormActions from '../../../hooks/useFormActions';
import i18n from '../../../i18n';
import yupSchema, {yupResolver} from '../../../schema/yup';
import {Liferay} from '../../../services/liferay';
import {UserAccount} from '../../../services/rest';

type UserManagementProps = {
	myUserAccount: UserAccount;
};

const UserManagement: React.FC<UserManagementProps> = ({myUserAccount}) => {
	const [form, setForm] = useState(myUserAccount || {});

	const {
		form: {onClose},
	} = useFormActions();

	const {
		formState: {errors},

		register,
	} = useForm<UserManagementProps>({
		defaultValues: {},
		resolver: yupResolver(yupSchema.user),
	});

	const inputProps = {
		errors,
		register,
		required: true,
	};

	const {setDropdownIcon, setHeading} = useHeader();

	useEffect(() => {
		setDropdownIcon('cog');
	}, [setDropdownIcon]);

	useEffect(() => {
		setTimeout(() => {
			setHeading(
				[
					{
						category: i18n.translate('manage'),
						title: i18n.translate('manage-users'),
					},
				],
				false
			);
		});
	}, [setDropdownIcon, setHeading]);

	const onChange = ({target: {name, value}}: any) => {
		setForm({
			...form,
			[name]: value,
		});
	};
	const navigate = useNavigate();

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
								<Form.Input
									{...inputProps}
									label={i18n.translate('first-name')}
									name="firstname"
									required
									value={form.givenName}
								/>

								<Form.Input
									{...inputProps}
									label={i18n.translate('last-name')}
									name="lastname"
									required
									value={form.familyName}
								/>

								<Form.Input
									{...inputProps}
									label={i18n.translate('email-address')}
									name="emailAddress"
									required
									value={form.emailAddress}
								/>

								<Form.Input
									{...inputProps}
									label={i18n.translate('screen-name')}
									name="screeName"
									required
									value={form.alternateName}
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
								<Avatar
									displayName
									name={Liferay.ThemeDisplay.getUserName()}
									url={form.image}
								/>

								<br />

								<Form.File name="inputFile" required={false} />
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
								<ClayButton
									className="bg-neutral-2 borderless neutral text-neutral-7"
									onClick={() => navigate('password')}
								>
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
										checked
										label={i18n.translate('testray-user')}
										onChange={onChange}
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
										checked
										label="Testray Analyst"
										onChange={onChange}
									/>
								</div>

								<div className="col-12 ml-4 mt-2">
									<p>
										{i18n.translate(
											'testers-carry-permissions-to-analyze-test-results-by-workflowing-results-or-collaborating-on-tasks'
										)}
									</p>
								</div>
							</div>

							<div>
								<div className="col-12">
									<ClayCheckbox
										checked
										label="Testray Administrator"
										onChange={onChange}
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

					<Form.Footer
						onClose={onClose}
						onSubmit={() => alert('Test')}
					/>
				</ClayForm>
			</Container>
		</ClayLayout.Container>
	);
};

const UserManagementPre = () => {
	const [{myUserAccount}] = useContext(AccountContext);

	if (!myUserAccount) {
		return null;
	}

	return <UserManagement myUserAccount={myUserAccount} />;
};

export default UserManagementPre;
