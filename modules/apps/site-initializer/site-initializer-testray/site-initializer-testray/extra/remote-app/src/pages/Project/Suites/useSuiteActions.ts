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
import {TestraySuite, deleteResource} from '../../../services/rest';
import {Action, ActionsHookParameter} from '../../../types';

const useSuiteActions = ({isHeaderActions}: ActionsHookParameter = {}) => {
	const {form} = useFormActions();
	const navigate = useNavigate();
	const {removeItemFromList} = useMutate();

	const actionsRef = useRef([
		{
			action: (suite) =>
				navigate(isHeaderActions ? 'update' : `${suite.id}/update`),
			icon: 'pencil',
			name: isHeaderActions
				? i18n.sub('edit-x', 'suite')
				: i18n.translate('edit'),
			permission: 'UPDATE',
		},
		{
			action: ({id}, mutate) => {
				deleteResource(`/suites/${id}`)
					?.then(() => removeItemFromList(mutate, id))
					.then(form.onSuccess)
					.catch(form.onError);
				isHeaderActions && navigate('../');
			},
			icon: 'trash',
			name: isHeaderActions
				? i18n.sub('delete-x', 'suite')
				: i18n.translate('delete'),
			permission: 'DELETE',
		},
	] as Action<TestraySuite>[]);

	return {
		actions: actionsRef.current,
		navigate,
	};
};

export default useSuiteActions;
