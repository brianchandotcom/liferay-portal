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
import React from 'react';
import {useForm} from 'react-hook-form';
import {useNavigate} from 'react-router-dom';

import Form from '../../../components/Form';
import Container from '../../../components/Layout/Container';
import i18n from '../../../i18n';
import yupSchema from '../../../schema/yup';

const ChangeUserPassword: React.FC = () => {
	const navigate = useNavigate();

	const {
		formState: {errors},
		handleSubmit,
		register,
	} = useForm({resolver: yupResolver(yupSchema.password)});

	const _onSubmit = () => alert('successful send');

	const inputProps = {
		errors,
		register,
		required: true,
	};

	return (
		<Container className="change-user-password">
			<ClayForm>
				<div>
					<Form.Input
						{...inputProps}
						label={i18n.translate('new-password')}
						name="password"
						placeholder="Password"
						type="password"
					/>
				</div>

				<Form.Input
					{...inputProps}
					label={i18n.translate('confirm-password')}
					name="confirmpassword"
					onPaste={(event) => {
						event.preventDefault();

						return false;
					}}
					placeholder="Confirm Password"
					type="password"
				/>
			</ClayForm>

			<div>
				<Form.Footer
					onClose={() => navigate(-1)}
					onSubmit={handleSubmit(_onSubmit)}
				/>
			</div>
		</Container>
	);
};
export default ChangeUserPassword;
