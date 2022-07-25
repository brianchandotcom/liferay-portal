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

import ClayForm, {ClayCheckbox} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import {useForm} from 'react-hook-form';
import {useOutletContext, useParams} from 'react-router-dom';

import Form from '../../../components/Form';
import Container from '../../../components/Layout/Container';
import {useFetch} from '../../../hooks/useFetch';
import useFormActions from '../../../hooks/useFormActions';
import i18n from '../../../i18n';
import yupSchema, {yupResolver} from '../../../schema/yup';
import {
	createUserAccount,
	updateUserAccount,
} from '../../../services/rest/TestrayUserAccounts';

type userFormDefault = {
	__typename?: any;
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
	const {data} = useFetch('/roles');

	const {userId} = useParams();

	const userAccount = useOutletContext<any>();

	const currentUser = userAccount?.userAccount || [];
	const roles = data?.items || [];

	const {
		form: {onClose, onError, onSave, onSubmitRest},
	} = useFormActions();

	const {
		formState: {errors},
		handleSubmit,
		register,
		setValue,
		watch,
	} = useForm<userFormDefault>({
		defaultValues: userId ? currentUser : {},
		resolver: yupResolver(yupSchema.user),
	});

	const _onSubmit = (form: userFormDefault) => {
		delete form.password;
		delete form.repassword;
		delete form.roles;
		delete form.__typename;
		delete form.roleBriefs;

		onSubmitRest(
			{...form, userId},
			{
				create: createUserAccount,
				update: updateUserAccount,
			}
		)
			.then(onSave)
			.catch(onError);
	};

	const rolesWatch = watch('roles') || [];

	const setCheckedValue = (value: any) => {
		const valueInsideList = rolesWatch?.includes(value);
		let newRoles = [...rolesWatch];

		if (valueInsideList) {
			newRoles = newRoles.filter((role) => role !== value);
		}
		else {
			newRoles = [...newRoles, Number(value)];
		}

		setValue('roles', newRoles);
	};

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

				<Form.Divider />

				<ClayLayout.Row justify="start">
					<ClayLayout.Col size={12} sm={12} xl={3}>
						<h5 className="font-weight-normal">
							{i18n.translate('roles')}
						</h5>
					</ClayLayout.Col>

					<ClayLayout.Col size={12} sm={12} xl={9}>
						{roles.map(({id, name}: {id: number; name: string}) => (
							<div className="mt-2" key={id}>
								<ClayCheckbox
									checked={rolesWatch.includes(id)}
									label={name}
									onChange={(event) =>
										setCheckedValue(event.target.value)
									}
									value={id}
								/>
							</div>
						))}
					</ClayLayout.Col>
				</ClayLayout.Row>

				<Form.Divider />

				<Form.Footer
					onClose={onClose}
					onSubmit={handleSubmit(_onSubmit)}
				/>
			</ClayForm>
		</Container>
	);
};

export default UserForm;
