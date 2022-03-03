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

import {Align} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {Context} from '@clayui/modal';
import {ReactElement, useContext} from 'react';

import i18n from '../../i18n';
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
	const [, dispatch] = useContext(Context);

	const onOpenModal = (title: string, body: ReactElement) => {
		dispatch({
			payload: {
				body,
				footer: [],
				header: title,
				size: 'lg',
			},
			type: 1,
		});
	};

	const MANAGE_DROPDOWN = [
		{
			items: [
				{icon: 'plus', label: i18n.translate('new-project'), path: '/'},
				{
					icon: 'cog',
					label: 'Case Types',
					onClick: () =>
						onOpenModal(
							i18n.translate('case-types'),
							<CaseTypeModal />
						),
					path: '/',
				},
			],
			title: 'System',
		},
		{
			items: [
				{
					icon: 'cog',
					label: 'Categories',
					onClick: () =>
						onOpenModal(
							i18n.translate('categories'),
							<CategoryModal />
						),
					path: '/',
				},
				{
					icon: 'cog',
					label: 'Options',
					onClick: () =>
						onOpenModal(
							i18n.translate('options'),
							<OptionsModal />
						),
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
					path: '/',
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
