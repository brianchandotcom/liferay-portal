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

import {UserAccount} from '../../../graphql/queries';
import useFormModal from '../../../hooks/useFormModal';
import i18n from '../../../i18n';
import {Security} from '../../../security';
import {Action} from '../../../types';

const useUserActions = () => {
	const navigate = useNavigate();
	const formModal = useFormModal();

	const actions: Action[] = [
		{
			action: (userAccount: UserAccount) => {
				navigate(`${userAccount?.id}/update`);
			},
			name: i18n.translate('edit'),
		},
	];

	return {
		actions: (row: any) => Security.filterActions(actions, row.actions),
		formModal,
	};
};

export default useUserActions;
