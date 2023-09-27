/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useRef} from 'react';

import useFormActions from '../../../../hooks/useFormActions';
import useModalContext from '../../../../hooks/useModalContext';
import useMutate from '../../../../hooks/useMutate';
import i18n from '../../../../i18n';
import {Liferay} from '../../../../services/liferay';
import {
	TestrayCaseResult,
	testrayCaseResultImpl,
} from '../../../../services/rest';
import {Action} from '../../../../types';
import {UserListView} from '../../../Manage/User';

const useBuildTestActions = () => {
	const {form} = useFormActions();
	const {removeItemFromList, updateItemFromList} = useMutate();
	const {onOpenModal, state} = useModalContext();

	const actionsRef = useRef([
		{
			action: (caseResult, mutate) =>
				onOpenModal({
					body: (
						<UserListView
							listViewProps={{
								managementToolbarProps: {
									display: {columns: false},
								},
							}}
							tableProps={{
								onClickRow: (user) =>
									testrayCaseResultImpl
										.assignTo(caseResult, user.id)
										.then(() =>
											updateItemFromList(
												mutate,
												caseResult.id,
												{user},
												{revalidate: true}
											)
										)
										.then(form.onSuccess)
										.catch(form.onError)
										.finally(state.onClose),
							}}
						/>
					),
					size: 'full-screen',
					title: i18n.translate('users'),
				}),
			icon: 'user',
			name: i18n.translate('assign'),
			permission: 'UPDATE',
		},
		{
			action: (caseResult, mutate) => {
				(caseResult.user &&
				caseResult.user.id.toString() ===
					Liferay.ThemeDisplay.getUserId()
					? testrayCaseResultImpl.removeAssign(caseResult)
					: testrayCaseResultImpl.assignToMe(caseResult)
				).then((user) =>
					updateItemFromList(
						mutate,
						caseResult.id,
						{user},
						{revalidate: true}
					)
						.then(form.onSuccess)
						.catch(form.onError)
				);
			},
			icon: 'user',
			name: (caseResult) =>
				i18n.translate(
					caseResult.user &&
						caseResult.user.id.toString() ===
							Liferay.ThemeDisplay.getUserId()
						? 'unassign-myself'
						: 'assign-to-me'
				),
			permission: 'UPDATE',
		},
		{
			action: ({id}, mutate) =>
				testrayCaseResultImpl
					.removeResource(id)
					?.then(() => removeItemFromList(mutate, id))
					.then(form.onSuccess)
					.catch(form.onError),
			icon: 'trash',
			name: i18n.translate('delete'),
			permission: 'DELETE',
		},
	] as Action<TestrayCaseResult>[]);

	return {
		actions: actionsRef.current,
		form,
	};
};

export default useBuildTestActions;
