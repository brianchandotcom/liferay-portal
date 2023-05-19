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

import useFormActions from '../../../../../../hooks/useFormActions';
import useMutate from '../../../../../../hooks/useMutate';
import i18n from '../../../../../../i18n';
import {
	TestrayCaseResult,
	deleteResource,
} from '../../../../../../services/rest';
import {Action, ActionsHookParameter} from '../../../../../../types';

const useCaseResultActions = (
	{isHeaderActions}: ActionsHookParameter = {isHeaderActions: true}
) => {
	const {form} = useFormActions();
	const {removeItemFromList} = useMutate();
	const navigate = useNavigate();
	const actionsRef = useRef([
		{
			action: ({id}, mutate) => {
				deleteResource(`/caseresults/${id}`)
					?.then(() => removeItemFromList(mutate, id))
					.then(form.onSave)
					.catch(form.onError);
				isHeaderActions && navigate('../');
			},
			icon: 'trash',
			name: i18n.translate(
				isHeaderActions ? 'delete-case-result' : 'delete'
			),
			permission: 'DELETE',
		},
	] as Action<TestrayCaseResult>[]);

	return {
		actions: actionsRef.current,
	};
};

export default useCaseResultActions;
