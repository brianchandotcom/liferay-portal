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
import {useState} from 'react';

import {
	CreateRequirementCaseBatch,
	DeleteRequirementCase,
} from '../../../graphql/mutations';
import {
	TestrayRequirement,
	TestrayRequirementCase,
} from '../../../graphql/queries';
import useFormModal from '../../../hooks/useFormModal';
import i18n from '../../../i18n';
import {Action} from '../../../types';
import {State} from './RequirementCaseLinkModal';

const useRequirementCaseActions = (testrayRequirement: TestrayRequirement) => {
	const [onDeleteRequirementCase] = useMutation(DeleteRequirementCase);
	const [onCreateRequirementCase] = useMutation(CreateRequirementCaseBatch);
	const [forceRefetch, setForceRefetch] = useState(0);

	const {forceRefetch: modalForceRefetch, modal} = useFormModal({
		onSave: (cases: State) => {
			if (cases.length) {
				onCreateRequirementCase({
					variables: {
						data: cases.map((caseId) => ({
							caseId,
							requirementId: testrayRequirement.id,
						})),
					},
				})
					.then(() => {
						setTimeout(() => {
							setForceRefetch(new Date().getTime());
						}, 1000);
					})
					.catch(console.error);
			}
		},
	});

	const actions: Action[] = [
		{
			action: ({id}: TestrayRequirementCase) =>
				onDeleteRequirementCase({variables: {id}})
					.then(() => modal.onSave())
					.catch(modal.onError),
			name: i18n.translate('delete'),
		},
	];

	return {
		actions,
		formModal: {
			...modal,
			forceRefetch: modalForceRefetch || forceRefetch,
		},
	};
};

export default useRequirementCaseActions;
