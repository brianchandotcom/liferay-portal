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

import {useState} from 'react';

import useFormModal from '../../../hooks/useFormModal';
import useMutate from '../../../hooks/useMutate';
import i18n from '../../../i18n';
import {
	TestrayRequirementCase,
	createRequirementCaseBatch,
	deleteRequirementCaseBatch,
	deleteResource,
} from '../../../services/rest';
import {Action} from '../../../types';
import {State} from './CaseRequirementLinkModal';

type UseCaseRequirementActions = {
	caseId?: number;
	requirementId?: number;
};

const useCaseRequirementActions = ({
	caseId,
	requirementId,
}: UseCaseRequirementActions = {}) => {
	const [forceRefetch, setForceRefetch] = useState(0);
	const {removeItemFromList} = useMutate();

	const {forceRefetch: modalForceRefetch, modal} = useFormModal({
		onSave: ({items, state}: {items: number; state: State}) => {
			if (state.length) {
				createRequirementCaseBatch(
					state.map(
						(requirementCase) =>
							({
								caseId,
								requirementId,
								...requirementCase,
							} as any)
					)
				)
					.then(() => {
						deleteRequirementCaseBatch(items)
							.then(() => {
								setTimeout(() => {
									setForceRefetch(new Date().getTime());
								}, 100);
							})
							.catch(console.error);
					})
					.catch(console.error);
			}

			return deleteRequirementCaseBatch(items)
				.then(() => {
					setTimeout(() => {
						setForceRefetch(new Date().getTime());
					}, 100);
				})
				.catch(console.error);
		},
	});

	const actions: Action<TestrayRequirementCase>[] = [
		{
			action: ({id}, mutate) =>
				deleteResource(`/requirementscaseses/${id}`)
					?.then(() => removeItemFromList(mutate, id))
					.then(() => modal.onSave())
					.catch(modal.onError),
			icon: 'trash',
			name: i18n.translate('delete'),
		},
	];

	return {
		actions,
		formModal: {
			...modal,
			forceRefetch: forceRefetch || modalForceRefetch,
		},
	};
};

export default useCaseRequirementActions;
