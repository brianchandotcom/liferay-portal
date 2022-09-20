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

import {useCallback, useState} from 'react';
import {useForm} from 'react-hook-form';

import Form from '../../../components/Form';
import DualListBox, {Boxes} from '../../../components/Form/DualListBox';
import Modal from '../../../components/Modal';
import {withVisibleContent} from '../../../hoc/withVisibleContent';
import {useFetch} from '../../../hooks/useFetch';
import {FormModalOptions} from '../../../hooks/useFormModal';
import i18n from '../../../i18n';
import yupSchema, {yupResolver} from '../../../schema/yup';
import {
	APIResponse,
	TestrayComponent,
	testrayComponentImpl,
	testrayTeamImpl,
} from '../../../services/rest';
import {searchUtil} from '../../../util/search';

type TeamForm = typeof yupSchema.team.__outputType;

type TeamProps = {
	modal: FormModalOptions;
	projectId: number;
};

const onMapDefault = ({id, name, ...rest}: TestrayComponent) => ({
	label: name,
	teamId: rest.r_teamToComponents_c_teamId,
	value: id.toString(),
});

export type SelectComponentsProps = {
	projectId: number;
	setState: any;
	state: State;
	teamId: number;
};

const UNASSIGNED_TEAM_ID = 0;

const SelectComponents: React.FC<SelectComponentsProps> = ({
	projectId,
	setState,
	teamId,
}) => {
	const {data: unassigned, isValidating} = useFetch<
		APIResponse<TestrayComponent>
	>(
		`/components?filter=${searchUtil.eq(
			'projectId',
			projectId
		)} and ${searchUtil.eq('teamId', UNASSIGNED_TEAM_ID)}`
	);

	const {data: current} = useFetch<APIResponse<TestrayComponent>>(
		teamId && !isValidating
			? `/components?filter=${searchUtil.eq(
					'projectId',
					projectId
			  )} and ${searchUtil.eq('teamId', teamId)}`
			: null
	);

	const getComponentsDualBox = useCallback(() => {
		const currentItems = current?.items || [];
		const unassignedItems = unassigned?.items || [];

		return [
			unassignedItems.map(onMapDefault),
			currentItems.map(onMapDefault),
		];
	}, [unassigned, current]);

	const componentsDualBox = getComponentsDualBox();

	return (
		<DualListBox
			boxes={componentsDualBox}
			leftLabel={i18n.translate('unassigned')}
			rightLabel={i18n.translate('current')}
			setValue={setState}
		/>
	);
};
export type State = Boxes<{teamId: number}>;

const TeamFormModal: React.FC<TeamProps> = ({
	modal: {modalState, observer, onClose, onError, onSave, onSubmit},
	projectId,
}) => {
	const [state, setState] = useState<State>([]);
	const {
		formState: {errors},
		handleSubmit,
		register,
	} = useForm<TeamForm>({
		defaultValues: modalState,
		resolver: yupResolver(yupSchema.team),
	});

	const _onSubmit = (teamForm: TeamForm) => {
		onSubmit(
			{id: teamForm.id, name: teamForm.name, projectId},
			{
				create: (data) => testrayTeamImpl.create(data),
				update: (id, data) => testrayTeamImpl.update(id, data),
			}
		)
			.then((teamResponse) =>
				testrayComponentImpl.assignTeamsToComponents(
					teamResponse.id.toString(),
					state
				)
			)
			.then(onSave)
			.catch(onError);
	};

	return (
		<Modal
			last={
				<Form.Footer
					onClose={onClose}
					onSubmit={handleSubmit(_onSubmit)}
				/>
			}
			observer={observer}
			size="full-screen"
			title={
				modalState?.id
					? i18n.sub('edit-x', 'team')
					: i18n.translate('new-team')
			}
			visible
		>
			<Form.Input
				errors={errors}
				label={i18n.translate('name')}
				name="name"
				register={register}
				required
			/>

			<SelectComponents
				projectId={projectId}
				setState={setState}
				state={state}
				teamId={modalState?.id}
			/>
		</Modal>
	);
};

export default withVisibleContent(TeamFormModal);
