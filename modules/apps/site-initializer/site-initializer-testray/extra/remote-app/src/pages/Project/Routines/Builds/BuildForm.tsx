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
import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayForm, {ClayCheckbox} from '@clayui/form';
import {useEffect, useState} from 'react';
import {useForm} from 'react-hook-form';
import {useNavigate, useOutletContext, useParams} from 'react-router-dom';

import Form from '../../../../components/Form';
import Container from '../../../../components/Layout/Container';
import {CreateBuild, UpdateBuild} from '../../../../graphql/mutations';
import {
	CTypePagination,
	TestrayBuild,
	TestrayCase,
	TestrayRoutine,
	getRoutines,
} from '../../../../graphql/queries';
import {
	TestrayProductVersion,
	getProductVersions,
} from '../../../../graphql/queries/testrayProductVersion';
import {useHeader} from '../../../../hooks';
import useFormActions from '../../../../hooks/useFormActions';
import useFormModal from '../../../../hooks/useFormModal';
import i18n from '../../../../i18n';
import yupSchema, {yupResolver} from '../../../../schema/yup';
import {searchUtil} from '../../../../util/search';
import {CaseListView} from '../../Cases';
import SuiteFormSelectModal from '../../Suites/modal';
import BuildOptionModal from './BuildOptionModal';
import BuildSelectOptionsModal from './BuildSelectOptionsModal';

type RoutineBuildData = {
	description: string;
	gitHash: string;
	id?: number;
	name: string;
	productVersionId: string;
	routineId: string;
	template: boolean;
};

const RoutineBuildForm = () => {
	const [cases, setCases] = useState<number[]>([]);

	const {projectId, routineId} = useParams();

	const {testrayBuild}: {testrayBuild?: TestrayBuild} = useOutletContext();

	const navigate = useNavigate();

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
			  }
			: {
					routineId,
					template: false,
			  },
		resolver: yupResolver(yupSchema.build),
	});

	const {
		form: {onClose, onSubmit},
	} = useFormActions();

	const {modal} = useFormModal({
		onSave: (newCases) =>
			setCases((prevCases) => [...new Set([...prevCases, ...newCases])]),
	});

	const {modal: optionModal} = useFormModal();
	const {modal: optionSelectModal} = useFormModal();

	const {setTabs} = useHeader({
		shouldUpdate: false,
		timeout: 50,
		title: i18n.translate('new-build'),
	});

	const {data: routinesData} = useQuery<
		CTypePagination<'routines', TestrayRoutine>
	>(getRoutines, {
		variables: {
			filter: searchUtil.eq('projectId', projectId as string),
		},
	});

	const {data: productVersionsData} = useQuery<
		CTypePagination<'productVersions', TestrayProductVersion>
	>(getProductVersions, {
		onCompleted: (data) =>
			setValue(
				'productVersionId',
				data?.c?.productVersions?.items[0]?.id?.toString()
			),
		variables: {
			filter: searchUtil.eq('projectId', projectId as string),
		},
	});

	const routines = routinesData?.c.routines.items || [];
	const productVersions = productVersionsData?.c.productVersions.items || [];

	const template = watch('template');

	const inputProps = {
		errors,
		register,
	};

	const _onSubmit = async (data: RoutineBuildData) => {
		if (testrayBuild) {
			data.id = testrayBuild.id;
		}

		await onSubmit(
			data,
			{
				createMutation: CreateBuild,
				updateMutation: UpdateBuild,
			},
			{
				update(cache, {data: {createBuild, updateBuild}}) {
					cache.modify({
						fields: {
							builds(buildCache) {
								return {
									...buildCache,
									items: createBuild
										? [...buildCache.items, createBuild]
										: buildCache.items.map(
												(build: TestrayBuild) => {
													if (
														build.id ===
														updateBuild.id
													) {
														return {
															...build,
															...updateBuild,
														};
													}

													return build;
												}
										  ),
								};
							},
						},
					});
				},
			}
		);

		navigate(-1);
	};

	useEffect(() => {
		setTabs([]);
	}, [setTabs]);

	return (
		<Container className="container">
			<ClayForm className="container pt-2">
				<ClayCheckbox
					checked={template}
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
								],
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
