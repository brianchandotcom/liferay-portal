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

import {DeleteTestrayCase} from '../../../graphql/mutations';
import {TestrayCase} from '../../../graphql/queries';
import useFormModal from '../../../hooks/useFormModal';
import i18n from '../../../i18n';

const useCaseActions = () => {
	const [onDeleteTestrayCase] = useMutation(DeleteTestrayCase);

	const formModal = useFormModal();
	const modal = formModal.modal;

	return {
		actions: [
			{
				action: () => modal.open(),
				name: i18n.translate('edit'),
			},
			{
				action: () => alert('Link'),
				name: i18n.translate('link-requirements'),
			},
			{
				action: ({id: testrayCaseId}: TestrayCase) =>
					onDeleteTestrayCase({variables: {testrayCaseId}})
						.then(() => modal.onSave())
						.catch(modal.onError),
				name: i18n.translate('delete'),
			},
		],
		formModal,
	};
};

export default useCaseActions;
