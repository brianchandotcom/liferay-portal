/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useState} from 'react';
import {useForm} from 'react-hook-form';

import Form from '../../../components/Form';
import DualListBox, {Boxes} from '../../../components/Form/DualListBox';
import Modal from '../../../components/Modal';
import SearchBuilder from '../../../core/SearchBuilder';
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

const SelectComponents: React.FC<SelectComponentsProps> = ({
	projectId,
	setState,
	teamId,
}) => {
	const {data: unassigned, isValidating} = useFetch<
		APIResponse<TestrayComponent>
	>('/components', {
		params: {
			filter: new SearchBuilder()
				.eq('projectId', projectId)
				.and()
				.eq('teamId', testrayComponentImpl.UNASSIGNED_TEAM_ID)
				.build(),
		},
	});

	const {data: current} = useFetch<APIResponse<TestrayComponent>>(
		teamId && !isValidating ? '/components' : null,
		{
			params: {
				filter: new SearchBuilder()
					.eq('projectId', projectId)
					.and()
					.eq('teamId', teamId)
					.build(),
			},
		}
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
		formState: {errors, isSubmitting},
		handleSubmit,
		register,
	} = useForm<TeamForm>({
		defaultValues: modalState,
		resolver: yupResolver(yupSchema.team),
	});

	const _onSubmit = (teamForm: TeamForm) =>
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

	return (
		<Modal
			last={
				<Form.Footer
					onClose={onClose}
					onSubmit={handleSubmit(_onSubmit)}
					primaryButtonProps={{loading: isSubmitting}}
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
