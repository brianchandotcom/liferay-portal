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

import useFormActions from '../../hooks/useFormActions';
import useModalContext from '../../hooks/useModalContext';
import useMutate from '../../hooks/useMutate';
import i18n from '../../i18n';
import {TestrayProject, testrayProjectImpl} from '../../services/rest';
import {Action, ActionsHookParameter} from '../../types';
import ComponentsModal from '../Standalone/Components/ComponentsModal';
import ProductVersionModal from '../Standalone/ProductVersions/ProductVersionModal';
import TeamsModal from '../Standalone/Teams/TeamsModal';

const useProjectActions = ({isHeaderActions}: ActionsHookParameter = {}) => {
	const {form} = useFormActions();
	const navigate = useNavigate();
	const {removeItemFromList} = useMutate();
	const {onOpenModal} = useModalContext();

	const actionsRef = useRef([
		{
			action: (project) => navigate(`/project/${project.id}/update`),
			icon: 'pencil',
			name: i18n.translate(isHeaderActions ? 'edit-project' : 'edit'),
			permission: 'UPDATE',
		},
		{
			action: (project) =>
				onOpenModal({
					body: <ComponentsModal projectId={project.id} />,
					size: 'full-screen',
					title: `${i18n.translate('components')} - ${project.name}`,
				}),
			icon: 'order-form-cog',
			name: i18n.translate('manage-components'),
		},
		{
			action: (project) =>
				onOpenModal({
					body: <TeamsModal projectId={project.id} />,
					size: 'full-screen',
					title: `${i18n.translate('teams')} - ${project.name}`,
				}),
			icon: 'community',
			name: i18n.translate('manage-teams'),
		},
		{
			action: (project) =>
				onOpenModal({
					body: <ProductVersionModal projectId={project.id} />,
					size: 'full-screen',
					title: `${i18n.translate('product-version')} - ${
						project.name
					}`,
				}),
			icon: 'cog',
			name: i18n.translate('manage-product-versions'),
		},

		{
			action: ({id}, mutate) =>
				testrayProjectImpl
					.remove(id)
					?.then(() => removeItemFromList(mutate, id))
					.then(form.onSuccess)
					.catch(form.onError),
			icon: 'trash',
			name: i18n.translate(isHeaderActions ? 'delete-project' : 'delete'),
			permission: 'DELETE',
		},
		{
			action: ({id}) => navigate(`/project/${id}/cases/export`),
			icon: 'print',
			name: i18n.translate('export-cases'),
		},
	] as Action<TestrayProject>[]);

	return {
		actions: actionsRef.current,
		navigate,
	};
};

export default useProjectActions;
