/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import {yupResolver} from '@hookform/resolvers/yup';
import React, {useContext} from 'react';
import {useForm} from 'react-hook-form';
import {useOutletContext} from 'react-router-dom';
import {KeyedMutator} from 'swr';

import Form from '../../../components/Form';
import Container from '../../../components/Layout/Container';
import {TestrayContext} from '../../../context/TestrayContext';
import useFormActions from '../../../hooks/useFormActions';
import i18n from '../../../i18n';
import yupSchema from '../../../schema/yup';
import {UserAccount, liferayUserAccountsImpl} from '../../../services/rest';

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
	const {
		mutatePassword,
		userAccount,
	}: {
		mutatePassword: KeyedMutator<any>;
		userAccount: UserAccount;
	} = useOutletContext();

	const [{myUserAccount}] = useContext(TestrayContext);

	const {
		form: {onClose, onError, onSave, onSubmit},
	} = useFormActions();
	const isSelfAccount = myUserAccount?.id === userAccount.id;
	const {
		formState: {errors},
		handleSubmit,
		register,
		setError,
	} = useForm({
		defaultValues: userAccount as any,
		reValidateMode: 'onSubmit',
		resolver: yupResolver(
			isSelfAccount ? yupSchema.passwordRequired : yupSchema.password
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
				create: (data) => liferayUserAccountsImpl.create(data),
				update: (id, data) => liferayUserAccountsImpl.update(id, data),
			}
		)
			.then(mutatePassword)
			.then(() => onSave())
			.catch((error) => {
				if (error.info.type.includes('MustMatchCurrentPassword')) {
					return setError('currentPassword', {
						message: i18n.translate(
							'current-password-is-incorrect'
						),
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
				{isSelfAccount && (
					<Form.Input
						{...inputProps}
						label={i18n.translate('current-password')}
						name="currentPassword"
						placeholder={i18n.translate('current-password')}
						required
						type="password"
					/>
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

			<Form.Footer onClose={onClose} onSubmit={handleSubmit(_onSubmit)} />
		</Container>
	);
};
export default ChangeUserPassword;
