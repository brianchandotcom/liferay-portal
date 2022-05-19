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

import {useQuery} from '@apollo/client';
import ClayButton from '@clayui/button';
import ClayForm, {ClayCheckbox} from '@clayui/form';
import {useForm} from 'react-hook-form';

import Input from '../../../components/Input';
import InputSelect from '../../../components/Input/InputSelect';
import Container from '../../../components/Layout/Container';
import {
	CTypePagination,
	TestrayRoutine,
	getRoutines,
} from '../../../graphql/queries';
import {
	TestrayProductVersion,
	getProductVersions,
} from '../../../graphql/queries/testrayProductVersion';
import useFormActions from '../../../hooks/useFormActions';
import i18n from '../../../i18n';
import yupSchema, {yupResolver} from '../../../schema/yup';

type RoutineBuildData = {
	description: string;
	gitHash: string;
	name: string;
	productVersionId: string;
	routineId: string;
	template: boolean;
};

const RoutineBuildForm = () => {
	const {data: routinesData} = useQuery<
		CTypePagination<'routines', TestrayRoutine>
	>(getRoutines);

	const {data: productVersionsData} = useQuery<
		CTypePagination<'productVersions', TestrayProductVersion>
	>(getProductVersions);

	const routines = routinesData?.c.routines.items || [];
	const productVersions = productVersionsData?.c.productVersions.items || [];

	const {
		form: {onClose},
	} = useFormActions();

	const {
		formState: {errors},
		register,
		setValue,
		watch,
	} = useForm<RoutineBuildData>({
		defaultValues: {
			template: false,
		},
		resolver: yupResolver(yupSchema.build),
	});

	const template = watch('template');

	const inputProps = {
		errors,
		register,
		required: true,
	};

	return (
		<Container className="container">
			<ClayForm className="container pt-2">
				<Input
					{...inputProps}
					label={i18n.translate('name')}
					name="name"
				/>

				<InputSelect
					{...inputProps}
					label="routine"
					name="routineId"
					options={routines.map(({id: value, name: label}) => ({
						label,
						value,
					}))}
				/>

				<InputSelect
					{...inputProps}
					label="product-version"
					name="productVersionId"
					options={productVersions.map(
						({id: value, name: label}) => ({
							label,
							value,
						})
					)}
				/>

				<Input
					{...inputProps}
					label={i18n.translate('git-hash')}
					name="gitHash"
				/>

				<Input
					{...inputProps}
					label={i18n.translate('description')}
					name="description"
					type="textarea"
				/>

				<ClayCheckbox
					checked={template}
					label={i18n.translate('template')}
					onChange={() => setValue('template', !template)}
				/>

				<ClayButton.Group className="mb-4">
					<ClayButton displayType="secondary">
						{i18n.translate('select-cases')}
					</ClayButton>

					<ClayButton className="ml-3" displayType="secondary">
						{i18n.translate('select-case-parameters')}
					</ClayButton>
				</ClayButton.Group>

				<div>
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={() => onClose()}
						>
							{i18n.translate('close')}
						</ClayButton>

						<ClayButton displayType="primary">
							{i18n.translate('save')}
						</ClayButton>
					</ClayButton.Group>
				</div>
			</ClayForm>
		</Container>
	);
};
export default RoutineBuildForm;
