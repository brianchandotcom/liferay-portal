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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import {ClayInput} from '@clayui/form';
import {useEffect, useState} from 'react';
import {useForm} from 'react-hook-form';
import {useParams} from 'react-router-dom';

import Form from '../../components/Form';
import Container from '../../components/Layout/Container';
import {useHeader} from '../../hooks';
import {useFetch} from '../../hooks/useFetch';
import useFormActions from '../../hooks/useFormActions';
import useFormModal from '../../hooks/useFormModal';
import i18n from '../../i18n';
import yupSchema, {yupResolver} from '../../schema/yup';
import {Liferay} from '../../services/liferay';
import {TestrayCaseType, testrayTaskImpl} from '../../services/rest';
import {searchUtil} from '../../util/search';
import {UserListView} from '../Manage/User';
import useTestFlowAssign from './TestflowFormAssignUserActions';
import TestflowAssignUserModal from './modal';

type TestflowFormType = typeof yupSchema.task.__outputType;

const TestflowForm = () => {
	const {
		form: {onClose, onError, onSave, onSubmit},
	} = useFormActions();

	const [modalType, setModalType] = useState('assign-users');
	const [users, setUsers] = useState<number[]>([]);
	const {modal} = useFormModal({
		onSave: setUsers,
	});
	const {buildId} = useParams();
	const {actions} = useTestFlowAssign({setUsers});

	const {data} = useFetch('/casetypes?pageSize=100&fields=id,name');

	const {
		formState: {errors},
		handleSubmit,
		register,
		setValue,
		watch,
	} = useForm<TestflowFormType>({
		defaultValues: {
			buildId: Number(buildId ?? 0),
			caseTypes: [],
			userIds: [],
		},
		resolver: yupResolver(yupSchema.task),
	});

	useHeader({
		useHeading: [
			{
				category: i18n.translate('task'),
				title: i18n.translate('testflow'),
			},
		],
	});

	const caseTypes = data?.items || [];

	const onOpenModal = (option: 'select-users' | 'select-user-groups') => {
		setModalType(option);

		modal.open(users);
	};

	const _onSubmit = (form: TestflowFormType) => {
		let hasError = false;

		if (!form.caseTypes?.length) {
			hasError = true;

			Liferay.Util.openToast({
				message: i18n.translate(
					'mark-at-least-one-case-type-for-processing'
				),
				type: 'danger',
			});
		}

		if (!form.userIds?.length) {
			hasError = true;

			Liferay.Util.openToast({
				message: i18n.translate(
					'mark-at-last-one-user-or-user-group-for-assignment'
				),
				type: 'danger',
			});
		}

		if (hasError) {
			return;
		}

		onSubmit(form, {
			create: (data) => testrayTaskImpl.create(data),
			update: (id, data) => testrayTaskImpl.update(id, data),
		})
			.then(onSave)
			.catch(onError);
	};

	const inputProps = {
		errors,
		register,
	};

	const caseTypesWatch = watch('caseTypes') as number[];

	const onClickCaseType = (event: any) => {
		const value = Number(event.target.value);

		const caseTypesFiltered = caseTypesWatch.includes(value)
			? caseTypesWatch.filter((caseTypeId) => caseTypeId !== value)
			: [...caseTypesWatch, value];

		setValue('caseTypes', caseTypesFiltered);
	};

	useEffect(() => {
		setValue('userIds', users);
	}, [setValue, users]);

	return (
		<Container>
			<ClayInput.GroupItem shrink>
				<Form.Input
					{...inputProps}
					label={i18n.translate('name')}
					name="name"
					required
					size={100}
				/>
			</ClayInput.GroupItem>

			<Form.Clay.Group>
				<label className="mb-2 required">
					{i18n.translate('case-type')}
				</label>

				<div className="d-flex flex-wrap">
					{caseTypes.map(
						(caseType: TestrayCaseType, index: number) => (
							<div className="col-4" key={index}>
								<Form.Checkbox
									checked={caseTypesWatch.includes(
										caseType.id
									)}
									label={caseType.name}
									name={caseType.name}
									onChange={onClickCaseType}
									value={caseType.id}
								/>
							</div>
						)
					)}
				</div>
			</Form.Clay.Group>

			<label className="mb-2 required">
				<h5>{i18n.translate('users')}</h5>
			</label>

			<Form.Divider />

			<Form.Clay.Group>
				<ClayButton
					displayType="secondary"
					onClick={() => onOpenModal('select-users')}
				>
					{i18n.translate('assign-users')}
				</ClayButton>

				<ClayButton
					className="ml-2"
					displayType="secondary"
					onClick={() => onOpenModal('select-user-groups')}
				>
					{i18n.translate('assign-user-groups')}
				</ClayButton>
			</Form.Clay.Group>

			{!users.length && (
				<ClayAlert>
					{i18n.translate('there-are-no-linked-users')}
				</ClayAlert>
			)}

			{!!users.length && (
				<UserListView
					actions={actions}
					listViewProps={{
						managementToolbarProps: {
							visible: false,
						},
						variables: {filter: searchUtil.in('id', users)},
					}}
				/>
			)}

			<Form.Divider />

			<Form.Footer
				onClose={() => onClose()}
				onSubmit={handleSubmit(_onSubmit)}
			/>

			<TestflowAssignUserModal modal={modal} type={modalType as any} />
		</Container>
	);
};

export default TestflowForm;
