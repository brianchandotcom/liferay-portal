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
import {useForm} from 'react-hook-form';
import {useLocation, useNavigate, useOutletContext} from 'react-router-dom';

import Form from '../../../components/Form';
import Container from '../../../components/Layout/Container';
import {useFetch} from '../../../hooks/useFetch';
import useFormActions from '../../../hooks/useFormActions';
import i18n from '../../../i18n';
import yupSchema, {yupResolver} from '../../../schema/yup';
import {liferayUserAccountsRest} from '../../../services/rest';
import {RoleTypes} from '../../../util/constants';

type UserFormDefault = {
	alternateName: string;
	emailAddress: string;
	familyName: string;
	givenName: string;
	id: string;
	password?: string;
	repassword?: string;
	roleBriefs?: any;
	roles?: number[];
	testrayUser: boolean;
};

const UserForm = () => {
	const {data} = useFetch(`/roles?types=${RoleTypes.REGULAR}&fields=id,name`);
	const navigate = useNavigate();
	const {pathname} = useLocation();
	const isCreateForm = pathname.includes('create');

	const {mutateUser = () => {}, userAccount} = useOutletContext<any>() || {};

	const {
		form: {onClose, onError, onSave, onSubmit},
	} = useFormActions();

	const {
		formState: {errors},
		handleSubmit,
		register,
	} = useForm<UserFormDefault>({
		defaultValues: userAccount,
		resolver: yupResolver(yupSchema.user),
	});

	const _onSubmit = (form: UserFormDefault) => {
		onSubmit(
			{...form, userId: userAccount?.id},
			{
				create: (...params) =>
					liferayUserAccountsRest.create(...params),
				update: (...params) =>
					liferayUserAccountsRest.update(...params),
			}
		)
			.then(mutateUser)
			.then(() => onSave())
			.catch(onError);
	};

	const userRoles =
		userAccount?.roleBriefs.map(({id}: {id: number}) => id) || [];
	const roles = data?.items || [];

	const inputProps = {
		errors,
		register,
		required: true,
	};

	return (
		<Container className="container">
			<ClayForm className="container pt-2">
				<ClayLayout.Row justify="start">
					<ClayLayout.Col size={12} sm={12} xl={3}>
						<h5 className="font-weight-normal">
							{i18n.translate('user-information')}
						</h5>
					</ClayLayout.Col>

					<ClayLayout.Col size={12} sm={12} xl={9}>
						<ClayForm.Group className="form-group-sm">
							<Form.Input
								{...inputProps}
								label={i18n.translate('first-name')}
								name="givenName"
								required
							/>

							<Form.Input
								{...inputProps}
								label={i18n.translate('last-name')}
								name="familyName"
								required
							/>

							<Form.Input
								{...inputProps}
								label={i18n.translate('email-address')}
								name="emailAddress"
								required
								type="email"
							/>

							<Form.Input
								{...inputProps}
								label={i18n.translate('screen-name')}
								name="alternateName"
							/>
						</ClayForm.Group>
					</ClayLayout.Col>
				</ClayLayout.Row>

				<Form.Divider />

				{isCreateForm && (
					<ClayLayout.Row justify="start">
						<ClayLayout.Col size={12} sm={12} xl={3}>
							<h5 className="font-weight-normal mt-1">
								{i18n.translate('password')}
							</h5>
						</ClayLayout.Col>

						<ClayLayout.Col size={12} sm={12} xl={9}>
							<ClayForm.Group className="form-group-sm">
								<Form.Input
									{...inputProps}
									label={i18n.translate('password')}
									name="password"
									type="password"
								/>

								<Form.Input
									{...inputProps}
									label="Confirm Password"
									name="repassword"
									required
									type="password"
								/>
							</ClayForm.Group>
						</ClayLayout.Col>
					</ClayLayout.Row>
				)}

				{!isCreateForm && (
					<ClayLayout.Row justify="start">
						<ClayLayout.Col size={3} sm={12} xl={3}>
							<h5 className="font-weight-normal">
								{i18n.translate('change-password')}
							</h5>
						</ClayLayout.Col>

						<ClayLayout.Col size={3} sm={12} xl={3}>
							<ClayForm.Group className="form-group-sm">
								<ClayButton
									className="bg-neutral-2 borderless btn-light neutral text-neutral-7"
									onClick={() => navigate('password')}
								>
									{i18n.translate('change-password')}
								</ClayButton>
							</ClayForm.Group>
						</ClayLayout.Col>
					</ClayLayout.Row>
				)}

				<Form.Divider />

				<ClayLayout.Row className="mb-2" justify="start">
					<ClayLayout.Col size={12} sm={12} xl={3}>
						<h5 className="font-weight-normal">
							{i18n.translate('roles')}
						</h5>
					</ClayLayout.Col>

					<ClayLayout.Col size={12} sm={12} xl={9}>
						{roles.map(({id, name}: {id: number; name: string}) => (
							<div className="mt-2" key={id}>
								<ClayCheckbox
									checked={userRoles.includes(id)}
									disabled={!userRoles.includes(id)}
									label={name}
									onChange={() => null}
								/>
							</div>
						))}
					</ClayLayout.Col>
				</ClayLayout.Row>

				<Form.Footer
					onClose={onClose}
					onSubmit={handleSubmit(_onSubmit)}
				/>
			</ClayForm>
		</Container>
	);
};

export default UserForm;
