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

import {useMutation} from '@apollo/client';
import {useNavigate} from 'react-router-dom';

import {DeleteCase} from '../../../graphql/mutations';
import {TestrayCase} from '../../../graphql/queries';
import useFormModal from '../../../hooks/useFormModal';
import i18n from '../../../i18n';

const useCaseActions = () => {
	const [onDeleteCase] = useMutation(DeleteCase);

	const navigate = useNavigate();
	const formModal = useFormModal();
	const modal = formModal.modal;

	return {
		actions: [
			{
				action: ({id}: TestrayCase) => navigate(`${id}/update`),

				name: i18n.translate('edit'),
			},
			{
				action: () => alert('Link'),
				name: i18n.translate('link-requirements'),
			},
			{
				action: ({id}: TestrayCase) =>
					onDeleteCase({variables: {id}})
						.then(() => modal.onSave())
						.catch(modal.onError),
				name: i18n.translate('delete'),
			},
		],
		formModal,
	};
};

export default useCaseActions;
