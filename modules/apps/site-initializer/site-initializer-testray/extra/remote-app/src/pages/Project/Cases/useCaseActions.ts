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

import useFormModal from '../../../hooks/useFormModal';
import useMutate from '../../../hooks/useMutate';
import i18n from '../../../i18n';
import {Security} from '../../../security';
import {TestrayCase, deleteResource} from '../../../services/rest';
import {Action} from '../../../types';

const useCaseActions = () => {
	const {removeItemFromList} = useMutate();
	const navigate = useNavigate();
	const formModal = useFormModal();
	const modal = formModal.modal;

	const actions: Action[] = [
		{
			action: ({id}: TestrayCase) => navigate(`${id}/update`),
			name: i18n.translate('edit'),
			permission: 'UPDATE',
		},
		{
			action: () => alert('Link'),
			name: i18n.translate('link-requirements'),
		},
		{
			action: ({id}: TestrayCase, mutate) =>
				deleteResource(`/cases/${id}`)
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

export default useCaseActions;
