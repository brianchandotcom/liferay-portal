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

import {TestrayProject} from '../../graphql/queries';
import useFormModal from '../../hooks/useFormModal';
import useMutate from '../../hooks/useMutate';
import i18n from '../../i18n';
import {Security} from '../../security';
import {deleteResource} from '../../services/rest';
import {Action} from '../../types';

const useProjectActions = () => {
	const formModal = useFormModal();
	const {removeItemFromList} = useMutate();
	const modal = formModal.modal;

	const actions: Action[] = [
		{
			action: (item: TestrayProject) => modal.open(item),
			name: i18n.translate('edit'),
			permission: 'UPDATE',
		},
		{
			action: ({id}: TestrayProject, mutate) =>
				deleteResource(`/projects/${id}`)
					.then(() => removeItemFromList(mutate, id))
					.then(modal.onSuccess)
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

export default useProjectActions;
