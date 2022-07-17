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
import {useEffect, useState} from 'react';
import {useForm} from 'react-hook-form';
import {useNavigate, useOutletContext, useParams} from 'react-router-dom';

import Form from '../../../../components/Form';
import Container from '../../../../components/Layout/Container';
import {
	APIResponse,
	TestrayBuild,
	TestrayCase,
	TestrayRoutine,
} from '../../../../graphql/queries';
import {useHeader} from '../../../../hooks';
import {useFetch} from '../../../../hooks/useFetch';
import useFormActions from '../../../../hooks/useFormActions';
import useFormModal from '../../../../hooks/useFormModal';
import i18n from '../../../../i18n';
import yupSchema, {yupResolver} from '../../../../schema/yup';
import {createBuild, updateBuild} from '../../../../services/rest/TestrayBuild';
import {searchUtil} from '../../../../util/search';
import {CaseListView} from '../../Cases';
import SuiteFormSelectModal from '../../Suites/modal';
import BuildOptionModal from './BuildOptionModal';
import BuildSelectOptionsModal from './BuildSelectOptionsModal';

import type {KeyedMutator} from 'swr';

type RoutineBuildData = typeof yupSchema.build.__outputType;

const RoutineBuildForm = () => {
	const {modal: optionModal} = useFormModal();
	const {modal: optionSelectModal} = useFormModal();
	const navigate = useNavigate();
	const [cases, setCases] = useState<number[]>([]);
	const {projectId, routineId} = useParams();
	const {
		mutateBuild,
		testrayBuild,
	}: {
		mutateBuild: KeyedMutator<any>;
		testrayBuild?: TestrayBuild;
	} = useOutletContext();

	const {
		form: {onClose, onSubmitRest},
	} = useFormActions();

	const {modal} = useFormModal({
		onSave: (newCases) =>
			setCases((prevCases) => [...new Set([...prevCases, ...newCases])]),
	});

	const {
		formState: {errors},
		handleSubmit,
		register,
		setValue,
		watch,
	} = useForm<RoutineBuildData>({
		defaultValues: testrayBuild
			? {
					description: testrayBuild.description,
					gitHash: testrayBuild.gitHash,
					name: testrayBuild.name,
					productVersionId: String(testrayBuild.productVersion?.id),
					routineId: String(testrayBuild.routine?.id || routineId),
					template: testrayBuild.template,
			  }
			: {
					routineId,
					template: false,
			  },
		resolver: yupResolver(yupSchema.build),
	});

	const {setTabs} = useHeader({
		shouldUpdate: false,
		timeout: 150,
		title: i18n.translate('new-build'),
	});

	const {data: routinesData} = useFetch<APIResponse<TestrayRoutine>>(
		`/routines?fields=id,name&filter=${searchUtil.eq(
			'projectId',
			projectId as string
		)}`
	);

	const {data: productVersionsData} = useFetch<APIResponse<TestrayRoutine>>(
		`/productversions?fields=id,name&filter=${searchUtil.eq(
			'projectId',
			projectId as string
		)}`
	);

	// eslint-disable-next-line react-hooks/exhaustive-deps
	const productVersions = productVersionsData?.items || [];

	const routines = routinesData?.items || [];

	const template = watch('template');

	const inputProps = {
		errors,
		register,
	};

	const _onSubmit = async (data: RoutineBuildData) => {
		if (testrayBuild) {
			data.id = testrayBuild.id.toString();
		}

		const response = await onSubmitRest(data, {
			create: createBuild,
			update: updateBuild,
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

	useEffect(() => {
		setTabs([]);
	}, [setTabs]);

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
						/>
					</div>

					<ClayButtonWithIcon
						className="mt-5"
						displayType="secondary"
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

				<h3>{i18n.translate('cases')}</h3>

				<Form.Divider />

				<ClayButton.Group className="mb-4">
					<ClayButton
						displayType="secondary"
						onClick={() => modal.open()}
					>
						{i18n.translate('add-cases')}
					</ClayButton>

					<ClayButton className="ml-1" displayType="secondary">
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
							variables: {filter: searchUtil.in('id', cases)},
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

			<SuiteFormSelectModal modal={modal} type="select-cases" />

			<BuildOptionModal modal={optionModal} />

			<BuildSelectOptionsModal modal={optionSelectModal} />
		</Container>
	);
};

export default RoutineBuildForm;
