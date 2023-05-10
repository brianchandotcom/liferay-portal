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
import {useEffect, useMemo, useState} from 'react';
import {useForm} from 'react-hook-form';
import {useNavigate, useOutletContext, useParams} from 'react-router-dom';
import {KeyedMutator} from 'swr';
import {withPagePermission} from '~/hoc/withPagePermission';

import Form from '../../components/Form';
import Container from '../../components/Layout/Container';
import SearchBuilder from '../../core/SearchBuilder';
import {useHeader} from '../../hooks';
import {useFetch} from '../../hooks/useFetch';
import useFormActions from '../../hooks/useFormActions';
import useFormModal from '../../hooks/useFormModal';
import i18n from '../../i18n';
import yupSchema, {yupResolver} from '../../schema/yup';
import {Liferay} from '../../services/liferay';
import {
	APIResponse,
	TestrayCaseType,
	TestrayTask,
	TestrayTaskCaseTypes,
	TestrayTaskUser,
	testrayTaskImpl,
	testrayTaskUsersImpl,
} from '../../services/rest';
import {TaskStatuses} from '../../util/statuses';
import {UserListView} from '../Manage/User';
import useTestFlowAssign from './TestflowFormAssignUserActions';
import TestflowAssignUserModal, {TestflowAssigUserType} from './modal';

type TestflowFormType = typeof yupSchema.task.__outputType;

type OutletContext = {
	data: {
		testrayTask: TestrayTask;
		testrayTaskCaseTypes: TestrayTaskCaseTypes[];
		testrayTaskUser: TestrayTaskUser[];
	};
	mutate: {
		mutateTask: KeyedMutator<TestrayTask>;
		mutateTaskUser: KeyedMutator<APIResponse<TestrayTaskUser>>;
	};
	revalidate: {revalidateTaskUser: () => void};
};

const TestflowForm = () => {
	const {
		form: {onClose, onError, onSubmit, onSuccess},
	} = useFormActions();

	const [modalType, setModalType] = useState<TestflowAssigUserType>(
		'select-users'
	);
	const [userIds, setUserIds] = useState<number[]>([]);
	const {modal} = useFormModal({
		onSave: setUserIds,
	});
	const {buildId, taskId} = useParams();
	const {actions} = useTestFlowAssign({setUserIds});
	const navigate = useNavigate();

	const outletContext = useOutletContext<OutletContext>();

	const {setHeading} = useHeader({timeout: 210});

	const {
		data: {
			testrayTaskCaseTypes = [],
			testrayTaskUser = undefined,
			testrayTask = undefined,
		} = {},
		mutate: {mutateTask = () => null} = {mutateTask: undefined},
		revalidate: {revalidateTaskUser = () => null} = {
			revalidateTaskUser: undefined,
		},
	} = outletContext;

	const {data} = useFetch('/casetypes', {
		params: {
			fields: 'id,name',
			pageSize: 100,
		},
	});

	const caseTypes = useMemo(() => data?.items || [], [
		data?.items,
	]) as TestrayCaseType[];

	const taskCaseTypeIds = testrayTaskCaseTypes.map(
		({caseType}) => caseType?.id
	);

	const {
		formState: {errors, isSubmitting},
		handleSubmit,
		register,
		setValue,
		watch,
	} = useForm<TestflowFormType>({
		defaultValues: {
			buildId: Number(testrayTask?.build?.id ?? buildId),
			caseTypes: taskCaseTypeIds,
			dueStatus: TaskStatuses.IN_ANALYSIS,
			id: Number(taskId ?? 0),
			name: testrayTask?.name,
			userIds: [],
		},
		resolver: yupResolver(yupSchema.task),
	});

	const onOpenModal = (option: 'select-users' | 'select-user-groups') => {
		setModalType(option);

		modal.open(userIds);
	};

	const _onSubmit = async (form: TestflowFormType) => {
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

		try {
			const response = await onSubmit(form, {
				create: (data) => testrayTaskImpl.create(data),
				update: (id, data) => testrayTaskImpl.update(id, data),
			});

			await testrayTaskUsersImpl.assign(response.id, userIds);

			if (form.id) {
				mutateTask(response);

				revalidateTaskUser();
			}

			onSuccess();

			navigate(`/testflow/${response.id}`);
		}
		catch (error) {
			onError(error);
		}
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
		setHeading(
			[
				{
					category: i18n.translate('task'),
					title: i18n.translate('testflow'),
				},
			],
			true
		);
	}, [setHeading]);

	useEffect(() => {
		if (testrayTaskUser) {
			setUserIds(testrayTaskUser.map(({user}) => user?.id as number));
		}
	}, [setUserIds, testrayTaskUser]);

	useEffect(() => {
		setValue('userIds', userIds);
	}, [setValue, userIds]);

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
					{caseTypes.map((caseType, index: number) => (
						<div className="col-4" key={index}>
							<Form.Checkbox
								checked={caseTypesWatch.includes(caseType.id)}
								disabled={!!taskId}
								label={caseType.name}
								name={caseType.name}
								onChange={onClickCaseType}
								value={caseType.id}
							/>
						</div>
					))}
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

			{!userIds.length && (
				<ClayAlert>
					{i18n.translate('there-are-no-linked-users')}
				</ClayAlert>
			)}

			{!!userIds.length && (
				<UserListView
					actions={actions}
					listViewProps={{
						managementToolbarProps: {
							visible: false,
						},
						variables: {filter: SearchBuilder.in('id', userIds)},
					}}
				/>
			)}

			<Form.Divider />

			<Form.Footer
				onClose={() => onClose()}
				onSubmit={handleSubmit(_onSubmit)}
				primaryButtonProps={{loading: isSubmitting}}
			/>

			<TestflowAssignUserModal modal={modal} type={modalType} />
		</Container>
	);
};

export default withPagePermission(TestflowForm, {
	createPath:
		'/project/:projectId/routines/:routinesId/build/:buildId/testflow/create',
	restImpl: testrayTaskImpl,
});
