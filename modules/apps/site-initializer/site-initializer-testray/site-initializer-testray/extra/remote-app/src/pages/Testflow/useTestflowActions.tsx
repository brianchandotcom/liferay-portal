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
import {useNavigate} from 'react-router-dom';

import useFormModal from '../../hooks/useFormModal';
import useMutate from '../../hooks/useMutate';
import i18n from '../../i18n';
import {TestrayTask, testrayTaskImpl} from '../../services/rest';
import {Action} from '../../types';
import {TaskStatuses} from '../../util/statuses';

const useTestflowActions = () => {
	const {updateItemFromList} = useMutate();
	const navigate = useNavigate();
	const {forceRefetch, modal} = useFormModal();

	const actionsRef = useRef([
		{
			action: (task) =>
				navigate(
					`/project/${task.build?.project?.id}/routines/${task.build?.routine?.id}/build/${task.build?.id}`
				),
			icon: 'page',
			name: i18n.translate('view-associated-build'),
		},
		{
			action: (task, mutate) =>
				testrayTaskImpl.reanalyze(task).then(() =>
					updateItemFromList(
						mutate,
						0,
						{},
						{
							revalidate: true,
						}
					)
				),
			hidden: ({dueStatus}) => dueStatus.key === TaskStatuses.IN_ANALYSIS,
			icon: 'polls',
			name: i18n.translate('reanalyze'),
			permission: 'UPDATE',
		},
		{
			action: (subtask, mutate) =>
				testrayTaskImpl.remove(subtask.id).then(() =>
					updateItemFromList(
						mutate,
						0,
						{},
						{
							revalidate: true,
						}
					)
				),
			hidden: ({dueStatus}) => dueStatus.key === TaskStatuses.IN_ANALYSIS,
			icon: 'trash',
			name: i18n.translate('delete'),
			permission: 'DELETE',
		},
		{
			action: (task, mutate) => {
				const fn =
					task.subtaskScoreCompleted === task.subtaskScore
						? () => testrayTaskImpl.complete(task)
						: () => testrayTaskImpl.abandon(task);

				return fn().then(() =>
					updateItemFromList(
						mutate,
						0,
						{},
						{
							revalidate: true,
						}
					)
				);
			},
			hidden: ({dueStatus}) => dueStatus.key !== TaskStatuses.IN_ANALYSIS,
			icon: 'align-justify',
			name: (task) =>
				task.subtaskScoreCompleted === task.subtaskScore
					? i18n.translate('complete')
					: i18n.translate('abandon'),
			permission: 'UPDATE',
		},
	] as Action<TestrayTask>[]);

	return {
		actions: actionsRef.current,
		forceRefetch,
		modal,
	};
};

export default useTestflowActions;
