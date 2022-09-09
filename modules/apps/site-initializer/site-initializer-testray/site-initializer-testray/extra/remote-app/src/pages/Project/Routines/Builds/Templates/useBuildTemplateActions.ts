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

import useFormModal from '../../../../../hooks/useFormModal';
import i18n from '../../../../../i18n';
import {TestrayBuild} from '../../../../../services/rest';
import {Action} from '../../../../../types';

const useBuildTemplateActions = () => {
	const formModal = useFormModal();

	const actionsRef = useRef([
		{
			action: () => {
				alert('activated');
			},
			icon: 'logout',
			name: () => i18n.translate('activate'),
			permission: 'UPDATE',
		},
		{
			action: () => {
				alert('deleted');
			},
			icon: 'trash',
			name: i18n.translate('delete'),
			permission: 'DELETE',
		},
	] as Action<TestrayBuild>[]);

	return {
		actions: actionsRef.current,
		formModal,
	};
};

export default useBuildTemplateActions;
