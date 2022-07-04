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
import {useState} from 'react';
import {useForm} from 'react-hook-form';

import Form from '../../../components/Form';
import Container from '../../../components/Layout/Container';
import {
	CTypePagination,
	TestrayCase,
	TestrayRoutine,
	getRoutines,
} from '../../../graphql/queries';
import {
	TestrayProductVersion,
	getProductVersions,
} from '../../../graphql/queries/testrayProductVersion';
import {useHeader} from '../../../hooks';
import useFormActions from '../../../hooks/useFormActions';
import useFormModal from '../../../hooks/useFormModal';
import i18n from '../../../i18n';
import yupSchema, {yupResolver} from '../../../schema/yup';
import {searchUtil} from '../../../util/search';
import {CaseListView} from '../Cases';
import SuiteFormSelectModal from '../Suites/modal';

type RoutineBuildData = {
	description: string;
	gitHash: string;
	name: string;
	productVersionId: string;
	routineId: string;
	template: boolean;
};

const RoutineBuildForm = () => {
	const [cases, setCases] = useState<number[]>([]);

	const {data: routinesData} = useQuery<
		CTypePagination<'routines', TestrayRoutine>
	>(getRoutines);

	const {data: productVersionsData} = useQuery<
		CTypePagination<'productVersions', TestrayProductVersion>
	>(getProductVersions);

	const routines = routinesData?.c.routines.items || [];
	const productVersions = productVersionsData?.c.productVersions.items || [];

	useHeader({timeout: 20, useTabs: []});

	const {
		form: {onClose},
	} = useFormActions();

	const {modal} = useFormModal({
		onSave: (newCases) =>
			setCases((prevCases) => [...new Set([...prevCases, ...newCases])]),
	});

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
				<ClayCheckbox
					checked={template}
					label={i18n.translate('template')}
					onChange={() => setValue('template', !template)}
				/>

				<Form.Input
					{...inputProps}
					label={i18n.translate('name')}
					name="name"
				/>

				<Form.Select
					{...inputProps}
					label="routine"
					name="routineId"
					options={routines.map(({id: value, name: label}) => ({
						label,
						value,
					}))}
				/>

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
				/>

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
						onClick={() => modal.open()}
					>
						{i18n.translate('add-option')}
					</ClayButton>

					<ClayButton className="ml-1" displayType="secondary">
						{i18n.translate('select-stacks')}
					</ClayButton>
				</ClayButton.Group>

				<SuiteFormSelectModal modal={modal} type="select-cases" />

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
					<Form.Footer onClose={onClose} onSubmit={() => null} />
				</div>
			</ClayForm>
		</Container>
	);
};
export default RoutineBuildForm;
