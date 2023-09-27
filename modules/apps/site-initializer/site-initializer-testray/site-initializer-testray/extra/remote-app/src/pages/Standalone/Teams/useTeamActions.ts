/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useFormModal from '../../../hooks/useFormModal';
import useMutate from '../../../hooks/useMutate';
import i18n from '../../../i18n';
import {TestrayTeam, testrayTeamImpl} from '../../../services/rest';
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
				testrayTeamImpl
					.removeResource(id)
					?.then(() => removeItemFromList(mutate, id), modal.onSave)
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
