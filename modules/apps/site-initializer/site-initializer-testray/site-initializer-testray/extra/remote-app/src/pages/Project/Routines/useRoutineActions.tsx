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

import useFormActions from '../../../hooks/useFormActions';
import useModalContext from '../../../hooks/useModalContext';
import useMutate from '../../../hooks/useMutate';
import i18n from '../../../i18n';
import {TestrayRoutine, deleteResource} from '../../../services/rest';
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
			action: ({id}, mutate) => {
				deleteResource(`/routines/${id}`)
					?.then(() => removeItemFromList(mutate, id))
					.then(form.onSuccess)
					.catch(form.onError);
				isHeaderActions && navigate('../');
			},
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
