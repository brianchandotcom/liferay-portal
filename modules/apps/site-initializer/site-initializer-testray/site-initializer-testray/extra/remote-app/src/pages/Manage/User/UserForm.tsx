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
import {useCallback, useContext, useEffect, useMemo} from 'react';
import {useForm} from 'react-hook-form';
import {useLocation, useNavigate, useOutletContext} from 'react-router-dom';
import {KeyedMutator} from 'swr';
import {TestrayContext} from '~/context/TestrayContext';

import Form from '../../../components/Form';
import Container from '../../../components/Layout/Container';
import {useFetch} from '../../../hooks/useFetch';
import useFormActions from '../../../hooks/useFormActions';
import i18n from '../../../i18n';
import yupSchema, {yupResolver} from '../../../schema/yup';
import {Liferay} from '../../../services/liferay';
import {
	UserAccount,
	UserActions,
	liferayUserAccountsImpl,
} from '../../../services/rest';
import {liferayUserRolesRest} from '../../../services/rest/TestrayRolesUser';
import {RoleTypes} from '../../../util/constants';

type UserFormDefault = {
	actions: UserActions;
	alternateName: string;
	emailAddress: string;
	familyName: string;
	givenName: string;
	id: string;
	password?: string;
	repassword?: string;
	roleBriefs?: any;
	roles: number[];
	testrayUser: boolean;
};

type Action = {
	href: string;
	method: string;
};

type ActionsType = {
	actions: {
		[key: string]: Action;
	};
};

type OutletContext = {
	mutateUser: KeyedMutator<UserAccount>;
	userAccount: UserFormDefault;
};

const UserForm = () => {
	const [{myUserAccount}] = useContext(TestrayContext);

	const {data} = useFetch(`/roles?types=${RoleTypes.REGULAR}`);
	const navigate = useNavigate();

	const {pathname} = useLocation();
	const isCreateForm = pathname.includes('create');

	const {mutateUser = () => {}, userAccount} =
		useOutletContext<OutletContext>() || {};
	const {
		form: {onClose, onError, onSave, onSubmit, onSuccess},
	} = useFormActions();

	const {
		formState: {errors, isSubmitting},
		handleSubmit,
		register,
		setValue,
		watch,
	} = useForm<UserFormDefault>({
		defaultValues: {...userAccount, roles: []},
		resolver: yupResolver(yupSchema.user),
	});

	const rolesWatch = watch('roles') as number[];

	const _onSubmit = async (form: UserFormDefault) => {
		if (!rolesWatch?.length) {
			return Liferay.Util.openToast({
				message: i18n.translate('please-select-one-or-more-roles'),
				type: 'danger',
			});
		}

		try {
			const response = await onSubmit(
				{...form, userId: userAccount?.id},
				{
					create: (data) => liferayUserAccountsImpl.create(data),
					update: (id, data) =>
						liferayUserAccountsImpl.update(id, data),
				}
			);

			const _userAccount = liferayUserRolesRest.rolesToUser(
				form.roles,
				form.roleBriefs,
				response
			);

			mutateUser(_userAccount);

			onSave();
		}
		catch (error) {
			onError(error);
		}
	};

	const roles = data?.items || [];

	const checkPermissionRoles = roles.map((role: ActionsType) => {
		return !!role.actions['create-role-user-account-association'];
	});

	const userRoles = useMemo(
		() => (userAccount?.roleBriefs || []).map(({id}: {id: number}) => id),
		[userAccount?.roleBriefs]
	);

	const setRolesUser = useCallback(() => {
		setValue('roles', userRoles);
	}, [setValue, userRoles]);

	useEffect(() => {
		setRolesUser();
	}, [setRolesUser]);

	const onClickRoles = (event: React.FormEvent<HTMLInputElement>) => {
		const value = Number(event.currentTarget.value);

		const rolesFiltered = rolesWatch.includes(value)
			? rolesWatch.filter((rolesId) => rolesId !== value)
			: [...rolesWatch, value];

		setValue('roles', rolesFiltered);
	};
	const inputProps = {
		errors,
		register,
		required: true,
	};
	const hasDeletePermission =
		myUserAccount?.id !== Number(userAccount?.id) &&
		userAccount?.actions['delete-user-account'];

	const hasEditPermission =
		myUserAccount?.id !== Number(userAccount?.id) &&
		userAccount?.actions['patch-user-account'];

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
						{roles.map(
							(
								{id, name}: {id: number; name: string},
								index: number
							) => (
								<div className="mt-2" key={id}>
									<ClayCheckbox
										checked={rolesWatch.includes(id)}
										disabled={!checkPermissionRoles[index]}
										label={name}
										name={name}
										onChange={onClickRoles}
										value={id}
									/>
								</div>
							)
						)}
					</ClayLayout.Col>
				</ClayLayout.Row>

				<Form.Divider />

				{hasDeletePermission && (
					<ClayLayout.Row className="mb-6" justify="start">
						<ClayLayout.Col size={3} sm={12} xl={3}>
							<h5 className="font-weight-normal">
								{i18n.translate('delete-user')}
							</h5>
						</ClayLayout.Col>

						<ClayLayout.Col size={3} sm={12} xl={3}>
							<ClayForm.Group className="form-group-sm">
								<ClayButton
									displayType="danger"
									onClick={() =>
										liferayUserAccountsImpl
											.remove(userAccount?.id)
											.then(() => {
												navigate('/manage/user');
												onSuccess();
											})
											.catch(onError)
									}
								>
									{i18n.translate('delete-user')}
								</ClayButton>
							</ClayForm.Group>
						</ClayLayout.Col>
					</ClayLayout.Row>
				)}

				<Form.Footer
					hasPermission={Boolean(hasEditPermission)}
					onClose={onClose}
					onSubmit={handleSubmit(_onSubmit)}
					primaryButtonProps={{loading: isSubmitting}}
				/>
			</ClayForm>
		</Container>
	);
};

export default UserForm;
