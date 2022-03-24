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

import {useMutation, useQuery} from '@apollo/client';
import ClayButton from '@clayui/button';
import ClayForm, {ClayCheckbox} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import {useEffect, useState} from 'react';

import Input from '../../../components/Input';
import Container from '../../../components/Layout/Container';
import Modal from '../../../components/Modal';
import {
	createUserAccount,
	updateUserAccount,
} from '../../../graphql/mutations/liferayUserAccount';
import {Role, TypePagination, getLiferayRoles} from '../../../graphql/queries';
import {FormModalOptions} from '../../../hooks/useFormModal';
import i18n from '../../../i18n';

const userFormDefault = {
	alternateName: '',
	confirmPassword: '',
	emailAddress: '',
	familyName: '',
	givenName: '',
	id: '',
	password: '',
	testRayAnalyst: false,
	testrayAdministrator: false,
	testrayLead: false,
	testrayUser: false,
};

type UserForm = typeof userFormDefault;

type UserFormProps = {
	form: UserForm;
	onChange: (event: any) => void;
};

const UserForm: React.FC<UserFormProps> = ({form, onChange}) => {
	const {data} = useQuery<TypePagination<'roles', Role>>(getLiferayRoles);

	const roles = data?.roles.items || [];

	return (
		<Container>
			<ClayLayout.Row justify="start">
				<ClayLayout.Col size={12} sm={12} xl={3}>
					<h5 className="font-weight-normal">
						{i18n.translate('user-information')}
					</h5>
				</ClayLayout.Col>

				<ClayLayout.Col size={12} sm={12} xl={9}>
					<ClayForm.Group className="form-group-sm">
						<Input
							label={i18n.translate('first-name')}
							name="givenName"
							onChange={onChange}
							required
							value={form.givenName}
						/>

						<Input
							label={i18n.translate('last-name')}
							name="familyName"
							onChange={onChange}
							required
							value={form.familyName}
						/>

						<Input
							label={i18n.translate('email-address')}
							name="emailAddress"
							onChange={onChange}
							required
							type="email"
							value={form.emailAddress}
						/>

						<Input
							label={i18n.translate('screen-name')}
							name="alternateName"
							onChange={onChange}
							required
							value={form.alternateName}
						/>
					</ClayForm.Group>
				</ClayLayout.Col>
			</ClayLayout.Row>

			<hr />

			<ClayLayout.Row justify="start">
				<ClayLayout.Col size={12} sm={12} xl={3}>
					<h5 className="font-weight-normal mt-1">
						{i18n.translate('password')}
					</h5>
				</ClayLayout.Col>

				<ClayLayout.Col size={12} sm={12} xl={9}>
					<ClayForm.Group className="form-group-sm">
						<Input
							label={i18n.translate('password')}
							name="password"
							onChange={onChange}
							required
							type="password"
							value={form.password}
						/>

						<Input
							label="Confirm Password"
							name="repassword"
							onChange={onChange}
							required
							type="password"
							value={form.password}
						/>
					</ClayForm.Group>
				</ClayLayout.Col>
			</ClayLayout.Row>

			<hr />

			<ClayLayout.Row justify="start">
				<ClayLayout.Col size={12} sm={12} xl={3}>
					<h5 className="font-weight-normal">Roles</h5>
				</ClayLayout.Col>

				<ClayLayout.Col size={12} sm={12} xl={9}>
					{roles.map(({id, name}) => (
						<div className="mt-2" key={id}>
							<ClayCheckbox
								checked={false}
								label={name}
								onChange={onChange}
								value={id}
							/>
						</div>
					))}
				</ClayLayout.Col>
			</ClayLayout.Row>
		</Container>
	);
};

type UserFormModalProps = {
	modal: FormModalOptions;
};

const UserFormModal: React.FC<UserFormModalProps> = ({
	modal: {modalState, observer, onChange, onClose, onError, onSave, visible},
}) => {
	const [form, setForm] = useState(userFormDefault);

	const [onCreateUserAccount] = useMutation(createUserAccount);
	const [onUpdateUserAccount] = useMutation(updateUserAccount);

	useEffect(() => {
		if (visible && modalState) {
			setForm(modalState);
		}
	}, [visible, modalState]);

	const onSubmit = async () => {
		const variables = {
			userAccount: {
				alternateName: form.alternateName,
				emailAddress: form.emailAddress,
				familyName: form.familyName,
				givenName: form.givenName,
				password: form.password || undefined,
			},
		};

		try {
			if (form.id) {
				(variables as any).userAccountId = form.id;

				await onUpdateUserAccount({variables});
			} else {
				await onCreateUserAccount({variables});
			}

			onSave();
		} catch (error) {
			onError();
		}
	};

	return (
		<Modal
			last={
				<ClayButton.Group spaced>
					<ClayButton displayType="secondary" onClick={onClose}>
						{i18n.translate('close')}
					</ClayButton>

					<ClayButton displayType="primary" onClick={onSubmit}>
						{i18n.translate('save')}
					</ClayButton>
				</ClayButton.Group>
			}
			observer={observer}
			size="full-screen"
			title={i18n.translate(
				form.id ? 'edit-user-account' : 'new-user-account'
			)}
			visible={visible}
		>
			<UserForm form={form} onChange={onChange({form, setForm})} />
		</Modal>
	);
};

export default UserFormModal;
