/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayForm from '@clayui/form';
import {useEffect, useState} from 'react';
import {useForm} from 'react-hook-form';
import {useOutletContext, useParams, useSearchParams} from 'react-router-dom';
import {withPagePermission} from '~/hoc/withPagePermission';
import {BuildStatuses} from '~/util/statuses';

import Form from '../../../../../components/Form';
import Container from '../../../../../components/Layout/Container';
import SearchBuilder from '../../../../../core/SearchBuilder';
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

	const {buildId, buildTemplateId, projectId, routineId} = useParams();

	const [searchParams] = useSearchParams();

	const buildTemplate = searchParams.get(`template`);

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
		`/productversions?fields=id,name&filter=${SearchBuilder.eq(
			'projectId',
			projectId as string
		)}`
	);

	const {data: routinesData} = useFetch<APIResponse<TestrayRoutine>>(
		`/routines?fields=id,name&filter=${SearchBuilder.eq(
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
					dueStatus: BuildStatuses.ACTIVATED,
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
		tabs: [],
		timeout: 150,
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
							aria-label={i18n.sub('add-x', 'product-version')}
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

export default withPagePermission(BuildForm, {
	createPath: 'project/:projectId/routines/:routineId/create',
	restImpl: testrayBuildImpl,
});
