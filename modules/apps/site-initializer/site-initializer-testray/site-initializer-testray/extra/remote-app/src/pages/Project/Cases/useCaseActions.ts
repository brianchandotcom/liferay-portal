/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useRef} from 'react';
import {useNavigate} from 'react-router-dom';

import useFormActions from '../../../hooks/useFormActions';
import useMutate from '../../../hooks/useMutate';
import i18n from '../../../i18n';
import {TestrayCase, testrayCaseImpl} from '../../../services/rest';
import {Action, ActionsHookParameter} from '../../../types';

const useCaseActions = ({isHeaderActions}: ActionsHookParameter = {}) => {
	const {form} = useFormActions();
	const {removeItemFromList} = useMutate();
	const navigate = useNavigate();

	const actionsRef = useRef([
		{
			action: ({id}) =>
				navigate(isHeaderActions ? 'update' : `${id}/update`),
			icon: 'pencil',
			name: i18n.translate(isHeaderActions ? 'edit-case' : 'edit'),
			permission: 'UPDATE',
		},
		{
			action: ({id}) =>
				navigate(
					isHeaderActions ? 'requirements' : `${id}/requirements`
				),
			icon: 'shortcut',
			name: i18n.translate('link-requirements'),
		},
		{
			action: ({id}, mutate) =>
				testrayCaseImpl
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
			name: i18n.translate(isHeaderActions ? 'delete-case' : 'delete'),
			permission: 'DELETE',
		},
	] as Action<TestrayCase>[]);

	return {
		actions: actionsRef.current,
		navigate,
	};
};

export default useCaseActions;
