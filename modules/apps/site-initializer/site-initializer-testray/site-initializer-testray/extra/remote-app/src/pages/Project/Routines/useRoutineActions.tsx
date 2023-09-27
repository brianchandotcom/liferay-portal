/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useRef} from 'react';
import {useNavigate} from 'react-router-dom';

import useFormActions from '../../../hooks/useFormActions';
import useModalContext from '../../../hooks/useModalContext';
import useMutate from '../../../hooks/useMutate';
import i18n from '../../../i18n';
import {TestrayRoutine, testrayRoutineImpl} from '../../../services/rest';
import {Action, ActionsHookParameter} from '../../../types';
import EnvironmentFactorsModal from '../../Standalone/EnvironmentFactors/EnviromentFactorsModal';

const useRoutineActions = ({isHeaderActions}: ActionsHookParameter = {}) => {
	const {form} = useFormActions();
	const navigate = useNavigate();
	const {removeItemFromList} = useMutate();
	const {onOpenModal, state} = useModalContext();

	const actionsRef = useRef([
		{
			action: (routine) =>
				navigate(isHeaderActions ? 'update' : `${routine.id}/update`),
			icon: 'pencil',
			name: i18n.translate(isHeaderActions ? 'edit-routine' : 'edit'),
			permission: 'UPDATE',
		},
		{
			action: (routine) =>
				navigate(
					isHeaderActions ? 'templates' : `${routine.id}/templates`
				),
			icon: 'cog',
			name: i18n.translate('manage-templates'),
			permission: 'UPDATE',
		},
		{
			action: (routine) =>
				onOpenModal({
					body: (
						<EnvironmentFactorsModal
							onCloseModal={state.onClose}
							routineId={routine.id}
						/>
					),
					footer: <div id="environment-factor-modal-footer"></div>,
					footerDefault: false,
					size: 'full-screen',
					title: i18n.translate('select-default-environment-factors'),
				}),

			icon: 'display',
			name: i18n.translate('select-default-environment-factors'),
			permission: 'UPDATE',
		},
		{
			action: ({id}, mutate) =>
				testrayRoutineImpl
					.removeResource(id)
					?.then(() => removeItemFromList(mutate, id))
					.then(form.onSuccess)
					.then(() => {
						if (isHeaderActions) {
							navigate('../');
						}
					})
					.catch(form.onError),
			icon: 'trash',
			name: i18n.translate(isHeaderActions ? 'delete-routine' : 'delete'),
			permission: 'DELETE',
		},
	] as Action<TestrayRoutine>[]);

	return {
		actions: actionsRef.current,
		navigate,
	};
};

export default useRoutineActions;
