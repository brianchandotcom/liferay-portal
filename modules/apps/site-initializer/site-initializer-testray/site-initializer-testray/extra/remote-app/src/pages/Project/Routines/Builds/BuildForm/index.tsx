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
import ClayForm from '@clayui/form';
import {useEffect, useState} from 'react';
import {useForm} from 'react-hook-form';
import {useOutletContext, useParams} from 'react-router-dom';

import Form from '../../../../../components/Form';
import Container from '../../../../../components/Layout/Container';
import {useHeader} from '../../../../../hooks';
import {useFetch} from '../../../../../hooks/useFetch';
import useFormActions from '../../../../../hooks/useFormActions';
import useFormModal from '../../../../../hooks/useFormModal';
import i18n from '../../../../../i18n';
import yupSchema, {yupResolver} from '../../../../../schema/yup';
import {Liferay} from '../../../../../services/liferay';
import {
	APIResponse,
	TestrayBuild,
	TestrayProductVersion,
	TestrayRoutine,
	testrayBuildImpl,
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

	const {
		buildId,
		buildTemplate,
		buildTemplateId,
		projectId,
		routineId,
	} = useParams();

	useEffect(() => {
		if (buildId) {
			testrayBuildImpl
				.getCurrentCaseIds(buildId)
				.then(setCaseIds)
				.catch(console.error);
		}

		if (buildTemplateId) {
			testrayBuildImpl
				.getCurrentCaseIds(buildTemplateId)
				.then(setCaseIds)
				.catch(console.error);
		}
	}, [buildId, buildTemplateId]);

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

	const {mutateBuild, testrayBuild}: OutletContext = useOutletContext();

	const {
		form: {onClose, onError, onSave, onSubmit, submitting},
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
					description: testrayBuild.description,
					factorStacks: [{}],
					gitHash: testrayBuild.gitHash,
					name: testrayBuild.name,
					productVersionId: String(testrayBuild.productVersion?.id),
					projectId: Number(projectId),
					routineId: String(testrayBuild.routine?.id || routineId),
					template: testrayBuild.template,
					templateTestrayBuildId: buildTemplateId ?? '',
			  }
			: {
					active: true,
					factorStacks: [{}],
					projectId: Number(projectId),
					routineId,
					template: false,
					templateTestrayBuildId: buildTemplateId ?? '',
			  },
		resolver: yupResolver(
			buildTemplate ? yupSchema.buildTemplate : yupSchema.build
		),
	});

	useHeader({
		timeout: 150,
		useTabs: [],
	});

	const productVersionId = watch('productVersionId');
	const productVersions = productVersionsData?.items || [];
	const routines = routinesData?.items || [];

	const inputProps = {
		errors,
		register,
	};

	if (buildTemplate) {
		setValue('template', true);
	}

	const _onSubmit = async (data: BuildFormType) => {
		const hasFactorStacks = data.factorStacks.some((factorStack: any) =>
			Object.keys(factorStack).some(
				(key) => !!Object.keys(factorStack[key]).length
			)
		);

		if (!hasFactorStacks) {
			return Liferay.Util.openToast({
				message: i18n.translate(
					'at-least-one-environment-stack-is-required'
				),
				type: 'danger',
			});
		}

		data.caseIds = caseIds;

		if (testrayBuild) {
			data.id = testrayBuild.id.toString();
		}

		const response = await onSubmit(data, {
			create: (data) => testrayBuildImpl.create(data),
			update: (id, data) => testrayBuildImpl.update(id, data),
		})
			.then(onSave)
			.catch(onError);

		if (testrayBuild) {
			mutateBuild(response);
		}
	};

	return (
		<Container className="container">
			<ClayForm className="container pt-2">
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

				{!buildTemplate && (
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
							displayType="primary"
							onClick={() => newProductVersionModal.open()}
							symbol="plus"
							title={i18n.sub('add-x', 'product-version')}
						/>
					</div>
				)}

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

				<BuildFormRun control={control} register={register} />

				<BuildFormCases
					caseIds={caseIds}
					setCaseIds={setCaseIds}
					title={i18n.translate('cases')}
				/>

				<div className="mt-4">
					<Form.Footer
						onClose={onClose}
						onSubmit={handleSubmit(_onSubmit)}
						primaryButtonProps={{
							loading: submitting,
						}}
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
