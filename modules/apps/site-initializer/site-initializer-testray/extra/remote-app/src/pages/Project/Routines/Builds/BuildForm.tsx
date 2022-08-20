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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayForm, {ClayCheckbox} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import {Fragment, useEffect, useState} from 'react';
import {useFieldArray, useForm} from 'react-hook-form';
import {useNavigate, useOutletContext, useParams} from 'react-router-dom';

import Form from '../../../../components/Form';
import Container from '../../../../components/Layout/Container';
import {useHeader} from '../../../../hooks';
import {useFetch} from '../../../../hooks/useFetch';
import useFormActions from '../../../../hooks/useFormActions';
import useFormModal from '../../../../hooks/useFormModal';
import i18n from '../../../../i18n';
import yupSchema, {yupResolver} from '../../../../schema/yup';
import {
	APIResponse,
	TestrayBuild,
	TestrayCase,
	TestrayFactor,
	TestrayProductVersion,
	TestrayRoutine,
	testrayBuildRest,
	testrayFactorRest,
} from '../../../../services/rest';
import {searchUtil} from '../../../../util/search';
import FactorOptionsFormModal from '../../../Standalone/FactorOptions/FactorOptionsFormModal';
import ProductVersionFormModal from '../../../Standalone/ProductVersions/ProductVersionFormModal';
import {CaseListView} from '../../Cases';
import SuiteFormSelectModal from '../../Suites/modal';
import BuildSelectOptionsModal from './BuildSelectOptionsModal';
import BuildSelectSuitesModal from './BuildSelectSuitesModal';

import type {KeyedMutator} from 'swr';

type BuildFormType = {
	categories: {
		value: string;
	}[];
	description?: string;
	gitHash?: string;
	id?: string;
	name: string;
	productVersionId: string;
	promoted?: boolean;
	routineId: string;
	template?: boolean;
};

const BuildForm = () => {
	const {projectId, routineId} = useParams();

	const {data: factorsData} = useFetch<APIResponse<TestrayFactor>>(
		`${testrayFactorRest.resource}&filter=${searchUtil.eq(
			'routineId',
			routineId as string
		)}`,
		(response) => testrayFactorRest.transformDataFromList(response)
	);

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

	const {modal: optionModal} = useFormModal();
	const {modal: optionSelectModal} = useFormModal();
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

	const {modal: buildSelectSuitesModal} = useFormModal();
	const navigate = useNavigate();
	const [cases, setCases] = useState<number[]>([]);
	const {
		mutateBuild,
		testrayBuild,
	}: {
		mutateBuild: KeyedMutator<any>;
		testrayBuild?: TestrayBuild;
	} = useOutletContext();

	const {
		form: {onClose, onSubmit},
	} = useFormActions();

	const factorItems = factorsData?.items || [];

	const {modal} = useFormModal({
		onSave: (newCases) =>
			setCases((prevCases) => [...new Set([...prevCases, ...newCases])]),
	});

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
					categories: [{value: ''}],
					description: testrayBuild.description,
					gitHash: testrayBuild.gitHash,
					name: testrayBuild.name,
					productVersionId: String(testrayBuild.productVersion?.id),
					routineId: String(testrayBuild.routine?.id || routineId),
					template: testrayBuild.template,
			  }
			: {
					categories: [{value: ''}],
					routineId,
					template: false,
			  },
		resolver: yupResolver(yupSchema.build),
	});

	const {append, fields, remove} = useFieldArray({
		control,
		name: 'categories',
	});

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

	useEffect(() => {
		if (productVersions.length) {
			setValue('productVersionId', productVersions[0].id.toString());
		}
	}, [productVersions, setValue]);

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
							defaultOption={false}
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

				<h3>{i18n.translate('runs')}</h3>

				<Form.Divider />

				<ClayButton.Group className="mb-4">
					<ClayButton
						displayType="secondary"
						onClick={() => optionModal.open()}
					>
						{i18n.translate('add-option')}
					</ClayButton>

					<ClayButton
						className="ml-1"
						displayType="secondary"
						onClick={() => optionSelectModal.open()}
					>
						{i18n.translate('select-stacks')}
					</ClayButton>
				</ClayButton.Group>

				<ClayLayout.Row>
					{fields.map((field, index) => (
						<Fragment key={field.id}>
							<ClayLayout.Col size={12}>
								<ClayLayout.Row className="align-items-center d-flex justify-content-space-between">
									{factorItems.map((factorItem, index) => (
										<ClayLayout.Col key={index} size={2}>
											<Form.Select
												label={
													factorItem.factorCategory
														?.name
												}
												name="factorOption"
												options={[
													{
														label: factorItem
															.factorOption
															?.name as string,
														value: factorItem
															.factorOption
															?.id as number,
													},
												]}
											/>
										</ClayLayout.Col>
									))}

									<ClayLayout.Col className="d-flex justify-content-end">
										<ClayButtonWithIcon
											displayType="secondary"
											onClick={() => append({} as any)}
											symbol="plus"
										/>

										{index !== 0 && (
											<ClayButtonWithIcon
												className="ml-1"
												displayType="secondary"
												onClick={() => remove(index)}
												symbol="hr"
											/>
										)}
									</ClayLayout.Col>
								</ClayLayout.Row>

								<Form.Divider />
							</ClayLayout.Col>
						</Fragment>
					))}
				</ClayLayout.Row>

				<h3>{i18n.translate('cases')}</h3>

				<Form.Divider />

				<ClayButton.Group className="mb-4">
					<ClayButton
						displayType="secondary"
						onClick={() => modal.open()}
					>
						{i18n.translate('add-cases')}
					</ClayButton>

					<ClayButton
						className="ml-1"
						displayType="secondary"
						onClick={() => buildSelectSuitesModal.open()}
					>
						{i18n.translate('add-suites')}
					</ClayButton>
				</ClayButton.Group>

				{!!cases.length && (
					<CaseListView
						listViewProps={{
							managementToolbarProps: {visible: false},
							tableProps: {
								actions: [
									{
										action: ({id}: TestrayCase) =>
											setCases((prevCases) =>
												prevCases.filter(
													(prevCase: number) =>
														prevCase !== id
												)
											),
										name: i18n.translate('delete'),
									},
								] as any,
								columns: [
									{
										key: 'priority',
										value: i18n.translate('priority'),
									},
									{
										key: 'component',
										render: (component) => component.name,
										value: i18n.translate('component'),
									},
									{
										key: 'name',
										size: 'md',
										value: i18n.translate('name'),
									},
								],
							},
							variables: {
								filter: searchUtil.in('id', cases),
							},
						}}
					/>
				)}

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

			<FactorOptionsFormModal modal={optionModal} />

			<BuildSelectOptionsModal
				factorItems={factorItems}
				modal={optionSelectModal}
			/>

			<SuiteFormSelectModal modal={modal} type="select-cases" />

			<BuildSelectSuitesModal modal={buildSelectSuitesModal} />
		</Container>
	);
};

export default BuildForm;
