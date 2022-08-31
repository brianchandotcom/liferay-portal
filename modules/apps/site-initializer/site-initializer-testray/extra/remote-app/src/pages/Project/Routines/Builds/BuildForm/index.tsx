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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayForm, {ClayCheckbox} from '@clayui/form';
import {useState} from 'react';
import {useForm} from 'react-hook-form';
import {useNavigate, useOutletContext, useParams} from 'react-router-dom';

import Form from '../../../../../components/Form';
import Container from '../../../../../components/Layout/Container';
import {useHeader} from '../../../../../hooks';
import {useFetch} from '../../../../../hooks/useFetch';
import useFormActions from '../../../../../hooks/useFormActions';
import useFormModal from '../../../../../hooks/useFormModal';
import i18n from '../../../../../i18n';
import yupSchema, {yupResolver} from '../../../../../schema/yup';
import {
	APIResponse,
	TestrayBuild,
	TestrayProductVersion,
	TestrayRoutine,
	testrayBuildRest,
} from '../../../../../services/rest';
import {searchUtil} from '../../../../../util/search';
import ProductVersionFormModal from '../../../../Standalone/ProductVersions/ProductVersionFormModal';
import BuildFormCases from './BuildFormCases';
import BuildFormRun, {BuildFormType} from './BuildFormRun';

import type {KeyedMutator} from 'swr';

type OutletContext = {
	mutateBuild: KeyedMutator<any>;
	testrayBuild?: TestrayBuild;
};

const BuildForm = () => {
	const [caseIds, setCaseIds] = useState<number[]>([]);

	const {projectId, routineId} = useParams();

	const {data: productVersionsData, mutate} = useFetch<
		APIResponse<TestrayProductVersion>
	>(
		`/productversions?fields=id,name&filter=${searchUtil.eq(
			'projectId',
			projectId as string
		)}`
	);

	const {data: routinesData} = useFetch<APIResponse<TestrayRoutine>>(
		`/routines?fields=id,name&filter=${searchUtil.eq(
			'projectId',
			projectId as string
		)}`
	);

	const {modal: newProductVersionModal} = useFormModal({
		onSave: (produtVersion: TestrayProductVersion) => {
			mutate((productVersionResponse) => {
				if (!productVersionResponse) {
					return;
				}

				return {
					...productVersionResponse,
					items: [...productVersionResponse?.items, produtVersion],
				};
			});
		},
	});

	const navigate = useNavigate();
	const {mutateBuild, testrayBuild}: OutletContext = useOutletContext();

	const {
		form: {onClose, onSubmit},
	} = useFormActions();

	const {
		control,
		formState: {errors},
		handleSubmit,
		register,
		setValue,
		watch,
	} = useForm<BuildFormType>({
		defaultValues: testrayBuild
			? {
					categories: [{}],
					description: testrayBuild.description,
					gitHash: testrayBuild.gitHash,
					name: testrayBuild.name,
					productVersionId: String(testrayBuild.productVersion?.id),
					routineId: String(testrayBuild.routine?.id || routineId),
					template: testrayBuild.template,
			  }
			: {
					categories: [{}],
					routineId,
					template: false,
			  },
		resolver: yupResolver(yupSchema.build),
	});

	const categories = watch('categories');

	useHeader({
		timeout: 150,
		useTabs: [],
	});

	const productVersionId = watch('productVersionId');

	// eslint-disable-next-line react-hooks/exhaustive-deps
	const productVersions = productVersionsData?.items || [];

	const routines = routinesData?.items || [];

	const template = watch('template');

	const inputProps = {
		errors,
		register,
	};

	const _onSubmit = async (data: BuildFormType) => {
		data.caseIds = caseIds;

		if (testrayBuild) {
			data.id = testrayBuild.id.toString();
		}

		const response = await onSubmit(data, {
			create: (...params) => testrayBuildRest.create(...params),
			update: (...params) => testrayBuildRest.update(...params),
		});

		if (testrayBuild) {
			mutateBuild(response);
		}

		navigate(-1);
	};

	return (
		<Container className="container">
			<ClayForm className="container pt-2">
				<ClayCheckbox
					checked={template as boolean}
					label={i18n.translate('template')}
					onChange={() => setValue('template', !template)}
				/>

				<Form.Input
					{...inputProps}
					label={i18n.translate('name')}
					name="name"
					required
				/>

				<Form.Select
					{...inputProps}
					defaultOption={false}
					label="routine"
					name="routineId"
					options={routines.map(({id: value, name: label}) => ({
						label,
						value,
					}))}
				/>

				<div className="row">
					<div className="col-md-6">
						<Form.Select
							{...inputProps}
							label="product-version"
							name="productVersionId"
							options={productVersions.map(
								({id: value, name: label}) => ({
									label,
									value,
								})
							)}
							required
							value={productVersionId}
						/>
					</div>

					<ClayButtonWithIcon
						className="mt-5"
						displayType="secondary"
						onClick={() => newProductVersionModal.open()}
						symbol="plus"
						title={i18n.translate('add-product-version')}
					/>
				</div>

				<Form.Input
					{...inputProps}
					label={i18n.translate('git-hash')}
					name="gitHash"
				/>

				<Form.Input
					{...inputProps}
					label={i18n.translate('description')}
					name="description"
					type="textarea"
				/>

				<BuildFormRun
					categories={categories}
					control={control}
					register={register}
				/>

				<BuildFormCases caseIds={caseIds} setCaseIds={setCaseIds} />

				<div className="mt-4">
					<Form.Footer
						onClose={onClose}
						onSubmit={handleSubmit(_onSubmit)}
					/>
				</div>
			</ClayForm>

			<ProductVersionFormModal
				modal={newProductVersionModal}
				projectId={(projectId as unknown) as number}
			/>
		</Container>
	);
};

export default BuildForm;
