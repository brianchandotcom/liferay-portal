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

import {useNavigate} from 'react-router-dom';

import {TestrayBuild} from '../../../../graphql/queries';
import useFormModal from '../../../../hooks/useFormModal';
import useMutate from '../../../../hooks/useMutate';
import i18n from '../../../../i18n';
import {Security} from '../../../../security';
import {deleteResource} from '../../../../services/rest';
import {updateBuild} from '../../../../services/rest/TestrayBuild';
import {Action} from '../../../../types';

const useBuildActions = () => {
	const formModal = useFormModal();
	const {removeItemFromList, updateItemFromList} = useMutate();
	const navigate = useNavigate();

	const modal = formModal.modal;

	const actions: Action[] = [
		{
			action: () => alert('Archive'),
			name: i18n.translate('archive'),
		},
		{
			action: (testrayBuild: TestrayBuild) =>
				navigate(`build/${testrayBuild.id}/update`),
			name: i18n.translate('edit'),
			permission: 'UPDATE',
		},
		{
			action: ({id, promoted}: TestrayBuild, mutate) =>
				updateBuild(id, {
					promoted: !promoted,
				})
					.then(() =>
						updateItemFromList(mutate, id, {
							promoted: !promoted,
						})
					)
					.then(modal.onSuccess),
			name: i18n.translate('promote'),
			permission: 'UPDATE',
		},
		{
			action: ({id}: TestrayBuild, mutate) =>
				deleteResource(`/builds/${id}`)
					.then(() => removeItemFromList(mutate, id))
					.then(modal.onSave)
					.catch(modal.onError),
			name: i18n.translate('delete'),
			permission: 'DELETE',
		},
	];

	return {
		actions: (row: any) => Security.filterActions(actions, row.actions),
		formModal,
	};
};

export default useBuildActions;
