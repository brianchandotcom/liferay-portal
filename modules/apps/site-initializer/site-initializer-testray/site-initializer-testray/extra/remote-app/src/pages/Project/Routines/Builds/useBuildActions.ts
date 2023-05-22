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

import useFormModal from '../../../../hooks/useFormModal';
import useMutate from '../../../../hooks/useMutate';
import i18n from '../../../../i18n';
import {
	TestrayBuild,
	deleteResource,
	testrayBuildImpl,
} from '../../../../services/rest';
import {Action, ActionsHookParameter} from '../../../../types';

const useBuildActions = ({isHeaderActions}: ActionsHookParameter = {}) => {
	const formModal = useFormModal();
	const {removeItemFromList, updateItemFromList} = useMutate();
	const navigate = useNavigate();

	const modal = formModal.modal;

	const actionsRef = useRef([
		{
			action: () => alert('Archive'),
			icon: 'download',
			name: i18n.translate('export-csv'),
		},
		{
			action: (testrayBuild) =>
				navigate(
					isHeaderActions
						? 'update'
						: `build/${testrayBuild.id}/update`
				),
			icon: 'pencil',
			name: i18n.translate(isHeaderActions ? 'edit-build' : 'edit'),
			permission: 'UPDATE',
		},
		{
			action: ({id, promoted}, mutate) => {
				testrayBuildImpl
					.update(id, {
						promoted: !promoted,
					})
					.then(() => {
						if (isHeaderActions) {
							return mutate((prevData: any) => ({
								...prevData,
								promoted: !prevData?.promoted,
							}));
						}

						updateItemFromList(mutate, id, {
							promoted: !promoted,
						});
					})
					.then(modal.onSuccess);
			},
			icon: 'star',
			name: (build) =>
				i18n.translate(build?.promoted ? 'demote' : 'promote'),
			permission: 'UPDATE',
		},
		{
			action: () => alert('Archive'),
			icon: 'archive',
			name: i18n.translate('archive'),
			permission: 'UPDATE',
		},
		{
			action: ({id}, mutate) =>
				deleteResource(`/builds/${id}`)
					?.then(() => removeItemFromList(mutate, id))
					.then(modal.onSave)
					.then(() => {
						if (isHeaderActions) {
							navigate('../');
						}
					})
					.catch(modal.onError),
			icon: 'trash',
			name: i18n.translate(isHeaderActions ? 'delete-build' : 'delete'),
			permission: 'DELETE',
		},
	] as Action<TestrayBuild>[]);

	return {
		actions: actionsRef.current,
		formModal,
	};
};

export default useBuildActions;
