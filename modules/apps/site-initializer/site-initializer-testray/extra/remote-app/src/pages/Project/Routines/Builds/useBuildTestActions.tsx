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

import {useRef} from 'react';

import useAssignCaseResult from '../../../../hooks/useAssignCaseResult';
import useFormActions from '../../../../hooks/useFormActions';
import useModalContext from '../../../../hooks/useModalContext';
import useMutate from '../../../../hooks/useMutate';
import i18n from '../../../../i18n';
import {TestrayCaseResult, deleteResource} from '../../../../services/rest';
import {Action} from '../../../../types';
import {UserListView} from '../../../Manage/User';

const useBuildTestActions = () => {
	const {form} = useFormActions();
	const {removeItemFromList, updateItemFromList} = useMutate();
	const {onAssignToFetch, onAssignToMeFetch} = useAssignCaseResult();
	const {onOpenModal, state} = useModalContext();

	const actionsRef = useRef([
		{
			action: (caseResult, mutate) =>
				onOpenModal({
					body: (
						<UserListView
							listViewProps={{
								managementToolbarProps: {
									addButton: undefined,
									display: {columns: false},
								},
							}}
							tableProps={{
								onClickRow: (user) => {
									onAssignToFetch(caseResult, user.id)
										.then(() =>
											updateItemFromList(
												mutate,
												caseResult.id,
												{user},
												{revalidate: true}
											)
										)
										.finally(state.onClose);
								},
							}}
						/>
					),
					size: 'full-screen',
					title: i18n.translate('users'),
				}),
			icon: 'user',
			name: i18n.translate('assign'),
		},
		{
			action: (caseResult, mutate) =>
				onAssignToMeFetch(caseResult).then((user) =>
					updateItemFromList(
						mutate,
						caseResult.id,
						{user},
						{revalidate: true}
					)
				),
			icon: 'user',
			name: i18n.translate('assign-to-me'),
		},
		{
			action: ({id}, mutate) =>
				deleteResource(`/caseresults/${id}`)
					?.then(() => removeItemFromList(mutate, id))
					.then(form.onSave)
					.catch(form.onError),
			icon: 'trash',
			name: i18n.translate('delete'),
			permission: 'DELETE',
		},
	] as Action<TestrayCaseResult>[]);

	return {
		actions: actionsRef.current,
	};
};

export default useBuildTestActions;
