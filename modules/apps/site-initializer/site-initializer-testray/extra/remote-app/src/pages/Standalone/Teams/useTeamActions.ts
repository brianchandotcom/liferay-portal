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

import useFormModal from '../../../hooks/useFormModal';
import useMutate from '../../../hooks/useMutate';
import i18n from '../../../i18n';
import {
	TestrayTeam,
	deleteResource,
	getTeamsComponentsQuery,
} from '../../../services/rest';
import {Action} from '../../../types';

const useTeamActions = () => {
	const {removeItemFromList} = useMutate();
	const formModal = useFormModal();
	const modal = formModal.modal;

	const actions: Action<TestrayTeam>[] = [
		{
			action: (team) => modal.open(team),
			icon: 'pencil',
			name: i18n.translate('edit'),
			permission: 'UPDATE',
		},
		{
			action: ({id}, mutate) => {
				getTeamsComponentsQuery(id)
					.then((response) => {
						if (response?.items?.length) {
							throw new Error(
								i18n.translate(
									'the-team-cannot-be-deleted-because-it-has-associated-components'
								)
							);
						}
						deleteResource(`/teams/${id}`)
							?.then(() => removeItemFromList(mutate, id))
							.then(modal.onSave)
							.catch(modal.onError);
					})
					.catch(modal.onError);
			},
			icon: 'trash',
			name: i18n.translate('delete'),
			permission: 'DELETE',
		},
	];

	return {
		actions,
		formModal,
	};
};

export default useTeamActions;
