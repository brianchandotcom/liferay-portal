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
import useMutate from '../../../hooks/useMutate';
import i18n from '../../../i18n';
import {TestrayRoutine, deleteResource} from '../../../services/rest';
import {Action, ActionsHookParameter} from '../../../types';

const useRoutineActions = ({isHeaderActions}: ActionsHookParameter = {}) => {
	const {form} = useFormActions();
	const navigate = useNavigate();
	const {removeItemFromList} = useMutate();

	const actionsRef = useRef([
		{
			action: (routine) =>
				navigate(isHeaderActions ? 'update' : `${routine.id}/update`),
			icon: 'pencil',
			name: i18n.translate(isHeaderActions ? 'edit-routine' : 'edit'),
			permission: 'UPDATE',
		},
		{
			action: () => alert('Select Default Environment Factors'),
			icon: 'display',
			name: i18n.translate('select-default-environment-factors'),
		},
		{
			action: ({id}, mutate) =>
				deleteResource(`/routines/${id}`)
					?.then(() => removeItemFromList(mutate, id))
					.then(form.onSuccess)
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
