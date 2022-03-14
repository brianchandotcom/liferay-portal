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
import {ReactElement, useContext} from 'react';

import i18n from '../../i18n';
import {LIFERAY_URLS} from '../../services/liferay/liferay';
import CaseTypeModal from '../Modal/CaseTypeModal';
import CategoryModal from '../Modal/CategoryModal';
import OptionsModal from '../Modal/OptionsModal';

const useSidebarActions = () => {
	const [state, dispatch] = useContext(Context);

	const onOpenModal = (title: string, body: ReactElement, size: Size) => {
		dispatch({
			payload: {
				body,
				footer: [
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
				],
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
					label: i18n.translate('new-project'),
					onClick: () =>
						onOpenModal(i18n.translate('new-project'), <></>, 'lg'),
					path: '/',
				},
				{
					icon: 'cog',
					label: i18n.translate('case-types'),
					onClick: () =>
						onOpenModal(
							i18n.translate('case-types'),
							<CaseTypeModal />,
							'full-screen'
						),
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
						onOpenModal(
							i18n.translate('categories'),
							<CategoryModal />,
							'full-screen'
						),
					path: '/',
				},
				{
					icon: 'cog',
					label: i18n.translate('options'),
					onClick: () =>
						onOpenModal(
							i18n.translate('options'),
							<OptionsModal />,
							'full-screen'
						),
					path: '/',
				},
			],
			title: i18n.translate('environment-factors'),
		},
		{
			items: [
				{
					icon: 'plus',
					label: i18n.translate('add-case'),
					path: '/manage/addcase',
				},
				{
					icon: 'plus',
					label: i18n.translate('add-requirement'),
					path: '/manage/requirements',
				},
			],
			title: '',
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
