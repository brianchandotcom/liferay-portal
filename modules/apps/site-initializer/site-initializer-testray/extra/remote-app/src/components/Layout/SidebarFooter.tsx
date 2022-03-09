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
import {Align} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {Context} from '@clayui/modal';
import {ReactElement, useContext} from 'react';

import i18n from '../../i18n';
import NewProject from '../../pages/Project/NewProject';
import {Liferay} from '../../services/liferay/liferay';
import {Avatar} from '../Avatar';
import DropDown from '../DropDown';
import CaseTypeModal from '../Modal/CaseTypeModal';
import CategoryModal from '../Modal/CategoryModal';
import OptionsModal from '../Modal/OptionsModal';

const USER_DROPDOWN = [
	{
		items: [
			{
				icon: 'user',
				label: i18n.translate('manage-account'),
				path: '/manage/user',
			},
			{
				icon: 'logout',
				label: i18n.translate('sign-out'),
				path: `${window.location.origin}/c/portal/logout`,
			},
		],
		title: '',
	},
];

const SidebarFooter = () => {
	const [state, dispatch] = useContext(Context);

	const onOpenModal = (title: string, body: ReactElement, size: any) => {
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
						onOpenModal(
							i18n.translate('new-project'),
							<NewProject onClose={() => state.onClose()} />,
							'xl'
						),
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
					path: '/',
				},
				{
					icon: 'pencil',
					label: i18n.translate('manage-roles'),
					path: '/',
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
					path: '/',
				},
			],
			title: '',
		},
	];

	return (
		<div className="testray-sidebar-footer">
			<div className="divider divider-full" />

			<DropDown
				items={MANAGE_DROPDOWN}
				position={Align.RightBottom}
				trigger={
					<div className="align-items-center d-flex testray-sidebar-item">
						<ClayIcon fontSize={16} symbol="cog" />

						<span className="ml-1 testray-sidebar-text">
							{i18n.translate('manage')}
						</span>
					</div>
				}
			/>

			<DropDown
				items={USER_DROPDOWN}
				position={Align.RightBottom}
				trigger={
					<div className="testray-sidebar-item">
						<Avatar
							displayName
							name={Liferay.ThemeDisplay.getUserName()}
							url="https://clayui.com/images/long_user_image.png"
						/>
					</div>
				}
			/>
		</div>
	);
};

export default SidebarFooter;
