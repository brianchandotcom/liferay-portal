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

import ClayForm from '@clayui/form';
import {yupResolver} from '@hookform/resolvers/yup';
import React, {useContext} from 'react';
import {useForm} from 'react-hook-form';
import {useOutletContext} from 'react-router-dom';
import {KeyedMutator} from 'swr';

import Form from '../../../components/Form';
import Container from '../../../components/Layout/Container';
import {AccountContext} from '../../../context/AccountContext';
import useFormActions from '../../../hooks/useFormActions';
import i18n from '../../../i18n';
import yupSchema from '../../../schema/yup';
import {liferayUserAccountsRest} from '../../../services/rest';

type UserPasswordDefault = {
	alternateName?: string;
	currentPassword: string;
	emailAddress?: string;
	familyName?: string;
	givenName?: string;
	id: string;
	password: string;
	repassword?: string;
};

const ChangeUserPassword: React.FC = () => {
	const {userAccount} = useOutletContext<any>();
	const {
		mutatePassword,
	}: {mutatePassword: KeyedMutator<any>} = useOutletContext();

	const [{myUserAccount}] = useContext(AccountContext);

	const {
		form: {onClose, onError, onSave, onSubmit},
	} = useFormActions();
	const validationId = myUserAccount?.id === userAccount.id ? true : false;
	const {
		formState: {errors},
		handleSubmit,
		register,
		setError,
	} = useForm({
		defaultValues: userAccount,
		reValidateMode: 'onSubmit',
		resolver: yupResolver(
			validationId ? yupSchema.passwordRequired : yupSchema.password
		),
	});

	const _onSubmit = (form: UserPasswordDefault) => {
		delete form.alternateName;
		delete form.emailAddress;
		delete form.familyName;
		delete form.givenName;

		onSubmit(
			{...form, userId: userAccount.id},
			{
				create: (...params) =>
					liferayUserAccountsRest.create(...params),
				update: (...params) =>
					liferayUserAccountsRest.update(...params),
			}
		)
			.then(mutatePassword)
			.then(() => onSave())
			.catch((error) => {
				if (error.info.type === 'MustMatchCurrentPassword') {
					return setError('currentPassword', {
						message: i18n.translate('current-password-incorrect'),
					});
				}

				onError(error);
			});
	};

	const inputProps = {
		errors,
		register,
		required: true,
	};

	return (
		<Container className="change-user-password">
			<ClayForm>
				{validationId ? (
					<Form.Input
						{...inputProps}
						label={i18n.translate('current-password')}
						name="currentPassword"
						placeholder={i18n.translate('current-password')}
						required={true}
						type="password"
					/>
				) : (
					''
				)}

				<Form.Input
					{...inputProps}
					label={i18n.translate('new-password')}
					name="password"
					placeholder={i18n.translate('password')}
					type="password"
				/>

				<Form.Input
					{...inputProps}
					label={i18n.translate('confirm-password')}
					name="rePassword"
					onPaste={(event) => {
						event.preventDefault();

						return false;
					}}
					placeholder={i18n.translate('confirm-password')}
					type="password"
				/>
			</ClayForm>

			<div>
				<Form.Footer
					onClose={onClose}
					onSubmit={handleSubmit(_onSubmit)}
				/>
			</div>
		</Container>
	);
};
export default ChangeUserPassword;
