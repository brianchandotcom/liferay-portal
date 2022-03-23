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

import ClayButton from '@clayui/button';
import {Context} from '@clayui/modal';
import {Size} from '@clayui/modal/lib/types';
import React, {ReactElement, useContext} from 'react';

import useFormModal from '../../hooks/useFormModal';
import i18n from '../../i18n';
import ProjectModal from '../../pages/Project';
import CaseTypeModal from '../../pages/Standalone/CaseType/CaseTypeModal';
import FactorCategoryModal from '../../pages/Standalone/FactorCategory/FactorCategoryModal';
import OptionsModal from '../../pages/Standalone/FactorOptions/FactorOptionsModal';
import {LIFERAY_URLS} from '../../services/liferay/liferay';

interface ModalOptions {
	body: ReactElement;
	footer?: ReactElement;
	size: Size;
	title: string;
}

const useSidebarActions = () => {
	const {modal} = useFormModal();
	const [state, dispatch] = useContext(Context);

	const onOpenModal = ({body, footer, size, title}: ModalOptions) => {
		dispatch({
			payload: {
				body,
				footer: footer
					? [
							undefined,
							undefined,
							<ClayButton.Group key={3} spaced>
								<ClayButton
									displayType="secondary"
									onClick={state.onClose}
								>
									Cancel
								</ClayButton>

								<ClayButton key={4} onClick={state.onClose}>
									Save
								</ClayButton>
							</ClayButton.Group>,
					  ]
					: [],
				header: title,
				size,
			},
			type: 1,
		});
	};

	const MANAGE_DROPDOWN = [
		{
			items: [
				{
					icon: 'plus',
					label: i18n.translate('add-project'),
					onClick: () => {
						modal.setVisible(true);

						onOpenModal({
							body: (
								<ProjectModal PageContainer={React.Fragment} />
							),
							size: 'full-screen',
							title: i18n.translate('projects'),
						});
					},
					path: '/',
				},
				{
					icon: 'cog',
					label: i18n.translate('case-types'),
					onClick: () =>
						onOpenModal({
							body: <CaseTypeModal />,
							size: 'full-screen',
							title: i18n.translate('case-types'),
						}),
					path: '/',
				},
			],
			title: i18n.translate('system'),
		},
		{
			items: [
				{
					icon: 'cog',
					label: i18n.translate('categories'),
					onClick: () =>
						onOpenModal({
							body: <FactorCategoryModal />,
							size: 'full-screen',
							title: i18n.translate('categories'),
						}),
					path: '/',
				},
				{
					icon: 'cog',
					label: i18n.translate('options'),
					onClick: () =>
						onOpenModal({
							body: <OptionsModal />,
							size: 'full-screen',
							title: i18n.translate('options'),
						}),
					path: '/',
				},
			],
			title: i18n.translate('environment-factors'),
		},
		{
			items: [
				{
					icon: 'pencil',
					label: i18n.translate('manage-users'),
					path: '/manage/userlist',
				},
				{
					icon: 'pencil',
					label: i18n.translate('manage-user-groups'),
					path: LIFERAY_URLS.manage_user_groups,
				},
				{
					icon: 'pencil',
					label: i18n.translate('manage-roles'),
					path: LIFERAY_URLS.manage_roles,
				},
			],
			title: '',
		},
		{
			items: [
				{
					icon: 'filter',
					label: i18n.translate('manage-indexers'),
					path: '/',
				},
				{
					icon: 'pencil',
					label: i18n.translate('manage-server'),
					path: LIFERAY_URLS.manage_server,
				},
			],
			title: '',
		},
	];

	return MANAGE_DROPDOWN;
};

export default useSidebarActions;
